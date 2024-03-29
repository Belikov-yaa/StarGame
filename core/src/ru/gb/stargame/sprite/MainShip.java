package ru.gb.stargame.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gb.stargame.base.Ship;
import ru.gb.stargame.math.Rect;
import ru.gb.stargame.pool.BulletPool;
import ru.gb.stargame.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final float HEIGHT = 0.15f;
    private static final float BOTTOM_MARGIN = 0.05f;
    private static final int INVALID_POINTER = -1;
    private static final float RELOAD_INTERVAL = 0.25f;

    private boolean pressedLeft = false;
    private boolean pressedRight = false;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Sound bulletSound) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletSound = bulletSound;
        this.v = new Vector2();
        this.v0 = new Vector2(0.5f, 0);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletRegion = new TextureRegion(atlas.findRegion("bulletMainShip"));
        this.bulletV = new Vector2(0, 0.5f);
        this.bulletHeight = 0.01f;
        this.bulletPos = new Vector2();
        this.damage = 1;
        this.reloadInterval = RELOAD_INTERVAL;
        this.hp = 10;
        this.gunPosition = new Vector2(0f, halfHeight*0.9f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stopMove();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stopMove();
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (leftPointer == pointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stopMove();
            }
        } else if (rightPointer == pointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stopMove();
            }
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight)
                    moveRight();
                else
                    stopMove();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft)
                    moveLeft();
                else
                    stopMove();
                break;
        }
        return false;
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotateDeg(180);
    }

    private void stopMove() {
        v.setZero();
    }

    public boolean isBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getTop() < getBottom()
                || bullet.getBottom() > pos.y + halfHeight / 2);
    }
}
