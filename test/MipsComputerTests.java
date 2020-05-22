import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Before;
/**
* Unit tests written for MipsComputer class.
*
* @author Trent Julich
* @version 5/21/2020
**/
public class MipsComputerTests {

	/**
	* Mips computer object used in testing.
	**/
	private MipsComputer comp;

	/**
	* Method called before every test, used to reset the computers registers,
	* and all of its memory segments to zero.
	**/
	@Before
	public void setup() {
		this.comp = new MipsComputer();
		this.comp.reset();
	}

	/**
	* Test that is run to ensure that the computer is completely set to zero
	* after calling reset.
	**/
	@Test
	public void testReset() {
		// Reset computer for good measure.
		this.comp.reset();

		/*
		* Loop through each of the registers, and make sure they are empty.
		* Check can be done using the isNull function built into the BitString.
		*/
		for (int i = 0; i < 32; i++) {
			assertTrue(comp.getRegister(i).isNull());
		}
	}

	/**
	* Tests the setRegister method, making sure the value is properly stored.
	* Also tests the getRegister method to check the stored value.
	**/
	@Test
	public void testSetGetRegister() {
		// Setup value to store.
		BitString valueToSet = BitString.pad(new BitString(3, 7), 32);

		// Store the value.
		this.comp.setRegister(0, valueToSet);

		// Check if the value we get back is the one we stored.
		assertEquals(valueToSet, this.comp.getRegister(0));
	}

	/**
	* Tests whether data memory is correctly initialized to all zeros.
	* Also ensures that the getDataMemory() method works.
	**/
	@Test 
	public void testGetInitializeDataMemory() {
		for (int i = 0; i < comp.MAX_DATA; i++) {
			assertTrue(comp.getDataMemory(i).isNull());
		}
	}

	/**
	* Tests whether the setFromMars method successfully sets the flag, 
	* as well as that the getFromMars method returns the correct value.
	**/
	@Test 
	public void testSetFromMars() {
		this.comp.setFromMars(true);
		assertTrue(this.comp.getFromMars());
		this.comp.setFromMars(false);
		assertTrue(!this.comp.getFromMars());
	}
	
	/**
	* Test the add instruction.
	**/
	@Test
	public void testAdd() {
		// Set register 0 to 4.
		this.comp.setRegister(0, new BitString(32, 4));
		
		// add $0, $0, $0 -> 4 + 4 = 8 -> $0
		String addInstruction = "00000000000000000000000000100000";
		
		// Load instruction and execute it.
		comp.loadInstruction(BitString.fromBits(32, addInstruction.toCharArray()));
		comp.execute();
		
		// Value that should be in $0.
		BitString expectedValue = new BitString(32, 8);
		
		assertEquals(expectedValue, comp.getRegister(0));
	}

	/**
	* Test the and instruction.
	**/
	@Test 
	public void testAnd() {
		// Set register 0 to 0111
		this.comp.setRegister(0, new BitString(32, 7));
		
		// Set register 1 to 0101
		this.comp.setRegister(1, new BitString(32, 5));
		
		// $0 & $1 -> $2
		String andInstruction = "00000000001000000001000000100100";
		
		// Load instruction and execute it.
		comp.loadInstruction(BitString.fromBits(32, andInstruction.toCharArray()));
		comp.execute();
		
		// Value that should be help in $2
		BitString expectedValue = new BitString(32, 5);
		
		assertEquals(expectedValue, comp.getRegister(2));
	}

	/**
	* Test of the jr instruction.
	**/
	@Test
	public void testJR() {
		
		// New address to jump to.
		final BitString address = new BitString(32, 4);
		
		// Register to jump to.
		this.comp.setRegister(0, address);
		
		// The instruction 
		String instruction ="00000000000000000000000000001000";
		
		// Load and execute the instruction.
		comp.loadInstruction(BitString.fromBits(32, instruction.toCharArray()));
		comp.execute();
		
		// Extract the program counter.
		BitString pc = comp.getProgramCounter();
		
		assertEquals(pc, address);
	}

	/**
	* Test of the addi instruction.
	**/
	@Test
	public void testAddi() {
		// The instruction in binary. addi $0, $0, 100
		String instruction = "00100000000000000000000001100100";
		
		comp.loadInstruction(BitString.fromBits(32, instruction.toCharArray()));
		comp.execute();
		
		assertEquals(comp.getRegister(0), new BitString(32, 100));
	}

	/**
	* Test of the load word instruction and store word instruction.
	**/
	@Test
	public void testLWandSW() {
		comp.setRegister(0, new BitString(32, 4));
		String storeInstruction = "10101100001000000000000000000000";
		comp.loadInstruction(BitString.fromBits(32, storeInstruction.toCharArray()));
		String loadInstruction = "10001100001000110000000000000000";
		comp.loadInstruction(BitString.fromBits(32, loadInstruction.toCharArray()));
		BitString expectedString = new BitString(32, 4);
		
		comp.execute();
		
		System.out.println("expected: " + expectedString);
		System.out.println("real: " + comp.getDataMemory(0));
		
		assertEquals(comp.getDataMemory(0), expectedString);
	}
	
	/**
	* Test of the load word instruction and store word instruction part 2.
	**/
	@Test
	public void testLWandSW2() {
		comp.setRegister(0, new BitString(32, 4));
		
		String storeInstruction = "10101100001000000000000000001000";
		comp.loadInstruction(BitString.fromBits(32, storeInstruction.toCharArray()));
		
		String loadInstruction = "10001100001001000000000000001000";
		comp.loadInstruction(BitString.fromBits(32, loadInstruction.toCharArray()));
		
		BitString expectedString = new BitString(32, 4);
		
		comp.execute();
		
		assertEquals(new BitString(32, 4), comp.getDataMemory(2));
		
		assertEquals(comp.getRegister(4), comp.getDataMemory(2));
	}

	/**
	* Test of the beq instruction.
	**/
	@Test
	public void testBeq() {
		String beqInstruction = "00010000000000010000000000000100";
		comp.loadInstruction(BitString.fromBits(32, beqInstruction.toCharArray()));
		comp.execute();
		assertEquals(new BitString(32, 8), comp.getProgramCounter());
	}

	/**
	* Method used to test the jump instruction.
	**/
	@Test 
	public void testJump() {
		BitString expectedValue = new BitString(32, 4);
		String jumpInstruction = "000010 00000000000000000000000100";
		this.comp.loadInstruction(BitString.fromBits(32, jumpInstruction.toCharArray()));
		comp.execute();
		assertEquals(this.comp.getProgramCounter(), expectedValue);
	}
}
