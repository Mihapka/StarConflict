package ru.geekbrains.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pull.BulletPool;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.Explosion;
import ru.geekbrains.pool.ExplosionPool;

public class BaseShip extends Sprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;

    protected Vector2 v0;
    protected Vector2 v;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;

    protected boolean pressLeft;
    protected boolean pressRight;
    protected Sound sound;
    protected float bulletHeight;
    protected int damage;

    protected float reloadInterval;
    protected float reloadTimer;

    protected int hp;

    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;


    public BaseShip() {

    }

    public BaseShip(TextureRegion region, int rows, int cols, int frames) {

        super(region, rows, cols, frames);

    }

    private void shoot() {

        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, 1);
        sound.play(0.04f);
    }

    private void boom() {

        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    public void damage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
        frame = 1;
        damageAnimateTimer = 0f;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }

        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    @Override
    public void destroy() {

        super.destroy();
        boom();
    }
}