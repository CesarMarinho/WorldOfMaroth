package wom.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wom.util.Assets;

public class Rock extends GameObject{
	
	public TextureRegion rock;
	
	public Rock () {
		rock = Assets.instance.rock.rock;
		
		dimension.set(1f, 1f);
		bounds.set(0, 0, dimension.x , dimension.y);
	}


	@Override
	public void render(SpriteBatch batch) {
		
		TextureRegion reg = null;

		reg = rock;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
			rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
			false, false);

		
		batch.setColor(1, 1, 1, 1);
		
	}
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);

		
	}

}
