package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Inputs {
	
	public Joystick driveStick = new Joystick(0); //driver
	public Joystick opStick = new Joystick(1); //operator
	
	
	public double getLeftSpeed() {
		return -1 * driveStick.getRawAxis(1); //left thumbstick vertical, multiplied by -1 to un-invert
	}
	
	public double getRightSpeed() {
		return driveStick.getRawAxis(3); //right thumbstick vertical, un-inverting not necessary for some reason
	}
	
	public boolean getLeftBumper() {
		return driveStick.getRawButton(5);
	}
	
	public boolean getRightBumper() {
		return driveStick.getRawButton(6);
	}
	
	public boolean getAButton() {
		return driveStick.getRawButton(2);
	}
}
