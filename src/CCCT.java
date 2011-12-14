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
	static CCCT traveler = new CCCT();
	static boolean appRunning = true;
	static boolean roboterMoving = false;

	public void go() {
		pilot.travel(80, true);
		roboterMoving = true;
		initHead();

		while (pilot.isMoving()) {
			if (bump.isPressed()) {
				traveler.ninjaMove();
				break;
			}
		}
	}

	public void ninjaMove() {
		LCD.drawString("ninjaMove", 0, 0);

		roboterMoving = false;
		pilot.stop();
		pilot.travel(-20, true);

		while (pilot.isMoving()) {
			pilot.stop();
		}

		Motor.A.rotate(5);
		Motor.B.rotate(-5);

		traveler.go();
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

		// File file = new File("NyanCatJazz.wav");
		// Sound.playSample(file, 100);

		traveler.pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
		traveler.go();
	}

	public static void appEnd() {
		appRunning = false;
		System.exit(0);
	}

	public static void initHead() {
		Runnable head = new Runnable() {
			public void run() {
				while (appRunning || roboterMoving) {
					if (!roboterMoving) {
						break;
					}
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

				while (appRunning || roboterMoving) {
					if (!roboterMoving) {
						break;
					}

					int dist = usonic.getDistance();
					// LCD.drawString("Distance: " + dist, 0, 0);

					if (dist <= 100) {
						// appEnd();
						traveler.ninjaMove();
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
	}
}
