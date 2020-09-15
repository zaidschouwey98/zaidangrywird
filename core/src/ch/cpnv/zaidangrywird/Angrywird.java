package ch.cpnv.zaidangrywird;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;



import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import ch.cpnv.zaidangrywird.models.*;
import ch.cpnv.zaidangrywird.models.Scenery;


import static ch.cpnv.zaidangrywird.models.Scenery.BLOCK_SIZE;

public class Angrywird extends ApplicationAdapter implements InputProcessor {
	public static Random rand;

	public static final int WORLD_WIDTH = 1600;
	public static final int WORLD_HEIGHT = 900;
	public static final int FLOOR_HEIGHT = 120;
	public static final int SLINGSHOT_POWER = 4;

	private Texture background;
	private Scenery scene;
	private SpriteBatch batch;
	private Bird tweety;
	private Wasp waspy;

	private OrthographicCamera camera;



	@Override
	public void create () {

		batch = new SpriteBatch();
		rand = new Random(System.currentTimeMillis());
		background = new Texture("background.jpg");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();




		tweety = new Bird(new Vector2(120, WORLD_HEIGHT / 4), new Vector2(300, 400));

		waspy = new Wasp(new Vector2(WORLD_WIDTH / 2, WORLD_HEIGHT / 2), new Vector2(0, 0));

		scene = new Scenery();

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
		batch.end();
		collision();

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
	boolean bResult;


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
		Gdx.app.log("Angry","TouchDown");
		Vector3 pointTouched = camera.unproject(new Vector3(screenX,screenY,0));
		tweety.startAim(new Vector2(pointTouched.x,pointTouched.y));
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Gdx.app.log("Angry","TouchUp");
		Vector3 pointTouched = camera.unproject(new Vector3(screenX,screenY,0));
		tweety.launchFrom(new Vector2(pointTouched.x,pointTouched.y));
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		Gdx.app.log("Angry","TouchDragged");
		Vector3 pointTouched = camera.unproject(new Vector3(screenX,screenY,0));
		tweety.drag(new Vector2(pointTouched.x,pointTouched.y));
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



}
