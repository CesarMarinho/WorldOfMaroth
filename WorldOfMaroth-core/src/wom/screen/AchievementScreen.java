package wom.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;

import wom.util.Achievements;
import wom.util.Assets;
import wow.transition.ScreenTransition;
import wow.transition.ScreenTransitionSlice;

public class AchievementScreen extends GameScreen 
{
	private Stage stage = new Stage();
	private SpriteBatch spriteBatch;
	private TextureRegion title;
	private TextureRegion ach1;
	private TextureRegion ach2;
	private TextureRegion ach3;
	private TextureRegion ach4;
	private float timeLoading = 2f;
	
	public AchievementScreen(DirectedGame game) {
		super(game);
		
		spriteBatch = new SpriteBatch();
		title = new TextureRegion(Assets.instance.menuItens.achButton);
		ach1 = new TextureRegion(Assets.instance.menuItens.leeAndrows);
		ach2 = new TextureRegion(Assets.instance.menuItens.coins);
		ach3 = new TextureRegion(Assets.instance.menuItens.openDoors);
		ach4 = new TextureRegion(Assets.instance.menuItens.upgrades);
	}

	@Override
	public void render(float deltaTime) 
	{
		
		boolean [] achs = Achievements.instance.getAchievements();
		
		timeLoading -=deltaTime;
		stage.act(deltaTime);	
		
		spriteBatch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
		spriteBatch.draw(title, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight() - 50);
		
		
		if(achs[0])
			spriteBatch.setColor(0, 0, 1, 1);
		else
			spriteBatch.setColor(1, 0, 0, 1);
		
		spriteBatch.draw(ach1, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight() - 150);
		
		if(achs[1])
			spriteBatch.setColor(0, 0, 1, 1);
		else
			spriteBatch.setColor(1, 0, 0, 1);
		
		spriteBatch.draw(ach2, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight() - 200);
		
		if(achs[2])
			spriteBatch.setColor(0, 0, 1, 1);
		else
			spriteBatch.setColor(1, 0, 0, 1);
		
		spriteBatch.draw(ach3, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight() - 250);
		
		
		if(achs[3])
			spriteBatch.setColor(0, 0, 1, 1);
		else
			spriteBatch.setColor(1, 0, 0, 1);
		
		spriteBatch.draw(ach4, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight() - 300);
		
		spriteBatch.setColor(1, 1, 1, 1);
		
		
		spriteBatch.end();

		if(timeLoading <= 0)
		{	
			handleInput();
		}
	}

	public void handleInput()
	{
		if(Gdx.input.isKeyPressed(Keys.ENTER))
		{
			ScreenTransition transition = ScreenTransitionSlice.init(2, ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
			game.setScreen(new MenuScreen(game), transition);
			this.timeLoading = 10f;
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
