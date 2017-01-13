package org.usfirst.frc.team85.robot;

import com.ctre.CANTalon;

public class Outputs {
	
    private CANTalon _frontLeftMotor = new CANTalon(Addresses.LEFT_FRONT_MOTOR);
    private CANTalon _midLeftMotor = new CANTalon(Addresses.LEFT_MID_MOTOR);
    private CANTalon _backLeftMotor = new CANTalon(Addresses.LEFT_BACK_MOTOR);
    
    private CANTalon _frontRightMotor = new CANTalon(Addresses.RIGHT_FRONT_MOTOR);
    private CANTalon _midRightMotor = new CANTalon(Addresses.RIGHT_MID_MOTOR);
    private CANTalon _backRightMotor = new CANTalon(Addresses.RIGHT_BACK_MOTOR);
    
    public void setLeftSpeed(double speed) {
		_frontLeftMotor.set(speed);
		_midLeftMotor.set(speed);
		_backLeftMotor.set(speed);
    }
    
    public void setRightSpeed(double speed) {
		_frontRightMotor.set(speed);
		_midRightMotor.set(speed);
		_backRightMotor.set(speed);
    }

}