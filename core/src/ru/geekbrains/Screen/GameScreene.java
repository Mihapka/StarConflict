package ru.geekbrains.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pull.BulletPool;
import ru.geekbrains.pull.EnemyPool;
import ru.geekbrains.sprite.*;
import ru.geekbrains.utils.EnemyEmitter;
import ru.geekbrains.pull.ExplosionPool;

import java.util.List;

public class GameScreene extends BaseScreen {

    private static final int STAR_COUNT = 64;

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
    private GameOver gameOver;
    private ButtonNewGame newGame;
    private Star[] stars;

    boolean gameOverState = false;

    @Override
    public void show() {

        super.show();
        imgBackgraund = new Texture("Texture/bg.png");
        atlas = new TextureAtlas(Gdx.files.internal("Texture/atlas/mainAtlas.tpack"));
        backgraund = new Backgraund(imgBackgraund);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        exploSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, exploSound);
        enemyPool = new EnemyPool(bulletPool, worldBounds, enemyBulletSound, explosionPool);
        enemyEmitter = new EnemyEmitter(atlas, worldBounds, enemyPool);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        setMusic();
        gameOver = new GameOver(atlas);
        gameOverState = false;
        newGame = new ButtonNewGame(atlas);
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        if (!gameOverState) {
            mainShip.update(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
            bulletPool.updateActiveSprites(delta);
        }
        explosionPool.updateActiveSprites(delta);
    }

    private void draw() {

        Gdx.gl.glClearColor(0.1f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        backgraund.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (!gameOverState) {
            mainShip.draw(batch);
            enemyPool.drawActiveSprites(batch);
            bulletPool.drawActiveSprites(batch);
        } else {
            gameOver.draw(batch);
            newGame.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {

        backgraund.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGame.resize(worldBounds);
    }

    private void setMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/PPK _resurrection.mp3"));
        music.setLooping(true);
        music.setVolume(0.02f);
        music.play();
    }

    private void checkCollisions() {
        if (!gameOverState) {
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
                    }
                }
            }
            if (mainShip.isDestroyed()) {
                gameOverState = true;
                music.stop();
            }
        }
    }

    private void freeAllDestroed() {

        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
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
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {

        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 tourch, int pointer, int button) {

        mainShip.touchDown(tourch, pointer, button);
        newGame.touchDown(tourch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 tourch, int pointer, int button) {

        mainShip.touchUp(tourch, pointer, button);
        newGame.touchUp(tourch, pointer, button);
        return false;
    }
}

