import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class CCCT {
	DifferentialPilot pilot;
	TouchSensor bump = new TouchSensor(SensorPort.S1);
	static boolean appRunning = true;

	public void go() {
		pilot.travel(80, true);
		while (pilot.isMoving()) {
			if (bump.isPressed())
				pilot.stop();
		}
		// System.out.println(" " + pilot.getMovement().getDistanceTraveled());
		appRunning = false;

		// Button.waitForPress();
		System.exit(0);
	}

	public static void main(String[] args) {
		LCD.drawString("CCC Trier", 0, 0);
		Runnable r = new Runnable() {
			public void run() {
				while (appRunning) {
					Motor.C.rotate(80);
					Motor.C.rotate(-80);
					Motor.C.rotate(80);
					Motor.C.rotate(-80);
				}
			}
		};
		Thread t = new Thread(r);
		t.start();

		// File file = new File("NyanCatJazz.wav");
		// Sound.playSample(file, 100);

		CCCT traveler = new CCCT();
		traveler.pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
		traveler.go();
	}
}
