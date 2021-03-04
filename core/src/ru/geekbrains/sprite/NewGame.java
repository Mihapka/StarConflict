package ru.geekbrains.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.Screen.GameScreene;
import ru.geekbrains.base.BaseButton;
import ru.geekbrains.math.Rect;

public class NewGame extends BaseButton {

    private static final float HEIGHT = 0.04f;
    private GameScreene gameScreene;

    public NewGame(TextureAtlas atlas, GameScreene gameScreene){

        super(atlas.findRegion("button_new_game"));
        this.gameScreene = gameScreene;
    }
    @Override
    public void resize(Rect worldBounds) {

        setHeightProportion(HEIGHT);
    }

    @Override
    public void action() {

        gameScreene.starNewGame();
    }
}
