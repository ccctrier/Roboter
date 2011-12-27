package de.c3t.BehaviorRoboter;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import de.c3t.BehaviorRoboter.Behaviors.AvoidCollisions;
import de.c3t.BehaviorRoboter.Behaviors.GoForward;
import de.c3t.BehaviorRoboter.Behaviors.ListenToSound;
import de.c3t.BehaviorRoboter.Behaviors.ShutdownOnEscape;

public class Main {
	public static DifferentialPilot pilot;

	public static boolean isShuttingDown;

	public static void main(String[] args) {
		pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
		Behavior b1 = new GoForward();
		Behavior b2 = new ListenToSound();
		Behavior b3 = new AvoidCollisions();
		Behavior b4 = new ShutdownOnEscape();
		Behavior[] bArray = { b1, b2, b3, b4 };
		Arbitrator arby = new Arbitrator(bArray);
		arby.start();
	}

}
