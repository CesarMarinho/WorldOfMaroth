package wom.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;

import wom.util.Assets;
import wow.transition.ScreenTransition;
import wow.transition.ScreenTransitionSlice;

public class HistoryScreen extends GameScreen{
	
	
	private TextureRegion background;
	private float timeLoad = 4f;
	private SpriteBatch batch;
	
	public HistoryScreen(DirectedGame game)
	{
		super(game);
		batch = new SpriteBatch();
		background = new TextureRegion(Assets.instance.menuItens.begin);
	}

	@Override
	public void render(float deltaTime)
	{
		
		
		timeLoad -= deltaTime;
		
		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();
		
		
		if(timeLoad < 0)
		{
			handleInput();
		}
		
	}
	
	public void handleInput()
	{
		
		if(Gdx.input.isKeyPressed(Keys.ENTER))
		{
			ScreenTransition transition = ScreenTransitionSlice.init(2, ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
			game.setScreen(new GamePlayScreen(game), transition);
			timeLoad = 10f;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputProcessor getInputProcessor() {
		// TODO Auto-generated method stub
		return null;
	}

}
