package ch.cpnv.zaidangrywird.models;
import com.badlogic.gdx.math.Vector2;



public final class Bird extends MovingObject {
    private enum BirdState { init, aim, fly }

    private static final String sprite = "bird.png";
    public static final int WIDTH = 60;
    public static final int HEIGHT = 60;
    public Bird() {
        super(new Vector2(0, 0), WIDTH, HEIGHT, sprite, new Vector2(0, 0));
    }

    public Bird(Vector2 position, Vector2 speed) {
        super(position, WIDTH, HEIGHT, sprite, speed);
    }

    @Override
    public void accelerate(float dt) {
        speed.y += GRAVITY*dt; // That bird is a poor glider
    }

}


