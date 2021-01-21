import java.math.BigInteger;

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
		
		try {
			
		} catch(IllegalArgumentException e) {
		} catch(NumberFormatException e) {
			
		} // end try..catch
		
	} // end main
	
	static void sequence(String nStr) throws IllegalArgumentException,
					NumberFormatException {
		BigInteger n = new BigInteger(nStr);
		
		try {
			n = BigInteger(nStr);
		} catch(NumberFormatException e) {
			throw e;
		} // end try..catch
		
		if (n.signum() < 1) {
			throw new IllegalArgumentException("Error: N should be positive and > 0.");
		} // end if N > 0 check
		
		int nextVal = N;
		while (nextVal != 1) {
			if 
		} // end while
		
		
	} // end sequence
}
