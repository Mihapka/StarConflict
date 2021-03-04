package ru.geekbrains.pull;

import com.badlogic.gdx.audio.Sound;
import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.pull.ExplosionPool;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private  ExplosionPool explosionPool;
    private Rect worldBounds;
    private Sound bulletsound;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, Sound bulletsound, ExplosionPool explosionPool) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.bulletsound = bulletsound;

    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, worldBounds,explosionPool);
    }
}
