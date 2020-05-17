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
	
	private BitString[] dataMemory;
	private BitString[] instructionMemory;
	
	private int nextInstruction;
	private int programCounter;
	
	/**
	* Constructor for a new Mips Computer.
	**/
	public MipsComputer() {
		this.dataMemory = new BitString[MAX_DATA];
		this.instructionMemory = new BitString[MAX_INSTRUCTIONS];
		this.registers = new BitString[32];
		this.nextInstruction = 0;
		this.programCounter = 0;
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
			//System.out.println("First bits: " + theInstruction.subString(0, 7));
			//System.out.println("Last bits: " + theInstruction.subString(24, 31));
		}
	}
	
	/**
	* Method that is called to execute all instructions that have been loaded,
	* starting from zero.
	**/
	public void execute() {
		for (int i = 0; i < this.MAX_INSTRUCTIONS; i++) {
			BitString currentInstruction = this.instructionMemory[i];
			
			// If there is no instruction loaded, exit execution.
			if (currentInstruction == null) {
				break;
			}
			BitString opCodeSubString = currentInstruction.subString(0, 5);
			int opCode = opCodeSubString.toDecimal();
			
			// Instruction is an R type instruction.
			if (opCode == 0) {
				this.handleRType(currentInstruction);
			} else {
				switch(opCode) {
					default:
						System.out.println("Unknown Opcode");
						break;
				}
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
			default:
				System.out.println("Unknown instruction");
				break;
		}
		
		
	}
	
	/**
	* Method used to take values from the 2 source registers:
	* rs: theInstruction[6:10] (inclusive)
	* rt: theInstruction[11:15] (inclusive)
	* and write the result to the destination register:
	* rd: theInstruction[16:20] (inclusive)
	*
	* @param theInstruction The add instruction in binary.
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
	* Method used to reset the computere, initializing all registers, 
	* memory, IC and PC to zero.
	**/
	public void reset() {
		this.dataMemory = new BitString[this.MAX_DATA];
		this.instructionMemory = new BitString[this.MAX_INSTRUCTIONS];
		this.nextInstruction = 0;
		this.programCounter = 0;
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