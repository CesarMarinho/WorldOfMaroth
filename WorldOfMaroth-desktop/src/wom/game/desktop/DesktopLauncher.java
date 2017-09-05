package wom.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import wom.game.WomGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "World of Maroth";
		config.width = 800;
		config.height = 600;
		
		new LwjglApplication(new WomGame(), config);
	}
}
