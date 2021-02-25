package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.BaseShip;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pull.BulletPool;

public class MainShip extends BaseShip {

    private static final float HEIGHT = 0.1f;
    private static final float PADDING = 0.1f;
    private static final int INVALID_POINTER = -1;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;


    public MainShip(TextureAtlas atals, BulletPool bulletPool) {

        super(atals.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.bulletRegion = atals.findRegion("bulletMainShip");
        v = new Vector2();
        v0 = new Vector2(0.5f, 0);
        bulletV = new Vector2(0, 0.5f);
        bulletPos = new Vector2();
        bulletHeight = 0.01f;
        damage = 1;
        reloadInterval = 0.2f;
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        hp = 100;
    }

    public boolean keyDown(int keycode) {

        switch (keycode) {

            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressLeft = true;
                moveLeft();
                break;

            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressRight = true;
                moveRight();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {

        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressLeft = false;
                if (pressRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressRight = false;
                if (pressLeft) {
                    moveLeft();
                } else {
                    stop();
                }
        }
        return false;
    }

    private void moveRight() {

        v.set(v0);
    }

    private void moveLeft() {

        v.set(v0).rotate(180);
    }

    private void stop() {

        v.setZero();
    }

    public void dispose() {

        bulletSound.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {

        setHightProportions(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
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

        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        }
        if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 tourch, int pointer, int button) {

        if (tourch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

}
