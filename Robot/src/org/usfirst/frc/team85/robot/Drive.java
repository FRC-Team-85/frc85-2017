package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {
	
	private static Drive instance = null;

	public static Drive getInstance() {
		if (instance == null) {
			instance = new Drive();
		}

		return instance;
	}
	
    private InputsDrive _inputsDrive = InputsDrive.getInstance();
    private Outputs _outputs = Outputs.getInstance();
    
    boolean halfSpeed = false;
    
	private double leftSpeed = 0;
	private double rightSpeed = 0;
    
	public void FPSdrive(boolean forward, double limitedSpeed, boolean halfSpeed) {
		double turnScale = SmartDashboard.getNumber("turnScale", .5);
		double decreasedSpeed = SmartDashboard.getNumber("decreasedSpeed", 2);	
		leftSpeed = 0;
		rightSpeed = 0;
		
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
	
	public double getLeftSpeed() {
		return leftSpeed;
	}
	
	public double getRightSpeed() {
		return rightSpeed;
	}
		
	}
