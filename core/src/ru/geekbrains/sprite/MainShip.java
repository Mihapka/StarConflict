package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pull.BulletPull;

public class MainShip extends Sprite {

    private static final float HEIGHT = 0.1f;
    private static final float PADDING = 0.1f;
    private static final int INVALID_POINTER = -1;


    private final Vector2 v0 = new Vector2(0.5f, 0);
    private final Vector2 v = new Vector2();

    private Rect worldBounds;
    private BulletPull bulletPull;
    private TextureRegion bulletregion;
    private Vector2 bulletV;
    private Vector2 bulletPos;

    private boolean pressLeft;
    private boolean pressRight;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private float reloadInterval;
    private float reloadTimer;

    private Sound bulletSound;


    public MainShip(TextureAtlas atals, BulletPull bulletPull) {


        super(atals.findRegion("main_ship"), 1, 2, 2);
        this.bulletPull = bulletPull;
        this.bulletregion = atals.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);
        bulletPos = new Vector2();
        reloadInterval = 0.2f;
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));

//        float width = (atals.findRegion("main_ship").getRegionWidth() / 2);
//        float height = atals.findRegion("main_ship").getRegionHeight();
//        region = new TextureRegion(atals.findRegion("main_ship"),
//                0, 0, (atals.findRegion("main_ship").getRegionWidth() / 2)
//                , atals.findRegion("main_ship").getRegionHeight());
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

            case Input.Keys.W:
            case Input.Keys.UP:
                shoot();
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

    private void shoot() {

        bulletPos.set(pos.x, pos.y + getHeight());
        Bullet bullet = bulletPull.obtain();
        bullet.set(this, bulletregion, bulletPos, bulletV, 0.01f, worldBounds, 1);
        bulletSound.play(0.05f);
    }

    public void dispose(){

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

        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
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
