package de.c3t.BehaviorRoboter.Behaviors;

import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;

public class ShutdownOnEnter implements Behavior {

	@Override
	public void action() {
		System.exit(0);
	}

	@Override
	public void suppress() {
		// not possible
	}

	@Override
	public boolean takeControl() {
		return Button.ENTER.isPressed();
	}

}
