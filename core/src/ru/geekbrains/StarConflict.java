package ru.geekbrains;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class StarConflict extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    Texture backgraund;

    @Override
    public void create() {
        batch = new SpriteBatch();
        backgraund = new Texture("backgraund_001.jpg");
        img = new Texture("pikachu.jpg");
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        batch.draw(backgraund, 0, 0);
		batch.draw(img, 20, 20);
//        batch.draw(img, 0, 0, 20, 20, 150, 150);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}

