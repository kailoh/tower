package kloh.gameengine;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Sound {

	private AudioClip _sound1;

	public Sound(String path) {
		URL url = Sound.class.getResource(path);
		_sound1 = Applet.newAudioClip(url);
	}

	public void play() {
		_sound1.play();
	}

	public void stop() {
		_sound1.stop();
	}

	public void loop() {
		_sound1.loop();
	}
}

