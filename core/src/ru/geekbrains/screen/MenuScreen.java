package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private static final float V_LEN = 0.5f;

    private Texture img;
    private Vector2 touch;
    private Vector2 v;
    private Vector2 point;
    private Vector2 tmp;

    @Override
    public void show() {

        super.show();
        img = new Texture("badlogic.jpg");
        touch = new Vector2();
        v = new Vector2();
        point = new Vector2();
        tmp = new Vector2();
    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.1f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        batch.draw(img, point.x, point.y);
        batch.end();
        tmp.set(touch);
        if (tmp.sub(point).len() > v.len()) {
            point.add(v);
        } else {
            point.set(touch);
        }
        point.add(v);

    }

    @Override
    public void dispose() {

        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        v.set(touch.cpy().sub(point)).setLength(V_LEN);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                touch.y += 10;
                break;
            case Input.Keys.DOWN:
                touch.y -= 10;
                break;
            case Input.Keys.LEFT:
                touch.x -= 10;
                break;
            case Input.Keys.RIGHT:
                touch.x += 10;
                break;
        }
        return false;
    }
}
