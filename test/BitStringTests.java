import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;

import java.util.Arrays;
 
public class BitStringTests {
	
	public BitString bitString;
	
	/**
	* Reinitializes an empty bitstring of length 32 before
	* every test.
	**/
	@Before
	public void setup() {
		this.bitString = new BitString(32);
	}
	
	/**
	* Test if constructor with length argument correctly sets the 
	* bit array, as well as has the correct length.
	**/
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
	
	/**
	* Test to see if the BitString constructor which takes a length
	* and a value correctly set the bits, as well as the length of 
	* the BitString.
	**/
	@Test 
	public void testConstructorWithArgs() {
		BitString testString = new BitString(29, 7);
		char[] expectedBitString = { '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '1', '1', '1'};
		assertTrue("Fail: bit string did not match expected value.", 
						Arrays.equals(testString.getBits(), expectedBitString));
		assertEquals(expectedBitString.length, testString.getLength());
	}
	
	/** 
 	* Test of the method set() to see if it correctly sets the bits of 
	* the bit string to the binary representation of the given number.
	**/
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
	
	/**
	* Test of the method add() to see if it correctly calculates and creates
	* the resulting BitString.
	**/
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
	
	/**
	* Test of the toDecimal() instance method.
	**/
	@Test
	public void testToDecimal() {
		this.bitString.set(123456789);
		assertEquals("Fail: decimal values did not match", this.bitString.toDecimal(), 123456789);
	}
	
	/**
	* Test of the setBits() method, to ensure that it correctly sets up 
	* the BitString using the given bits.
	**/
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
	
	/**
	* Tests whether the new BitString constructor that takes a length 
	* correctly creates the bit string, as well as that the getLength()
	* method returns the correct value.
	**/
	@Test
	public void testGetLength() {
		this.bitString = new BitString(1324354657);
		assertEquals(1324354657, this.bitString.getLength());
	}
	
	/**
	* Test of the getBits() method to ensure that the correct bits 
	* are returned.
	**/
	@Test
	public void testGetBits() {
		char[] expectedBits = {'1', '1', '1'};
		this.bitString = new BitString(3, 7);
		assertTrue(Arrays.equals(this.bitString.getBits(), expectedBits));
	}
	
	/**
	* Test of the static factory method fromBits, which ensures that 
	* the BitString created using the method is initialized in the 
	* correct way.
	**/
	@Test 
	public void testFromBits() {
		char[] expectedBitString = { '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '0',
									 '0', '0', '0', '0', '0', '0', '0', '1',
									 '1', '0', '0', '0', '1', '1', '0', '0'};
		this.bitString = BitString.fromBits(expectedBitString.length, expectedBitString);
		assertTrue(Arrays.equals(this.bitString.getBits(), expectedBitString));
	}
	
	/**
	* Test of the subString() method, ensuring that it correctly 
	* creates a new BitString, using the values stored between the 
	* range given.
	**/
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
	
	/**
	* Test of the pad method, used to append zeros onto the left side of the 
	* given bit string, until it is the desired length.
	**/
	@Test 
	public void testPad() {
		char[] expectedBitString = { '0', '0', '0', '0', '0', '1', '1', '0' };
		char[] originalString = { '1', '1', '0'};
		this.bitString = BitString.fromBits(originalString.length, originalString);
		BitString paddedString = BitString.pad(this.bitString, 8);
		assertTrue(Arrays.equals(expectedBitString, paddedString.getBits()));
	}
	
	/**
	* Test to ensure that the pad method fails if the length that is passed in 
	* is less than or equal to the length of the given string.
	**/
	@Test
	public void testPadFail() {
		char[] string = { '0', '0', '0', '0', '0', '1', '1', '0' };
		this.bitString = BitString.fromBits(string.length, string);
		
		BitString equalPadAttempt = BitString.pad(this.bitString, this.bitString.getLength());
		assertEquals(equalPadAttempt, this.bitString);
		
		BitString smallerPadAttempt = BitString.pad(this.bitString, 1);
		assertEquals(smallerPadAttempt, this.bitString);
	}
	
	/**
	* Test to ensure that the signExtend method properly extends with
	* '0' bits when the number is positive.
	**/
	@Test 
	public void testSignExtendPositive() {
		this.bitString = new BitString(4, 2);
		char[] expectedBits = { '0', '0', '0', '0', '0', '0', '1', '0' };
		//final BitString extendString = BitString.signExtend(this.bitString, expectedBits.length);
		//char[] realBits = extendString.getBits();
		//assertTrue(Arrays.equals(expectedBits, realBits));
	}
}