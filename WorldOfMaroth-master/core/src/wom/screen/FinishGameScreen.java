package wom.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;

import wom.levels.Level;
import wom.util.Assets;
import wom.util.AudioManager;
import wom.util.CameraHelper;
import wom.world.WorldController;
import wow.transition.ScreenTransition;
import wow.transition.ScreenTransitionSlice;

public class FinishGameScreen extends GameScreen{
	private WorldController worldController;
	private Stage stage = new Stage();
	private SpriteBatch spriteBatch;
	private TextureRegion backGround;
	private float timeLoading = 2f;
	int cont;
	
	
	public FinishGameScreen( DirectedGame game, WorldController controller)
	{
		
		super(game);
		AudioManager.instance.play(Assets.instance.assetSound.winning);
		cont = 0;
		this.worldController = controller;
		init();
	}
	
	
	public void init()
	{
		spriteBatch = new SpriteBatch();
		backGround = new TextureRegion(Assets.instance.menuItens.backGround2);
	}

	@Override
	public void render(float deltaTime) 
	{
		timeLoading -=deltaTime;
		stage.act(deltaTime);
		
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		
		spriteBatch.begin();
		
		if(cont == 0)
			backGround = new TextureRegion(Assets.instance.menuItens.endGame);
		else
			backGround = new TextureRegion(Assets.instance.menuItens.tryAgain);
		
		spriteBatch.draw(backGround, 0, 0);	
		spriteBatch.draw(backGround, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight() - 50);
		
		
	
		spriteBatch.end();
	
		if(timeLoading <= 0)
		{	
			handleInput();
		}
		
	}

	public void handleInput()
	{	
		if(cont != 0)
		{
			if(Gdx.input.isKeyPressed(Keys.S))
			{
//				ScreenTransition transition = ScreenTransitionSlice.init(2, ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
//				
//				worldController.level = new Level("levels/level1.png");
//				game.setScreen(new GamePlayScreen(game, worldController), transition );
//				this.timeLoading = 10f;
				ScreenTransition transition = ScreenTransitionSlice.init(2, ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
				worldController.cameraHelper = new CameraHelper();
				worldController.level = new Level("levels/level1.png");
				worldController.cameraHelper.setTarget(worldController.level.mage);
				game.setScreen(new GamePlayScreen(game, worldController), transition );
				this.timeLoading = 10f;
				
			}
			if(Gdx.input.isKeyPressed(Keys.N))
			{
				AudioManager.instance.play(Assets.instance.assetSound.songMenu);
				
				ScreenTransition _transition = ScreenTransitionSlice.init(2, ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
				game.setScreen(new MenuScreen(game), _transition );
				this.timeLoading = 10f;	
			}
		}
		
		if(Gdx.input.isKeyPressed(Keys.ENTER))
			if(cont == 0)
				++cont;			
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
