import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;

import java.util.Arrays;
 
public class BitStringTests {
	
	public BitString bitString;
	
	@Before
	public void setup() {
		this.bitString = new BitString(32);
	}
	
	@Test
	public void testConstructorWithLength() {
		char[] expectedBitString = { '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '0'};
		assertTrue("Fail: empty bit string did not match expected value.", 
						Arrays.equals(this.bitString.getBits(), expectedBitString));
		assertEquals(expectedBitString.length, this.bitString.getLength());
	}
	
	@Test 
	public void testConstructorWithArgs() {
		BitString testString = new BitString(29, 7);
		char[] expectedBitString = { '0', '0', '0', '0', '0',
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
		final BitString theOther = new BitString(32, 139);
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
	
	@Test
	public void testGetLength() {
		this.bitString = new BitString(1324354657);
		assertEquals(1324354657, this.bitString.getLength());
	}
	
	@Test
	public void testGetBits() {
		char[] expectedBits = {'1', '1', '1'};
		this.bitString = new BitString(3, 7);
		assertTrue(Arrays.equals(this.bitString.getBits(), expectedBits));
	}
	
	@Test 
	public void testFromBits() {
		char[] expectedBitString = { '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '1',
									 '1', '0', '0', '0', '1', '1', '0', '0'};
		this.bitString = BitString.fromBits(expectedBitString.length, expectedBitString);
		assertTrue(Arrays.equals(this.bitString.getBits(), expectedBitString));
	}
	
	@Test
	public void testSubString() {
		char[] expectedBitString = { '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '1',
									 '1', '0', '0', '0', '1', '1', '0', '0'};
		char[] expectedSubString = {'0', '0', '1', '1'};
		this.bitString = BitString.fromBits(expectedBitString.length, expectedBitString);
		BitString subBitString = this.bitString.subString(21, 24);
		assertTrue(Arrays.equals(subBitString.getBits(), expectedSubString));
	}
	
}