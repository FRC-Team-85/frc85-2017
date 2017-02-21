package org.usfirst.frc.team85.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
	
	private static Shooter instance = null;

	public static Shooter getInstance() {
		if (instance == null) {
			instance = new Shooter();
		}

		return instance;
	}
		
    private CANTalon _shooter = new CANTalon(Addresses.SHOOTER_MOTOR);
    
    private double _speed = SmartDashboard.getNumber("shooter", 0);
    
    private Shooter() {
	    _shooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		_shooter.changeControlMode(TalonControlMode.Speed);
		
		_shooter.configNominalOutputVoltage(+0.0f, -0.0f);
		_shooter.configPeakOutputVoltage(+12.0f, -12.0f);    
		
		SmartDashboard.putNumber("shooter", 0);
    }
    
    public void setShooter(double targetSpeed) {
		_shooter.set(targetSpeed);
    }
}