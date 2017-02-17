package org.usfirst.frc.team85.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Outputs {

	private static Outputs instance = null;

	public static Outputs getInstance() {
		if (instance == null) {
			instance = new Outputs();
		}

		return instance;
	}

	private Vision _vision = new Vision();

	private CANTalon _frontLeftMotor = new CANTalon(Addresses.LEFT_FRONT_MOTOR);
	private CANTalon _backLeftMotor = new CANTalon(Addresses.LEFT_BACK_MOTOR);

	private CANTalon _frontRightMotor = new CANTalon(Addresses.RIGHT_FRONT_MOTOR);
	private CANTalon _backRightMotor = new CANTalon(Addresses.RIGHT_BACK_MOTOR);

	private CANTalon _gearMotor = new CANTalon(Addresses.GEAR_MOTOR);

	private CANTalon _climbMotor = new CANTalon(Addresses.CLIMB_MOTOR);

	private DigitalInput leftGearLimit = new DigitalInput(Addresses.GEAR_LEFT_LIMIT);
	private DigitalInput rightGearLimit= new DigitalInput(Addresses.GEAR_RIGHT_LIMIT);

	private double _speedScale = 900;

	private Outputs() {

		_frontLeftMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		_frontRightMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);

		_frontLeftMotor.changeControlMode(TalonControlMode.Speed);
		_frontRightMotor.changeControlMode(TalonControlMode.Speed);

		_frontLeftMotor.reverseSensor(true);

		_frontLeftMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		_frontLeftMotor.configPeakOutputVoltage(+12.0f, -12.0f);
		_frontRightMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		_frontRightMotor.configPeakOutputVoltage(+12.0f, -12.0f);

		_backLeftMotor.changeControlMode(TalonControlMode.Follower);
		_backLeftMotor.set(_frontLeftMotor.getDeviceID());

		_backRightMotor.changeControlMode(TalonControlMode.Follower);
		_backRightMotor.set(_frontRightMotor.getDeviceID());

		_frontLeftMotor.setProfile(0);
		_frontLeftMotor.setF(0.1097);
		_frontLeftMotor.setP(0.22);
		_frontLeftMotor.setI(0);
		_frontLeftMotor.setD(0);
		_frontRightMotor.setProfile(0);
		_frontRightMotor.setF(0.1097);
		_frontRightMotor.setP(0.22);
		_frontRightMotor.setI(0);
		_frontRightMotor.setD(0);
		
		_gearMotor.changeControlMode(TalonControlMode.PercentVbus);
		_gearMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
	}

	public double setLeftSpeed(double targetSpeed) {
		_frontLeftMotor.set(targetSpeed * _speedScale);
		return _frontLeftMotor.getSpeed();
	}

	public double setRightSpeed(double targetSpeed) {
		_frontRightMotor.set(targetSpeed * _speedScale);
		return _frontRightMotor.getSpeed();
	}

	public void setGearMotorSpeed(double speed) {
		SmartDashboard.putBoolean("Gear Manip Left Limit", leftGearLimit.get());
		SmartDashboard.putBoolean("Gear Manip Right Limit", rightGearLimit.get());
		if (leftGearLimit.get() && speed < 0) {
			speed = 0;
		} else if (rightGearLimit.get() && speed > 0) {
			speed = 0;
		}

		SmartDashboard.putNumber("Gear Manip Speed", speed);
		_gearMotor.set(speed);
	}

	/*public void setGearEncoder(double value) {
		_gearMotor.setPosition(value);
	}*/

	public double getGearEncoder() {
		return _gearMotor.getPosition();
	}

	public void drive(double left, double right) {
		setLeftSpeed(left);
		setRightSpeed(right);
	}

	public void climb(double speed) {
		_climbMotor.set(speed);
	}

	public void visionTrack() {

		double power = _vision.center();

		setLeftSpeed(-power);
		setRightSpeed(power);
	}
	/*
	 * public void encoderStraightDrive() {
	 * 
	 * int currentDifference = 0;
	 * 
	 * drive(leftSpeed,rightSpeed);
	 * 
	 * int leftEncoder = _inputs.getLeftFrontDriveEncoder(); int rightEncoder =
	 * _inputs.getRightFrontDriveEncoder();
	 * 
	 * currentDifference = leftEncoder - rightEncoder;
	 * 
	 * if (currentDifference > 0) { //positive = left is ahead by 1 leftSpeed =
	 * leftSpeed - .05; } else if (currentDifference < 0) { //negative = right
	 * is ahead by 1 rightSpeed = rightSpeed - .05; } else { //auto friendly
	 * values, set these to left joystick for teleop leftSpeed = 0.8; rightSpeed
	 * = 0.8; } }
	 */

}