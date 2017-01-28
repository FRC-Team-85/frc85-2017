package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision {
	
	public static double previousError = 0;
	public static double centerOfTarget = 0;
	
	public static void main(String[] args) {
		
		double[] centerXArray = null;
		
		NetworkTable _table;
		_table = NetworkTable.getTable("GRIP/myCountoursReport");
		centerXArray = _table.getNumberArray("centerX", centerXArray);
		
		double one = centerXArray[0];
		double two = centerXArray[1];
		
		centerOfTarget = Math.abs((two + one) / 2); //finds the center of the two targets; where the hook would be
	}
	
	public static double center() {

		double Kp = 0.0005;
		double Kd = 0.02;
		double Ki = 0.0; //not being used
		
		double maxPower = 0.95;
		double minPower = 0.48;
		
		double error = 160 - centerOfTarget; //distance from target, assumes image is 320p
		double changeInError = error - previousError;
		
		double power = Kp * error * Kd * changeInError;
		
		if (Math.abs(power) > maxPower) {
			if (power > 0) power = maxPower;
			else power = -maxPower;
		}
		
		if (Math.abs(power) < minPower) {
			if (power > 0) power = minPower;
			else power = -minPower;
		}
		
		previousError = error;
		
		return power;
		
	}

}
