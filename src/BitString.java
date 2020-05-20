import java.lang.StringBuilder;
import java.util.Arrays;
import java.util.Collections;
/**
* Class representing a string of bits (32 in this use case), used to 
* encode data for Java Mips Simulator.
*
* @author Trent Julich
* @version 5/15/20
**/
public class BitString {

	private int length;

	/** Array containing "0" or "1"'s, used to encode binary instructions. **/
	private char[] theBits;

	/**
	* Constructor for new empty bit string (initialized to all zeros).
	**/
	public BitString(final int length) {
		this.length = length;
		this.theBits = new char[this.length];
		for (int i = 0; i < this.length; i++) {
			this.theBits[i] = '0';
		}
	}
	
	/**
	* Constructor for a BitString with the given initial value.
	*
	* @param value The value to encode in the BitString.
	**/
	public BitString(final int length, final int value) {
		this.length = length;
		this.theBits = new char[this.length];
		for (int i = 0; i < this.length; i++) {
			this.theBits[i] = '0';
		}
		this.set(value);
	}
	
	/**
	* Method used to get the length of this bit string.
	*
	* 
	**/
	public int getLength() { return this.length; }
	
	/**
	* Method used to retrieve the char[] representing the bits of 
	* this BitString.
	*
	* @return The character array encoding this BitString's value.
	**/
	public char[] getBits() {
		return this.theBits;
	}
	
	/**
	* Method used to create a new BitString from an array of bits.
	*
	* @param char[] bits Character array containing bits ('0' or '1').
	* @return New BitString encoded with the bits contained in the 
	*  parameter.
	**/
	public static BitString fromBits(final int length, final char[] bits) {
		final BitString newBitString = new BitString(length);
		newBitString.setBits(bits);
		return newBitString;
	}
	
	/**
	* Method used to create a new BitString from the substring of another BitString.
	* Since Mips stores integers using Big Endian, index 0 returns the most significant
	* bit, while index length-1 will return the least significant bit.
	*
	* @param int start The index of the first bit to take.
	* @param int end The index of the last bit to take.
	* @return BitString the given substring.
	**/
	public BitString subString(final int start, final int end) {
		//System.out.println("Start: " + start);
		//System.out.println("End: " + end);
		//System.out.println("Length: " + length);
		if (start < 0 || end >= this.length || end < start) {
			System.out.println("Unable to create substring using indices given");
			return new BitString(0);
		}
		char[] newBits = new char[end-start+1];
		System.arraycopy(this.theBits, start, newBits, 0, end-start+1);
		return BitString.fromBits(newBits.length, newBits);
	}
	
	/**
	* Method used to set the individual bits of this BitString to 
	* the values stored in the given bit array. Only will set bits
	* until an invalid character is encountered.
	* 
	* @param char[] bits The array of chars (bits) to set this BitStrings bits to.
	**/
	public void setBits(final char[] bits) {
		if (bits.length != this.length) {
			System.out.println("Unable to set bits: given char array is of invalid size");
			System.out.println(bits);
			System.out.println(length);
		} else {
			for (int i = 0; i < this.length; i++) {
				char currentBit = bits[i];
				if (currentBit == '0' || currentBit == '1') {
					this.theBits[i] = currentBit;
				} else {
					System.out.println("Unable to set bits: chars must be only '0' or '1'");
					return;
				}
			}
		}
	}
	
	
	
	/**
	* Sets the value encoded in this BitString to the given value.
	*
	* @param int value The value to initialize the BitString to.
	**/
	public void set(final int value) {
		// No range checking if the value will be represented by 32 bits, 
		// because value is a java integer.
		final char[] valueInBinary = Integer.toBinaryString(value).toCharArray();
		
		// Copy value into bit array
		for (int i = this.theBits.length-1, j = valueInBinary.length-1; j >= 0; i--, j--) {
			this.theBits[i] = valueInBinary[j];
		}
	}
	
	/**
	* Method used to add this BitString with another, returning the result.
	* Calling this method leaves the value of both parameters unchanged.
	*
	* @param BitString theOther The other BitString to add.
	* @return New BitString encoded with the sum of the 2 others.
	**/
	public BitString add(final BitString theOther) {
		final int valueToEncode = this.toDecimal() + theOther.toDecimal();
		return new BitString(32, valueToEncode);
	}
	
	/**
	* Method used to retrieve the value encoded within the BitString
	* but in decimal.
	*
	* @returns The encoded value in decimal.
	**/
	public int toDecimal() {
		return Integer.parseInt(String.valueOf(this.theBits), 2);
	}
	
	// THIS IS ONLY PRETTY/ACCURATE FOR 32-BIT BITSTRINGS
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length/8; i++) {
			for (int j = i * 8; j < i*8+8; j++) {
				sb.append(this.theBits[j]);
			}
			sb.append("  ");
		}
		return sb.toString();
	}

}