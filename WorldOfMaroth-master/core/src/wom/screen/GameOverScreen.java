package wom.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;

import wom.util.Assets;
import wow.transition.ScreenTransition;
import wow.transition.ScreenTransitionSlice;

public class GameOverScreen extends GameScreen {
	
	private TextureRegion backGround;
	public float timeLoad = 5f;
	private SpriteBatch spriteBatch;
	private Stage stage;
	
	public GameOverScreen(DirectedGame game) {
		super(game);
		spriteBatch = new SpriteBatch();
		backGround = new TextureRegion(Assets.instance.menuItens.gameOver);	
		stage = new Stage();
	}

	@Override
	public void render(float deltaTime) {
		
		timeLoad-= deltaTime;
		spriteBatch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		spriteBatch.draw(backGround, 0, 0);		
		
		spriteBatch.end();
		
		if(timeLoad < 0)
		handleInput();
	}

	
	public void handleInput()
	{
		
		if(Gdx.input.isKeyPressed(Keys.ENTER))
		{
			ScreenTransition transition = ScreenTransitionSlice.init(2, ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
			game.setScreen(new MenuScreen(game), transition);
			this.timeLoad = 10f;
		}
		
	}
	
	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public InputProcessor getInputProcessor() {
		return stage;
	}

}
