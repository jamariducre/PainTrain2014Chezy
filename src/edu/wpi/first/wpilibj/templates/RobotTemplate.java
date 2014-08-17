/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    CANJaguar motor1, motor2, motor3;
    Encoder encode;
    Joystick joy;
    Timer time;
    double power;
    double can1Volt, can1Current,can2Volt,can2Current,can3Volt,can3Current;
    double currentTime;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
      encode = new Encoder(1,5);
      encode.start();
      encode.reset();
        try{
        motor1 = new CANJaguar(2);
        motor2 = new CANJaguar(5);
        motor3 = new CANJaguar(4);
        }catch(Exception e){
            System.out.println(e);
        }
        time = new Timer();
        time.start();
        joy = new Joystick(1);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
       try{
        can1Volt = motor1.getOutputVoltage();
        can1Current = motor1.getOutputCurrent();
        can2Volt = motor2.getOutputVoltage();
        can2Current = motor2.getOutputCurrent();
        can3Current = motor3.getOutputCurrent();
        can3Volt = motor3.getOutputVoltage();
       }catch(Exception e){
            System.out.println(e);
       }
      power = joy.getY();
      System.out.println(encode.get());
      if(encode.get() > 65){
          try{
          motor1.set(0);
          motor2.set(0);
          motor3.set(0);
          }catch(Exception e){
              System.out.println(e);
          }
      }else{
      try{
      motor1.set(power);
      motor2.set(power);
      motor3.set(power);
      }catch (Exception e){
          System.out.println(e);
      }
      }
       System.out.println(can1Volt + ", " + can1Current + ", " + can2Volt + ", " + can2Current + ", " + can3Volt + ", " + can3Current);
       System.out.println(getRPM());
    }
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    private double getRPM(){
        double rpm = 0;                                           //Declares an array of integers which the function will return.
        currentTime = time.get();                                 //Sets currentTime to the time calculatged from FPGA
        rpm = encode.getRaw() / (15000*currentTime);              //dAngle*(60000000us/360deg)/dTime = RPM                                                        //Resets encoder.                                                       //Resets the right wheel encoder.
        time.reset();                                             //This resets the timer.
        encode.reset();
        return rpm;
    
    }
}
