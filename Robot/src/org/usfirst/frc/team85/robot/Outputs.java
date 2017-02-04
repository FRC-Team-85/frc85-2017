package org.usfirst.frc.team85.robot;

import com.ctre.CANTalon;

public class Outputs {
	
	private Vision _vision = new Vision();
	
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

}