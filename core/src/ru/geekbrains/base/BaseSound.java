package ru.geekbrains.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class BaseSound extends BaseScreen {


    @Override
    public void show() {

        super.show();
        Sound bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        Sound bulletexplosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        Sound laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
    }
    public void soundPlay(Sound sound){
        sound.play(0.05f);
    }
}
