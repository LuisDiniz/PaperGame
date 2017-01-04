package br.cefetmg.games.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import br.cefetmg.games.Game;

public class DesktopLauncher {
    
        private static final int WIDTH = 1000;
        private static final int HEIGHT = 500;
    
	public static void main (String[] arg) {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.width = WIDTH;
            config.height = HEIGHT;
            new LwjglApplication(new Game(), config);
	}
}
