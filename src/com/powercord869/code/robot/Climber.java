/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.powercord869.code.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 *
 * @author Kevin
 */
public class Climber implements RobotControllable {
    DoubleSolenoid lifter;
    Logitech controller;
    private static Climber climber = new Climber();
    
    private Climber(){
        lifter = new DoubleSolenoid(LIFTER_SOLENOID_FORWARD, LIFTER_SOLENOID_BACK);
        controller = Logitech.getInstance();
    }
    
    public static Climber getInstance(){
        return climber;
    }
    
    public void control() {
        if(controller.getL2()){
            lifter.set(DoubleSolenoid.Value.kForward);
        }else{
            lifter.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public void stop() {
        
    }

  
    
}
