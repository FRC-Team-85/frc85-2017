package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.Encoder;

public class Inputs {

	Encoder _leftFrontDriveEncoder = new Encoder(0, 1, false, Addresses.LEFT_FRONT_ENCODER);
	/* Define encoders from addresses
	 * Ex : _dartEncoder = new Encoder(Addresses.CANNON.DART_ENCODER_CH_A, Addresses.CANNON.DART_ENCODER_CH_B);
	 *
	 * Configure encoders
	 * _leftFrontDriveEncoder.
	 *
	 *
	 *
	 */
	
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
