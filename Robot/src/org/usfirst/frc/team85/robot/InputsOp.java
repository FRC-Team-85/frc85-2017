package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.Joystick;

public class InputsOp {
	
	public Joystick opStick = new Joystick(1); //operator
	
		//Thumbtwigs 
			public double getLeftVert() {
				return -1 * opStick.getRawAxis(1); //left thumbstick vertical, multiplied by -1 to un-invert
			}
	
			public double getLeftHorz() {
				return -1 * opStick.getRawAxis(0); //left thumbstick vertical, multiplied by -1 to un-invert
			}
	
			public double getRightVert() {
				return opStick.getRawAxis(3); //right thumbstick vertical, un-inverting not necessary for some reason
			}
	
			public double getRightHorz() {
				return -1 * opStick.getRawAxis(2); //left thumbstick vertical, multiplied by -1 to un-invert
			}
		
		//Bumper cars
			public boolean getLeftBumper() {
				return opStick.getRawButton(5);
			}
			
			public boolean getRightBumper() {
				return opStick.getRawButton(6);
			}
		
		//I'm Triggered
			public boolean getLeftTrigger() {
				return opStick.getRawButton(7);
			}
			
			public boolean getRightTrigger() {
				return opStick.getRawButton(8);
			}
		
		//Don't push my buttons
			public boolean getAButton() {
				return opStick.getRawButton(2);
			}
			
			public boolean getBButton() {
				return opStick.getRawButton(3);
			}
			
			public boolean getXButton() {
				return opStick.getRawButton(1);
			}
			
			public boolean getYButton() {
				return opStick.getRawButton(4);
			}
			
}
