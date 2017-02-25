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
	
	private Joystick opStick;
	
		//Thumbsticks
			public double getLeftVert() {
				return opStick.getRawAxis(1);
			}
			
			public double getLeftHorz() {
				return opStick.getRawAxis(0);
			}
			
			public double getRightVert() {
				//return opStick.getRawAxis(5);
				return opStick.getRawAxis(3); //logitech
			}
			
			public double getRightHorz() {
				//return opStick.getRawAxis(4);
				return opStick.getRawAxis(2); //logitech
			}
		
		//Bumpers
			public boolean getLeftBumper() {
				//return opStick.getRawButton(4);
				return opStick.getRawButton(4); //logitech
			}
			
			public boolean getRightBumper() {
				//return opStick.getRawButton(5);
				return opStick.getRawButton(5); //logitech
			}
		
		//Triggers
			/*public boolean getLeftTrigger() {
				return opStick.getRawButton(7);  //logitech
			}
			
			public boolean getRightTrigger() {
				return opStick.getRawButton(8);  //logitech
			}*/
		
		//Buttons
			public boolean getAButton() {
				//return opStick.getRawButton(1);
				return opStick.getRawButton(2); //logitech
			}
			
			public boolean getBButton() {
				//return opStick.getRawButton(2);
				return opStick.getRawButton(3); //logitech
			}
			
			public boolean getXButton() {
				//return opStick.getRawButton(3);
				return opStick.getRawButton(11); //logitech //wrong
			}
			
			public boolean getYButton() {
				return opStick.getRawButton(4);
			}
			public boolean getStartButton() {
				return opStick.getRawButton(9);
			}
			
		//Dpad
			public double getVertDpad() {
				int pov = opStick.getPOV(); // not pressed is -1, forward is 0, backward is 180
				if (pov == 0) {
					return -1;
				}
				else if (pov == 180) {
					return 1;
				}
				
				return 0;  
			}
			
}
