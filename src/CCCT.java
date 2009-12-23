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


import lejos.nxt.*;

public class CCCT
{
	
	public static void main (String[] aArg)
	{
		LCD.drawString("CCC - Trier", 0, 0);
		
		//Ultraschall Test: Ausgabe auf Display.
			UltrasonicSensor p = new UltrasonicSensor(SensorPort.S1);
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
		
		//Fahre geradeaus und bei einem Hindernis drehe dich
		if(true) {
			
			Motor.A.setSpeed(500);
			Motor.B.setSpeed(500);
			Motor.A.forward();
			Motor.B.forward();	
			
			int z = 0;
			
			while(true){
				
				if(Button.LEFT.isPressed() || Button.ENTER.isPressed())
					break;
				
				if(z>10)
					//break;
				
				if(p.getDistance() < 50) {
					
					z++;
					
					Motor.A.stop();
					Motor.B.stop();
					
					
					Motor.A.backward();
					Motor.B.backward();
					
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}
					Motor.A.stop();
					Motor.B.stop();
					
					Motor.A.rotate(360);
					//Motor.B.rotate(-720);
					
					Motor.A.stop();
					Motor.B.stop();
					
					Motor.A.forward();
					Motor.B.forward();		
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}	
			}
		}	
		//ENDE
		System.exit(0);	
	}
}
