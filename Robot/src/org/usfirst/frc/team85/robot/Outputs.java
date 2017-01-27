package org.usfirst.frc.team85.robot;

import com.ctre.CANTalon;

public class Outputs {
	
	private Vision _vision = new Vision();
	
    private CANTalon _frontLeftMotor = new CANTalon(Addresses.LEFT_FRONT_MOTOR);
    private CANTalon _backLeftMotor = new CANTalon(Addresses.LEFT_BACK_MOTOR);
    
    private CANTalon _frontRightMotor = new CANTalon(Addresses.RIGHT_FRONT_MOTOR);
    private CANTalon _backRightMotor = new CANTalon(Addresses.RIGHT_BACK_MOTOR);
    
	private CANTalon _motorOne = new CANTalon(Addresses.MOTOR_ONE);
	private CANTalon _motorTwo = new CANTalon(Addresses.MOTOR_TWO);
	 
    public void setLeftSpeed(double speed) {
		_frontLeftMotor.set(speed);
		_backLeftMotor.set(speed);
    }
    
    public void setRightSpeed(double speed) {
		_frontRightMotor.set(speed);
		_backRightMotor.set(speed);
    }
    
    public void setMotorOne(double speed) {
    	_motorOne.set(speed);
    }
    
    public void setMotorTwo(double speed) {
    	_motorTwo.set(speed);
    }
    
    public void visionTrack() {
    	_motorTwo.set(_vision.center());
    }

}