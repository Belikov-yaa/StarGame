package ru.gb.stargame.pool;

import com.badlogic.gdx.audio.Sound;

import ru.gb.stargame.base.SpritesPool;
import ru.gb.stargame.math.Rect;
import ru.gb.stargame.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private final BulletPool bulletPool;
    private final Rect worldBounds;
    private final Sound bulletSound;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, Sound bulletSound) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.bulletSound = bulletSound;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, worldBounds, bulletSound);
    }
}
