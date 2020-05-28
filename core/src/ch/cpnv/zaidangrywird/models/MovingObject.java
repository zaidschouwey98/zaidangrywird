package ch.cpnv.zaidangrywird.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class MovingObject extends PhysicalObject {
    public final static float G = 250f; // Gravity, for objects that fall

    protected Vector2 speed;
    protected boolean frozen; // Allows to temporarily freeze the movement

    public MovingObject(Vector2 position, float width, float height, String picname, Vector2 speed) {
        super(position, width, height, picname);
        this.speed = speed;
    }

    public void move(float dt) {
        translate(speed.x * dt, speed.y * dt);
    }

    // the accelerate method implements the speed change, which depends on the physics of the derived object, reason why it is abstract here
    public abstract void accelerate(float dt);

    public void freeze() {
        frozen = true;
    }

    public boolean isFrozen() {
        return frozen;
    }
}
