package wom.screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;

import wom.world.WorldController;
import wom.world.WorldRenderer;

public class GamePlayScreen extends GameScreen
{
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	private boolean pause = false;
	
	
	public GamePlayScreen(DirectedGame game) {
		
		super(game);
		worldController = new WorldController(game);
		init();
	
	}
	

	public GamePlayScreen(DirectedGame game, WorldController controller) {
		super(game);
		worldController = controller; 
		init();
	}
	
	public void init()
	{
	
		worldRenderer = new WorldRenderer(worldController);
		Gdx.input.setCatchBackKey(true);

	}

	@Override
	public void render(float deltaTime) 
	{
	
		if (!pause) {
	
			worldController.update(deltaTime);
		}
		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		worldRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
		
	}

	@Override
	public void show() {
		Gdx.input.setCatchBackKey(true);
		
	}
	
	@Override
	public InputProcessor getInputProcessor () {
		return worldController;
	}

	@Override
	public void hide() {
		worldRenderer.dispose();
		Gdx.input.setCatchBackKey(false);
		
	}

	@Override
	public void pause()
	{
		pause = true;
				
	}
	
	@Override
	public void resume()
	{
		pause = false;
	}
	
}
