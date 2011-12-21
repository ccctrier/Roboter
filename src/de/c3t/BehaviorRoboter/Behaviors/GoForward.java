package de.c3t.BehaviorRoboter.Behaviors;

import de.c3t.BehaviorRoboter.Main;
import lejos.robotics.subsumption.Behavior;

public class GoForward implements Behavior {

	private boolean supressed;

	@Override
	public void action() {
		supressed = false;
		Main.pilot.forward();
		while (!supressed)
			Thread.yield();
		Main.pilot.stop();
	}

	@Override
	public void suppress() {
		supressed = true;
	}

	@Override
	public boolean takeControl() {
		// we ALWAYS want control
		return true;
	}

}
