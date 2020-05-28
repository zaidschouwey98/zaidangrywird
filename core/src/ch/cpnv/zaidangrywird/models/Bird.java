package ch.cpnv.zaidangrywird.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ch.cpnv.zaidangrywird.Angrywird;

public final class Bird extends MovingObject {
    private enum BirdState { init, aim, fly }

    private static final String sprite = "bird.png";
    public static final int WIDTH = 60;
    public static final int HEIGHT = 60;
    private BirdState state = BirdState.init;

    public Bird(Vector2 position, Vector2 speed) {
        super(position, WIDTH, HEIGHT, sprite, speed);
    }


    public void unFreeze() {
        //super.unFreeze();
        //state = BirdState.fly;
    }

    @Override
    public void accelerate(float dt) {

    }
}
