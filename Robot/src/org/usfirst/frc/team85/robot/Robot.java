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
    private InputsOp _inputsOp = InputsOp.getInstance();
    private Outputs _outputs = Outputs.getInstance();
    private Shooter _shooter = Shooter.getInstance();
    private Drive _drive = new Drive();
    private DriverAssistCameras _driverAssistCameras;
    private Auto _auto = new Auto();
    
    private boolean encoderReset = false;
    private boolean forward = true;
    
    NetworkTable table;
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//NetworkTable.setClientMode();
		//NetworkTable.setIPAddress("roborio-85-frc.local");
		//table = NetworkTable.getTable("SmartDashboard");
		SmartDashboard.putNumber("turnScale", .5);
/*
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("roborio-85-frc.local");
		table = NetworkTable.getTable("SmartDashboard"); 
		
		_inputs.driveEncodersReset(); */
		String auto = SmartDashboard.getString("autoFileString", "");
		if (auto == null || auto.isEmpty())
		{
			SmartDashboard.putString("autoFileString", "use:<name>:move, 0.5, 0.5, 20, 20:wait,2:move,-0.8,-0.8, 10, 10");
		}
		
		_driverAssistCameras = new DriverAssistCameras();
		
		_outputs.releaseRightFlap();
		_outputs.releaseLeftFlap();
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
		_outputs.releaseRightFlap();
		_outputs.releaseLeftFlap();
		_auto.resetI();
		_outputs.resetDriveEncoders();
		_auto.initAuto(SmartDashboard.getString("autoFileString", ""));
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		//_auto.initAuto(SmartDashboard.getString("autoFileString", "use:<name>:move, 0.5, 0.5, 20000, 20000"));
		_auto.run();
		SmartDashboard.putNumber("DriveRightEncoder", _outputs.getRightEncoder());
		SmartDashboard.putNumber("DriveLeftEncoder", _outputs.getLeftEncoder());
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		//Sets which direction is forward
		if (_inputsDrive.getAButton()) {
			_driverAssistCameras.setForward();
			forward = true;
		}
		else if (_inputsDrive.getBButton()) {
			_driverAssistCameras.setReverse();
			forward = false;
		}
		
		//Moves gear manipulator according to operator right joystick
		if (_inputsOp.getRightHorz() > .1) {
			_outputs.setGearMotorSpeed(_inputsOp.getRightHorz());
		} 
		else if (_inputsOp.getRightHorz() < -.1) {
			_outputs.setGearMotorSpeed(_inputsOp.getRightHorz());
		}
		else {
			_outputs.setGearMotorSpeed(0);
		}
			
		//Turns on climb roller
		if (_inputsOp.getYButton()) {
			_outputs.climb(1);
		}
		else {
			_outputs.climb(0);
		}
		
		//Intake
		if(_inputsOp.getAButton()) {
			_outputs.setIntake(1);
		}
		else {
			_outputs.setIntake(0);
		}
		
		//Stage
		if(_inputsOp.getBButton()) {
			_outputs.setStage(1);
		}
		else {
			_outputs.setStage(0);
		}
		
		//Shooter
		if(_inputsOp.getRightBumper()) {
			_outputs.setShooter(-1);
		}
		else {
			_outputs.setShooter(0);
		}

		_drive.FPSdrive(forward, 0.69);
	
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
	}
}
