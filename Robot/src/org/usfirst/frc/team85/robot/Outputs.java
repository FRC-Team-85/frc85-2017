package org.usfirst.frc.team85.robot;

import com.ctre.CANTalon;

import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
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
	
    private CANTalon _stage = new CANTalon(Addresses.STAGE_MOTOR);
    private CANTalon _intake = new CANTalon(Addresses.INTAKE_MOTOR);
    private CANTalon _shooter = new CANTalon(Addresses.SHOOTER_MOTOR);
	
	private Servo _leftServo = new Servo(Addresses.LEFT_SERVO);
	private Servo _rightServo = new Servo(Addresses.RIGHT_SERVO);
	
	DigitalInput leftGearLimit = new DigitalInput(Addresses.GEAR_LEFT_LIMIT);
	DigitalInput rightGearLimit = new DigitalInput(Addresses.GEAR_RIGHT_LIMIT);
	
	private double _speedScale = 900;
	
	private boolean driveOverride = false;
	private boolean opOverride = false;
	
	private Outputs() {

		_frontLeftMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		_frontRightMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		
	    //_shooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		//_shooter.changeControlMode(TalonControlMode.Speed);
		
		//_shooter.configNominalOutputVoltage(+0.0f, -0.0f);
		//_shooter.configPeakOutputVoltage(+12.0f, -12.0f);

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
		
		if(!driveOverride) {
			_frontLeftMotor.changeControlMode(TalonControlMode.Speed);
			_frontLeftMotor.set(targetSpeed * _speedScale);
		}
		else {
			_frontLeftMotor.changeControlMode(TalonControlMode.PercentVbus);
			_frontLeftMotor.set(targetSpeed);
		}
		
		return _frontLeftMotor.getSpeed();
		
	}

	public double setRightSpeed(double targetSpeed) {
		if(!driveOverride) {
			_frontRightMotor.changeControlMode(TalonControlMode.Speed);
			_frontRightMotor.set(targetSpeed * _speedScale);
		}
		else {
			_frontRightMotor.changeControlMode(TalonControlMode.PercentVbus);
			_frontRightMotor.set(targetSpeed);
		}
		
		return _frontRightMotor.getSpeed();
	}
	
	public double getLeftEncoder() {
		return _frontLeftMotor.getPosition();
	}
	
	public double getRightEncoder() {
		return _frontRightMotor.getPosition();
	}
	
	public void resetDriveEncoders() {
		System.out.println("Pre-reset Left encoder is " + getLeftEncoder());
		System.out.println("Pre-reset Right encoder is " + getRightEncoder());
		
		_frontLeftMotor.setPosition(0);
		_frontRightMotor.setPosition(0);

		System.out.println("Post-reset Left encoder is " + getLeftEncoder());
		System.out.println("Post-reset Right encoder is " + getRightEncoder());
	}

	public void setGearMotorSpeed(double speed) {
		SmartDashboard.putBoolean("Gear Manip Left Limit", leftGearLimit.get());
		SmartDashboard.putBoolean("Gear Manip Right Limit", rightGearLimit.get());
		if (leftGearLimit.get() && speed < 0 && !opOverride) {
			speed = 0;
		}
		else if (rightGearLimit.get() && speed > 0 && !opOverride) {
			speed = 0;
		}

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
	
    public void setStage(double speed) {
    	_stage.set(speed);
    }
    
    public void setIntake(double speed) {
    	_intake.set(speed);
    }
    
    public void setShooter(double speed) {
    	_shooter.set(speed);
    	//_shooter.set(speed);
    }

	public void releaseLeftFlap() {
		_leftServo.set(1); //change if wrong, fully right
	}
	
	public void releaseRightFlap() {
		_rightServo.set(0); //change if wrong, fully left
	}
	
	public void setDriveOverride(boolean on) {
		driveOverride = on;
		SmartDashboard.putBoolean("Drive Override", on);
	}
	
	public void setOpOverride(boolean on) {
		opOverride = on;
		SmartDashboard.putBoolean("Op Override", on);
	}
}