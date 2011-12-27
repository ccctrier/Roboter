package de.c3t.BehaviorRoboter.Behaviors;

import de.c3t.BehaviorRoboter.Main;
import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;

public class ShutdownOnEnter implements Behavior {

	@Override
	public void action() {
		Main.isShuttingDown = true;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
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
