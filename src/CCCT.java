import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Robot that stops if it hits something before it completes its travel.
 */
public class CCCT {
	DifferentialPilot pilot;
	TouchSensor bump = new TouchSensor(SensorPort.S1);

	public void go() {
		pilot.travel(-200, true);
		while (pilot.isMoving()) {
			if (bump.isPressed())
				pilot.stop();
		}
		System.out.println(" " + pilot.getMovement().getDistanceTraveled());
		Button.waitForPress();
	}

	public static void main(String[] args) {

		Motor.C.forward();
		Motor.C.rotate(90);

		// CCCT traveler = new CCCT();
		// traveler.pilot = new DifferentialPilot(2.25f, 5.5f, Motor.C, null);
		// traveler.go();
	}
}
