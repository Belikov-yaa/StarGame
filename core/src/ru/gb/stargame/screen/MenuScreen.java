package ru.gb.stargame.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.gb.stargame.base.BaseScreen;
import ru.gb.stargame.sprite.Background;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture bg;
    private Vector2 position;

    private Background background;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        position = new Vector2(0, 0);

        bg = new Texture("textures/bg.png");
        background = new Background(bg);

//        destination = new Vector2(0, 0);
//        imgVelocity = new Vector2(0, 0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(img, position.x, position.y, 0.5f, 0.5f);
        batch.end();
//        if (!imgPosition.epsilonEquals(destination)) {
//            if (destination.cpy().sub(imgPosition).len() < imgVelocity.len()) {
//                imgPosition.set(destination);
//            } else {
//                imgPosition.add(imgVelocity);
//            }
//        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        destination.set(screenX, Gdx.graphics.getHeight() - screenY);
//        imgVelocity = destination.cpy().sub(imgPosition);
//        imgVelocity.nor().scl(3);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

}
