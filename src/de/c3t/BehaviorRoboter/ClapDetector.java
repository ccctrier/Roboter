package de.c3t.BehaviorRoboter;
//The following code is "stolen" from http://code.google.com/p/josemariakim/source/browse/trunk/lab3/ClapController/ClapDetector.java
// and http://josemariakim.blogspot.com/2010/09/lab-session-3.html
import lejos.nxt.*;
/**
 * Clap car controller 
 */

public class ClapDetector 
{
        private static int samplePeriode = 5;
        private static SoundSensor sound = new SoundSensor(SensorPort.S3);
        private static int last_read = 0;

        private static boolean rising = false;

        // This function reads sound value and looks for maximum.
        // Returns maximum based on the last 3 readings.
        private static int lookForMax() throws Exception
        {
                int read, max = 0;

                read = sound.readValue();
                LCD.drawInt(read,4,10,2); 
                if (read > last_read)
                        rising = true;
                if (read < last_read)
                {
                        if (rising) max = last_read;
                        rising = false;
                }

                last_read = read;
                return max;
        }

        // This function will block while looking for a maximum peak.
        // It looks for maximum peak between min_max and max_max limits 
        // between timestart and timeend looking for the maximum.
        // If timestart = 0 the function returns when first maximum is found
        private static boolean lookForMaxInInterval(int min_max, int max_max, 
                                                            int timestart, int timeend) throws Exception
        {
                int count = 0; 
                int max;
                int cntstart = timestart/samplePeriode;
                int cntend = timeend/samplePeriode;

                // Looking for maximum within limits
                while (true)
                {
                        // Wait sample period
                        count++;
                        Thread.sleep(samplePeriode);

                        // Look for maximum peak
                        max = lookForMax();
                        LCD.drawInt(max,4,10,1); 

                        if (min_max <= max && max <= max_max)
                        {
                                // Max found
                                if (cntstart == 0) return true;
                                if (count < cntstart) return false; // Maximum to early
                                return true; // Maximum within limits
                        }
                        if (cntstart > 0 && count > cntend)
                                return false; // Timeout looking for maximum
                }

                        }

        // Wait for clap sound peak pattern
        // Peak 1 
        // Peak 2
        // Peak 3
        public static void waitForClapSoundPattern() throws Exception
        {
                int state = 1;
                boolean waiting = true;

                LCD.clear();
                LCD.drawString("State 1", 0, 0);
                LCD.refresh();

                while (waiting)
                {
                        switch (state)
                        {
                        case 1:
                                if (lookForMaxInInterval(50,100,0,0)) // Peak 1
                                {       
                                        LCD.clear();
                                        LCD.drawString("State 2", 0, 0);
                                        LCD.refresh();
                                        state = 2;
                                }
                                break;

                        case 2:
                                if (lookForMaxInInterval(50,100,500,2000)) // Peak 2
                                {       
                                        LCD.clear();
                                        LCD.drawString("State 3", 0, 0);
                                        LCD.refresh();
                                        state = 3;
                                }
                                else
                                {
                                        LCD.clear();
                                        LCD.drawString("State 1", 0, 0);
                                        LCD.refresh();
                                        state = 1; // Timeout - Start again looking for clap
                                }
                                break;

                        case 3:
                                LCD.clear();
                                LCD.drawString("Clap detected", 0, 0);
                                LCD.refresh();
                                waiting = false;
                                break;
                        }
                }
        }

        public static void main(String [] args) throws Exception
        {
                LCD.drawString("dB level: ",0,0);
                LCD.refresh();

                boolean res = true;

                //      while (! Button.ESCAPE.isPressed())
                //{

                waitForClapSoundPattern();

                Motor.B.forward();
                Motor.A.forward();

                waitForClapSoundPattern();
                Motor.B.stop();
                Motor.A.stop();

                /*
                        lookForMaxInInterval(50,100,0,0);

                        LCD.clear();
                        LCD.drawString("Clap1Detected", 0, 0);
                        LCD.refresh();

                        res = lookForMaxInInterval(50,100,500,2000);
                        if (res)
                        {
                                LCD.drawString("Clap2Detected", 0, 0);
                                LCD.refresh();
                        }
                        else
                        {
                                LCD.drawString("No clap 2 detected", 0, 0);
                                LCD.refresh();
                        }
                 */     

                //}

                LCD.clear();
                LCD.drawString("Program stopped", 0, 0);
                Thread.sleep(2000); 
        }
}
