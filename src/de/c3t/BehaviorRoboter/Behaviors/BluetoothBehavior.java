package de.c3t.BehaviorRoboter.Behaviors;

import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.robotics.subsumption.Behavior;
import de.c3t.BehaviorRoboter.Main;
import de.c3t.BehaviorRoboter.RC.ComunicationConstants;

public class BluetoothBehavior implements Behavior {
	Boolean isrunning = true;
	Boolean takeControl = false;
	Boolean supressed = false;
	
	public BluetoothBehavior() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					// main loop
					while (true) {
						// Listen for incoming connection
						NXTConnection btc = Bluetooth.waitForConnection();
						btc.setIOMode(NXTConnection.RAW);
						// The InputStream for read data
						DataInputStream dis = btc.openDataInputStream();
						// loop for read data
						takeControl = true;
						isrunning = true;
						while (isrunning) {
							Byte n = -1;
							n = dis.readByte();
							switch (n) {
								case ComunicationConstants.exit:
									isrunning = false;
									break;
									
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
						takeControl = false;
						dis.close();
						try {
							Thread.sleep(100); // wait for data to drain
						} catch (InterruptedException e) {}
						btc.close();
					}
				} catch (IOException e) {	
				}
			}
		}).start();
	}

	@Override
	public void action() {
		supressed = false;
		while (!supressed)
			Thread.yield();
	}

	@Override
	public void suppress() {
		supressed = true;
	}

	@Override
	public boolean takeControl() {
		return takeControl;
	}

}
