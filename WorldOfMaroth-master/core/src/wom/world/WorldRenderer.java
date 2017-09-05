package wom.world;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import wom.util.Assets;
import wom.util.Constants;


public class WorldRenderer implements Disposable
{
	public WorldController worldController;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	public AssetManager assetManager;
	private OrthographicCamera cameraGUI;

	
	public WorldRenderer(WorldController worldController)
	{
		this.worldController = worldController;
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.direction.set(0, 0, -1);
		camera.update();
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true);
		cameraGUI.update();
		
	}
	
	public void render()
	{
		
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.level.render(batch);
		batch.end();
		
		renderGui(batch);
		
		
	}
	
	private void renderGui (SpriteBatch batch) {
		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();

		
		renderGuiScore(batch);		
		renderGuiExtraLive(batch);		
		
		//renderGuiGameOverMessage(batch);

		batch.end();
	}

	private void renderGuiScore (SpriteBatch batch) {
		float x = -15;
		float y = -15;
		float offsetX = 45;
		float offsetY = 45;
		if (worldController.scoreVisual < worldController.score) {
			long shakeAlpha = System.currentTimeMillis() % 360;
			float shakeDist = 1.5f;
			offsetX += MathUtils.sinDeg(shakeAlpha * 2.2f) * shakeDist;
			offsetY += MathUtils.sinDeg(shakeAlpha * 2.9f) * shakeDist;
		}
		
		batch.draw(Assets.instance.iron.iron, x, y, offsetX, offsetY, 75, 75, 0.35f, -0.35f, 0);
		Assets.instance.fonts.defaultBig.draw(batch, "" + (int)worldController.scoreVisual, x + 75, y + 37);
		
		
		batch.draw(Assets.instance.keyBlue.keyBlue, x, y + 50, offsetX, offsetY, 75, 75, 0.35f, -0.35f, 0);
		Assets.instance.fonts.defaultBig.draw(batch, "" + (int)worldController.level.mage.bKeys, x + 75, y +90);
		
		batch.draw(Assets.instance.keyGreen.keyGreen, x, y + 100, offsetX, offsetY, 75, 75, 0.35f, -0.35f, 0);
		Assets.instance.fonts.defaultBig.draw(batch, "" + (int)worldController.level.mage.gKeys, x + 75, y +140);
		
		batch.draw(Assets.instance.keyYellow.keyYellow, x, y + 150, offsetX, offsetY, 75, 75, 0.35f, -0.35f, 0);
		Assets.instance.fonts.defaultBig.draw(batch, "" + (int)worldController.level.mage.yKeys, x + 75, y + 190);
	}	

	private void renderGuiExtraLive (SpriteBatch batch) {
 		float x = cameraGUI.viewportWidth - 50 - Constants.LIVES_START * 50;
		float y = -15;
		for (int i = 0; i < Constants.LIVES_START; i++) {
			if (worldController.lives <= i) batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
			batch.draw(Assets.instance.mage.body, x + i * 50, y, 50, 50, 120, 100, 0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);
		}
		if (worldController.lives >= 0 && worldController.livesVisual > worldController.lives) {
			int i = worldController.lives;
			float alphaColor = Math.max(0, worldController.livesVisual - worldController.lives - 0.5f);
			float alphaScale = 0.35f * (2 + worldController.lives - worldController.livesVisual) * 2;
			float alphaRotate = -45 * alphaColor;
			batch.setColor(1.0f, 0.7f, 0.7f, alphaColor);
			batch.draw(Assets.instance.mage.body, x + i * 50, y, 50, 50, 120, 100, alphaScale, -alphaScale, alphaRotate);
			batch.setColor(1, 1, 1, 1);
		}
	}
	
	private void renderGuiGameOverMessage (SpriteBatch batch) {
		float x = cameraGUI.viewportWidth / 2;
		float y = cameraGUI.viewportHeight / 2;
		if (worldController.isGameOver()) {
			BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
			fontGameOver.setColor(1, 0.75f, 0.25f, 1);
			fontGameOver.draw(batch, "GAME OVER", x, y, 1, Align.center, true);
			fontGameOver.setColor(1, 1, 1, 1);
		}
	}
	
	public void resize(int width, int height)
	{
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / (float)height) * (float)width;
		camera.update();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}

}
