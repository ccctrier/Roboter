/**
 Howto:
 
 LeJOS installieren:
 
 http://lejos.sourceforge.net/nxj-downloads.php
 
 Kompilieren:
 
 nxjc CCCT.java
 
 Upload (Am besten mit Bluetooth):
 
 nxj -r Start
 
 API-Doku:
 
 http://lejos.sourceforge.net/nxt/nxj/api/index.html
*/


import javax.microedition.lcdui.Graphics;

import lejos.nxt.*;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.navigation.TachoPilot;

public class CCCT
{
	static Graphics graphics;
	static Pilot pilot;
	
	public static void main (String[] aArg)
	{
		LCD.drawString("CCC - Trier", 0, 0);
		pilot = new TachoPilot(2.1f, 4.4f, Motor.A, Motor.B, true);
		
		//Ultraschall Test: Ausgabe auf Display.
		UltrasonicSensor usonic = new UltrasonicSensor(SensorPort.S1);
//			for(int i = 0; i<100; i++)
//			{
//				int dist = p.getDistance();
//				LCD.drawString("Distance: " + dist, 0, 0);
//				Thread.sleep(500);
//			}
		
		//Kopf bewegen:
		Runnable r = new Runnable()
		{
			public void run()
			{
				while(true) {
					
					Motor.C.setSpeed(200);
					Motor.C.rotate(-150);
					//LCD.drawString("ROTATE! ", 0, 0);
					Motor.C.rotate(300);
					Motor.C.rotate(-150);
					
				}
				
			}
		};
		Thread t=new Thread(r);
		t.start();
		
//		int x = 50;
//		int y = 32;
//		
//		graphics.clear();
//		graphics.drawArc(x-10, y-10, 20, 20, 0, 360);
//		graphics.drawArc(x-20, y-20, 40, 40, 0, 360);
//		graphics.drawArc(x-30, y-30, 60, 60, 0, 360);
//		
//		for(int i = 15; i<=85;i++)
//		{
//			graphics.setPixel(1, i, y);
//		}
//		
//		for(int i = 0; i<=64;i++)
//		{
//			graphics.setPixel(1, x, i);
//		}
//		
//		graphics.refresh();
		
		//Fahre geradeaus und bei einem Hindernis drehe dich
		if(true) {
			pilot.setMoveSpeed(200);
			pilot.setTurnSpeed(200);
			pilot.forward();
			
			while(true){
				
				if(Button.LEFT.isPressed() || Button.ENTER.isPressed())
					break;
				
				if(usonic.getDistance() < 50) {
					
					pilot.stop();
					pilot.travel(-5);
					pilot.rotate(180);
					pilot.forward();
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}	
			}
		}
		System.exit(0);	
	}
}
