package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.Joystick;

public class InputsDrive {
	
	public Joystick driveStick = new Joystick(0); //driver
	
		//Thumbsticks
			public double getLeftSpeed() {
				return -1 * driveStick.getRawAxis(1); //left thumbstick vertical, multiplied by -1 to un-invert
			}
			
			public double getRightSpeed() {
				return driveStick.getRawAxis(3); //right thumbstick vertical, un-inverting not necessary for some reason
			}
		
		//Bumpers
			public boolean getLeftBumper() {
				return driveStick.getRawButton(5);
			}
			
			public boolean getRightBumper() {
				return driveStick.getRawButton(6);
			}
		
		//Triggers
			public boolean getLeftTrigger() {
				return driveStick.getRawButton(7);
			}
			
			public boolean getRightTrigger() {
				return driveStick.getRawButton(8);
			}
		
		
		//Buttons
			public boolean getAButton() {
				return driveStick.getRawButton(2);
			}
			
			public boolean getBButton() {
				return driveStick.getRawButton(3);
			}
			
			public boolean getXButton() {
				return driveStick.getRawButton(1);
			}
			
			public boolean getYButton() {
				return driveStick.getRawButton(4);
			}
}
