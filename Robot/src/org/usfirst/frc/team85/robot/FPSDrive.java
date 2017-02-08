package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FPSDrive {
	
    private InputsDrive _inputsDrive = new InputsDrive();
    private Outputs _outputs = new Outputs();
    
	public void drive() {
		
		
		double turnScale = SmartDashboard.getNumber("turnScale", .5);
		double leftSpeed = 0;
		double rightSpeed = 0;
		if(Math.abs(_inputsDrive.getLeftVert()) >= 0.05 ) {
			leftSpeed = _inputsDrive.getLeftVert();
			rightSpeed = _inputsDrive.getLeftVert();
		}
		
		if(Math.abs(_inputsDrive.getRightHorz()) >= 0.05) {
			leftSpeed = leftSpeed + turnScale * -_inputsDrive.getRightHorz();
			rightSpeed = rightSpeed + turnScale * _inputsDrive.getRightHorz();
		}
		
		_outputs.setLeftSpeed(leftSpeed);
		_outputs.setRightSpeed(rightSpeed);
	}

	public void driveCurve() {
		if(Math.abs(_inputsDrive.getLeftVert()) >= 0.05 ) {
			_outputs.setLeftSpeed(_inputsDrive.getLeftVert());
			_outputs.setRightSpeed(_inputsDrive.getLeftVert());
		}
		else if (Math.abs(_inputsDrive.getRightHorz()) >= 0.05) {
			_outputs.setLeftSpeed(-1 * _inputsDrive.getRightHorz());
			_outputs.setRightSpeed(_inputsDrive.getRightHorz());
		}
		else {
			_outputs.setLeftSpeed(0);
			_outputs.setRightSpeed(0);
		}
	}
	
}
