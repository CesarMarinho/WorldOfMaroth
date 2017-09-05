package wom.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wom.util.Assets;

public class GreenDoor extends GameObject {
	
	public TextureRegion greenDoor;
	
	public boolean open;
	
	public GreenDoor() {
		
		dimension.set(1f, 1f);
		greenDoor = Assets.instance.greenDoor.greenDoor;

		bounds.set(0, 0, dimension.x, dimension.y);

		open = false;
	}

	@Override
	public void render(SpriteBatch batch) 
	{
		if (open) return;

		TextureRegion reg = greenDoor;
		
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
			rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
	}	

}
