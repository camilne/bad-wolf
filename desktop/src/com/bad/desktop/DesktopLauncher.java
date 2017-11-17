package com.bad.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bad.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.title = "Bad Wolf";
		config.vSyncEnabled = true;
		//config.useGL30 = true;
		config.resizable = false;
		new LwjglApplication(new Main(), config);
	}
}
