/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.powercord869.code.robot.autonomous;

import com.powercord869.code.robot.Fan;
import com.powercord869.code.robot.RobotDrive;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Kevin
 */
public abstract class AutonomousRoutine {

    protected static int DRIVE_AND_TURN_ROUTINE = 1;
    protected static int DRIVE_AND_RUN_FAN_ROUTINE = 2;
    protected static int DRIVE_RUN_FAN_DRIVE_BACK_AND_SHIT = 3;
    private static EncoderControl encoders;
    private Fan fan;
    private double distanceToTravel = 0;//change 
    private static DriverStation driver;
    private int routineNumber;
    RobotDrive drive;
    protected Timer time;
    protected static double distanceTraveled = 0;
    private static double DISTANCE_TO_SPIN = (Math.PI * 11 * EncoderControl.CLICKS_PER_INCH);
    private boolean reset = false;

    public AutonomousRoutine() {
        time = new Timer();
        fan = Fan.getInstance();
        encoders = EncoderControl.getInstance();
        drive = RobotDrive.getInstance();
        driver = DriverStation.getInstance();
    }

    protected void setRoutineNumber(int number) {
        this.routineNumber = number;
    }

    protected void setDistanceToTravel(double travel) {
        distanceToTravel = travel;
    }

    protected Timer getTimer() {
        return time;
    }

    public RobotDrive getDrive() {
        return drive;
    }

    public double getDistanceToTravel() {
        return distanceToTravel;
    }

    public double getDistanceTraveled() {
        return (encoders.getLeftDistance() + encoders.getRightDistance()) / 2;
    }

    protected DriverStation getDriverStation() {
        return driver;
    }

    protected Fan getFan() {
        return fan;
    }

    public int getRoutineNumber() {
        return routineNumber;
    }

    public EncoderControl getEncoders() {
        return encoders;
    }

    public double getEncoderOffset() {
        return encoders.getLeftDistance() - encoders.getRightDistance();
    }

    protected void drive(double distance) {
        double offset = getEncoderOffset();
        if (getDistanceTraveled() < distance) {
            if (offset > 40) {
                drive.setLeftMotors(.4);
                drive.setRightMotors(.5);
            } else if (offset < -40) {
                drive.setRightMotors(.4);
                drive.setLeftMotors(.5);
            } else {
                drive.setRightMotors(1);
                drive.setLeftMotors(1);
            }
        } else {
            drive.setLeftMotors(0);
            drive.setRightMotors(0);

        }

    }
    //im working on it! i just wrote some random stuff to get my mind going, ill get it though6

    protected void turn(int degrees) {
        double distanceTurned = Math.abs(getEncoders().getLeftDistance()) + Math.abs(getEncoders().getRightDistance());
     double change = (90 / 360) * DISTANCE_TO_SPIN;
     if(getEncoders().getLeftDistance() + getEncoders().getRightDistance() != 0 && !reset){
         getEncoders().reset();
         reset = true;
     }
     if(distanceTurned < change && degrees < 180){
     drive.setLeftMotors(.5);
     drive.setRightMotors(-.5);    
     
    }else if(distanceTurned < change && degrees >= 180){
          drive.setLeftMotors(-.5);
          drive.setRightMotors(.5);  
     }else{
         drive.setLeftMotors(0);
         drive.setRightMotors(0);
     }
}

    public abstract void run();

    public abstract boolean validate(); //i think this is pointless now, not sure yet though. I should be though :/

    public abstract void stop();
}
