package com.bad.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bad.Main;

public class DesktopLauncher {

	private static String[] defaultArgs = {
			"avatars/avatar1.png",
			"1280",
			"720",
			"false"
	};

	public static void main (String[] arg) {
		System.arraycopy(arg, 0, defaultArgs, 0, arg.length);

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Integer.parseInt(defaultArgs[1]);
		config.height = Integer.parseInt(defaultArgs[2]);
		config.fullscreen = Boolean.parseBoolean(defaultArgs[3]);
		config.title = "Obscurité";
		config.vSyncEnabled = true;
		config.resizable = false;
		new LwjglApplication(new Main(defaultArgs[0]), config);
	}
}
