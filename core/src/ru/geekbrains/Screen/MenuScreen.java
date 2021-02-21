package ru.geekbrains.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.*;

public class MenuScreen extends BaseScreen {

    private static final int STAR_COUNT = 256;

//    private Texture img;
//    private Logo logo;

    private Texture imgbackgraund;
    private Backgraund backgraund;
    private TextureAtlas atlas;

    private Star[] stars;

    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;


    private final Game game;

    public MenuScreen(Game game) {

        this.game = game;
    }

    @Override
    public void show() {

//        img = new Texture("Texture/pikachu.jpg");
//        logo = new Logo(img);

        super.show();
        imgbackgraund = new Texture("Texture/bg.png");
        backgraund = new Backgraund(imgbackgraund);
        atlas = new TextureAtlas(Gdx.files.internal("Texture/atlas/menuAtlas.atlas"));
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
    }

    @Override
    public void render(float delta) {
      
        update(delta);
        draw();
    }

    @Override
    public void dispose() {

//        img.dispose();
        imgbackgraund.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {

//        logo.resize(worldBounds);
        backgraund.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    public void update(float delta) {

//        logo.update(delta);
        for (Star star : stars) {
            star.update(delta);
        }
    }

    public void draw() {

//        logo.draw(batch);
        Gdx.gl.glClearColor(0.1f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        backgraund.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }

    @Override
    public boolean touchDown(Vector2 tourch, int pointer, int button) {

        buttonExit.touchDown(tourch, pointer, button);
        buttonPlay.touchDown(tourch, pointer, button);
//        logo.touchDown(tourch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 tourch, int pointer, int button) {

        buttonExit.touchUp(tourch, pointer, button);
        buttonPlay.touchUp(tourch, pointer, button);
        return false;
    }
}
