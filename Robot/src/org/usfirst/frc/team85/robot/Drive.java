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
	
	private boolean _forward;
	
	private double _leftTarget;
	private double _rightTarget;
	private double _lastLeftPosition = 0;
	private double _lastRightPosition = 0;
	private double _leftScale = 1;
	private double _rightScale = 1;
	
	public void FPSdrive(boolean forward, double limitedSpeed, boolean halfSpeed, double forwardSpeed) {
		double turnScale = SmartDashboard.getNumber("turnScale", .5);
		double decreasedSpeed = SmartDashboard.getNumber("decreasedSpeed", 2);	
		_forward = forward;
		leftSpeed = 0;
		rightSpeed = 0;
		
			if(Math.abs(forwardSpeed) >= 0 ) {
				leftSpeed = forwardSpeed;
				rightSpeed = forwardSpeed;
				
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
			
			if(Math.abs(_inputsDrive.getRightHorz()) >= 0) {
				leftSpeed = leftSpeed + turnScale * -_inputsDrive.getRightHorz();
				rightSpeed = rightSpeed + turnScale * _inputsDrive.getRightHorz();
				resetStraightDriving();
			}
			else {
				double leftPos = _outputs.getLeftEncoder();
				double rightPos = _outputs.getRightEncoder();
								
				double leftDiff = leftPos - _lastLeftPosition;
				double rightDiff = rightPos - _lastRightPosition;
				
				SmartDashboard.putNumber("Straight driving left difference", leftDiff);
				SmartDashboard.putNumber("Straight driving right difference", rightDiff);
				
				double tolerance = SmartDashboard.getNumber("Straight driving position tolerance", 0.01);
				double scaleFactor = SmartDashboard.getNumber("Straight driving scale change factor", 0.99);
				if (SmartDashboard.getBoolean("Straight driving auto calibration", false) && leftSpeed != 0 && rightSpeed != 0 && _lastLeftPosition != 0 && _lastRightPosition != 0) {
					if (leftDiff - rightDiff > tolerance) {
						_leftScale *= scaleFactor;
					}
					else if (rightDiff - leftDiff > tolerance) {
						_rightScale *= scaleFactor;
					}
					
					SmartDashboard.putNumber("Straight driving left scale", _leftScale);
					SmartDashboard.putNumber("Straight driving right scale", _rightScale);
				}
				else {
					_leftScale = SmartDashboard.getNumber("Straight driving left scale", 1);
					_rightScale = SmartDashboard.getNumber("Straight driving right scale", 1);
				}
				
				_lastLeftPosition = leftPos;
				_lastRightPosition = rightPos;
				
				
				leftSpeed *= _leftScale;
				rightSpeed *= _rightScale;
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
	
	public void tankDrive(boolean forward, double limitedSpeed, boolean halfSpeed) {
		double decreasedSpeed = SmartDashboard.getNumber("decreasedSpeed", 2);
		leftSpeed = 0;
		rightSpeed = 0;
		
		leftSpeed = _inputsDrive.getLeftVert();
		rightSpeed = _inputsDrive.getRightVert();
			
		if(halfSpeed) {
			leftSpeed = leftSpeed / decreasedSpeed;
			rightSpeed = rightSpeed / decreasedSpeed;
		}
		
		if(_inputsDrive.getVertDpad() == 1) {
			leftSpeed = -limitedSpeed;
			rightSpeed = -limitedSpeed;
		}
		else if(_inputsDrive.getVertDpad() == -1) {
			leftSpeed = limitedSpeed;
			rightSpeed = limitedSpeed;
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
	
	public void resetStraightDriving() {
		_lastLeftPosition = 0;
		_lastRightPosition = 0;
	}
	
	public void setDistanceTargets(double leftTarget, double rightTarget) {
		_leftTarget = _outputs.getLeftEncoder() + leftTarget;
		_rightTarget = _outputs.getRightEncoder() + rightTarget;
	}
	
	public boolean driveBackwards(double speed) {
		double rightEnc = _outputs.getRightEncoder();
		double leftEnc = _outputs.getLeftEncoder();
		
		if(leftEnc <= _leftTarget && rightEnc <= _rightTarget) {
			_outputs.setLeftSpeed(0);
			_outputs.setRightSpeed(0);
			return false;
		}
		else {
			_outputs.setLeftSpeed(-speed);
			_outputs.setRightSpeed(-speed);
			return true;
		}
	}
	
	public double getLeftSpeed() {
		return leftSpeed;
	}
	
	public double getRightSpeed() {
		return rightSpeed;
	}
	
	public double getLeftStick() {
		return _inputsDrive.getLeftVert();
	}
	
	public double getRightStick() {
		return _inputsDrive.getRightHorz();
	}
	
	public boolean getDirection() {
		return _forward;
	}
		
	}
