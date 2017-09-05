package wom.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wom.util.Assets;
import wom.util.AudioManager;
import wom.util.Constants;

public class Mage extends GameObject{

	
	public  float JUMP_TIME_MAX = 0.6f;
	private final float JUMP_TIME_MIN = 0.1f;
	public int bKeys = 0;
	public int yKeys = 0;
	public int gKeys = 0;	
	
	private Animation animNormal;
	private Animation animWalking;
	private Animation animAttacking;
	
	public boolean isDamage = false;
	public boolean isAttacking = false;
	public boolean isWalking = false;
	
	public float cooldown = -1;
	public enum VIEW_DIRECTION {
		LEFT, RIGHT
	}

	public enum JUMP_STATE {
		GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING
	}


	private TextureRegion regHead;

	public VIEW_DIRECTION viewDirection;

	public JUMP_STATE jumpState;
	public float timeJumping;

	public float timeLeftDamage;
	public ParticleEffect dustParticles = new ParticleEffect();

	public Mage () {
		init();
	}
	
	public void init ()
	{
		dimension.set(1, 1);

		animNormal = Assets.instance.mage.animNormal;
		animWalking = Assets.instance.mage.animWalking;
		animAttacking = Assets.instance.mage.animAttacking;
		setAnimation(animNormal);

		origin.set(dimension.x / 2, dimension.y / 2);

		bounds.set(0, 0, dimension.x -(dimension.x/10), dimension.y);

		terminalVelocity.set(3.0f, 4.0f);
		friction.set(12.0f, 0.0f);
		acceleration.set(0.0f, -25.0f);

		viewDirection = VIEW_DIRECTION.RIGHT;

		jumpState = JUMP_STATE.FALLING;
		timeJumping = 0;

	}
	
	public void setDamaged(boolean isDamage)
	{
		this.isDamage = isDamage;
		
		if (isDamage)
		{
			timeLeftDamage = Constants.TIME_DAMAGED;
		}
	}
	
	@Override
	public void update (float deltaTime) 
	{
		super.update(deltaTime);
		
		cooldown -= deltaTime;
		
		if (velocity.x != 0) {
			viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
		}
		
		if (timeLeftDamage > 0) 
		{
			timeLeftDamage -= deltaTime;
			if (timeLeftDamage < 0) 
			{
				timeLeftDamage = 0;
				setDamaged(false);
			}
		}
		
		if(isWalking){
			if(animation != animWalking)setAnimation(animWalking);
		}
		else {
			if(animation == animWalking)setAnimation(animNormal);
		}
		
		if(isAttacking){
			if(animation != animAttacking)setAnimation(animAttacking);
		}
		else {
			if(animation == animAttacking)setAnimation(animNormal);
		}
	}

	@Override
	protected void updateMotionY (float deltaTime) {
		switch (jumpState) {
		case GROUNDED:
			jumpState = JUMP_STATE.FALLING;
			if (velocity.x != 0) {
				dustParticles.setPosition(position.x + dimension.x / 2, position.y);
				dustParticles.start();
			}
			break;
		case JUMP_RISING:
			
			timeJumping += deltaTime;
			
			if (timeJumping <= JUMP_TIME_MAX) {
				
				velocity.y = terminalVelocity.y;
			}
			break;
		case FALLING:
			break;
		case JUMP_FALLING:
			timeJumping += deltaTime;
			if (timeJumping > 0 && timeJumping <= JUMP_TIME_MIN) {
				
				velocity.y = terminalVelocity.y;
			}
		}
		if (jumpState != JUMP_STATE.GROUNDED) {
			dustParticles.allowCompletion();
			super.updateMotionY(deltaTime);
		}
	}

	@Override
	public void render (SpriteBatch batch) {
		TextureRegion reg = regHead;

		float dimCorrectionX = 0;
		float dimCorrectionY = 0;
		
		if (isDamage)
		{
			batch.setColor(1, 1, 1, 0.5f);
		}
		
		reg = animation.getKeyFrame(stateTime, true);
		
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x + dimCorrectionX, dimension.y
			+ dimCorrectionY, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
			reg.getRegionHeight(), viewDirection == VIEW_DIRECTION.LEFT, false);

		batch.setColor(1, 1, 1, 1);
	}

	public void setJumping (boolean jumpKeyPressed) 
	{
		switch (jumpState) {
		case GROUNDED: // Character is standing on a platform
			if (jumpKeyPressed) {
				AudioManager.instance.play(Assets.instance.assetSound.jump);
				timeJumping = 0;
				jumpState = JUMP_STATE.JUMP_RISING;
			}
			break;
		case JUMP_RISING: 
			if (!jumpKeyPressed) {
				jumpState = JUMP_STATE.JUMP_FALLING;
			}
			break;
		case FALLING:
		case JUMP_FALLING:
			break;
		}
	}
	
	public void setWalking(boolean keyPressed){		
		if(keyPressed){
			if(isWalking){
				//do nothing
			}else{
				isWalking = true;
			}
		}else{
			isWalking = false;
		}
	}
	public void setAttacking(boolean keyPressed){
		if(keyPressed){
			if(isAttacking){
				//do nothing
			}else{
				isAttacking = true;
			}
		}else{
			isAttacking = false;
		}
	}
	
	
	public boolean hasGreenKeys()
	{
		return gKeys == 0?false:true;
	}
	
	public boolean hasYellowKeys()
	{
		return yKeys == 0?false:true;
	}
	
	public boolean hasBlueKeys()
	{
		return bKeys == 0?false:true;
	}
	
	public void removebKeys()
	{
		bKeys -=1;
	}
	
	public void removeyKeys()
	{
		yKeys -=1;
	}
	
	public void removegKeys()
	{
		gKeys -= 1;
	}
	
	
	public void addbKeys()
	{
		bKeys += 1;
	}
	
	public void addgKeys()
	{
		gKeys += 1;
	}
	
	public void addyKeys()
	{
		yKeys +=1;
	}
	
	
}
