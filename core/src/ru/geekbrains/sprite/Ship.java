package ru.geekbrains.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Ship extends Sprite {

    private static final float HEIGHT = 0.1f;
    private static final float PADDING = 0.1f;
    TextureRegion region;

    private int pointer;
    private boolean pressed;
    private final Vector2 v = new Vector2();
    private final Vector2 vLeft = new Vector2(0.5f, 0);
//    private final Vector2 vRigft = new Vector2(1f, 0);
//    private final Vector2 vUp = new Vector2(0, 0.5f);
//    private final Vector2 vDщnw = new Vector2(0, 1f);


    public Ship(TextureAtlas atals) {


        super(atals.findRegion("main_ship"));

//        float width = (atals.findRegion("main_ship").getRegionWidth() / 2);
//        float height = atals.findRegion("main_ship").getRegionHeight();
        region = new TextureRegion(atals.findRegion("main_ship"),
                0, 0, (atals.findRegion("main_ship").getRegionWidth() / 2)
                , atals.findRegion("main_ship").getRegionHeight());
    }

    public Ship(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {

        setHightProportions(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
//        setLeft(worldBounds.getRight() - 0.4f);
    }

    @Override
    public void update(float delta) {

        pos.mulAdd(v, delta);
    }

    @Override
    public void setHightProportions(float height) {
        super.setHightProportions(height);
    }


    @Override
    public boolean touchDragged(Vector2 tourch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(Vector2 tourch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDown(Vector2 tourch, int pointer, int button) {
        return false;
    }

    public boolean keyDown(int keycode) {

        switch (keycode) {

            case Input.Keys.W:
            case Input.Keys.UP:
                v.set(vLeft).rotate90(0);
                break;

            case Input.Keys.A:
            case Input.Keys.LEFT:
                v.set(vLeft).rotate(180);
                break;

            case Input.Keys.S:
            case Input.Keys.DOWN:
                v.set(vLeft).rotate(270);
                break;

            case Input.Keys.D:
            case Input.Keys.RIGHT:
                v.set(vLeft);
                break;
        }
        return false;
    }


    public boolean keyUp(int keycode) {

        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                v.setZero();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                v.setZero();
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                v.setZero();
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                v.setZero();
                break;
        }
        return false;
    }
}
