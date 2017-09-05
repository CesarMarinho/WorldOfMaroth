package wom.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wom.util.Assets;

public class KeyYellow extends GameObject {
	
	public TextureRegion keyYellow;
	
	public boolean collected;
	
	public KeyYellow() {
		
		dimension.set(0.5f, 0.5f);
		keyYellow = Assets.instance.keyYellow.keyYellow;

		bounds.set(0, 0, dimension.x, dimension.y);

		collected = false;
	}

	@Override
	public void render(SpriteBatch batch) 
	{
		if (collected) return;

		TextureRegion reg = keyYellow;
		
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
			rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
	}	

}
