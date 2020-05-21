import java.util.Map;
import java.util.HashMap;

/**
* Class representing a simplified Mips computer. Contains 32 registers,
* a collection of BitStrings representing data memory, as well as a collection of 
* BitStrings representing instruction memory. Allows a user to load in an 
* array of instructions to the instruction memory, execute the instructions,
* and print the computers contents to the console.
*
* @author Trent Julich
* @version 5/15/2020
**/
public class MipsComputer {
	
	private static final int MAX_INSTRUCTIONS = 200;
	private static final int MAX_DATA = 500;
	private static final int WORD_LENGTH = 32;
	
	private final BitString ZERO = new BitString(0);
	
	private BitString[] registers;

	//private Map registers;

	private BitString[] dataMemory;
	private BitString[] instructionMemory;

	private BitString programCounter;
	
	private int nextInstruction;
	
	
	
	/**
	* Constructor for a new Mips Computer.
	**/
	public MipsComputer() {
		this.dataMemory = new BitString[MAX_DATA];
		this.instructionMemory = new BitString[MAX_INSTRUCTIONS];
		this.registers = new BitString[32];
		this.nextInstruction = 0;
		this.programCounter = new BitString(32, 0);
		for (int i = 0; i < 32; i++) {
			this.registers[i] = new BitString(WORD_LENGTH);
		}
	}
	
	/**
	* Method used to load an instruction into the next line of instruction memory.
	* If there is already the maximum number of instructions loaded into instruction
	* memory, the instruction is not added.
	*
	* @param theInstruction The instruction that should be added to instruction
	* memory.
	**/
	public void loadInstruction(final BitString theInstruction) {
		if (this.nextInstruction >= this.MAX_INSTRUCTIONS) {
			System.out.println("Max number of instructions loaded: " + this.MAX_INSTRUCTIONS);
		} else {
			this.instructionMemory[this.nextInstruction] = theInstruction;
			this.nextInstruction++;
		}
	}
	
	/**
	* Method that is called to execute all instructions that have been loaded,
	* starting from zero.
	**/
	public void execute() {
		while (this.programCounter.toDecimal()/4 < this.MAX_INSTRUCTIONS) {
			if (this.programCounter.toDecimal() % 4 != 0) {
				System.out.println("Error, program counter not on a word boundary. Unable to read instruction");
				break;
			}
			BitString currentInstruction = this.instructionMemory[this.programCounter.toDecimal()/4];
			this.programCounter = programCounter.add(new BitString(32, 4));
			
			// If there is no instruction loaded, exit execution.
			if (currentInstruction == null) {
				break;
			}
			BitString opCodeSubString = currentInstruction.subString(0, 5);
			int opCode = opCodeSubString.toDecimal();
			
			// Instruction is an R type instruction.
			if (opCode == 0) {
				this.handleRType(currentInstruction);
			} else if (opCode == 2) {// Only J type instruction currently implemented.
				System.out.println("DETECTED J INSTRUCTION");
			} else { // Must be an I type instruction, pass to correct handler.
				this.handleIType(currentInstruction, opCode);
			}
		}
	}
	
	/**
	* Method used to set the given register to the given value.
	* 
	* @param int registerNumber The register to write to.
	* @param BitString value The value to write to the register.
	**/
	public void setRegister(final int registerNumber, final BitString value) {
		if (registerNumber < 0 || registerNumber > 31) {
			System.out.println("Invalid register number");
		} else {
			this.registers[registerNumber] = value;
		}
	}
	
	///////////////////////////
	/// R-Type Instructions ///
	///////////////////////////

	/**
	* Method used to determine the function of the instruction,
	* and pass to the functions handler.
	*
	* @param BitString theInstruction The instruction to find function of.
	**/
	public void handleRType(final BitString theInstruction) {
		final BitString theFuncCodeSubString = theInstruction.subString(26, 31);
		final int theFuncCode = theFuncCodeSubString.toDecimal();
		switch(theFuncCode) {
			case 32:
				this.add(theInstruction);
				break;
			case 36:
				this.and(theInstruction);
				break;
			default:
				System.out.println("Unknown instruction");
				break;
		}
	}
	
	/**
	* Method used to take values from the 2 source registers:
	* rs: theInstruction[6:10] (inclusive)
	* rt: theInstruction[11:15] (inclusive)
	* and write the sum to the destination register:
	* rd: theInstruction[16:20] (inclusive)
	*
	* @param BitString theInstruction The add instruction in binary.
	**/
	private void add(final BitString theInstruction) {
		int sourceOne = theInstruction.subString(6, 10).toDecimal();
		int sourceTwo = theInstruction.subString(11, 15).toDecimal();
		int dest      = theInstruction.subString(16, 20).toDecimal();
		
		if (sourceOne > 31 || sourceTwo > 31 || dest > 31 
				|| sourceOne < 0 || sourceTwo < 0 || dest < 0) 
		{
			System.out.println("Register number out of bounds");
		} else {
			this.registers[dest] = registers[sourceOne].add(registers[sourceTwo]);
		}
	}
	
