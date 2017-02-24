package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.Joystick;

public class InputsDrive {
	
	private static InputsDrive instance = null;
	
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
	
		//Thumbsticks
			public double getLeftVert() {
				return driveStick.getRawAxis(1);
			}
			
			public double getLeftHorz() {
				return driveStick.getRawAxis(0);
			}
			
			public double getRightVert() {
				return driveStick.getRawAxis(5);
				//return driveStick.getRawAxis(3); //logitech
			}
			
			public double getRightHorz() {
				return driveStick.getRawAxis(4);
				//return driveStick.getRawAxis(2); //logitech
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
				return driveStick.getRawButton(1);
				//return driveStick.getRawButton(2); //logitech
			}
			
			public boolean getBButton() {
				return driveStick.getRawButton(2);
				//return driveStick.getRawButton(3); //logitech
			}
			
			public boolean getXButton() {
				return driveStick.getRawButton(3);
				//return driveStick.getRawButton(1); //logitech
			}
			
			public boolean getYButton() {
				return driveStick.getRawButton(4);
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
