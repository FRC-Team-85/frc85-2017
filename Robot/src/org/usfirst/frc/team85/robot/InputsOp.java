package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.Joystick;

public class InputsOp {
	
	private static InputsOp instance = null;
	
	public static InputsOp getInstance() {
		if (instance == null) {
			instance = new InputsOp();
		}
		return instance;
	}
	
	private InputsOp() {
		opStick = new Joystick(1);
	}
	
	public Joystick opStick = new Joystick(1);
	
		//Thumbsticks
			public double getLeftVert() {
				return -1 * opStick.getRawAxis(1);
			}
	
			public double getLeftHorz() {
				return -1 * opStick.getRawAxis(0);
			}
	
			public double getRightVert() {
				return opStick.getRawAxis(3);
			}
	
			public double getRightHorz() {
				return -1 * opStick.getRawAxis(2);
			}
		
		//Bumpers
			public boolean getLeftBumper() {
				return opStick.getRawButton(5);
			}
			
			public boolean getRightBumper() {
				return opStick.getRawButton(6);
			}
		
		//Triggers
			public boolean getLeftTrigger() {
				return opStick.getRawButton(7);
			}
			
			public boolean getRightTrigger() {
				return opStick.getRawButton(8);
			}
		
		//Buttons
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
