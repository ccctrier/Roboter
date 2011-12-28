package de.c3t.BehaviorRoboter.Behaviors;

import java.io.File;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;
import de.c3t.BehaviorRoboter.Main;

public class AvoidCollisions implements Behavior {
	private UltrasonicSensor usonic;

	private TouchSensor bump;

	private final File soundFile = new File("exterminate.wav");

	public AvoidCollisions() {
		usonic = new UltrasonicSensor(SensorPort.S2);
		bump = new TouchSensor(SensorPort.S1);
		Motor.C.setSpeed(80);
		Runnable head = new Runnable() {
			public void run() {
				while (!Main.isShuttingDown) {
					Motor.C.rotate(40);
					Motor.C.rotate(-80);
					Motor.C.rotate(40);
				}
			}
		};
		Thread headThread = new Thread(head);
		headThread.start();
	}

	@Override
	public void action() {
		int oldVolume = Sound.getVolume();
		Sound.setVolume(100);
		Sound.playSample(soundFile, 100); // 100 ... volume
		Sound.setVolume(oldVolume);

		Main.pilot.travel(-10);
		Main.pilot.rotate(80);
	}

	@Override
	public void suppress() {
	}

	@Override
	public boolean takeControl() {
		return bump.isPressed() || usonic.getDistance() <= 20; // we want control, if distance < 20 or
																														// bumper pressed
	}

}
