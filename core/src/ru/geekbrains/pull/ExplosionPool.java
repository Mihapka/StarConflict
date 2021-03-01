package ru.geekbrains.pull;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {
    private final TextureAtlas atlas;
    private final Sound exploSound;

    public ExplosionPool(TextureAtlas atlas, Sound exploSound) {
        this.atlas = atlas;
        this.exploSound = exploSound;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(atlas, exploSound);
    }
}

