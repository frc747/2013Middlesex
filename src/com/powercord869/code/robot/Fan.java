/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.powercord869.code.robot;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author Kevin
 */
public class Fan implements RobotControllable {
    private Victor fanControl;
    private Victor fanBlades;
    private Logitech controller;
    private static Fan fan = new Fan();
     private double move;
     private double spin;
    private Fan() {
    
        this.controller = Logitech.getInstance();
        fanControl = new Victor(FAN_CONTROL);
        fanBlades = new Victor(FAN_BLADES);
    }
    
    public static Fan getInstance(){
        return fan;
    }

    public void control() {
        // I like my ternary operators :(
       move = (controller.getLeftStickY() < -.9 || controller.getDpadY()< -.9)  ? 1.0 : (controller.getLeftStickY() > .9 || controller.getDpadY() > .9)  ? -1.0 : 0;
       spin = controller.getR2() &&  !controller.getR1()  ? -1.0 :  controller.getR2() &&  controller.getR1() ? 1 : 0;
       oscillateFan(spin);
       moveFan(move);
    }
    
  
   
    public void oscillateFan(double intensity){
     fanBlades.set(intensity);
    }
    
    public void moveFan(double intensity){
        fanControl.set(intensity);
       
    }

    public void stop() {
        fanControl.set(0);
        fanBlades.set(0);
    }
}
