package wom.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import wom.objects.GameObject;

public class CameraHelper
{
	
	private static final float MAX_ZOOM_IN = 1.5f;
	private static final float MAX_ZOOM_OUT  = 10.0f;
	private final float FOLLOW_SPEED = 4.0f;
	private Vector2 position;
	private float zoom;
	private GameObject target;
	
	public CameraHelper()
	{
		position = new Vector2(0, 0);
		zoom = 1.5f;
		target = null;
	}
	
	public void setPosition(int x, int y)
	{
		position.x = x;
		position.y = y;
	}
	
	public Vector2 getPosition()
	{
		return position;
	}
	
	public void setTarget(GameObject target)
	{
		this.target = target;
	}
	
	public void setZoom(float amount)
	{
		zoom = MathUtils.clamp(amount, MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}
	
	public void addZoom(float amount)
	{
		setZoom(amount + zoom);
	}
	
	public boolean hasTarget()
	{
		return target != null;
	}
	
	public boolean hasTarget(GameObject target)
	{
		return hasTarget() && this.target == target;
	}
	
	
	public void update(float deltaTime)
	{
		
		if(!hasTarget())
			return;
		

		position.lerp(target.position, FOLLOW_SPEED * deltaTime);
		position.y = Math.max(-1f, position.y);
	}
	
	public void applyTo(OrthographicCamera camera)
	{
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}

}
