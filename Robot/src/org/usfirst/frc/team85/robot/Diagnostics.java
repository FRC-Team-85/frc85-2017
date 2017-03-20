package org.usfirst.frc.team85.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Diagnostics {
	
	private Outputs _outputs = Outputs.getInstance();
	
	public void log() {
	
		double leftEncoder = _outputs.getLeftEncoder();
		double rightEncoder = _outputs.getRightEncoder();
		
		File log = new File("log.csv");
		
		String leftEncoder1 = Double.toString(leftEncoder);
		String rightEncoder1 = Double.toString(rightEncoder);
				
		try {
			
			BufferedWriter out = new BufferedWriter(new FileWriter(log, true));
			
			if (log.exists() == false) {
				log.createNewFile();
				out.append("Left Encoder,Right Encoder");
				out.newline();
				out.close();
			}
			
			
			out.append(leftEncoder1 + "," + rightEncoder1);
			out.newLine();
			out.close();
				
		} catch (Exception e) {
			System.out.println("error");
		}
	}
		
}

