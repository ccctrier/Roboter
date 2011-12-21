package de.c3t.BehaviorRoboter.Behaviors;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;
import de.c3t.BehaviorRoboter.Main;

public class AvoidCollisions implements Behavior {
	private UltrasonicSensor usonic;

	public AvoidCollisions() {
		usonic = new UltrasonicSensor(SensorPort.S2);
	}

	@Override
	public void action() {
		Main.pilot.travel(-20);
		Main.pilot.rotate(10);
	}

	@Override
	public void suppress() {
	}

	@Override
	public boolean takeControl() {
		return usonic.getDistance() <= 20; //we want control, if distance < 20
	}

}
