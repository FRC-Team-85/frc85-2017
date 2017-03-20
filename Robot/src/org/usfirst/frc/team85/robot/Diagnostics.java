package org.usfirst.frc.team85.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Diagnostics {
	
	 private InputsDrive _inputsDrive = InputsDrive.getInstance();
	 private InputsOp _inputsOp = InputsOp.getInstance();
	 private Outputs _outputs = Outputs.getInstance();
	 private Shooter _shooter = Shooter.getInstance();
	 private Drive _drive = new Drive();
	 private DriverAssistCameras _driverAssistCameras;
	 private Auto _auto = new Auto();
	
	public void log() {
	
		double leftEncoder = _outputs.getLeftEncoder();
		double rightEncoder = _outputs.getRightEncoder();
		
		File log = new File("log.txt");
		
		String leftEncoder1 = Double.toString(leftEncoder);
		String rightEncoder1 = Double.toString(rightEncoder);
				
		try {
					
			if (log.exists() == false) {
				log.createNewFile();
			}
				
			BufferedWriter out = new BufferedWriter(new FileWriter(log, true));
			
			out.append("LeftEncoder - " + leftEncoder1 + "; RightEncoder - " + rightEncoder1);
			out.newLine();
			out.close();
				
		} catch (Exception e) {
			System.out.println("error");
		}
	}
		
}

