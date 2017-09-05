package wom.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wom.objects.Mage.VIEW_DIRECTION;
import wom.util.Assets;

public class Archer extends GameObject{
	
	public AtlasRegion body;
	public float timeAttack = -1f;
	public VIEW_DIRECTION viewDirection;
	public boolean isDead;
	public Arrow arrow;
	
	private Animation animNormal; 
	private Animation animNull;
	
	public Archer()
	{
		arrow = new Arrow();
		dimension.set(1f, 1f);
		body = Assets.instance.archer.body;
		bounds.set(0, 0, dimension.x, dimension.y);
		viewDirection= VIEW_DIRECTION.RIGHT;
		isDead = false;
		
		animNormal = Assets.instance.archer.animNormal;
		animNull = Assets.instance.archer.animNull;
		setAnimation(animNormal);
	}
	
	public void setViewDirection(VIEW_DIRECTION viewDirection)
	{
		this.viewDirection = viewDirection;
	}
	
	@Override
	public void render(SpriteBatch batch) 
	{
		if(!isDead)
		{
			TextureRegion reg = body;
			
			arrow.render(batch);
			reg = animation.getKeyFrame(stateTime, true);
			
			batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
				rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), viewDirection == VIEW_DIRECTION.LEFT, false);	

			batch.setColor(1, 1, 1, 1);
		}
	}	
	
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		
		if(timeAttack >= 0)
		{
			arrow.update(deltaTime);
			//setAnimation(animNormal);
			
		}
		else	
		{	
			timeAttack = 4f;
			arrow.setDirection(position.x, position.y);
			arrow.setViewDirection(viewDirection);
			//setAnimation(animNull);
		}
		timeAttack -= deltaTime;
		
		
	}

}
