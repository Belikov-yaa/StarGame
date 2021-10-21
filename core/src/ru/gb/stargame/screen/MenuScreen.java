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
        if (!imgPosition.epsilonEquals(destination)) {
            if (destination.cpy().sub(imgPosition).len() < imgVelocity.len()) {
                imgPosition.set(destination);
            } else {
                imgPosition.add(imgVelocity);
            }
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        destination.set(screenX, Gdx.graphics.getHeight() - screenY);
        imgVelocity = destination.cpy().sub(imgPosition);
        imgVelocity.nor().scl(3);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

}
