package org.usfirst.frc.team85.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import edu.wpi.first.wpilibj.DriverStation;

public class Diagnostics {
	
	private Outputs _outputs = Outputs.getInstance();

	File log;
	BufferedWriter out = null;
	
	public void init()
	{
		try
		{
			close();
			
			log = new File("/home/lvuser/log" + System.currentTimeMillis() + ".csv");
			if (log.exists() == false) {
				log.createNewFile();
				out = new BufferedWriter(new FileWriter(log, true));
				out.append("Match Time,Left Encoder,Right Encoder,Climb Current,Climb Left Limit,Climb Right Limit");
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
		String leftEncoder1 = Double.toString(leftEncoder);
		String rightEncoder1 = Double.toString(rightEncoder);
		String climberCurrent = Double.toString(_outputs.getClimberCurrent());
		String climbLeftLimit = Boolean.toString(_outputs.leftClimberLimit.get());
		String climbRightLimit = Boolean.toString(_outputs.rightClimberLimit.get());
				
		try {
			if (leftEncoder != 0 || rightEncoder != 0)
			{
				out.append(matchTime + "," + leftEncoder1 + "," + rightEncoder1 + "," + climberCurrent + "," + climbLeftLimit + "," + climbRightLimit);
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

