package wom.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wom.util.Assets;

public class Ground extends GameObject {

	
	public TextureRegion ground;

	
	public Ground () {
		
		ground = Assets.instance.ground.ground;
		
		dimension.set(1, 1f);
		bounds.set(0, 0, dimension.x , dimension.y);

	}




	@Override
	public void render (SpriteBatch batch) {
		TextureRegion reg = null;

		reg = ground;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
			rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
			false, false);

		
		batch.setColor(1, 1, 1, 1);
	}

	

	
	
}
