package wom.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import wom.objects.Mage.VIEW_DIRECTION;
import wom.util.Assets;

public class VerticalPower extends GameObject{
	
	public AtlasRegion body;
	
	public VerticalPower()
	{
		dimension.set(0.5f, 0.5f);
		body = Assets.instance.vulture.vulturePower;
		bounds.set(0, 0, dimension.x, dimension.y);
		terminalVelocity.x = 100f;
		
	}
	
	@Override
	public void render(SpriteBatch batch) 
	{
		
			TextureRegion reg = body;
			
			batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
				rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}	
	
	public void setDirection(float x, float y)
	{
		this.position.x = x;
		this.position.y = y;
	}
	
	public void update(float deltaTime)
	{
		
		velocity.y = -terminalVelocity.x;
		super.update(deltaTime);	
	}
	
}