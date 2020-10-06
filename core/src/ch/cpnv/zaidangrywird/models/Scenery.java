package ch.cpnv.zaidangrywird.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.ArrayList;

import ch.cpnv.zaidangrywird.Angrywird;

/**
 * Contains all the static items to display in our world
 * Created by Xavier on 12.05.18.
 */

public final class Scenery {

    public static final int BLOCK_SIZE = 60;
    private static final float X_MIN = 250;
    private static final float X_MAX = Angrywird.WORLD_WIDTH-150;
    private static final float Y_MAX = Angrywird.WORLD_HEIGHT * 0.75f;

    private ArrayList<PhysicalObject> scene;

    public Scenery() {
        scene = new ArrayList<PhysicalObject>();
    }

    /**
     * Add one piece of scenary
     * @param el
     */
    public void addElement (PhysicalObject el)
    {
        scene.add(el);
    }

    public void dropElement (PhysicalObject el) throws ObjectOutOfBoundsException, SceneCollapseException {
        // Check horizontal placement
        if (el.getX() < X_MIN || el.getX() > X_MAX) throw new ObjectOutOfBoundsException(el.toString());
        // Best case: on the floor
        el.setY(Angrywird.FLOOR_HEIGHT);
        // Use a rectangle to detect "collison" on the way down
        Rectangle fallPath = new Rectangle(el.getX(),0,el.getWidth(),Angrywird.WORLD_HEIGHT);
        for (PhysicalObject p : scene) {
            if (p.getBoundingRectangle().overlaps(fallPath) && (p.getY()+p.getHeight() > el.getY())) {
                // Check if object can stand
                if (el.getX()+el.getWidth()/2 < p.getX() || el.getX()+el.getWidth()/2 > p.getX()+p.getWidth()) throw new SceneCollapseException(el.toString());
                el.setY(p.getY()+p.getHeight());
                // Check max height of scenery
                if (el.getY()+el.getHeight() > Y_MAX) throw new ObjectOutOfBoundsException(el.toString());
            }
        }
        scene.add(el);
    }

    /**
     * Lay down a line of blocks to act a floor to the scene
     */
    public void addFloor()
    {
        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        // Set its world position
        groundBodyDef.position.set(new Vector2(0, 10));

        // Create a body from the definition and add it to the world
        Body groundBody = Angrywird.world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(Angrywird.WORLD_WIDTH, 10.0f);
        // Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);
        // Clean up after ourselves
        groundBox.dispose();

        addElement(new PhysicalObject(new Vector2(100,90),100,200,"slingshot1.png"));
        for (int i = 40; i < Angrywird.WORLD_WIDTH / BLOCK_SIZE; i++) {
            addElement(new PhysicalObject(new Vector2(i * BLOCK_SIZE, Angrywird.FLOOR_HEIGHT), BLOCK_SIZE, BLOCK_SIZE, "block.png"));
        }
    }
    public void addPigs(){

            Pig pig = new Pig(new Vector2(400,800),60,60,"pig.png");

            addElement(pig);
    }
    /**
     * Render the whole scenary
     * @param batch
     */
    public void draw(Batch batch)
    {
        for (PhysicalObject p : scene) p.draw(batch);
    }

}
