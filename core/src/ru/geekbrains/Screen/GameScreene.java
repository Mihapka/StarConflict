package ru.geekbrains.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pull.BulletPull;
import ru.geekbrains.sprite.Backgraund;
import ru.geekbrains.sprite.EnemiShip;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;

public class GameScreene extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private Music music;

    private Texture imgBackgraund;
    private TextureAtlas atlas;

    private Backgraund backgraund;
    private MainShip mainShip;
    private EnemiShip enemiShip;

    private BulletPull bulletPull;

    private Star[] stars;

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
        bulletPull = new BulletPull();
        mainShip = new MainShip(atlas, bulletPull);
        enemiShip = new EnemiShip(atlas,bulletPull);
        setMusic();
//        region = new TextureRegion(new TextureAtlas(Gdx.files.internal("Texture/atlas/mainAtlas.tpack")).
//                findRegion("main_ship"),
//                0, 0, (new TextureAtlas(Gdx.files.internal("Texture/atlas/mainAtlas.tpack")).
//                findRegion("main_ship").getRegionWidth() / 2)
//                , new TextureAtlas(Gdx.files.internal("Texture/atlas/mainAtlas.tpack")).
//                findRegion("main_ship").getRegionHeight());

    }

    private void setMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/PPK _resurrection.mp3"));
        music.setLooping(true);
        music.setVolume(0.02f);
        music.play();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        mainShip.update(delta);
        enemiShip.update(delta);
        bulletPull.updateActiveSprites(delta);
    }

    private void freeAllDestroed() {

        bulletPull.freeAllDestroyedActiveSprites();
    }

    private void draw() {

        Gdx.gl.glClearColor(0.1f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        backgraund.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        enemiShip.draw(batch);
        bulletPull.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void render(float delta) {
        update(delta);
        freeAllDestroed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {

        backgraund.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        enemiShip.resize(worldBounds);
    }

    @Override
    public void dispose() {

        atlas.dispose();
        imgBackgraund.dispose();
        bulletPull.dispose();
        music.dispose();
        mainShip.dispose();
        enemiShip.dispose();
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
        return false;
    }

    @Override
    public boolean touchUp(Vector2 tourch, int pointer, int button) {

        mainShip.touchUp(tourch, pointer, button);
        return false;
    }
}

