package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Inputs {
	
	public Joystick driveStick = new Joystick(0);
	
	
	public double getLeftSpeed() {
		return -1 * driveStick.getRawAxis(1);
	}
	
	public double getRightSpeed() {
		return driveStick.getRawAxis(5);
	}
	
	public boolean getLeftBumper() {
		return driveStick.getRawButton(5);
	}
	
	public boolean getRightBumper() {
		return driveStick.getRawButton(6);
	}
}
