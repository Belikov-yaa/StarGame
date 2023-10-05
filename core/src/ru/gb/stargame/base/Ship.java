package ru.gb.stargame.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gb.stargame.math.Rect;
import ru.gb.stargame.pool.BulletPool;
import ru.gb.stargame.pool.ExplosionPool;
import ru.gb.stargame.sprite.Bullet;
import ru.gb.stargame.sprite.Explosion;

public class Ship extends Sprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;

    protected ExplosionPool explosionPool;
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
    protected Vector2 gunPosition;

    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

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

    public void doDamage(int hitPoints) {
        this.hp -= hitPoints;
        if (this.hp < 0) {
            this.hp = 0;
            destroy();
        }
        damageAnimateTimer = 0f;
        frame = 1;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (!(getTop() < worldBounds.getBottom()
                || getBottom() > worldBounds.getTop()
                || getRight() < worldBounds.getLeft()
                || getLeft() > worldBounds.getRight())) {
            explode();
        }
    }

    private void explode() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(this.pos, getHeight());
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            bulletPos.set(pos.x + gunPosition.x, pos.y + gunPosition.y);
            shoot();
        }
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }
}
