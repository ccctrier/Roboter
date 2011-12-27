package de.c3t.BehaviorRoboter.Behaviors;

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.SoundSensor;
import lejos.robotics.subsumption.Behavior;

public class ListenToSound implements Behavior {
	private boolean supressed;

	  public ListenToSound() {
		    SoundSensor sound = new SoundSensor(SensorPort.S3);
		    int soundLevel;
			LCD.clear();
		    LCD.drawString("Level: ", 0, 0);
		    soundLevel = sound.readValue();
		    LCD.drawInt(soundLevel,3,7,0);
		    try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    }
		  

	@Override
	public boolean takeControl() {
		// never take control until code is finished
		return false;
	}

	@Override
	public void action() {
		// do this if you have control
		supressed = true;
		Sound.beep();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sound.beep();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void suppress() {
		// stop action when called
		supressed = true;
		
	}
}
