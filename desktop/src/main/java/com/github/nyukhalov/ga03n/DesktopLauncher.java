
package com.github.nyukhalov.ga03n;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Genetic AI";
		config.useGL30 = false;
		config.width = 800;
		config.height = 600;

		new LwjglApplication(new Simulation(), config);
	}
}
