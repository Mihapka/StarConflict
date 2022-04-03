package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class TrackingStar extends Star{

    private final Vector2 sumV;

    public TrackingStar(TextureAtlas atlas) {
        super(atlas);
        sumV = new Vector2();
    }

    public void update(float delta, Vector2 trackingV) {
        sumV.setZero().mulAdd(trackingV, 0.1f).rotate(180).add(v);
        pos.mulAdd(sumV, delta);
        checkBounds();
    }
}
