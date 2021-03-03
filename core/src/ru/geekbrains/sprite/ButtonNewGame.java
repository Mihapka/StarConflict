package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.base.BaseButton;
import ru.geekbrains.math.Rect;

public class ButtonNewGame extends BaseButton {

    private static final float HEIGHT = 0.04f;

    public ButtonNewGame(TextureAtlas atlas) {

        super(atlas.findRegion("button_new_game"));
    }

    @Override
    public void resize(Rect worldBounds) {

        setHeightProportion(HEIGHT);
    }

    @Override
    public void action() {

    }
}
