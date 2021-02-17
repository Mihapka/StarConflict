package ru.geekbrains.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Backgraund;
import ru.geekbrains.sprite.Logo;
import ru.geekbrains.sprite.Star;

public class MenuScreen extends BaseScreen {

    private static final float V_LEN = 0.01f;

    private Texture imgbackgraund;
    private Texture img;
    private TextureAtlas atlas;

    private Backgraund backgraund;
    private Logo logo;
    private Star star;

    @Override
    public void show() {

        super.show();
        imgbackgraund = new Texture("Texture/bg.png");
        img = new Texture("Texture/pikachu.jpg");
        backgraund = new Backgraund(imgbackgraund);
        logo = new Logo(img);
        atlas = new TextureAtlas(Gdx.files.internal("Texture/atlas/menuAtlas.atlas"));
        star = new Star(atlas);
    }

    @Override
    public void render(float delta) {

        update(delta);
        draw();
    }

    @Override
    public void dispose() {

        img.dispose();
        imgbackgraund.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {

        logo.resize(worldBounds);
        backgraund.resize(worldBounds);
        star.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 tourch, int pointer, int button) {

        logo.touchDown(tourch, pointer, button);
        return false;
    }

    public void update(float delta){

        logo.update(delta);
        star.update(delta);
    }
    public void draw(){

        Gdx.gl.glClearColor(0.1f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        backgraund.draw(batch);
        logo.draw(batch);
        star.draw(batch);
        batch.end();
    }
}
