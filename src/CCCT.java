import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class CCCT {
	DifferentialPilot pilot;
	TouchSensor bump = new TouchSensor(SensorPort.S1);

	public void go() {
		pilot.travel(20, true);
		while (pilot.isMoving()) {
			if (bump.isPressed())
				pilot.stop();
		}
		System.out.println(" " + pilot.getMovement().getDistanceTraveled());
		Button.waitForPress();
	}

	public static void main(String[] args) {
		Motor.C.rotate(180);
		Motor.C.rotate(-180);
		Motor.C.rotate(180);
		Motor.C.rotate(-180);

		// File file = new File("NyanCatJazz.wav");
		// Sound.playSample(file, 100);

		CCCT traveler = new CCCT();
		traveler.pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
		traveler.go();
	}
}
