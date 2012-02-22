package de.c3t.BehaviorRoboter.Behaviors;

import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.robotics.subsumption.Behavior;
import de.c3t.BehaviorRoboter.Main;
import de.c3t.BehaviorRoboter.RC.ComunicationConstants;

public class BluetoothBehavior implements Behavior {
	Boolean isConnected = false;

	Boolean hasControl = false;

	Boolean takeControl = false;

	Boolean supressed = false;

	public BluetoothBehavior() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					// main loop
					while (true) {
						LCD.drawString("waiting", 0, 4);
						// Listen for incoming connection
						NXTConnection btc = Bluetooth.waitForConnection();
						LCD.drawString("has con", 0, 4);
						btc.setIOMode(NXTConnection.RAW);
						// The InputStream for read data
						DataInputStream dis = btc.openDataInputStream();

						isConnected = true;
						LCD.drawString("isConnected: "+isConnected, 0, 3);
						// loop for read data
						while (isConnected) {
							Byte n = -1;
							n = dis.readByte();
							if (n == ComunicationConstants.takeControl) {
								takeControl = !takeControl;
								LCD.drawString("takeControl: "+takeControl, 0, 0);
							} else if (n == ComunicationConstants.exit) {
								isConnected = false;
							} else if (hasControl) {
								switch (n) {
									case ComunicationConstants.forward:
										Main.pilot.travel(5);
										break;

									case ComunicationConstants.backward:
										Main.pilot.travel(-5);
										break;

									default:
										break;
								}
							}
						}
						takeControl = false;
						dis.close();
						try {
							Thread.sleep(100); // wait for data to drain
						} catch (InterruptedException e) {
						}
						btc.close();
					}
				} catch (IOException e) {
				}
			}
		}).start();
	}

	@Override
	public void action() { // TODO: command-dispatcher in die action()-funktion verschieben
		LCD.drawString("in action  ", 0, 5);
		supressed = false;
		hasControl = true;
		LCD.drawString("hasControl: "+hasControl, 0, 1);
		LCD.drawString("supressed: "+supressed, 0, 2);
		while (!supressed)
			Thread.yield();
		hasControl = false;
		LCD.drawString("hasControl: "+hasControl, 0, 1);
		LCD.drawString("supressed: "+supressed, 0, 2);
		LCD.drawString("left action", 0, 5);
	}

	@Override
	public void suppress() {
		supressed = true;
		LCD.drawString("supressed: "+supressed, 0, 2);
	}

	@Override
	public boolean takeControl() {
		LCD.drawString("TakeControl()"+takeControl, 0, 6);
		return takeControl;
	}

}
