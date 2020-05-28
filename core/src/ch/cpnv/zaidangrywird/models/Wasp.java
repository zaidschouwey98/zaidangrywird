package ch.cpnv.zaidangrywird.models;

import com.badlogic.gdx.math.Vector2;

import ch.cpnv.zaidangrywird.Angrywird;

public final class Wasp extends MovingObject {
    private static final int AGITATION = 15; // How sharply speed changes
    private static final String PICNAME = "wasp.png";
    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;

    public Wasp(Vector2 position, Vector2 speed) {
        super(position, WIDTH, HEIGHT, PICNAME, speed);
    }

    @Override
    public void accelerate(float dt) {
        Vector2 random = new Vector2(
                Angrywird.rand.nextFloat() - 0.5f,
                Angrywird.rand.nextFloat() - 0.5f
        );
        speed = speed.add(random.scl(AGITATION * dt));
    }
}
