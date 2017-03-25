package org.usfirst.frc.team85.robot;

import com.ctre.CANTalon;

import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
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
	Relay visionLED = new Relay(Addresses.VISION_LED);
	
	DigitalInput leftClimberLimit = new DigitalInput(Addresses.CLIMB_LEFT_LIMIT);
	DigitalInput rightClimberLimit = new DigitalInput(Addresses.CLIMB_RIGHT_LIMIT);
	
	private double _speedScale = 900;
	private double gearSpeed;
	private double shooterSpeed;
	private double climbSpeed;
	
	private boolean driveOverride = false;
	private boolean opOverride = false;
	
	private Outputs() {

		_backLeftMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		_frontRightMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		
	    //_shooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		//_shooter.changeControlMode(TalonControlMode.Speed);
		
		//_shooter.configNominalOutputVoltage(+0.0f, -0.0f);
		//_shooter.configPeakOutputVoltage(+12.0f, -12.0f);

		_backLeftMotor.reverseSensor(true);

		_backLeftMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		_backLeftMotor.configPeakOutputVoltage(+12.0f, -12.0f);
		_frontRightMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		_frontRightMotor.configPeakOutputVoltage(+12.0f, -12.0f);

		_frontLeftMotor.changeControlMode(TalonControlMode.Follower);
		_frontLeftMotor.set(_backLeftMotor.getDeviceID());

		_backRightMotor.changeControlMode(TalonControlMode.Follower);
		_backRightMotor.set(_frontRightMotor.getDeviceID());

		_backLeftMotor.setProfile(0);
		_backLeftMotor.setF(0.1097);
		_backLeftMotor.setP(0.22);
		_backLeftMotor.setI(0);
		_backLeftMotor.setD(0);
		_frontRightMotor.setProfile(0);
		_frontRightMotor.setF(0.1097);
		_frontRightMotor.setP(0.22);
		_frontRightMotor.setI(0);
		_frontRightMotor.setD(0);
		
		_gearMotor.changeControlMode(TalonControlMode.PercentVbus);
		_gearMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		visionLED.setDirection(Direction.kForward);
	}

	public double setLeftSpeed(double targetSpeed) {
		
		if(!driveOverride) {
			_backLeftMotor.changeControlMode(TalonControlMode.Speed);
			_backLeftMotor.set(targetSpeed * _speedScale);
		}
		else {
			_backLeftMotor.changeControlMode(TalonControlMode.PercentVbus);
			_backLeftMotor.set(targetSpeed);
		}
		
		return _backLeftMotor.getSpeed();
		
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
	
	public double getLeftSpeed() {
		return _backLeftMotor.getSpeed();
	}
	
	public double getRightSpeed() {
		return _frontRightMotor.getSpeed();
	}
	
	public double getLeftEncoder() {
		return _backLeftMotor.getPosition();
	}
	
	public double getRightEncoder() {
		return _frontRightMotor.getPosition();
	}
	
	public void resetDriveEncoders() {
		System.out.println("Pre-reset Left encoder is " + getLeftEncoder());
		System.out.println("Pre-reset Right encoder is " + getRightEncoder());
		
		_backLeftMotor.setPosition(0);
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
		gearSpeed = speed;
	}
	
	public double getGearMotorSpeed() {
		return gearSpeed;
	}

	/*public void setGearEncoder(double value) {
		_gearMotor.setPosition(value);
	}*/

	public double getGearEncoder() {
		return _gearMotor.getPosition();
	}

	public void resetGearEncoder() {
		_gearMotor.setPosition(0);
	}
	
	public void drive(double left, double right) {
		setLeftSpeed(left);
		setRightSpeed(right);
	}

	public void climb(double speed) {
		if(!leftClimberLimit.get() && !rightClimberLimit.get()) {
			_climbMotor.set(speed);
		}
		else {
			_climbMotor.set(0);
		}
		
		climbSpeed = speed;
	}
	
	public double getClimbSpeed() {
		return climbSpeed;
	}
	
	public double getClimberCurrent()
	{
		return _climbMotor.getOutputCurrent();
	}
	
	public double getFrontLeftCurrent()
	{
		return _frontLeftMotor.getOutputCurrent();
	}
	
	public double getFrontRightCurrent()
	{
		return _frontRightMotor.getOutputCurrent();
	}
	
	public double getBackLeftCurrent()
	{
		return _backLeftMotor.getOutputCurrent();
	}
	
	public double getBackRightCurrent()
	{
		return _backRightMotor.getOutputCurrent();
	}
	
	public double getFrontLeftVoltage()
	{
		return _frontLeftMotor.getOutputVoltage();
	}
	
	public double getFrontRightVoltage()
	{
		return _frontRightMotor.getOutputVoltage();
	}
	
	public double getBackLeftVoltage()
	{
		return _backLeftMotor.getOutputVoltage();
	}
	
	public double getBackRightVoltage()
	{
		return _backRightMotor.getOutputVoltage();
	}
	
	public boolean getDriveOverride()
	{
		return driveOverride;
	}
	
	public boolean getOpOverride()
	{
		return opOverride;
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
    	_shooter.set(-speed);
    	shooterSpeed = -speed;
    }
    
    public double getShooterSpeed() {
    	return shooterSpeed;
    }

    public double getShooterVoltage() {
    	return _shooter.getOutputVoltage();
    }
    
    public double getShooterCurrent() {
    	return _shooter.getOutputCurrent();
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
	
    public void setLED(boolean on) {
    	if(on) {
    		visionLED.set(Relay.Value.kOn);
    	}
    	else {
    		visionLED.set(Relay.Value.kOff);
    	}
    }
}