package de.c3t.BehaviorRoboter.Behaviors;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;
import de.c3t.BehaviorRoboter.Main;

public class AvoidCollisions implements Behavior {
	private UltrasonicSensor usonic;

	private TouchSensor bump;

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
