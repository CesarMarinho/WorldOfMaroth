package wom.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import wom.objects.Mage.VIEW_DIRECTION;
import wom.util.Assets;

public class Slime extends GameObject {
	
	public TextureRegion slime;
	private float timeWalk = 3f;
	private VIEW_DIRECTION direction = VIEW_DIRECTION.RIGHT;
	public boolean dead = false;
	public float timeColide = -1f;

	
	public Slime(){
		slime = Assets.instance.slime.slime;
		terminalVelocity.x = 4;
		dimension.set(1, 1);
		bounds.set(0, 0, dimension.x , dimension.y);
	}
	
	@Override
	public void update(float deltaTime)
	{
		if(!dead)
		{
			super.update(deltaTime);
			timeColide -= deltaTime;
			timeWalk -= deltaTime;
			
			if(timeWalk >= 0)
			{
				
				velocity.x = walk();
			}
			else
			{
				changeWalk();
			}
		}
	}
	
	public void timeWithoutColide()
	{
		timeColide = 0.2f;
	}
	
	public void changeWalk()
	{
		direction = direction == VIEW_DIRECTION.RIGHT?VIEW_DIRECTION.LEFT: VIEW_DIRECTION.RIGHT;
		timeWalk = 3f;
	}
	
	public float walk()
	{
		return direction == VIEW_DIRECTION.RIGHT?terminalVelocity.x:-terminalVelocity.x;
	}
	


	@Override
	public void render(SpriteBatch batch) {
		
		if(!dead)
		{
			TextureRegion reg =  slime;

			batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x , dimension.y
			, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
			reg.getRegionHeight(), direction == VIEW_DIRECTION.LEFT, false);

		}
						
		batch.setColor(1, 1, 1, 1);
	}

}
