package ru.geekbrains.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Backgraund;
import ru.geekbrains.sprite.Ship;
import ru.geekbrains.sprite.Star;

public class GameScreene extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private Texture imgBackgraund;
    private TextureAtlas atlas;

    private Backgraund backgraund;
    private Ship ship;
    private TextureRegion region;

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
        region = new TextureRegion(new TextureAtlas(Gdx.files.internal("Texture/atlas/mainAtlas.tpack")).
                findRegion("main_ship"),
                0,0, (new TextureAtlas(Gdx.files.internal("Texture/atlas/mainAtlas.tpack")).
                findRegion("main_ship").getRegionWidth() / 2)
                ,new TextureAtlas(Gdx.files.internal("Texture/atlas/mainAtlas.tpack")).
                findRegion("main_ship").getRegionHeight());

        ship = new Ship(region);

    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {

        backgraund.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        ship.resize(worldBounds);
    }

    @Override
    public void dispose() {

        atlas.dispose();
        imgBackgraund.dispose();
        super.dispose();

    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        ship.update(delta);
    }

    private void draw() {

        Gdx.gl.glClearColor(0.1f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        backgraund.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        ship.draw(batch);
        batch.draw(region,0,0);
        batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {

        ship.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        ship.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 tourch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(Vector2 tourch, int pointer, int button) {
        return false;
    }
}
