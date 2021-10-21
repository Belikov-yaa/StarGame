package ru.gb.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.gb.stargame.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    Vector2 imgPosition, destination, imgVelocity;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        imgPosition = new Vector2(0, 0);
        destination = new Vector2(0, 0);
        imgVelocity = new Vector2(0, 0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(img, imgPosition.x, imgPosition.y);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

}
