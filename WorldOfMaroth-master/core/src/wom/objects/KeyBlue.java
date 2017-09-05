package wom.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wom.util.Assets;

public class KeyBlue extends GameObject {
	
	public TextureRegion keyBlue;
	
	public boolean collected;
	
	public KeyBlue() {
		
		dimension.set(0.5f, 0.5f);
		keyBlue = Assets.instance.keyBlue.keyBlue;

		bounds.set(0, 0, dimension.x, dimension.y);

		collected = false;
	}

	@Override
	public void render(SpriteBatch batch) 
	{
		if (collected) return;

		TextureRegion reg = keyBlue;
		
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
			rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
	}	

}
