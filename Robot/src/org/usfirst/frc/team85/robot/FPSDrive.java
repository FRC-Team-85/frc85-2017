package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FPSDrive {
	
	private InputsDrive _inputsDrive = InputsDrive.getInstance();
	private Outputs _outputs = Outputs.getInstance();
	
	public void drive(boolean forward, double limitedSpeed) {
		double turnScale = SmartDashboard.getNumber("turnScale", .5);
		double leftSpeed = 0;
		double rightSpeed = 0;
		
		if(Math.abs(_inputsDrive.getLeftVert()) >= 0.05 ) {
			leftSpeed = _inputsDrive.getLeftVert();
			rightSpeed = _inputsDrive.getLeftVert();
		}
		else if(_inputsDrive.getVertDpad() == 1) {
			leftSpeed = -limitedSpeed;
			rightSpeed = -limitedSpeed;
		}
		else if(_inputsDrive.getVertDpad() == -1) {
			leftSpeed = limitedSpeed;
			rightSpeed = limitedSpeed;
		}
		
		if(Math.abs(_inputsDrive.getRightHorz()) >= 0.05) {
			leftSpeed = leftSpeed + turnScale * -_inputsDrive.getRightHorz();
			rightSpeed = rightSpeed + turnScale * _inputsDrive.getRightHorz();
		}
		if (forward) {
			SmartDashboard.putNumber("leftSpeed", _outputs.setLeftSpeed(-leftSpeed));
			SmartDashboard.putNumber("rightSpeed", _outputs.setRightSpeed(-rightSpeed));
		}
		else if (!forward) {
			SmartDashboard.putNumber("leftSpeed", _outputs.setLeftSpeed(rightSpeed));
			SmartDashboard.putNumber("rightSpeed", _outputs.setRightSpeed(leftSpeed));	
		}
		
	}
	
}
