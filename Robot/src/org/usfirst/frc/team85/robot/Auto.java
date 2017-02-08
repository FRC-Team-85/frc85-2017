package org.usfirst.frc.team85.robot;

import java.util.ArrayList;
import java.util.Arrays;

public class Auto {
	
	private Outputs _outputs = new Outputs();
	private Inputs _inputs = new Inputs();
	//selected autonomous sequence as 2d array, entries are commands, array entries are command pieces separated by ","
	private ArrayList<String[]> autoSequence = new ArrayList<String[]>();
	
	public void initAuto(String fileString) {
		//argument fileString is the file
		//will be file without whitespace and comments
		String string = "";
		//is toggled for comments
		boolean comment = false;
		String name = "moveshoot";
		
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
		string = string.replaceAll(System.getProperty("line.separator"), "").replaceAll(" ", "").replaceAll("\t", "").replaceAll("<", "").replaceAll(">", "");
		
		System.out.println(string);
		
		//whole file split into sequences by ";"
		ArrayList<String> sequences = new ArrayList<String>(Arrays.asList(string.split(";")));
		//whole file as 2d array, entries are CommandStrings entries split apart by ":" into arrays
		ArrayList<String[]> commands = new ArrayList<String[]>();

		
		//split sequences into individual commands by ":"
		for (int i = 0; i < sequences.size(); i++) { 
			commands.add(sequences.get(i).split(":"));
		}
		
		for (int i = 0; i < commands.size(); i++) {
			//detects which sequence says "use"
			if (commands.get(i)[1].equals(name)) {
				//split by "," and put in autonSequence
				for (int j = 0; j < commands.get(i).length; j++) {
					autoSequence.add(commands.get(i)[j].split(","));
				}
				
				break;
			}
		}
	}
		
	public void doAuto() {
		//loops over commands in the sequence and executes them
		for (int i = 1; i < autoSequence.size(); i++) { //count starts at 1 because 1st command indicates sequence to use
			switch (autoSequence.get(i)[0]) { //switch is the first parameter of the command
				case "move":
					//System.out.println("move robot");
					/*
					_inputs.driveEncodersReset();
					while (_inputs.getLeftFrontEncoder < autoSequence.get(i)[4]
						&& _inputs.getRightFrontEncoder < autoSequence.get(i)[5]) {
					
					_outputs.setLeftSpeed(Integer.parseInt(autoSequence.get(i)[2]));
					_outputs.setRightSpeed(Integer.parseInt(autoSequence.get(i)[3]));
					}
					*/
					break;
				
				case "findBoiler":
					//vision targeting stuff
					//System.out.println("find boiler target");
					break;
				
				case "findGear":
					//vision targeting stuff
					//System.out.println("find gear target");
					break;
				
				case "shoot":
					//shoot into boiler
					//System.out.println("shoot");
					break;
				
				case "gear":
					//place gear
					//System.out.println("place gear");
					break;
			}
		}
	}
	
}
