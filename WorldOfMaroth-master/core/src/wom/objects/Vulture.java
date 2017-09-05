package wom.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import wom.objects.Mage.VIEW_DIRECTION;
import wom.util.Assets;

public class Vulture extends  GameObject{
	
	public AtlasRegion body;
	public float timeAttack = -1f;
	public float timeColide = -	1f;
	public float timeWalk;
	public VIEW_DIRECTION viewDirection;
	public boolean isDead;
	public VerticalPower verticalPower;
	private Animation animNormal;
	
	public Vulture()
	{
		verticalPower = new VerticalPower();
		dimension.set(1f, 1f);
		terminalVelocity.x = 4;
		body = Assets.instance.vulture.vulture;
		bounds.set(0, 0, dimension.x, dimension.y);
		viewDirection= VIEW_DIRECTION.RIGHT;
		isDead = false;
		animNormal = Assets.instance.vulture.animNormal;
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
			
			reg = animation.getKeyFrame(stateTime, true);
			
			batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
				rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), viewDirection == VIEW_DIRECTION.LEFT, false);
		
			verticalPower.render(batch);			
			
			batch.setColor(1, 1, 1, 1);
		}
	}	
	
	public void update(float deltaTime)
	{
		if(timeAttack >= 0)
		{
			verticalPower.update(deltaTime);
			
		}
		else	
		{	
			timeAttack = 12f;
			verticalPower.setDirection(position.x, position.y);
			
		}
		
		if(timeWalk >= 0)
		{
			
			velocity.x = walk();
		}
		else
		{
			changeWalk();
		}
		
		super.update(deltaTime);
		timeAttack -= deltaTime;
		timeWalk -= deltaTime;
		timeColide -= deltaTime;
	}
	
	public float walk()
	{
		return viewDirection == VIEW_DIRECTION.RIGHT?terminalVelocity.x:-terminalVelocity.x;
	}
	
	
	public void timeWithoutColide()
	{
		timeColide = 0.2f;
	}
	
	public void changeWalk()
	{
		viewDirection = viewDirection == VIEW_DIRECTION.RIGHT?VIEW_DIRECTION.LEFT: VIEW_DIRECTION.RIGHT;
		timeWalk = 3f;
	}

	
}
