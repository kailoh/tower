package kloh.project;

import kloh.gameengine.Application;

public class Main {

	public static void main(String[] args) {
		Application application = new Application();
		application.setScreen(new MenuScreen(application));
		application.startup(); // begin processing events
	}

}