package ch.cpnv.zaidangrywird.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class PhysicalObject extends Sprite {
    public PhysicalObject(Vector2 position, float width, float height, String picname)
    {
        super(new Texture(picname));
        setBounds(position.x, position.y, width, height);
    }
}
