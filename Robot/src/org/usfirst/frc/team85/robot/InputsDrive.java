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
	
		//Thumbsticks
			public double getLeftVert() {
				return driveStick.getRawAxis(1);
			}
			
			public double getLeftHorz() {
				return driveStick.getRawAxis(0);
			}
			
			public double getRightVert() {
				if (_xbox) {					
					return driveStick.getRawAxis(5);
				}
				else {
					return driveStick.getRawAxis(3); //logitech
				}
			}
			
			public double getRightHorz() {
				if (_xbox) {					
					return driveStick.getRawAxis(4);
				}
				else {
					return driveStick.getRawAxis(2); //logitech
				}
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
