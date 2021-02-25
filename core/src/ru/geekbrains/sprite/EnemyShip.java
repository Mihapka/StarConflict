package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.BaseShip;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pull.BulletPool;


public class EnemyShip extends BaseShip {

    public EnemyShip(BulletPool bulletPool, Rect worldBounds) {

        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.bulletSound = bulletSound;
        v = new Vector2();
        v0 = new Vector2();
        bulletPos = new Vector2();
        bulletV = new Vector2();
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    }

    @Override
    public void update(float delta) {

        if (getTop() > worldBounds.getTop()) {
            reloadTimer = reloadInterval* 0.5f;
        }else {
            v.set(v0);
        }
        super.update(delta);
        bulletPos.set(pos.x, pos.y - getHeight());
        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            Vector2 bulletV,
            int damage,
            float reloadInterval,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(bulletV);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        setHightProportions(height);
        this.hp = hp;
        v.set(0, -0.2f);
    }
}