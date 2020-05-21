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
		// Test R-Format instructions.
		// op     rs    rt   rd   shamt  funct
		"000000 00001 00010 00011 00000 100000",  // add $1 + $2 = $3
	
		// Test I-Format instructions.
		// opcode   rs     rt      immediate 
		"001000    00001 00001 0000 0000 0000 0111" // addi $1 = $1 + 7
	};

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
		
		for (int i = 0; i < INSTRUCTIONS.length; i++) {
			comp.loadInstruction(convertToBitString(INSTRUCTIONS[i].replaceAll("\\s", "")));
		}
		
		BitString test1 = BitString.fromBits(32, "00000000000000000000000000100000".toCharArray());
		comp.setRegister(1, test1);
		comp.setRegister(2, test1);
		
		comp.execute();
		
		comp.print();
	}
}