	/**
	* Method used to take values from the 2 source registers:
	* rs: theInstruction[6:10] (inclusive)
	* rt: theInstruction[11:15] (inclusive)
	* bitwise and them together, and store the result in:
	* rd: theInstruction[16:20] (inclusive)
	*
	* @param BitString theInstruction The instruction containing
	* source and destination registers.
	**/
	private void and(final BitString theInstruction) {
		int sourceOne = theInstruction.subString(6, 10).toDecimal();
		int sourceTwo = theInstruction.subString(11, 15).toDecimal();
		int dest      = theInstruction.subString(16, 20).toDecimal();
		
		if (sourceOne > 31 || sourceTwo > 31 || dest > 31 
				|| sourceOne < 0 || sourceTwo < 0 || dest < 0) 
		{
			System.out.println("Register number out of bounds: " + theInstruction);
		} else {
			char[] newBits = new char[32];
			char[] rsBits = this.registers[sourceOne].getBits();
			char[] rtBits = this.registers[sourceTwo].getBits();
			// Loop through both source registers, creating new bit string as 
			// we go.
			for (int i = 0; i < 32; i++) {
				if (rsBits[i] == '1' && rtBits[i] == '1') {
					newBits[i] = '1';
				} else {
					newBits[i] = '0';
				}
			}
			this.registers[dest] = BitString.fromBits(32, newBits);
		}
	}
	
	/**
	* Method used to set the PC to the value stored in the 
	* first source register:
	* rs: theInstruction[6:10] (inclusive).
	*
	* @param BitString theInstruction The instruction containing 
	* the source register.
	**/
	private void jr(final BitString theInstruction) {
		int sourceRegister = theInstruction.subString(6, 10).toDecimal();
		
		if (sourceRegister > 31 || sourceRegister < 0) {
			System.out.println("Register number out of bounds: " + theInstruction);
		} else {
			this.programCounter = BitString.fromBits(32, this.registers[sourceRegister].getBits());
		}
	}
	
	///////////////////////////
	/// I-Type Instructions ///
	///////////////////////////
	
	/**
	* Function used to determine opcode of I-Type instruction, and
	* pass it to correct method.
	*
	* @param BitString theInstruction The instruction containing:
	* theInstruction[0:5] (inclusive) : opcode
	* theInstruction[6:10] (inclusive) : rs 
	* theInstruction[11:15] (inclusive) : rt 
	* theInstruction[16:31] (inclusive) : immediate value
	* @param int opCode The opcode of the instruction. 
	**/
	private void handleIType(final BitString theInstruction, final int opCode) {
		
		// Parse out register information.
		final int destination = theInstruction.subString(6, 10).toDecimal();
		final int source = theInstruction.subString(11, 15).toDecimal();
		final BitString immediate = theInstruction.subString(16, 31);
		
		// Check if registers are in bounds.
		if (source > 31 || source < 0 || destination > 31 || destination < 0) {
			System.out.println("Invalid register number: " + theInstruction);
			return;
		}
	
		// Match on which operation the instruction is.
		switch(opCode) {
			case 8: 
				this.addi(destination, source, immediate);
				break;
			case 12:
				this.andi(destination, source, immediate);
				break;
			default:
				System.out.println("Invalid I type instruction: " + theInstruction);
		}
	}
	
	/**
	* Method used to add an immediate value to the given register (rs) and write
	* the result to register (rt).
	*
	* @param int destination The destination register to write the result to.
	* @param int source The source register to get the first operand from.
	* @param BitString immediateValue The value in binary to add to the value in source.
	**/
	private void addi(final int destination, final int source, final BitString immediateValue) {
		this.registers[destination] = this.registers[source].add(immediateValue);
	}
	
	/**
	* Method used to bitwise and a sign extended immediate value
	* with the value in rs, and write the result to rt.
	*
	* @param int destination The register to write the result to.
	* @param int source The source to pull the first operand from.
	* @param BitString immediateValue The value to add to the value in source.
	**/
	private void andi(final int destination, final int source, final BitString immediateValue) {
		
	}
	
	///////////////////////
	/// Utility Methods ///
	///////////////////////
	
	/**
	* Method used to reset the computere, initializing all registers, 
	* memory, IC and PC to zero.
	**/
	public void reset() {
		this.dataMemory = new BitString[this.MAX_DATA];
		this.instructionMemory = new BitString[this.MAX_INSTRUCTIONS];
		this.nextInstruction = 0;
		this.programCounter = new BitString(32, 0);
		for (final BitString curString : this.registers) {
			curString.set(0);
		}
	}
	
	/**
	* Method used to print all registers to the console.
	**/
	public void print() {
		System.out.println("---------------------------------------------------");
		System.out.println("REGISTERS");
		System.out.println("---------------------------------------------------");
		for (int i = 0; i < 32; i++) {
			// One extra space added so that all the columns line up.
			if (i < 10) {
				System.out.println("Register " + i + ":  " + this.registers[i]);
			// Dont have the extra space here.
			} else {
				System.out.println("Register " + i + ": " + this.registers[i]);
			}
		}
		System.out.println("---------------------------------------------------");
		System.out.println("INSTRUCTIONS");
		System.out.println("---------------------------------------------------");
		
		int count = 0;
		for (BitString currentString = this.instructionMemory[count]; currentString != null; count++){
			currentString = this.instructionMemory[count];
			if (currentString != null) {
				System.out.println("" + count + " | " + currentString);
			} else {
				System.out.println("               ---------END---------");
			}
		}
	}
	
}