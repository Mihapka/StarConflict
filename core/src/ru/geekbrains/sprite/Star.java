package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

public class Star extends Sprite {

    private static final float HEIGHT = 0.05f;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHightProportions(HEIGHT);
        float x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(x,y);
    }
}
