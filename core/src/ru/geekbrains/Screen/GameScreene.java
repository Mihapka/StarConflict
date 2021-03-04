package ru.geekbrains.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pull.BulletPool;
import ru.geekbrains.pull.EnemyPool;
import ru.geekbrains.sprite.*;
import ru.geekbrains.utils.EnemyEmitter;
import ru.geekbrains.pull.ExplosionPool;
import ru.geekbrains.utils.Font;

import java.util.List;

public class GameScreene extends BaseScreen {

    private static final int STAR_COUNT = 64;
    private static final float FONT = 0.02f;
    private static final float PADDING = 0.01f;
    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private enum State {PLAYING, GAME_OVER}

    private Music music;
    private Sound enemyBulletSound;
    private Sound exploSound;

    private Texture imgBackgraund;
    private TextureAtlas atlas;

    private Backgraund backgraund;
    private MainShip mainShip;
    private EnemyPool enemyPool;
    private EnemyEmitter enemyEmitter;

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;

    private TrackingStar[] stars;

    private GameOver gameOver;
    private NewGame newGame;

    private State state;

    private Font font;

    private int frags;
    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;

    @Override
    public void show() {

        super.show();
        imgBackgraund = new Texture("Texture/bg.png");
        atlas = new TextureAtlas(Gdx.files.internal("Texture/atlas/mainAtlas.tpack"));
        backgraund = new Backgraund(imgBackgraund);
        bulletPool = new BulletPool();
        exploSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, exploSound);
        enemyPool = new EnemyPool(bulletPool, worldBounds, enemyBulletSound, explosionPool);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        gameOver = new GameOver(atlas);
        newGame = new NewGame(atlas, this);
        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(FONT);
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbLevel = new StringBuilder();
        stars = new TrackingStar[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new TrackingStar(atlas);
        }
        enemyEmitter = new EnemyEmitter(atlas, worldBounds, enemyPool);
        setMusic();
        state = State.PLAYING;
    }

    public void starNewGame() {

        state = State.PLAYING;
        frags = 0;

        mainShip.startNewGame();

        bulletPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
    }

    private void update(float delta) {
        for (TrackingStar star : stars) {
            star.update(delta, mainShip.getV());
        }
        if (state == State.PLAYING) {
            mainShip.update(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);
            bulletPool.updateActiveSprites(delta);
        }
        explosionPool.updateActiveSprites(delta);
    }

    private void draw() {

        Gdx.gl.glClearColor(0.1f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        backgraund.draw(batch);
        for (TrackingStar star : stars) {
            star.draw(batch);
        }
        if (state == State.PLAYING) {
            mainShip.draw(batch);
            enemyPool.drawActiveSprites(batch);
            bulletPool.drawActiveSprites(batch);
        } else {
            if (state == State.GAME_OVER) {
                gameOver.draw(batch);
                newGame.draw(batch);
            }
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    public void printInfo() {

        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);

        font.draw(batch, sbFrags
                        .append(FRAGS)
                        .append(frags),
                worldBounds.getLeft() + PADDING,
                worldBounds.getTop() - PADDING);

        font.draw(batch, sbHP
                        .append(HP)
                        .append(mainShip.getHp()),
                worldBounds.pos.x,
                worldBounds.getTop() - PADDING, Align.center);

        font.draw(batch, sbLevel
                        .append(LEVEL)
                        .append(enemyEmitter.getLevel()),
                worldBounds.getRight() - PADDING,
                worldBounds.getTop() - PADDING, Align.right);

    }

    private void setMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/PPK _resurrection.mp3"));
        music.setLooping(true);
        music.setVolume(0.02f);
        music.play();
    }

    private void checkCollisions() {
        if (state == State.GAME_OVER) {
            return;
        }
        /*список активных вражеских кораблей*/
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) {
                continue;
            }
            float minDist = enemyShip.getWidth() + (mainShip.getWidth() * 0.9f);
            if (enemyShip.pos.dst(mainShip.pos) < minDist) {
                enemyShip.destroy();
                mainShip.damage(enemyShip.getDamage());
            }
        }
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() != mainShip) {
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
                continue;
            }
            for (EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isDestroyed()) {
                    continue;
                }
                if (enemyShip.isBulletCollision(bullet)) {
                    enemyShip.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemyShip.isDestroyed()) {
                        frags++;
                    }
                }
            }
        }
        if (mainShip.isDestroyed()) {
            state = State.GAME_OVER;
            music.stop();
        }
    }

    private void freeAllDestroed() {

        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    @Override
    public void resize(Rect worldBounds) {

        backgraund.resize(worldBounds);
        for (TrackingStar star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGame.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        freeAllDestroed();
        draw();
    }

    @Override
    public void dispose() {

        atlas.dispose();
        imgBackgraund.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        music.dispose();
        enemyBulletSound.dispose();
        exploSound.dispose();
        mainShip.dispose();
        enemyPool.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {

        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            newGame.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            newGame.touchUp(touch, pointer, button);
        }
        return false;
    }
}

