package ru.gb.stargame.pool;

import ru.gb.stargame.base.SpritesPool;
import ru.gb.stargame.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
