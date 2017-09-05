package wom.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wom.util.Assets;

public class KeyGreen extends GameObject {
	
	public TextureRegion keyGreen;
	
	public boolean collected;
	
	public KeyGreen() 
	{
		
		dimension.set(0.5f, 0.5f);
		keyGreen = Assets.instance.keyGreen.keyGreen;

		bounds.set(0, 0, dimension.x, dimension.y);

		collected = false;
	}

	@Override
	public void render(SpriteBatch batch) 
	{
		if (collected) return;

		TextureRegion reg = keyGreen;
		
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
			rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
	}	

}
