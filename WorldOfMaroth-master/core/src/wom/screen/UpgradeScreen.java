package wom.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;

import wom.util.Assets;
import wom.util.AudioManager;
import wom.util.Constants;
import wom.world.WorldController;
import wow.transition.ScreenTransition;
import wow.transition.ScreenTransitionSlice;

public class UpgradeScreen extends GameScreen{

	private WorldController worldController;
	private Stage stage = new Stage();
	private SpriteBatch spriteBatch;
	private TextureRegion backGround;
	private TextureRegion title;
	private TextureRegion lifPlus;
	private TextureRegion timeFlying;
	private TextureRegion back;
	private float timeLoading = 2f;
	int cont;
	
	
	public UpgradeScreen( DirectedGame game, WorldController controller)
	{
		super(game);
		cont = 0;
		this.worldController = controller;
		init();
	}
	
	public UpgradeScreen(DirectedGame game) {
		super(game);
		init();
		cont = 0;
	}
	
	public void init()
	{
		spriteBatch = new SpriteBatch();
		title = new TextureRegion(Assets.instance.menuItens.upgrades);
		lifPlus = new TextureRegion(Assets.instance.menuItens.buyLife);
		timeFlying = new TextureRegion(Assets.instance.menuItens.conquestFly);
		back = new TextureRegion(Assets.instance.menuItens.voltar);
		backGround = new TextureRegion(Assets.instance.menuItens.backGround2);
	}

	@Override
	public void render(float deltaTime) 
	{
		timeLoading -=deltaTime;
		stage.act(deltaTime);
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		
		if(timeLoading <= 0)
		{	
			handleInput();
		}
		
		spriteBatch.begin();
		
		spriteBatch.draw(backGround, 0, 0);	
		spriteBatch.draw(title, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight() - 50);
		
		
		switch(cont)
		{
		
		case 0:
			spriteBatch.draw(back, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight() - 150);
			spriteBatch.setColor(0.25f, 0.25f, 0.25f, 1);			
			spriteBatch.draw(lifPlus, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight() - 200);
			spriteBatch.draw(timeFlying, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight() - 250);
			
			spriteBatch.setColor(1, 1, 1, 1);
			break;
			
			
		case 1:
			spriteBatch.draw(lifPlus, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()- 200);		
			spriteBatch.setColor(0.25f, 0.25f, 0.25f, 1);
			spriteBatch.draw(back, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight() - 150);			
			spriteBatch.draw(timeFlying, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight() - 250);
			spriteBatch.setColor(1, 1, 1, 1);
			break;
				
		case 2:
			spriteBatch.draw(timeFlying, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight() - 250);
			spriteBatch.setColor(0.25f, 0.25f, 0.25f, 1);
			spriteBatch.draw(back, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight() - 150);			
			spriteBatch.draw(lifPlus, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight() - 200 );
			spriteBatch.setColor(1, 1, 1, 1);
			break;
		}
		
		spriteBatch.end();
	}

	public void handleInput()
	{
		
		if(Gdx.input.isKeyPressed(Keys.S))
		{
			AudioManager.instance.play(Assets.instance.assetSound.songMenu);
			timeLoading = 0.3f;
			cont = cont == 2? 2: ++cont;
		}
		else
		if(Gdx.input.isKeyPressed(Keys.W))
		{	
			AudioManager.instance.play(Assets.instance.assetSound.songMenu);
			timeLoading = 0.3f;
			cont = cont == 0? 0: --cont;	
		}
		else
		if(Gdx.input.isKeyPressed(Keys.ENTER))
		{
			AudioManager.instance.play(Assets.instance.assetSound.songMenu);
			switch(cont)
			{
			case 0:
				ScreenTransition transition = ScreenTransitionSlice.init(2, ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
				game.setScreen(new GamePlayScreen(game, worldController), transition );
				this.timeLoading = 10f;
				break;
				
			case 1:
				timeLoading = 0.3f;
				
				if(worldController.score >= 5000 && worldController.lives < Constants.LIVES_START)
				{
					worldController.lives  += 1;
					worldController.score -= 5000;
					worldController.scoreVisual -= 5000;
				}
					
				break;
				
			case 2:

				timeLoading = 0.3f;
				
				if(worldController.score >= 15000 && worldController.numUpFly < 5)
				{
					worldController.numUpFly +=1;
					worldController.score -= 15000;
					worldController.scoreVisual -= 15000;
					worldController.level.mage.JUMP_TIME_MAX += 0.1f;
				}
				
				break;
		
			}
			
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
		// TODO Auto-generated method stub
		return stage;
	}

}
