package wom.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wom.util.Assets;

public abstract class KeyObject extends GameObject {
	
public TextureRegion iron;
	
	public boolean collected;
	
	public KeyObject() {
		dimension.set(0.5f, 0.5f);

		iron = Assets.instance.iron.iron;

		bounds.set(0, 0, dimension.x, dimension.y);

		collected = false;
	}

}
