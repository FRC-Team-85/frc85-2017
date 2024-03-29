package org.usfirst.frc.team85.robot;

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {
	
	private Outputs _outputs = Outputs.getInstance();
	private Drive _drive = Drive.getInstance();
	
	//autonomous sequence as 2d array, each entry is a command (separated by ":"), entries are arrays of command pieces (separated by ",")
	private ArrayList<String[]> autoSequence = new ArrayList<String[]>();
	private int i = 0;
	private double _waitTime = 0;
	private double _waitStart = 0;
	private double currentTime = 0;
	private Timer _waitTimer = new Timer();
	boolean creepStarted = false;
	boolean shimmyStop = false;
	double moveTime = 0.5;
	boolean gyroReset = false;
	
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
			fileString = "move, 0.35, 0.35, 6.75, 6.75:wait, 0.5:creep, 0.15, 0.15, 2.2, 2.2:creep, 0.0001, 0.0001, 2.1, 2.1";
		}
		else if(auto == 2) { //TURN LEFT FROM DANGER ZONE (WIDE)
			fileString = "move, 0.35, 0.35, 8.0, 8.0:wait, 0.3:move, -0.3, 0.3, 1.65, 1.65:wait, 0.3:move, 0.35, 0.35, 6.1, 6.1:wait, 0.1:creep, 0.15, 0.15, 3.2, 3.2:wait, 2.0";
		}
		else if(auto == 3) { //TURN RIGHT FROM DANGER ZONE (WIDE)
			fileString = "move, 0.35, 0.35, 8.0, 8.0:wait, 0.3:move, 0.3, -0.3, 1.65, 1.65:wait, 0.3:move, 0.35, 0.35, 6.1, 6.1:wait, 0.1:creep, 0.15, 0.15, 3.2, 3.2:wait, 2.0";
		}
		else if(auto == 4) { //TURN LEFT FROM SAFE ZONE (NARROW)
			fileString = "move, 0.35, 0.35, 9.85, 9.85:wait, 0.5:gyro, -0.2, 0.2, 48:wait, 0.3:creep, 0.15, 0.15, 4.8, 4.8:wait, 2.0";
		}
		else if(auto == 5) { //TURN RIGHT FROM SAFE ZONE (NARROW)
			fileString = "move, 0.35, 0.35, 10, 10:wait, 0.5:gyro, 0.2, -0.2, 42:wait, 0.3:creep, 0.15, 0.15, 4.8, 4.8:wait, 2.0";
		}
		else if (auto == 6) { //SHOOT RED
			fileString = "shoot, 0.87, 10:wait, 0.5:move, -0.15, -0.75, 4.2, 4.2:wait, 0.5:move, -0.45, -0.45, 12.5, 12.5";
		}
		else if (auto == 7) { //SHOOT BLUE
			fileString = "shoot, 0.87, 10:wait, 0.5:move, -0.75, -0.15, 4.2, 4.2:wait, 0.5:move, -0.45, -0.45, 12.5, 12.5";
		}
		else {
			fileString = SmartDashboard.getString("autoFileString", "");
		}
		
		SmartDashboard.putString("autoFileString", fileString);
		
		String string = ""; //will be without whitespace and comments
		
		boolean comment = false; //is toggled for comments
		
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
		
		//remove enter character, space character, tabs and <>, runs faster doing all at once?
		string = string.replaceAll(System.getProperty("line.separator"), "").replaceAll(" ", "").replaceAll("\t", "").replaceAll("<", "").replaceAll(">", "");
		
		System.out.println(string);
		
		ArrayList<String> commands = new ArrayList<String>(Arrays.asList(string.split(":"))); //entries are commands split apart by ":"
		

		try {
			System.out.println("number of commands: " + commands.size());
		
			for (int j = 0; j < commands.size(); j++) { //split by "," and put in autonSequence
				autoSequence.add(commands.get(j).split(","));
			}
			
			//System.out.println("Amount of );
			_outputs.resetDriveEncoders();
			
			_waitTimer.start();
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}	
	
	//is called repeatedly in autonomousPeriodic
	public void run() {
		//System.out.println("autoSequence size:" + autoSequence.size());
		if (i < autoSequence.size()) {
			//System.out.println("Loop reached");
			switch (autoSequence.get(i)[0]) { //switch is the first parameter of the command
				case "move":
					if (autoSequence.get(i).length < 5) {
						System.out.println("Error: Move sequence requires 4 arguments.");
						break;
					}
					
					System.out.println("move robot");
					double left = Double.parseDouble(autoSequence.get(i)[1]);
					double right = Double.parseDouble(autoSequence.get(i)[2]);
					if (left == right) {
						_drive.FPSdrive(true, 0,  false, -left);
					}
					else {
						_outputs.drive(left, right);
					}
					
					if (Math.abs(_outputs.getLeftEncoder()) >= Double.parseDouble(autoSequence.get(i)[3])
						|| Math.abs(_outputs.getRightEncoder()) >= Double.parseDouble(autoSequence.get(i)[4])) {
						i++;
						_outputs.drive(0, 0);
						_outputs.resetDriveEncoders();
					}
					
					break;
					
				case "wait":
					currentTime = _waitTimer.get();
					if (autoSequence.get(i).length < 2) {
						System.out.println("Error: Wait sequence requires 1 argument.");
						break;
					}
					
					if (_waitTime == 0)
					{
						_waitTime = Double.parseDouble(autoSequence.get(i)[1]);
						_waitStart = currentTime;
						System.out.printf("Wait for '%f' seconds starting at second '%f'.\n", _waitTime, _waitStart);
						_outputs.gyroReset();
					}
					else if (currentTime - _waitStart >= _waitTime)
					{
						System.out.printf("Ending wait at '%f' seconds.\n", currentTime);
						i++;
						_waitTime = 0;
					}
					
					break;
				
				case "shoot":
					//shoot into boiler
					
					if (autoSequence.get(i).length < 3) {
						System.out.println("Error: Shoot sequence requires 2 arguments.");
						break;
					}
					
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
						_outputs.setStage(SmartDashboard.getNumber("Speed Stage", 0.5));
						_outputs.setIntake(SmartDashboard.getNumber("Speed Intake", 1));
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
					if (autoSequence.get(i).length < 5) {
						System.out.println("Error: Creeper sequence requires 4 arguments.");
						break;
					}
					
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
					
				case "gyro":
					if (autoSequence.get(i).length < 4) {
						System.out.println("Error: Gyro sequence requires 3 arguments.");
						break;
					}
					
					double angle = Math.abs(_outputs.gyroAngle());
					
					
					
					System.out.println("turn gyro");
					_outputs.drive(
							Double.parseDouble(autoSequence.get(i)[1]),
							Double.parseDouble(autoSequence.get(i)[2])
							);
					
					if (angle >= Double.parseDouble(autoSequence.get(i)[3])) {
						i++;
						_outputs.drive(0, 0);
						_outputs.resetDriveEncoders();
						_outputs.gyroReset();
					}
					
					break;
					
				case "findGear":
					//vision targeting stuff
					//System.out.println("find gear target");
					
					break;
			}
		}
	}
	
}
