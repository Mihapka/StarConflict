package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.base.BaseButton;
import ru.geekbrains.math.Rect;

public class ButtonExit extends BaseButton {

    private static final float HEIGHT = 0.1f;
    private static final float PADDING = 0.05f;

    public ButtonExit(TextureAtlas atlas) {

        super(atlas.findRegion("btExit"));
    }

    @Override
    public void resize(Rect worldBounds) {

        setHightProportions(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
        setRight(worldBounds.getRight() - PADDING);
    }

    @Override
    public void action() {

        Gdx.app.exit();
    }
}
