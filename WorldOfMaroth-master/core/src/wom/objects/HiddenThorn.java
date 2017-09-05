package wom.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HiddenThorn extends Thorn 
{
	
	private boolean hidden = false;
	
	public boolean ishidden()
	{
		return hidden;
	}
	
	public void show()
	{
		hidden = false;
	}
	
	public void hidden()
	{
		hidden = true;
	}
	
	@Override
	public void render(SpriteBatch batch) 
	{
		if(!hidden)
		{
			super.render(batch);	
		}
		
		
	}

}
