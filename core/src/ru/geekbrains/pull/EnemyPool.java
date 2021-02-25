package ru.geekbrains.pull;

import com.badlogic.gdx.audio.Sound;
import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private Rect worldBounds;
    private Sound bulletsound;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, Sound bulletsound) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.bulletsound = bulletsound;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, worldBounds);
    }
}
