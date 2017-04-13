package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.Joystick;

public class InputsDrive {
	
	private static InputsDrive instance = null;
	private boolean _xbox;
	
	public static InputsDrive getInstance() {
		if (instance == null) {
			instance = new InputsDrive();
		}
		return instance;
	}
	
	private InputsDrive() {
		driveStick = new Joystick(0);
	}
	
	private Joystick driveStick;
	
	public void setXbox(boolean xbox) {
		_xbox = xbox;
	}
	
	private double getDeadband()
	{
		if (_xbox) {
			return 0.2;
		}
		else {
			return 0.02;
		}
	}
	
		//Thumbsticks
			public double getLeftVert() {
				double value = driveStick.getRawAxis(1);
				if(Math.abs(value) >= getDeadband()) {
					return value;
				}
				
				return 0;
			}
			
			public double getLeftHorz() {
				double value = driveStick.getRawAxis(0);
				if(Math.abs(value) >= getDeadband()) {
					return value;
				}
				
				return 0;
			}
			
			public double getRightVert() {
				double value;
				if (_xbox) {					
					value = driveStick.getRawAxis(5);
				}
				else {
					value = driveStick.getRawAxis(3); //logitech
				}
				
				if(Math.abs(value) >= getDeadband()) {
					return value;
				}
				
				return 0;
			}
			
			public double getRightHorz() {
				double value;
				if (_xbox) {					
					value = driveStick.getRawAxis(4);
				}
				else {
					value = driveStick.getRawAxis(2); //logitech
				}
				
				if(Math.abs(value) >= getDeadband()) {
					return value;
				}
				
				return 0;
			}
		
		//Bumpers
			public boolean getLeftBumper() {
				return driveStick.getRawButton(5);
			}
			
			public boolean getRightBumper() {
				return driveStick.getRawButton(6);
			}
		
		//Triggers
			/*public boolean getLeftTrigger() {
				return driveStick.getRawButton(7);  //logitech
			}
			
			public boolean getRightTrigger() {
				return driveStick.getRawButton(8);  //logitech
			}*/
		
		//Buttons
			public boolean getAButton() {
				if (_xbox) {					
					return driveStick.getRawButton(1);
				}
				else {
					return driveStick.getRawButton(2); //logitech
				}
			}
			
			public boolean getBButton() {
				if (_xbox) {					
					return driveStick.getRawButton(2);
				}
				else {					
					return driveStick.getRawButton(3); //logitech
				}
			}
			
			public boolean getXButton() {
				if (_xbox) {					
					return driveStick.getRawButton(3);
				}
				else {
					return driveStick.getRawButton(1); //logitech
				}
			}
			
			public boolean getYButton() {
				return driveStick.getRawButton(4);
			}
			
			public boolean getStartButton() {
				return driveStick.getRawButton(8);
			}
			
		//Dpad
			public double getVertDpad() {
				int pov = driveStick.getPOV(); // not pressed is -1, forward is 0, backward is 180
				if (pov == 0) {
					return -1;
				}
				else if (pov == 180) {
					return 1;
				}
				
				return 0;  
			}
			
}
