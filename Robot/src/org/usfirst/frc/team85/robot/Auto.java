package org.usfirst.frc.team85.robot;

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class Auto {
	
	private Outputs _outputs = Outputs.getInstance();
	
	//selected autonomous sequence as 2d array, entries are commands, array entries are command pieces separated by ","
	private ArrayList<String[]> autoSequence = new ArrayList<String[]>();
	private int i = 0;
	private double _waitTime = 0;
	private double _waitStart = 0;
	private double currentTime = 0;
	private Timer _waitTimer = new Timer();
	
	public void resetAuto() {
		_waitTime = 0;
		_waitStart = 0;
		i = 0;
		currentTime = 0;
		_waitTimer.reset();
		autoSequence.clear();
	}
	
	
	public void initAuto(String fileString) {
		resetAuto();
		//argument fileString is the file
		//will be file without whitespace and comments
		String string = "";
		//is toggled for comments
		boolean comment = false;
		
		//remove comments
		for (int i = 0; i < fileString.length(); i++) {
			if (fileString.charAt(i) == '#') {
				comment = !comment;
				continue;
			}
			
			if (!comment) {
				string = string + fileString.charAt(i);
			}
		}
		
		//remove enter character, space character, tabs and <>
		//runs faster doing all at once?
		string = string.replaceAll(System.getProperty("line.separator"), "").replaceAll(" ", "").replaceAll("\t", "").replaceAll("<", "").replaceAll(">", "");
		
		System.out.println(string);
		
		//whole file split into sequences by ";"
		ArrayList<String> sequences = new ArrayList<String>(Arrays.asList(string.split(";")));
		System.out.println("sequences size: " + sequences.size());
		//whole file as 2d array, entries are CommandStrings entries split apart by ":" into arrays
		ArrayList<String[]> commands = new ArrayList<String[]>();
		System.out.println("commands.size: " + commands.size());

		try
		{
			//split sequences into individual commands by ":"
			for (int j = 0; j < sequences.size(); j++) { 
				commands.add(sequences.get(j).split(":"));
			}
			
			System.out.println("Amount of commands: " + commands.size());
		
			for (int k = 0; k < commands.size(); k++) {
				//detects which sequence says "use"
				String[] command = commands.get(k);
				if (command[0].equals("use")) {
					System.out.println("Found 'use' in command.");
					//split by "," and put in autonSequence
					for (int j = 2; j < command.length; j++) {
						autoSequence.add(command[j].split(","));
					}
				
					break;
				}
			}
			
			//System.out.println("Amount of );
			_outputs.resetDriveEncoders();
			
			_waitTimer.start();
		}
		catch (Exception ex)
		{
			System.out.println(ex.toString());
		}
	}	
		
	public void run() {
		//loops over commands in the sequence and executes them
		//count starts at 1 because 1st command indicates sequence to use
		//System.out.println("autoSequence size:" + autoSequence.size());
		if (i < autoSequence.size()) {
			//System.out.println("Loop reached");
			//switch is the first parameter of the command
			switch (autoSequence.get(i)[0]) {
				case "move":
					System.out.println("move robot");
					_outputs.drive(
							Double.parseDouble(autoSequence.get(i)[1]),
							Double.parseDouble(autoSequence.get(i)[2])
							);
					
					if (Math.abs(_outputs.getLeftEncoder()) >= Double.parseDouble(autoSequence.get(i)[3])
						|| Math.abs(_outputs.getRightEncoder()) >= Double.parseDouble(autoSequence.get(i)[4])) {
						i++;
						_outputs.drive(0, 0);
						_outputs.resetDriveEncoders();
					}
					
					break;
					
				case "wait":
					currentTime = _waitTimer.get();
					
					if (_waitTime == 0)
					{
						_waitTime = Double.parseDouble(autoSequence.get(i)[1]);
						_waitStart = currentTime;
						System.out.printf("Wait for '%f' seconds starting at second '%f'.\n", _waitTime, _waitStart);
					}
					else if (currentTime - _waitStart >= _waitTime)
					{
						System.out.printf("Ending wait at '%f' seconds.\n", currentTime);
						i++;
						_waitTime = 0;
					}
					
					break;
				
				case "findBoiler":
					//vision targeting stuff
					//System.out.println("find boiler target");
					
					break;
				
				case "shoot":
					//shoot into boiler
					
					currentTime = _waitTimer.get();
					
					if (_waitTime == 0) {
						_outputs.setStage(1.0);
						_outputs.setShooter(Double.parseDouble(autoSequence.get(i)[1]));
						_waitTime = Double.parseDouble(autoSequence.get(i)[2]);
						_waitStart = currentTime;
						System.out.printf("Wait for '%f' seconds starting at second '%f'.\n", _waitTime, _waitStart);
					}
					else if (currentTime - _waitStart >= _waitTime) {
						System.out.printf("Ending wait at '%f' seconds.\n", currentTime);
						_outputs.setStage(0.0);
						_outputs.setShooter(0.0);
						i++;
						_waitTime = 0;
					}
					
					break;
				
				case "findGear":
					//vision targeting stuff
					//System.out.println("find gear target");
					
					break;
				
				case "gear":
					//place gear
					//System.out.println("place gear");
					//do we need this case?
					
					break;
			}
		}
	}
	
}
