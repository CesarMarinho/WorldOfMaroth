package wom.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wom.util.Assets;

public class Turtle extends GameObject{
	
	public AtlasRegion turtle;
	private Animation animNormal;
	
	public Turtle()
	{
		dimension.set(1f, 1f);
		turtle = Assets.instance.turtle.body;
		bounds.set(0, 0, dimension.x, dimension.y);
		animNormal = Assets.instance.turtle.animNormal;
		
		setAnimation(animNormal);
}

	@Override
	public void render(SpriteBatch batch) 
	{
			
		TextureRegion reg = turtle;
		
		reg = animation.getKeyFrame(stateTime, true);
		
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
			rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
		batch.setColor(1, 1, 1, 1);
	}	
	
	public void update(float deltaTime)
	{
		
	}

}
