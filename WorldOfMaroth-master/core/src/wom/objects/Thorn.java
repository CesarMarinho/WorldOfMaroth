package wom.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wom.util.Assets;

public class Thorn extends GameObject {
	
public TextureRegion thorn;
	
	public Thorn () {
		thorn = Assets.instance.thorn.thorn;
		
		dimension.set(0.5f, 0.5f);
		bounds.set(0, 0, dimension.x, dimension.y);
	}
	

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = thorn;

		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
			rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
			false, false);

		
		batch.setColor(1, 1, 1, 1);
		
	}

}
