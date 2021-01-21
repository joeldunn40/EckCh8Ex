/** Eck Exercise 8.1
 * Allow user to specify values of A, B & C of a quadratic
 * Ax^2 + Bx + C. Program returns the root, but throw exceptions
 * if A == 0 or B*B - 4*A*C < 0.
 * @author jd07
 *
 */

import textio.TextIO;

public class CatchRootErrors {

	/** Invite user to input values of A, B & C
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		double a, b, c; // values of coefficients 
		double rootVal; // solution
		boolean goAgain;
		// Ask user to input values
		do {
			System.out.println("Enter values of A, B & C separated by whitespaces:");
			a = TextIO.getDouble();
			b = TextIO.getDouble();
			c = TextIO.getDouble();
			System.out.printf("Entered Values: A = %.2f B = %.2f C = %.2f\n",a,b,c);

			try {
				rootVal = root(a,b,c);
				System.out.println("Root is " + rootVal);
			} catch(IllegalArgumentException e ) {
				System.out.println("Sorry there was a problem:");
				System.out.println(e.getMessage());;
			} // end try..catch
			System.out.println("Again?");
			goAgain = TextIO.getBoolean();
		} while (goAgain);
		System.out.println("Thanks for playing");
	}

	/** From Eck
	 * Returns the larger of the two roots of the quadratic equation
	 * A*x*x + B*x + C = 0, provided it has any roots.  If A == 0 or
	 * if the discriminant, B*B - 4*A*C, is negative, then an exception
	 * of type IllegalArgumentException is thrown.
	 */
	static public double root( double A, double B, double C ) 
			throws IllegalArgumentException {
		if (A == 0) {
			throw new IllegalArgumentException("A can't be zero.");
		}
		else {
			double disc = B*B - 4*A*C;
			if (disc < 0)
				throw new IllegalArgumentException("Discriminant < zero.");
			return  (-B + Math.sqrt(disc)) / (2*A);
		}
	}
}
