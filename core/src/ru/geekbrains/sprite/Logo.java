package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Logo extends Sprite {

    private static final float LEN = 0.01f;
    private static final float HIGHT = 0.02f;


    private Vector2 logoTouch;
    private Vector2 v;
    private Vector2 tmp;

    public Logo(Texture texture) {
        super(new TextureRegion(texture));
        logoTouch = new Vector2();
        v = new Vector2();
        tmp = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {

        setHightProportions(HIGHT);
    }

    @Override
    public void update(float delta) {
        tmp.set(logoTouch);
        if (tmp.sub(pos).len() > LEN) {
            pos.add(v);
        } else {
            pos.set(logoTouch);
        }
    }

    @Override
    public boolean touchDown(Vector2 logoTouch, int pointer, int button) {
        this.logoTouch.set(logoTouch);
        v.set(this.logoTouch.cpy().sub(pos)).setLength(LEN);
        return false;
    }
}
