package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
    private InputsDrive _inputsDrive = InputsDrive.getInstance();
    private Diagnostics _diagnostics = new Diagnostics();
    private InputsOp _inputsOp = InputsOp.getInstance();
    private Outputs _outputs = Outputs.getInstance();
    private Shooter _shooter = Shooter.getInstance();
    private Drive _drive = Drive.getInstance();
    private DriverAssistCameras _driverAssistCameras;
    private Auto _auto = new Auto();
    
    private boolean encoderReset = false;
    private boolean forward = true;
    private boolean teleopHasInited = false;
    private double gearCenterTolerance = 20000;	//subject to change
    private boolean _driveDistance = false;
    private double shooterSpeed;
    
    NetworkTable table;
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

		//SmartDashboard.putNumber("turnScale", .5);
		//SmartDashboard.putNumber("decreasedSpeed", 2);
		//SmartDashboard.putBoolean("Drive Override", false);
		//SmartDashboard.putBoolean("Op Override", false);
		
		//SmartDashboard.putNumber("stageSpeed", 1);
		SmartDashboard.putNumber("AUTO MODE", 0);
		SmartDashboard.putNumber("Speed Shooter", 0.87);
		SmartDashboard.putNumber("Speed Stage", 0.5);
		SmartDashboard.putNumber("Speed Intake", 1);
		SmartDashboard.putBoolean("Xbox Driver controller", true);
		SmartDashboard.putNumber("Backwards Drive Distance", 0.15);
		SmartDashboard.putBoolean("Straight driving auto calibration", false);
		SmartDashboard.putNumber("Straight driving position tolerance", 0.05);
		SmartDashboard.putNumber("Straight driving scale change factor", 0.999);
		SmartDashboard.putNumber("Straight driving left scale", 1);
		SmartDashboard.putNumber("Straight driving right scale", 1);

		String auto = SmartDashboard.getString("autoFileString", "");
		if (auto == null || auto.isEmpty())
		{
			SmartDashboard.putString("autoFileString", "");
		}
		
		_driverAssistCameras = new DriverAssistCameras();
		
		_outputs.releaseRightFlap();
		_outputs.releaseLeftFlap();
		_outputs.gyroCalibrate();
		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		_outputs.resetDriveEncoders();
		_auto.initAuto();
		_diagnostics.init();
		_outputs.setDriveBrakeMode(true);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		_auto.run();
		_diagnostics.log();
		SmartDashboard.putNumber("Drive Left Encoder", _outputs.getLeftEncoder());
		SmartDashboard.putNumber("Drive Right Encoder", _outputs.getRightEncoder());
	}
	
	@Override
	public void disabledPeriodic() {
		_auto.resetAuto();
		_outputs.climb(0);
		_outputs.drive(0, 0);
		_outputs.resetDriveEncoders();
		_diagnostics.close();
		_drive.resetStraightDriving();
		_outputs.setDriveBrakeMode(false);
		SmartDashboard.putNumber("Gyro Angle", _outputs.gyroAngle());
	}
	
	@Override
	public void teleopInit() {
		_diagnostics.init();
		_outputs.setDriveBrakeMode(false);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		//center gear
		/*
			if(!teleopHasInited && !_outputs.leftGearLimit.get()) {
				_outputs.setGearMotorSpeed(-1);
			}
		*/
		
		//Sets which direction is forward
		
		_diagnostics.log();
		
		if (_inputsDrive.getAButton()) {
			_driverAssistCameras.setForward();
			forward = true;
		}
		else if (_inputsDrive.getBButton()) {
			_driverAssistCameras.setReverse();
			forward = false;
		}
		
		SmartDashboard.putNumber("GearEncoder", _outputs.getGearEncoder());
		//Moves gear manipulator according to operator right joystick
		if(_inputsOp.getXButton() && !(Math.abs(_outputs.getGearEncoder()) <= gearCenterTolerance)) {//buttons subject to change
			if(_outputs.getGearEncoder() > gearCenterTolerance) {
				_outputs.setGearMotorSpeed(-.5);
			}
			else if(_outputs.getGearEncoder() < gearCenterTolerance) {
				_outputs.setGearMotorSpeed(.5);
			}
		}
		else if (_inputsOp.getRightHorz() > .1 || _inputsOp.getRightHorz() < -.1) {
			_outputs.setGearMotorSpeed(_inputsOp.getRightHorz());
		}
		else {
			_outputs.setGearMotorSpeed(0);
		}
		
		/*
		//attempts at centering gear manipulator
		if(_outputs.leftGearLimit.get()) {
			_outputs.resetGearEncoder();
			System.out.println("Gear Encoder Reset");
		}
		
		
		*/
		
		SmartDashboard.putNumber("Drive Left Encoder", _outputs.getLeftEncoder());
		SmartDashboard.putNumber("Drive Right Encoder",  _outputs.getRightEncoder());

		//Turns on climb roller
		SmartDashboard.putNumber("Climb output", _inputsOp.getLeftVert());
		if(_inputsOp.getLeftVert() <= -.2) {
			_outputs.climb(Math.abs(_inputsOp.getLeftVert()));
		}
		else {
			_outputs.climb(0);
		}
		
		
		//Intake
		if(_inputsOp.getAButton()) {
			_outputs.setIntake(SmartDashboard.getNumber("Speed Intake", 1));
		}
		else {
			_outputs.setIntake(0);
		}
		
		//Stage
		if(_inputsOp.getRightTrigger()) {
			_outputs.setStage(SmartDashboard.getNumber("Speed Stage", 0.5));
		}
		else {
			_outputs.setStage(0);
		}
		
		//Shooter
		if(_inputsOp.getLeftTrigger()) {
			_outputs.setShooter(SmartDashboard.getNumber("Speed Shooter", 0.87));
		}
		else {
			_outputs.setShooter(0);
		}
		

		if(_inputsOp.getYButton() && !_driveDistance) {
			double distance = -Math.abs(SmartDashboard.getNumber("Backwards Drive Distance", 0.15));
			_drive.setDistanceTargets(distance, distance);
			_driveDistance = true;
		}
		
		_inputsDrive.setXbox(SmartDashboard.getBoolean("Xbox Driver controller", true));

		_outputs.setPID();
		//Decreased Speed
		if (_driveDistance) {
			_driveDistance = _drive.driveBackwards(0.3);
		}
		else if(_inputsDrive.getRightBumper()) {
			//_drive.FPSdrive(forward, 0.69, true);
			//SmartDashboard.putNumber("buttonPressed", 1);
			_drive.tankDrive(forward, 0.69, false);
		}
		else {
			_drive.FPSdrive(forward, 0.69, false, _inputsDrive.getLeftVert());
			//SmartDashboard.putNumber("buttonPressed", 0);
		}
		
		//Drive override
		if(_inputsDrive.getLeftBumper()) {
			_outputs.setDriveOverride(true);
		}
		else {
			_outputs.setDriveOverride(false);
		}
		
		//Op override
		if(_inputsOp.getLeftBumper()) {
			_outputs.setOpOverride(true);
		}
		else {
			_outputs.setOpOverride(false);
		}
		
		//Vision LED
		if(_inputsOp.getStartButton()) {
			_outputs.setLED(true);
			//SmartDashboard.putBoolean("ledbutton", true);
		}
		else {
			_outputs.setLED(false);
			//SmartDashboard.putBoolean("ledbutton", false);
		}
		
		if(_inputsOp.getVertDpad() == 1) {
			shooterSpeed = shooterSpeed + .002;
		}
		else if(_inputsOp.getVertDpad() == -1) {
			shooterSpeed = shooterSpeed - .002;
		}
		
SmartDashboard.putNumber("Gyro Angle", _outputs.gyroAngle());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
	}
}
