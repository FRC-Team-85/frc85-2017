package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.Encoder;

public class Inputs {

	Encoder _leftFrontDriveEncoder = new Encoder(0, 1, false, Addresses.LEFT_FRONT_ENCODER);
	Encoder _leftBackDriveEncoder = new Encoder(0, 1, false, Addresses.LEFT_BACK_ENCODER);
	Encoder _rightFrontDriveEncoder = new Encoder(0, 1, false, Addresses.RIGHT_FRONT_ENCODER);
	Encoder _rightBackDriveEncoder = new Encoder(0, 1, false, Addresses.RIGHT_BACK_ENCODER);

	public int getLeftFrontDriveEncoder() {
		return _leftFrontDriveEncoder.get();
	}
	
	public int getLeftBackDriveEncoder() {
		return _leftBackDriveEncoder.get();
	}
	
	public int getRightFrontDriveEncoder() {
		return _rightFrontDriveEncoder.get();
		
	}
	
	public int getRightBackDriveEncoder() {
		return _rightBackDriveEncoder.get();
	}
	
	public void driveEncodersReset() {
		_leftFrontDriveEncoder.reset();
		_leftBackDriveEncoder.reset();
		_rightFrontDriveEncoder.reset();
		_rightBackDriveEncoder.reset();
	}

}
