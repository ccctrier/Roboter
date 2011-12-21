package de.c3t.BehaviorRoboter;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import de.c3t.BehaviorRoboter.Behaviors.GoForward;

public class Main {
	public static DifferentialPilot pilot;

	public static void main(String[] args) {
		pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
    Behavior b1 = new GoForward();
    Behavior [] bArray = {b1};
    Arbitrator arby = new Arbitrator(bArray);
    arby.start();
	}

}
