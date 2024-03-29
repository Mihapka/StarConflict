package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseButton extends Sprite {

    private static final float PRESS_SCALE = 0.9f;

    private int pointer;
    private boolean pressed;

    public BaseButton(TextureRegion region) {
        super(region);
    }


    @Override
    public boolean touchDown(Vector2 tourch, int pointer, int button) {

        if (pressed || !isMe(tourch)) {
            return false;
        }

        this.pointer = pointer;
        pressed = true;
        scale = PRESS_SCALE;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 tourch, int pointer, int button) {

        if (this.pointer != pointer || !pressed) {
            return false;
        }

        if (isMe(tourch)) {
            action();
        }
        pressed = false;
        scale = 1f;
        return false;
    }

    public abstract void action();
}
