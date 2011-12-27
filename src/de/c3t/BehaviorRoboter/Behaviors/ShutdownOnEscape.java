package de.c3t.BehaviorRoboter.Behaviors;

import de.c3t.BehaviorRoboter.Main;
import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;

public class ShutdownOnEscape implements Behavior {

	@Override
	public void action() {
		Main.isShuttingDown = true;
		try {
			Thread.sleep(2200);
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
		return Button.ESCAPE.isPressed();
	}

}
