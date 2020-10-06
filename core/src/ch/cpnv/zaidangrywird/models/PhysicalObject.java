package ch.cpnv.zaidangrywird.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import ch.cpnv.zaidangrywird.Angrywird;

public class PhysicalObject extends Sprite {
    public PhysicalObject(Vector2 position, float width, float height, String picname)
    {
        super(new Texture(picname));
        setBounds(position.x, position.y, width, height);
    }
}
