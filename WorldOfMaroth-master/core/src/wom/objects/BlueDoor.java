package wom.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wom.util.Assets;

public class BlueDoor extends GameObject {
	
	public TextureRegion blueDoors;
	
	public boolean open;
	
	public BlueDoor() {
		
		dimension.set(1f, 1f);
		blueDoors = Assets.instance.blueDoor.blueDoor;

		bounds.set(0, 0, dimension.x, dimension.y);

		open = false;
	}

	@Override
	public void render(SpriteBatch batch) 
	{
		if (open) return;

		TextureRegion reg = blueDoors;
		
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
			rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
	}	

}
