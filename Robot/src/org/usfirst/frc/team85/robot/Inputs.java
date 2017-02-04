package org.usfirst.frc.team85.robot;

public class Inputs {

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
	
}
