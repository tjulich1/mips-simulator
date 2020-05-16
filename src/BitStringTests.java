import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;

import java.util.Arrays;
 
public class BitStringTests {
	
	public BitString bitString;
	
	@Before
	public void setup() {
		this.bitString = new BitString();
	}
	
	@Test
	public void testDefaultConstructor() {
		char[] expectedBitString = { '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '0'};
		assertTrue("Fail: empty bit string did not match expected value.", 
						Arrays.equals(this.bitString.getBits(), expectedBitString));
	}
	
	@Test 
	public void testConstructorWithArgs() {
		BitString testString = new BitString(7);
		char[] expectedBitString = { '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '1', '1', '1'};
		assertTrue("Fail: bit string did not match expected value.", 
						Arrays.equals(testString.getBits(), expectedBitString));
	}
	
	@Test
	public void testSet() {
		this.bitString.set(1956);
		char[] expectedBitString = { '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '1', '1', '1',
									 '1', '0', '1', '0', '0', '1', '0', '0'};
		assertTrue("Fail: bit string did not match expected value",
						Arrays.equals(this.bitString.getBits(), expectedBitString));
	}
	
	@Test
	public void testAdd() {
		this.bitString.set(257);
		final BitString theOther = new BitString(139);
		final BitString resultBitString = this.bitString.add(theOther);
		char[] expectedBitString = { '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '1',
									 '1', '0', '0', '0', '1', '1', '0', '0'};
		assertTrue("Fail: Resulting bit string did not match expected value.",
						Arrays.equals(resultBitString.getBits(), expectedBitString));
	}
	
	@Test
	public void testToDecimal() {
		this.bitString.set(123456789);
		assertEquals("Fail: decimal values did not match", this.bitString.toDecimal(), 123456789);
	}
	
	@Test
	public void testSetBits() {
		char[] expectedBitString = { '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '1',
									 '1', '0', '0', '0', '1', '1', '0', '0'};
		this.bitString.setBits(expectedBitString);
		assertTrue("Fail: bits were not properly set", 
					Arrays.equals(this.bitString.getBits(), expectedBitString));
	}
}