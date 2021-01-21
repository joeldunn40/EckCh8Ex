import java.math.BigInteger;
import textio.TextIO;

/** Eck Exercise 8.2
 * User inputs a positive integer, N, the program prints out all steps
 * in the 3N+1 sequence.
 * 
 * Have to catch 2 errors:
 * 	entered value isn't a string representation of an integer:
 * 		thrown by BigInteger(string): NumberFormatException
 * 	integer < 1: BigInteger.signnum < 1
 * @author jd07
 *
 */
public class ThreeNplus1BigIntegers {
	public static void main(String[] args) {
		
		boolean legalValue = true;
		String numStr;
		
		do {
			try {
				System.out.println("Enter a positive integer:");
				numStr = TextIO.getln();
				sequence(numStr);
				legalValue = true;
			} catch(IllegalArgumentException e) {
				System.out.println("That's not a legal number: ");
				System.out.println(e.getMessage());
				System.out.println("... try again... ");
				legalValue = false;
			} // end trycatch
		} while (!legalValue);
		
		System.out.println("Bye!");
	} // end main
	
	static void sequence(String nStr) throws NumberFormatException, IllegalArgumentException  {
		BigInteger nextVal;
		BigInteger count = new BigInteger("0");
		BigInteger one = new BigInteger("1");
		BigInteger two = new BigInteger("2");
		BigInteger three = new BigInteger("3");
		

		try {
			nextVal = new BigInteger(nStr);			
		} catch(NumberFormatException e) {
			throw e;
		}
		
		if ( !(nextVal.signum() == 1) ) {
			throw new IllegalArgumentException("You can only enter a positive integer");
		} // end if
		
		System.out.printf("%s : %s\n",count.toString(),nextVal.toString());
		// Within loop nextVal is never equal to 1 (i.e. greater than one)
		while (!nextVal.equals(one)) {
			count = count.add(one);
			// Find parity of nextVal
			if (nextVal.testBit(0)) {
				// nextVal is odd
				nextVal = nextVal.multiply(three).add(one);
			} else {
				// nextVal is even
				nextVal = nextVal.divide(two);
			} // end if parity
			System.out.printf("%s : %s\n",count.toString(),nextVal.toString());
		} // end while
		
		
	} // end sequence
} // end of class
