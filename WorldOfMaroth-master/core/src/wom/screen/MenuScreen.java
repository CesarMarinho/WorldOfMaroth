package wom.screen;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;

import wom.util.Assets;
import wom.util.AudioManager;
import wom.util.Constants;
import wow.transition.ScreenTransition;
import wow.transition.ScreenTransitionSlice;

public class MenuScreen extends GameScreen {

	private Stage stage = new Stage();
	private TextureRegion background;
	private TextureRegion beginButton;
	private TextureRegion endButton;
	private TextureRegion achButton;
	private SpriteBatch spriteBatch;
	private int menu = 0;
	
	private float timeWait = Constants.TIME_LOAD;
	
	public MenuScreen(DirectedGame game) {
		super(game);
		AudioManager.instance.stopMusic();
		AudioManager.instance.play(Assets.instance.assetMusic.menuSong);
		spriteBatch = new SpriteBatch();
		background = new TextureRegion(Assets.instance.menuItens.titleMaroth);
		beginButton = new TextureRegion(Assets.instance.menuItens.beginButton);
		achButton = new TextureRegion(Assets.instance.menuItens.achButton);
		endButton = new TextureRegion(Assets.instance.menuItens.endButton);
	}

	@Override
	public void render(float deltaTime)
	{
		
		stage.act(deltaTime);
		spriteBatch.begin();
		spriteBatch.draw(background, 0, 0);
		
		switch(menu)
		{
		
		case 0:			
			spriteBatch.draw(beginButton, 20, 20);
			spriteBatch.setColor(0.25f, 0.25f, 0.25f, 1);			
			spriteBatch.draw(achButton, 350, 15);
			spriteBatch.draw(endButton,700, 20);
			spriteBatch.setColor(1, 1, 1, 1);
			
			break;

		case 1:

			spriteBatch.draw(achButton, 350, 15);
			spriteBatch.setColor(0.25f, 0.25f, 0.25f, 1);
			spriteBatch.draw(beginButton, 20, 20);
			spriteBatch.draw(endButton, 700, 20);
			spriteBatch.setColor(1, 1, 1, 1);
			break;

		case 2:

			spriteBatch.setColor(0.25f, 0.25f, 0.25f, 1);
			spriteBatch.draw(beginButton, 20, 20);
			spriteBatch.draw(achButton, 350, 15);
			spriteBatch.setColor(1, 1, 1, 1);
			spriteBatch.draw(endButton, 700, 20);

			break;
			
		}
		spriteBatch.end();
		
		timeWait -= deltaTime;
		
		if(timeWait <=0)
		{
			handleInput();
		}
	}
	
	
	public void handleInput()
	{
		
		if(Gdx.input.isKeyPressed(Keys.A))
		{	
			AudioManager.instance.play(Assets.instance.assetSound.songMenu);
			timeWait = 0.3f;
			menu = menu >0? --menu:menu;
		}	
		else
		if(Gdx.input.isKeyPressed(Keys.D))				
		{	
			AudioManager.instance.play(Assets.instance.assetSound.songMenu);
			timeWait = 0.3f;
			menu  = menu < 2? ++menu: menu;
		}
		else
		if(Gdx.input.isKeyPressed(Keys.ENTER))
		{		
			AudioManager.instance.play(Assets.instance.assetSound.songMenu);
			AudioManager.instance.stopMusic();
			switch(menu)
			{
			case 0:
				ScreenTransition transition = ScreenTransitionSlice.init(2, ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
				game.setScreen(new HistoryScreen(game), transition);
				timeWait = 10f;
				break;
			
			case 1:
				ScreenTransition _transition = ScreenTransitionSlice.init(2, ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
				game.setScreen(new AchievementScreen(game), _transition);
				timeWait = 10f;
				break;
				
			case 2:
				Gdx.app.exit();
				break;
			}
		}
	}
	
	@Override
	public void resize(int width, int height) 
	{
		
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
