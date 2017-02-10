package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FPSDrive {
	
    private InputsDrive _inputsDrive = new InputsDrive();
    private Outputs _outputs = new Outputs();
    
	public void drive(boolean forward, double limitedSpeed) {
		double turnScale = SmartDashboard.getNumber("turnScale", .5);
		double leftSpeed = 0;
		double rightSpeed = 0;
		
		if(Math.abs(_inputsDrive.getLeftVert()) >= 0.05 ) {
			leftSpeed = _inputsDrive.getLeftVert();
			rightSpeed = _inputsDrive.getLeftVert();
		}
		else if(_inputsDrive.getVertDpad() == -1) {
			leftSpeed = -limitedSpeed;
			rightSpeed = -limitedSpeed;
		}
		else if(_inputsDrive.getVertDpad() == 1) {
			leftSpeed = limitedSpeed;
			rightSpeed = limitedSpeed;
		}
		
		if(Math.abs(_inputsDrive.getRightHorz()) >= 0.05) {
			leftSpeed = leftSpeed + turnScale * -_inputsDrive.getRightHorz();
			rightSpeed = rightSpeed + turnScale * _inputsDrive.getRightHorz();
		}
		if (forward) {
			_outputs.setLeftSpeed(leftSpeed);
			_outputs.setRightSpeed(rightSpeed);
		}
		else if (!forward) {
			_outputs.setLeftSpeed(-rightSpeed);
			_outputs.setRightSpeed(-leftSpeed);	
		}
		
	}
	
}
