package org.usfirst.frc.team85.robot;

import com.ctre.CANTalon;

public class Outputs {
	
	private Vision _vision = new Vision();
	private Inputs _inputs = new Inputs();
	
    private CANTalon _frontLeftMotor = new CANTalon(Addresses.LEFT_FRONT_MOTOR);
    private CANTalon _backLeftMotor = new CANTalon(Addresses.LEFT_BACK_MOTOR);
    
    private CANTalon _frontRightMotor = new CANTalon(Addresses.RIGHT_FRONT_MOTOR);
    private CANTalon _backRightMotor = new CANTalon(Addresses.RIGHT_BACK_MOTOR);

    public void setLeftSpeed(double speed) {
		_frontLeftMotor.set(speed);
		_backLeftMotor.set(speed);
    }
    
    public void setRightSpeed(double speed) {
		_frontRightMotor.set(speed);
		_backRightMotor.set(speed);
    }
    
    public void drive(double left, double right) {
    	setLeftSpeed(left);
    	setRightSpeed(right);
    }

    public void visionTrack() {
    	
    	double power = _vision.center();
    	
    	setLeftSpeed(-power);
    	setRightSpeed(power);
    }
/*
    public void encoderStraightDrive() {
    	
    	int currentDifference = 0;
	
    	_inputs.driveEncodersReset();
	
		drive(speedL,speedR);
	
		int leftEncoder = _inputs.getLeftFrontDriveEncoder();
		int rightEncoder = _inputs.getRightFrontDriveEncoder();
	
		currentDifference = leftEncoder - rightEncoder;
	
		if (currentDifference > 0) { //positive = left is ahead by 1
			speedL = speedL - .05;
		}
		else if (currentDifference < 0) { //negative = right is ahead by 1
			speedR = speedR - .05;
		}
		else { //auto friendly values, set these to left joystick for teleop
			speedL = 0.8;
			speedR = 0.8;
		}
    }
*/
}