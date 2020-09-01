package ch.cpnv.zaidangrywird.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class MovingObject extends PhysicalObject {
    protected final static float GRAVITY = -9.81f * 16 ;
    protected Vector2 speed;

    public MovingObject(Vector2 position, float width, float height, String picname, Vector2 speed) {
        super(position, width, height, picname);
        this.speed = speed;
    }


    // the accelerate method implements the speed change, which depends on the physics of the derived object, reason why it is abstract here
    public abstract void accelerate(float dt);

    public final void move(float dt) {
        translate(speed.x * dt, speed.y * dt);
    }

}
