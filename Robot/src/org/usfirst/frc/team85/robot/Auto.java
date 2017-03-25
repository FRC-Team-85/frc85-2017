package org.usfirst.frc.team85.robot;

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {
	
	private Outputs _outputs = Outputs.getInstance();
	
	//selected autonomous sequence as 2d array, entries are commands, array entries are command pieces separated by ","
	private ArrayList<String[]> autoSequence = new ArrayList<String[]>();
	private int i = 0;
	private double _waitTime = 0;
	private double _waitStart = 0;
	private double currentTime = 0;
	private Timer _waitTimer = new Timer();
	boolean creepStarted = false;
	boolean shimmyStop = false;
	double moveTime = 0.5;
	
	private Timer _creepTimer = new Timer();
	
	public void resetAuto() {
		_waitTime = 0;
		_waitStart = 0;
		i = 0;
		currentTime = 0;
		_waitTimer.reset();
		autoSequence.clear();
		shimmyStop = false;
		creepStarted = false;
		_creepTimer.reset();
		_outputs.setGearMotorSpeed(0);
	}
	
	
	public void initAuto() {
		
		double auto = SmartDashboard.getNumber("AUTO MODE", 0);
		
		resetAuto();
		
		String fileString;
		
		
		if(auto == 1) { //CENTER
			fileString = "use:<name>:move, 0.335, 0.35, 6.2, 6.2:wait, 0.5:creep, 0.15, 0.15, 2.2, 2.2:creep, 0.0001, 0.0001, 2.1, 2.1";
		}
		else if(auto == 2) { //TURN LEFT FROM DANGER ZONE (WIDE)
			fileString = "use:<name>:move, 0.335, 0.35, 5.75, 5.75:wait, 0.3:move, 0.0, 0.5, 3, 3:wait, 0.3:move, 0.335, 0.35, 4.8, 4.8:wait, 0.1:creep, 0.15, 0.15, 3.2, 3.2:wait, 2.0";
		}
		else if(auto == 3) { //TURN RIGHT FROM DANGER ZONE (WIDE)
			fileString = "use:<name>:move, 0.335, 0.35, 5.75, 5.75:wait, 0.3:move, 0.5, 0.0, 2.9, 2.9:wait, 0.3:move, 0.335, 0.35, 4.8, 4.8:wait, 0.1:creep, 0.15, 0.15, 3.2, 3.2:wait, 2.0";
		}
		else if(auto == 4) { //TURN LEFT FROM SAFE ZONE (NARROW)
			fileString = "use:<name>:move, 0.335, 0.35, 9.3, 9.3:wait, 0.3:move, 0.0, 0.5, 3, 3:wait, 0.3:creep, 0.15, 0.15, 1.1, 1.1:wait, 2.0";
		}
		else if(auto == 5) { //TURN RIGHT FROM SAFE ZONE (NARROW)
			fileString = "use:<name>:move, 0.335, 0.35, 9.3, 9.3:wait, 0.3:move, 0.5, 0.0, 2.9, 2.9:wait, 0.3:creep, 0.15, 0.15, 1.1, 1.1:wait, 2.0";
		}
		else if (auto == 6) { //SHOOT RED
			fileString = "use:<name>:shoot, 1, 6:wait, 0.5:move, 0.75, 0.15, 3.8, 3.8:wait, 0.5:move, 0.435, 0.45, 8.5, 8.5";
		}
		else if (auto == 7) { //SHOOT BLUE
			fileString = "use:<name>:shoot, 1, 6:wait, 0.5:move, 0.15, 0.75, 3.8, 3.8:wait, 0.5:move, 0.435, 0.45, 8.5, 8.5";
		}
		else {
			fileString = SmartDashboard.getString("autoFileString", "");
		}
		
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
					//vision targeting stuff		//System.out.println("find boiler target");
					
					break;
				
				case "shoot":
					//shoot into boiler
					
					currentTime = _waitTimer.get();
			
					if (_waitTime == 0) {
						_outputs.setShooter(Double.parseDouble(autoSequence.get(i)[1]));
						_waitTime = Double.parseDouble(autoSequence.get(i)[2]);
						_waitStart = currentTime;
						System.out.printf("Shooting for '%f' seconds starting at second '%f'.\n", _waitTime, _waitStart);	
					}
					else if (currentTime - _waitStart < 1.5) { //no stage motor, time for shooter to warm up
						_outputs.setStage(0.0);
						_outputs.setIntake(0.0);
						_outputs.setShooter(Double.parseDouble(autoSequence.get(i)[1]));
					}
					else if (currentTime - _waitStart >= 1.5 && currentTime - _waitStart < _waitTime) { //stage motor enabled, balls begin to shoot
						_outputs.setStage(1.0);
						_outputs.setIntake(1.0);
						_outputs.setShooter(Double.parseDouble(autoSequence.get(i)[1]));
					}
					else if (currentTime - _waitStart >= _waitTime) {
						System.out.printf("Ending shoot at '%f' seconds.\n", currentTime);
						_outputs.setStage(0.0);
						_outputs.setIntake(0.0);
						_outputs.setShooter(0.0);
						i++;
						_waitTime = 0;
					}
					
					break;
					
				case "creep": //move and get jiggy with it
					System.out.println("creep");
	
					if (!creepStarted) {
						_creepTimer.start();
						creepStarted = true;
					}

					double time = _creepTimer.get();	
					
					if(time < moveTime && !shimmyStop) {
						_outputs.setGearMotorSpeed(1);
					}
					else if (time > moveTime && time < moveTime*2 && !shimmyStop) {
						_outputs.setGearMotorSpeed(-1);
					}
					else {
						_creepTimer.reset();
					}
					
					if (Math.abs(_outputs.getLeftEncoder()) >= Double.parseDouble(autoSequence.get(i)[3])
						|| Math.abs(_outputs.getRightEncoder()) >= Double.parseDouble(autoSequence.get(i)[4])) {
						//i++;
						_outputs.drive(0, 0);
						//_outputs.setGearMotorSpeed(0);
						//_outputs.resetDriveEncoders();
						//shimmyStop = true;
						//_creepTimer.stop();
					}
					else {
						_outputs.drive(
								Double.parseDouble(autoSequence.get(i)[1]),
								Double.parseDouble(autoSequence.get(i)[2])
								);
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
