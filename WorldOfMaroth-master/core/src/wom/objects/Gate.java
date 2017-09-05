package wom.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import wom.util.Assets;

public class Gate extends GameObject{
	
	public AtlasRegion gate;
	
	public Gate()
	{
		dimension.set(1f, 1f);
		gate = Assets.instance.gate.gate;
		bounds.set(0, 0, dimension.x, dimension.y);
}

	@Override
	public void render(SpriteBatch batch) 
	{
			
		TextureRegion reg = gate;
		
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
			rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
	}	
	
	public void update(float deltaTime)
	{
		
	}

}
