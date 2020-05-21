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
	*
	* @param int length The length of the BitString.
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
	* @param int length The length of the bit string.
	* @param int value The value to encode in the BitString.
	**/
	public BitString(final int length, final int value) {
		this.length = length;
		this.theBits = new char[this.length];
		
		if (value >= 0) {
			for (int i = 0; i < this.length; i++) {
				this.theBits[i] = '0';
			}
		} else {
			for (int i = 0; i < this.length; i++) {
				this.theBits[i] = '1';
			}
		}
		this.set(value);
	}
	
	/**
	* Method used to create a new bit string by inverting each bit in the
	* original bit string.
	*
	* @param BitString original The original BitString
	* @return BitString the inverted bitString
	**/
	public static BitString invert(final BitString original) {
		final int oldLength = original.getLength();
		char[] oldBits = original.getBits();
		char[] newBits = new char[oldLength];
		for (int i = 0; i < oldLength; i++) {
			if (oldBits[i] == '0') {
				newBits[i] = '1';
			} else {
				newBits[i] = '0';
			}
		}
		return BitString.fromBits(newBits.length, newBits);
	}
	
	/**
	* Method used create a new BitString my padding another 
	* BitString. If the BitString parameter is of the same or
	* lesser length than newLength, the original BitString will
	* be returned with no additional padding.
	*
	* @param BitString string The string to pad.
	* @param int newLength The length to pad to.
	* @return BitString The padded bit string or original string.
	**/
	public static BitString pad(final BitString string, final int newLength) {
		if (string.getLength() >= newLength) {
			//System.out.println("Unable to pad string");
			return string;
		} else {
			char[] newBits = new char[newLength];
			Arrays.fill(newBits, '0');
			char[] oldBits = string.getBits();
			for(int i = oldBits.length - 1; i > -1; i--) {
				newBits[newBits.length - oldBits.length + i] = oldBits[i];
			}
			return BitString.fromBits(newLength, newBits);
		}
	}
	
	/**
	* Method used to extend the original string with either 1's or 0's,
	* (depending on leading bit) and create new BitString of new length.
	*
	* @param BitString originalString The string to sign extend.
	* @param int newLength The length to extend to.
	* @return Resulting sign extended bit string, or original string if error occurs.
	**/
	public static BitString signExtend(final BitString originalString, final int newLength) {
		
		final int oldLength = originalString.getLength();
		
		BitString newString;
		if (oldLength >= newLength) {
			//System.out.println("Unable to extend string of length " + oldLength + 
			//						" to length " + newLength);
			newString = originalString;
		} else {
			char[] newBits = new char[newLength];
			char[] oldBits = originalString.getBits();
			
			// If the old string is negative, extend with 1's. 
			if (oldBits[0] == '1') {
				Arrays.fill(newBits, '1');
			} else {
				Arrays.fill(newBits, '0');
			}
			
			// Copy over old bits.
			for (int i = oldLength - 1; i > -1; i--) {
				newBits[newBits.length - (oldLength - i)] = oldBits[i];
			}
			
			newString = BitString.fromBits(newLength, newBits);
		}
		return newString;
	}
	
	/**
	* Method used to get the length of this bit string.
	*
	* @return The length of the BitString.
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
		} else {
			for (int i = 0; i < this.length; i++) {
				char currentBit = bits[i];
				if (currentBit == '0' || currentBit == '1') {
					this.theBits[i] = currentBit;
				} else {
					System.out.println("Unable to set bits: chars must be only '0' or '1'");
					System.out.println(Arrays.toString(bits));
					return;
				}
			}
		}
	}
	
	/**
	* Check to see if this instruction is a null instruction (all zeroes).
	*
	* @return True if all zero, false if not.
	**/
	public boolean isNull() {
		for (int i = 0; i < this.length; i++) {
			if (this.theBits[i] == '1') {
				return false;
			}
		}
		return true;
	}
	
	/**
	* Sets the value encoded in this BitString to the given value.
	*
	* @param int value The value to initialize the BitString to.
	**/
	public void set(final int value) {
		// Convert value to binary.
		final char[] valueInBinary = Integer.toBinaryString(value).toCharArray();
		
		// Copy value into bit array
		for (int i = this.theBits.length-1, j = valueInBinary.length-1; (j >= 0 && i >= 0); i--, j--) {
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
		
		char[] firstBits = BitString.signExtend(this, 32).getBits();
		char[] secondBits = BitString.signExtend(theOther, 32).getBits();
		char[] resultBits = new char[32];
		
		char carryBit = '0';
		
		for(int i = 31; i > -1; i--) {
			if (firstBits[i] == '1' && secondBits[i] == '0' ||
					firstBits[i] == '0' && secondBits[i] == '1') {
				if (carryBit == '1') {
					resultBits[i] = '0';
				} else {
					resultBits[i] = '1';
				}
			} else if (firstBits[i] == '0' && secondBits[i] == '0') {
				if (carryBit == '1') {
					resultBits[i] = '1';
					carryBit = '0';
				} else {
					resultBits[i] = '0';
				}
			} else if (firstBits[i] == '1' && secondBits[i] == '1') {
				if (carryBit == '1') {
					resultBits[i] = '1';
				} else {
					resultBits[i] = '0';
					carryBit = '1';
				}
			}
		}
		
		return BitString.fromBits(32, resultBits);
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
	
	/**
	*
	**/
	public BitString twosComp(final BitString originalBitString) {
		
		char[] newBits = new char[originalBitString.getLength()];
		
		boolean firstSetBit = false;
		for (int i = originalBitString.getLength() - 1; i > -1; i--) {
			// This is the first '1' we have found.
			if (this.theBits[i] == '1' && !firstSetBit) {
				firstSetBit = true;
			// We have already found the first set bit, flip everything else.
			} else if (firstSetBit) {
				if (this.theBits[i] == '1') {
					newBits[i] = '0';
				} else {
					newBits[i] = '1';
				}
			}
		}
		return BitString.fromBits(newBits.length, newBits);
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		
		int bitsPlaced = 0;
		int i = 0;
		while(i < this.length) {
			sb.append(this.theBits[i]);
			bitsPlaced++;
			i++;
			if (bitsPlaced == 8) {
				bitsPlaced = 0;
				sb.append("  ");
			}
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		return (o == this) || (!(o instanceof BitString)) ||
					Arrays.equals(((BitString)o).getBits(), this.theBits);
	}

}