package ch.cpnv.zaidangrywird;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import ch.cpnv.zaidangrywird.models.*;
import ch.cpnv.zaidangrywird.models.Scenery;


import static ch.cpnv.zaidangrywird.models.Scenery.BLOCK_SIZE;

public class Angrywird extends ApplicationAdapter implements InputProcessor {
	public static Random rand;

	public static final int WORLD_WIDTH = 1600;
	public static final int WORLD_HEIGHT = 900;
	public static final int FLOOR_HEIGHT = 120;
	public static final int SLINGSHOT_POWER = 4;
	public static World world;
	public static Array<Body> bodies = new Array<Body>();
	private Texture background;
	private Scenery scene;
	private SpriteBatch batch;
	private Bird tweety;
	private Wasp waspy;
	private Pig pig;
	private Vector2 touchOrigin;
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;


	@Override
	public void create () {
		debugRenderer = new Box2DDebugRenderer();
		Box2D.init();
		world = new World(new Vector2(0, -10), true);
		batch = new SpriteBatch();
		rand = new Random(System.currentTimeMillis());
		background = new Texture("background.jpg");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();


		CreateBlock();

		tweety = new Bird(new Vector2(120, WORLD_HEIGHT / 4), new Vector2(300, 400));

		waspy = new Wasp(new Vector2(WORLD_WIDTH / 2, WORLD_HEIGHT / 2), new Vector2(0, 0));

		scene = new Scenery();

		for (int i=0; i<5;i++){

		}
		for (int i=0; i<50; i++) {
			try {
				scene.dropElement(new PhysicalObject(new Vector2(rand.nextFloat()*WORLD_WIDTH, 0), BLOCK_SIZE, BLOCK_SIZE, "block.png"));
			} catch (ObjectOutOfBoundsException e) {
				Gdx.app.log("ANGRY", "Object out of bounds: "+e.getMessage());
			} catch (SceneCollapseException e) {
				Gdx.app.log("ANGRY", "Unstable object: "+e.getMessage());
			}
		}
		scene.addFloor();
		scene.addPigs();


		Gdx.input.setInputProcessor(this);
	}

	public void update(){
		float dt = Gdx.graphics.getDeltaTime(); // number of milliseconds elapsed since last render
		waspy.accelerate(dt);
		waspy.move(dt);

		// --------- Bird
		if(tweety.getState() == Bird.BirdState.FLYING){
			tweety.accelerate(dt);
			tweety.move(dt);
		}
		// --------- Wasp
		// Apply changes to the wasp...
	}
	@Override
	public void render () {

		update();
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
		tweety.draw(batch);
		waspy.draw(batch);
		scene.draw(batch);


		collision();

		debugRenderer.render(world, camera.combined);
		world.getBodies(bodies);
		for(Body b : bodies){
			PhysicalObject e = (PhysicalObject) b.getUserData();
			// Update the entities/sprites position and angle
			e.setPosition(b.getPosition().x, b.getPosition().y);
			// We need to convert our angle from radians to degrees
			e.setRotation(MathUtils.radiansToDegrees * b.getAngle());

		}
		batch.end();
		world.step(1/60f, 6, 2);
	}

	private void CreateBlock(){
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(200, 600);

		// Create our body in the world using our body definition
		Body body = world.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		CircleShape circle = new CircleShape();
		circle.setRadius(20f);

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 40f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.1f; // Make it bounce a little bit
		PhysicalObject test = new PhysicalObject(new Vector2( 200, 600), 40, 40, "block.png");
		// Create our fixture and attach it to the body
		Fixture fixture = body.createFixture(fixtureDef);
		body.setUserData(test);
		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		circle.dispose();
	}
	private void collision(){
		Rectangle rectanglebird = tweety.getBoundingRectangle();
		Rectangle rectanglewasp = waspy.getBoundingRectangle();
		//touche wasp
		boolean birdToWaspisOverlaping = rectanglebird.overlaps(rectanglewasp);

		//touche wasp or pig
		if(birdToWaspisOverlaping){
			tweety.reset();
		}


	}


	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 pointTouched = camera.unproject(new Vector3(screenX,screenY,0));
		tweety.startAim(new Vector2(pointTouched.x,pointTouched.y));
		touchOrigin = new Vector2(pointTouched.x,pointTouched.y);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		Vector3 pointTouched = camera.unproject(new Vector3(screenX,screenY,0));
		if((pointTouched.x<touchOrigin.x)&&(pointTouched.y<touchOrigin.y))
			tweety.launchFrom(new Vector2(pointTouched.x,pointTouched.y));
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{

		Vector3 pointTouched = camera.unproject(new Vector3(screenX, screenY, 0));
		if((pointTouched.x<touchOrigin.x)&&(pointTouched.y<touchOrigin.y))
			tweety.drag(new Vector2(pointTouched.x, pointTouched.y));


		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {

		return false;
	}
	private Vector2 getAbsolutePosition(int x, int y) {
		Vector3 pos = camera.unproject(new Vector3(x, y, 0));
		return new Vector2(pos.x, pos.y);
	}


}
