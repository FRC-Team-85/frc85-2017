package org.usfirst.frc.team85.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import edu.wpi.first.wpilibj.DriverStation;

public class Diagnostics {
	
	private Outputs _outputs = Outputs.getInstance();
	private Drive _drive = Drive.getInstance();
	private InputsDrive _inputsDrive = InputsDrive.getInstance();
	private InputsOp _inputsOp = InputsOp.getInstance();
	
	private long epoch = System.currentTimeMillis()/1000;
	private String date = new java.text.SimpleDateFormat("MM-dd-yyyy HH-mm-ss").format(new java.util.Date (epoch*1000));

	File log;
	BufferedWriter out = null;
	
	public void init()
	{
		try
		{
			close();
			
			log = new File("/home/lvuser/log " + date + ".csv");
			if (log.exists() == false) {
				log.createNewFile();
				out = new BufferedWriter(new FileWriter(log, true));
				out.append("Match Time,Left Enc Dist,Right Enc Dist,Left Enc Speed,Right Enc Speed,"
						+ "Left Calc Speed,Right Calc Speed,Climb Current,Climb Left Limit,Climb Right Limit,"
						+ "Front Left Current,Front Right Current,Back Left Current,Back Right Current,"
						+ "Front Left Voltage,Front Right Voltage,Back Left Voltage,Back Right Voltage,"
						+ "Drive Override,Op Override,Up/Down Stick,Left/Right Stick,Battery Voltage,Forward Direction,Gear Speed,Gear Axis,Shooter Speed,"
						+ "Shooter Current,Shooter Voltage,Climb Speed,Climb Axis");
				out.newLine();
			}	
		} catch (Exception ex) {
			System.out.println("Error creating log file: " + ex.toString());
		}
		
	}
	
	public void log() {
	
		double leftEncoder = _outputs.getLeftEncoder();
		double rightEncoder = _outputs.getRightEncoder();
		
		String matchTime = Double.toString(DriverStation.getInstance().getMatchTime());
		String leftEncoderDist = Double.toString(leftEncoder);
		String rightEncoderDist = Double.toString(rightEncoder);
		String climberCurrent = Double.toString(_outputs.getClimberCurrent());
		String climbLeftLimit = Boolean.toString(_outputs.leftClimberLimit.get());
		String climbRightLimit = Boolean.toString(_outputs.rightClimberLimit.get());
		
		String frontLeftCurrent = Double.toString(_outputs.getFrontLeftCurrent());
		String frontRightCurrent = Double.toString(_outputs.getFrontRightCurrent());
		String backLeftCurrent = Double.toString(_outputs.getBackLeftCurrent());
		String backRightCurrent = Double.toString(_outputs.getBackRightCurrent());
	
		String frontLeftVoltage = Double.toString(_outputs.getFrontLeftVoltage());
		String frontRightVoltage = Double.toString(_outputs.getFrontRightVoltage());
		String backLeftVoltage = Double.toString(_outputs.getBackLeftVoltage());
		String backRightVoltage = Double.toString(_outputs.getBackRightVoltage());	
		
		String leftEncSpeed = Double.toString(_outputs.getLeftSpeed());	
		String rightEncSpeed = Double.toString(_outputs.getRightSpeed());
		
		String leftCalcSpeed = Double.toString(_drive.getLeftSpeed());	
		String rightCalcSpeed = Double.toString(_drive.getRightSpeed());
		
		String driveOverride = Boolean.toString(_outputs.getDriveOverride());
		String opOverride = Boolean.toString(_outputs.getOpOverride());
		
		String leftStick = Double.toString(_drive.getLeftStick());	
		String rightStick = Double.toString(_drive.getRightStick());
		
		String batteryVoltage = Double.toString(DriverStation.getInstance().getBatteryVoltage());

		String forwardDirection = Boolean.toString(_drive.getDirection());
		
		String gearSpeed = Double.toString(_outputs.getGearMotorSpeed());
		String gearAxis = Double.toString(_inputsOp.getRightHorz());
		String shooterSpeed = Double.toString(_outputs.getShooterSpeed());
		String shooterCurrent = Double.toString(_outputs.getShooterCurrent());
		String shooterVoltage = Double.toString(_outputs.getShooterVoltage());
		String climbSpeed = Double.toString(_outputs.getClimbSpeed());
		String climbAxis = Double.toString(_inputsOp.getLeftVert());
		
		try {
			if (leftEncoder != 0 || rightEncoder != 0)
			{
				out.append(matchTime + "," + leftEncoderDist + "," + rightEncoderDist + "," + leftEncSpeed + "," + rightEncSpeed + "," 
						+ leftCalcSpeed + "," + rightCalcSpeed + "," + climberCurrent + "," + climbLeftLimit + "," + climbRightLimit + "," 
						+ frontLeftCurrent+ "," + frontRightCurrent+ "," + backLeftCurrent+ "," + backRightCurrent + "," 
						+ frontLeftVoltage+ "," + frontRightVoltage+ "," + backLeftVoltage + "," + backRightVoltage+ "," 
						+ driveOverride + "," + opOverride + "," + leftStick + "," + rightStick + "," + batteryVoltage + "," + forwardDirection + "," + gearSpeed
						+ "," + gearAxis + "," + shooterSpeed + "," + shooterCurrent + "," + shooterVoltage + "," + climbSpeed + "," + climbAxis);
				out.newLine();
			}
		} catch (Exception ex) {
			System.out.println("Error writing diagnostic log: " + ex.toString());
		}
	}
	
	public void close()
	{
		if (out != null)
		{
			try
			{			
				out.close();
				out = null;
			} catch (Exception ex) {
				System.out.println("Error closing file: " + ex.toString());
			}
		}
	}
		
}

