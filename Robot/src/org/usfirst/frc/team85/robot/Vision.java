package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {
	
	public static double power = 0;
	public static double previousError = 0;
	public static double centerOfTarget = 0;
	
	public static double center() {
		
		double[] centerXArray = null;
		
		NetworkTable _table;
		_table = NetworkTable.getTable("GRIP/myContoursReport");
		centerXArray = _table.getNumberArray("centerX", centerXArray);
		
		double one = centerXArray[0];
		double two = centerXArray[1];
		
		centerOfTarget = (one + two) / 2; //finds the center of the two targets; where the hook would be

		//probably make two classes

		double Kp = 0.5;
		double Kd = 0.2;
		double Ki = 0.0; //not being used
		
		double maxPower = 0.95;
		double minPower = 0.48;
		
		double error = centerOfTarget - 160; //distance from target, assumes image is 320p
		double changeInError = error - previousError;
		
		power = Kp * error + Kd * changeInError;
		
		if (Math.abs(power) > maxPower) {
			power = maxPower;
		}
		
		if (Math.abs(power) < minPower) {
			power = minPower;
		}
		
		SmartDashboard.putNumber("power", power);
		SmartDashboard.putNumber("centerOfTarget", centerOfTarget);
		SmartDashboard.putNumber("error", error);
		
		previousError = error;
		
		return power;		
	}

}
