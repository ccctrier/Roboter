import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
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
		// Button.waitForPress();

		appEnd();
	}

	public static void main(String[] args) {
		LCD.drawString("CCC Trier", 0, 0);

		Runnable buttons = new Runnable() {
			public void run() {
				while (true) {
					if (Button.LEFT.isPressed() || Button.ENTER.isPressed()) {
						appEnd();
					}
				}
			}
		};
		Thread buttonsThread = new Thread(buttons);
		buttonsThread.start();

		Runnable head = new Runnable() {
			public void run() {
				while (appRunning) {
					Motor.C.rotate(80);
					Motor.C.rotate(-80);
					Motor.C.rotate(80);
					Motor.C.rotate(-80);
				}
			}
		};
		Thread headThread = new Thread(head);
		headThread.start();

		Runnable headSensor = new Runnable() {
			public void run() {
				UltrasonicSensor usonic = new UltrasonicSensor(SensorPort.S2);

				while (appRunning) {
					int dist = usonic.getDistance();
					// LCD.drawString("Distance: " + dist, 0, 0);

					if (dist <= 100) {
						appEnd();
					}

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread headSensorThread = new Thread(headSensor);
		headSensorThread.start();

		// File file = new File("NyanCatJazz.wav");
		// Sound.playSample(file, 100);

		CCCT traveler = new CCCT();
		traveler.pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
		traveler.go();
	}

	public static void appEnd() {
		appRunning = false;
		System.exit(0);
	}
}
