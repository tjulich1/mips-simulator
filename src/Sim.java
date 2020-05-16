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
	
	private static final String[] INSTRUCTIONS = {
		
		"00000000 00000000 00000000 00000000",
		"00000000 00000000 00000000 00000000",
		"00000000 00000000 00000000 00000000"
	
	};

	/**
	* Method used to convert Strings to BitStrings, in order to 
	* allow the instructions to be entered more easily above.
	*
	* @param String theBits The bits to create a BitString from.
	**/
	private static BitString convertToBitString(final String theBits) {
		return BitString.fromBits(theBits.toCharArray());
	}
	
	/**
	* Entry point into the program.
	**/
	public static void main(String... strings) {
		final MipsComputer comp = new MipsComputer();
		
		for (int i = 0; i < INSTRUCTIONS.length; i++) {
			comp.loadInstruction(convertToBitString(INSTRUCTIONS[i].replaceAll("\\s", "")));
		}
		
		comp.execute();
		comp.print();
		
	}
}