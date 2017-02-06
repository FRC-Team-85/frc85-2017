package org.usfirst.frc.team85.robot;

public class FPSDrive {
	
    private InputsDrive _inputsDrive = new InputsDrive();
    private Outputs _outputs = new Outputs();

	public void drive() {
		if(Math.abs(_inputsDrive.getLeftVert()) >= 0.05 ) {
			_outputs.setLeftSpeed(Math.pow(_inputsDrive.getLeftVert(), 3));
			_outputs.setRightSpeed(Math.pow(-1 * _inputsDrive.getLeftVert(), 3));
		}
		else if (_inputsDrive.getRightHorz() <= -0.05) {
			_outputs.setLeftSpeed(-1 * Math.pow(_inputsDrive.getRightHorz(), 3));
			_outputs.setRightSpeed(-1 * Math.pow(_inputsDrive.getRightHorz(), 3));
		}
		else if (_inputsDrive.getRightHorz() >= 0.05) {
			_outputs.setLeftSpeed(-1 * Math.pow(_inputsDrive.getRightHorz(), 3));
			_outputs.setRightSpeed(-1 * Math.pow(_inputsDrive.getRightHorz(), 3));
		}
		else {
			_outputs.setLeftSpeed(0);
			_outputs.setRightSpeed(0);
		}
		
		//-1 * Math.pow(_inputsDrive.getLeftVert(), 3));
	}
	
}
