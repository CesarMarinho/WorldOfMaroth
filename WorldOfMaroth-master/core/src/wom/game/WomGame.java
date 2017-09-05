package wom.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Interpolation;

import wom.screen.DirectedGame;
import wom.screen.GamePlayScreen;
import wom.screen.MenuScreen;
import wom.util.Achievements;
import wom.util.Assets;
import wom.util.AudioManager;
import wow.transition.ScreenTransition;
import wow.transition.ScreenTransitionSlice;

public class WomGame extends DirectedGame
{
	@Override
	public void create() 
	{
		Achievements.instance.init();
		Assets.instance.init(new AssetManager());	
		
		ScreenTransition transition = ScreenTransitionSlice.init(2, ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
		setScreen(new MenuScreen(this), transition);
	}
}
