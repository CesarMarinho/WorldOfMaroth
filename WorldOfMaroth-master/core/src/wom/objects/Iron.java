package wom.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import wom.util.Assets;

public class Iron extends GameObject {
	
	public TextureRegion iron;
	
	public boolean collected;
	
	public Iron () {
		dimension.set(0.5f,0.5f);

		iron = Assets.instance.iron.iron;

		bounds.set(0, 0, dimension.x, dimension.y);

		collected = false;
	}




	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		if (collected) return;

		TextureRegion reg = null;

		reg = iron;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
			rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
	}

	public int getScore() {		
		return 100;
	}
	

}
