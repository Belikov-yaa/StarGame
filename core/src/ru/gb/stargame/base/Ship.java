package ru.gb.stargame.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gb.stargame.math.Rect;
import ru.gb.stargame.pool.BulletPool;
import ru.gb.stargame.sprite.Bullet;

public class Ship extends Sprite {

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected float bulletHeight;
    protected Sound bulletSound;
    protected Vector2 bulletPos;

    protected int damage;
    protected float reloadTimer;
    protected float reloadInterval;
    protected int hp;

    protected Vector2 v;
    protected Vector2 v0;
    protected Rect worldBounds;

    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, worldBounds, bulletHeight, damage);
        bulletSound.play();
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        bulletPos.set(pos);
    }
}
