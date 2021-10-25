package ru.gb.stargame.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.gb.stargame.base.BaseScreen;
import ru.gb.stargame.math.Rect;
import ru.gb.stargame.sprite.Background;
import ru.gb.stargame.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture bg;
    private Vector2 position;

    private Background background;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");

        position = new Vector2();

        bg = new Texture("textures/bg.png");
        background = new Background(bg);

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        background.draw(batch);

        batch.draw(img, position.x, position.y, 0.5f, 0.5f);
        batch.end();

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        position.set(touch);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        bg.dispose();
    }

}
