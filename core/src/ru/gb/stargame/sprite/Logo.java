package ru.gb.stargame.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gb.stargame.base.Sprite;
import ru.gb.stargame.math.Rect;

public class Logo extends Sprite {

    private Vector2 dest;
    private Vector2 velosity;
    private final float V_LEN = 0.05f; //

    public Logo(Texture texture) {
        super(new TextureRegion(texture));
        velosity = new Vector2();
        dest = new Vector2(0, 0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (!pos.epsilonEquals(dest)) {
            if (dest.cpy().sub(pos).len() < V_LEN) {
                pos.set(dest);
            } else {
                pos.add(velosity);
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.3f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        dest.set(touch);
        velosity = touch.cpy().sub(pos).setLength(V_LEN);
        return false;
    }
}
