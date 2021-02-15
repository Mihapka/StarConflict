package ru.geekbrains.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Backgraund;
import ru.geekbrains.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private static final float V_LEN = 0.5f;

    private Texture imgbackgraund;
    private Texture img;

    private Backgraund backgraund;
    private Logo logo;


    @Override
    public void show() {
        super.show();
        imgbackgraund = new Texture("Texture/backgraund_001.jpg");
        img = new Texture("Texture/pikachu.jpg");
        backgraund = new Backgraund(imgbackgraund);
        logo = new Logo(img);
    }

    @Override
    public void render(float delta) {
        logo.update(delta);
        Gdx.gl.glClearColor(0.1f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        backgraund.draw(batch);
        logo.draw(batch);
//        batch.draw(img, 0, 0, 1f, 1f);
        batch.end();

    }

    @Override
    public void dispose() {
        img.dispose();
        imgbackgraund.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        logo.resize(worldBounds);
        backgraund.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 tourch, int pointer, int button) {
        logo.touchDown(tourch, pointer, button);
        return false;
    }
}
