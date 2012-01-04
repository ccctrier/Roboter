package de.c3t.BehaviorRoboter.Behaviors;

import java.io.File;

import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;
import de.c3t.BehaviorRoboter.ClapDetector;
import de.c3t.BehaviorRoboter.Main;

public class ListenToSound implements Behavior {
	private boolean wantControl = false;

	final File soundFile = new File("exterminate.wav");

	public ListenToSound() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Main.isShuttingDown) {
					try {
						ClapDetector.waitForClapSoundPattern();
					} catch (Exception e) {
					} // grrr, y do you throw ALL Exceptions?
					wantControl = true;
				}
			}
		}).start();
	}

	@Override
	public boolean takeControl() {
		return wantControl;
	}

	@Override
	public void action() {
		wantControl = false;
		int oldVolume = Sound.getVolume();
		Sound.setVolume(100);
		Sound.playSample(soundFile, 100); // 100 ... volume
		Sound.setVolume(oldVolume);

		Main.pilot.rotate(-180);
	}

	@Override
	public void suppress() { // action doesn't run for a long time -> we do not implement this
	}
}
