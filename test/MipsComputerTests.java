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


}
