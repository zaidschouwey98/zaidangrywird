package ch.cpnv.zaidangrywird.models;
import com.badlogic.gdx.math.Vector2;

import ch.cpnv.zaidangrywird.Angrywird;


public final class Bird extends MovingObject {

    public enum BirdState {
        READY,
        AIMING,
        FLYING
    }
    private static final String sprite = "bird.png";
    public static final int WIDTH = 60;
    public static final int HEIGHT = 60;


    private BirdState state = BirdState.READY;
    private Vector2 aimOrigin;
    private Vector2 dragOffset;

    public Bird(){
        super(new Vector2(0,0),WIDTH,HEIGHT,sprite,new Vector2(0,0));
    }

    public Bird(Vector2 position, Vector2 speed) {
        super(position, WIDTH, HEIGHT, sprite, speed);
    }

    public BirdState getState(){
        return state;
    }

    public void setState(BirdState state){
        this.state = state;
    }

    public void startAim(Vector2 position){
        if(state == BirdState.READY) {
            aimOrigin = position.cpy();
            dragOffset = position.sub(getX(), getY());
            state = BirdState.AIMING;
        }
    }

    public void drag(Vector2 position){
        if(state==BirdState.AIMING){

                setPosition(position.x-dragOffset.x,position.y-dragOffset.y);


        }
    }
    public void launchFrom(Vector2 position){
        if(state == BirdState.AIMING){
            speed = aimOrigin.sub(position).scl(Angrywird.SLINGSHOT_POWER);
            state=BirdState.FLYING;
        }

    }
    @Override
    public void accelerate(float dt) {
        speed.y += GRAVITY*dt;
    }
    public void reset(){
        state = BirdState.READY;
        this.setPosition(120,Angrywird.WORLD_HEIGHT/4);
    }
}


