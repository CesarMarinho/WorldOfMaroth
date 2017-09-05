package wom.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wom.objects.Mage.VIEW_DIRECTION;
import wom.util.Assets;

public class EnergyBall extends GameObject {
	
	public float MAX_TIME = 0.2f;
	public TextureRegion energyBall;	
	public boolean isAttacking;
	public float timeAtack;
	
	private VIEW_DIRECTION direction = VIEW_DIRECTION.RIGHT;
	
	public void setPosition(float x, float y){
		this.position.x = x;
		this.position.y = y;
	}
		
	public EnergyBall () {
		dimension.set(0.5f,0.5f);
		energyBall = Assets.instance.energyBall.energyBall;
		bounds.set(0, 0, dimension.x, dimension.y);
		this.terminalVelocity.x = 10;
		isAttacking = false;
		timeAtack = -1f;
		
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
		float dimCorrectionX = 0;
		float dimCorrectionY = 0;
		
		if(isAttacking)	{
			TextureRegion reg = energyBall;
			batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x + dimCorrectionX, dimension.y
					+ dimCorrectionY, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
					reg.getRegionHeight(), direction == VIEW_DIRECTION.LEFT, false);
		}
	}	
	
	public void update(float deltaTime){
		
		super.update(deltaTime);
		
		if(timeAtack >=0)
		{
			velocity.x = walk();
		}
		else
		{
			this.isAttacking = false;
		}
		
		timeAtack -=deltaTime;
	}
	

	
	public void setTimeAtack(float timeAtack)
	{
		this.timeAtack = timeAtack;
	}
	
	public float walk()	{
		return direction == VIEW_DIRECTION.RIGHT?terminalVelocity.x:-terminalVelocity.x;
	}
	
	public void setAttacking(boolean keyPressed){
		if(keyPressed){
			if(isAttacking){
				//do nothing
			}else{
				isAttacking = true;
			  timeAtack = MAX_TIME;
			}
		}else{
			isAttacking = false;
		}
	}
	
	public void setDirection(VIEW_DIRECTION direction){
		this.direction = direction;
	}

}

