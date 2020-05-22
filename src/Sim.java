import java.io.File;
import java.util.Scanner;
import java.io.IOException;
/**
* Simulator class used to load and execute a program using 
* the MipsComputer class.
*
* The simulator will:
* 1) create empty computer.
* 2) load instructions binary file at file path if present,
*    else from array of BitStrings below.
* 3) execute instructions in computer.
* 4) print final computer state to the screen.
* 
* @author Trent Julich
* @version 5/16/2020
**/
public class Sim {
	
	/**
	* Array of instructions which are loaded one by one into the computer.
	* Note, the white space will all be trimmed out, the only requirement is 
	* each String in the array contains exactly 32 1's or 0's, and nothing else.
	* Instructions which are unable to be interpretted will be skipped, and a 
	* message printed to the console.
	* 
	**/
	private static final String[] INSTRUCTIONS = {
		// R-Format instruction
		//   op    rs    rt    rd   shamt funct
		//"000000 00000 00010 00000 00000 001000",
	
		// I-Format instruction
		// opcode   rs     rt          immediate
		//"001000  00000  00000   0000 0000 0000 0001",
	
		//"001000  00000  00000   0000 0000 0000 0111",
		//"101011  00000  00000   0000 0000 0000 0000",
		//"100011  00000  00111   0000 0000 0000 0000",
		  "00100000001000010000000001100100"
	};

	/**
	* Relative path to the input file, if it exists.
	**/
	private static final String filePath = "../input/instructions.txt";

	/**
	* Method used to convert Strings to BitStrings, in order to 
	* allow the instructions to be entered more easily above.
	*
	* @param String theBits The bits to create a BitString from.
	**/
	private static BitString convertToBitString(final String theBits) {
		return BitString.fromBits(32, theBits.toCharArray());
	}
	
	/**
	* Entry point into the program.
	**/
	public static void main(String... strings) {
		
		final MipsComputer comp = new MipsComputer();
		
		File f = new File(filePath);
		if (f.exists() && !f.isDirectory()) {
			comp.setFromMars(true);
			readInstructionsFromFile(f, comp);
		} else {
			comp.setFromMars(false);
			readInstructionsFromArray(comp);
		}
		
		comp.execute();
		comp.print();
	}
	
	/**
	* Method used to load computer with instructions that are found in 
	* the given file. 
	*
	* @param File file The file to read instructions from.
	* @param MipsComputer comp The computer to load the instructions into.
	**/
	public static void readInstructionsFromFile(final File file, final MipsComputer comp) {
		System.out.println("Loading instructions from file...");
		final Scanner s;
		try {
			s = new Scanner(file);
			while(s.hasNextLine()){
				comp.loadInstruction(convertToBitString(s.nextLine().replaceAll("\\s", "")));
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Method used to load computer with instructions that are found in the 
	* static array found above.
	*
	* @param MipsComputer comp The computer to load with the instructions.
	**/
	public static void readInstructionsFromArray(final MipsComputer comp) {
		System.out.println("Loading instructions from array...");
		for (int i = 0; i < INSTRUCTIONS.length; i++) {
			comp.loadInstruction(convertToBitString(INSTRUCTIONS[i].replaceAll("\\s", "")));
		}
	}
}