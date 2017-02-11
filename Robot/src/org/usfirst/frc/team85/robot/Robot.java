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
	
    private InputsDrive _inputsDrive = new InputsDrive();
    private InputsOp _inputsOp = new InputsOp();
    private Inputs _inputs = new Inputs();
    private Outputs _outputs = new Outputs();
    private FPSDrive _fpsDrive = new FPSDrive();
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
		
		SmartDashboard.putString("autoFileString", "");
		_driverAssistCameras = new DriverAssistCameras();
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
		_auto.initAuto(SmartDashboard.getString("autoFileString", ""));
		_auto.doAuto();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
/*
		if (!encoderReset) {
			_inputs.driveEncodersReset();
			return true;
		}

		double speedOne = SmartDashboard.getNumber("speedOne", 0);
		double speedTwo = SmartDashboard.getNumber("speedTwo", 0);
*/
		if (_inputsDrive.getAButton()) {
			_driverAssistCameras.setForward();
			forward = true;
		}
		else if (_inputsDrive.getBButton()) {
			_driverAssistCameras.setReverse();
			forward = false;
		}
		
		if (_inputsDrive.getLeftBumper()) {
			
			_outputs.setGearMotorSpeed(-.25); //random placeholder
		}
		
		if (_inputsDrive.getRightBumper()) {
			_outputs.setGearMotorSpeed(.25); //random placeholder
		}
		
		_fpsDrive.drive(forward, 0.699999999999999999991);
		

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
	}
}

