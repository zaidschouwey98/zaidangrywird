package ch.cpnv.zaidangrywird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.Texture;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ch.cpnv.zaidangrywird.models.*;

public class Angrywird extends ApplicationAdapter {
	public static Random rand;

	public static final int WORLD_WIDTH = 1600;
	public static final int WORLD_HEIGHT = 900;

	private Texture background;

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

		tweety = new Bird(new Vector2(100, Math.abs(WORLD_HEIGHT/4)), new Vector2(50, 150));
		waspy = new Wasp(new Vector2(30, 30), new Vector2(60, 60));



	}

	@Override
	public void render () {
		update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
		tweety.draw(batch);
		waspy.draw(batch);
		batch.end();

	}





	public void update(){
		float dt = Gdx.graphics.getDeltaTime(); // number of milliseconds elapsed since last render
		waspy.accelerate(dt);
		waspy.move(dt);
		// --------- Bird
		// Apply changes to the bird. The magnitude of the changes depend on the time elapsed since last update !!!

		// --------- Wasp
		// Apply changes to the wasp...
	}
}
