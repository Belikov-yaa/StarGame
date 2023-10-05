package ru.gb.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.gb.stargame.base.BaseScreen;
import ru.gb.stargame.math.Rect;
import ru.gb.stargame.pool.BulletPool;
import ru.gb.stargame.pool.EnemyPool;
import ru.gb.stargame.sprite.Background;
import ru.gb.stargame.sprite.Bullet;
import ru.gb.stargame.sprite.EnemyShip;
import ru.gb.stargame.sprite.MainShip;
import ru.gb.stargame.sprite.Star;
import ru.gb.stargame.util.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private TextureAtlas atlas;
    private Texture bg;
    private Background background;

    private Star[] stars;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;

    private EnemyEmitter enemyEmitter;

    private Music music;
    private Sound bulletSound;
    private Sound laserSound;

    @Override
    public void show() {
        super.show();

        setMusicParam("sounds/music.mp3");

        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));

        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        enemyPool = new EnemyPool(bulletPool, worldBounds, bulletSound);

        mainShip = new MainShip(atlas, bulletPool, laserSound);

        enemyEmitter = new EnemyEmitter(enemyPool, worldBounds, atlas);
    }

    private void setMusicParam(String fileName) {
        music = Gdx.audio.newMusic(Gdx.files.internal(fileName));
        music.setVolume(0.2f);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        bulletPool.updateActiveObjects(delta);
        enemyPool.updateActiveObjects(delta);
        mainShip.update(delta);
        enemyEmitter.generate(delta);
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        bulletPool.drawActiveObjects(batch);
        enemyPool.drawActiveObjects(batch);
        mainShip.draw(batch);
        batch.end();
    }

    private void checkCollisions() {
        List<EnemyShip> enemyShips = enemyPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShips) {
            if (!enemyShip.isDestroyed() &&
                    enemyShip.pos.dst(mainShip.pos) < (mainShip.getHalfWidth() + enemyShip.getHalfWidth())) {
                enemyShip.destroy();
            }
        }
        List<Bullet> bullets = bulletPool.getActiveObjects();
        for (Bullet bullet : bullets) {
            if (bullet.isDestroyed()) continue;
            if (bullet.getOwner() != mainShip) {
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.doDamage(bullet.getDamage());
                    bullet.destroy();
                }
                continue;
            }
            for (EnemyShip enemyShip : enemyShips) {
                if (enemyShip.isBulletCollision(bullet)) {
                    enemyShip.doDamage(bullet.getDamage());
                    bullet.destroy();
                }

            }
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        music.dispose();
        bulletSound.dispose();
        laserSound.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }

}
