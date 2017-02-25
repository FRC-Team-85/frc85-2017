package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {
	
    private InputsDrive _inputsDrive = InputsDrive.getInstance();
    private InputsOp _inputsOp = InputsOp.getInstance();
    private Outputs _outputs = Outputs.getInstance();
    
    private boolean prankCode = false;
    boolean halfSpeed = false;
    
	public void FPSdrive(boolean forward, double limitedSpeed, boolean halfSpeed) {
		double turnScale = SmartDashboard.getNumber("turnScale", .5);
		double leftSpeed = 0;
		double rightSpeed = 0;
		
		double decreasedSpeed = SmartDashboard.getNumber("decreasedSpeed", 2);	
		
		if(Math.abs(_inputsDrive.getLeftVert()) >= 0.2 ) { //logitech deadband: 0.02, xbox deadband: 0.2
			leftSpeed = _inputsDrive.getLeftVert();
			rightSpeed = _inputsDrive.getLeftVert();
			
			if(halfSpeed) {
				leftSpeed = leftSpeed / decreasedSpeed;
				rightSpeed = rightSpeed / decreasedSpeed;
			}
		}
		else if(_inputsDrive.getVertDpad() == 1) {
			leftSpeed = -limitedSpeed;
			rightSpeed = -limitedSpeed;
		}
		else if(_inputsDrive.getVertDpad() == -1) {
			leftSpeed = limitedSpeed;
			rightSpeed = limitedSpeed;
		}
		
		if(Math.abs(_inputsDrive.getRightHorz()) >= 0.2) { //logitech deadband: 0.02, xbox deadband: 0.2
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
	
	if (_inputsOp.getStartButton()) {
		
		prankCode = !prankCode;
		
		if (prankCode) {
			InputsOp _inputsDrive = InputsOp.getInstance();
			InputsDrive _inputsOp = InputsDrive.getInstance();
		}
		else {
			InputsDrive _inputsDrive = InputsDrive.getInstance();
			InputsOp _inputsOp = InputsOp.getInstance();
		}
	}
	
}
