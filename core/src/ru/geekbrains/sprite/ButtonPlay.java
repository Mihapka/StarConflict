package ru.geekbrains.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.geekbrains.Screen.GameScreene;
import ru.geekbrains.base.BaseButton;
import ru.geekbrains.math.Rect;

public class ButtonPlay extends BaseButton {

    private static final float HEIGHT = 0.1f;
    private static final float PADDING = 0.05f;

    private final Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {

        super(atlas.findRegion("btStart"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {

        setHightProportions(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
        setLeft(worldBounds.getLeft() + PADDING);
    }

    @Override
    public void action() {

        game.setScreen(new GameScreene());
    }
}
