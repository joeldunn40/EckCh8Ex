import textio.TextIO;

/** Eck Exercise 8.4
 * User enters a function as string
 * If the function is valid, the user can then enter numbers to evaluate the function
 * The user can then enter another function and so on
 * Uses the Expr class from Eck examples.
 * @author Joel
 *
 */
public class CalcFuncValues {

	/* Start function loop
	 * Ask user for function
	 * 	if function illegal, 
	 * 		ask again
	 * 	else 
	 * 		Start evaulation loop
	 * 			ask user to input number to evaluate
	 * 			if result is finite, prints number
	 * 			else prints message
	 * 		User can leave number loop by entering blank 
	 *	user can exit function loop by entering blank
	 */
	public static void main(String[] args) {
		boolean funcLoop = true; // Enter function string Loop controller
		boolean evalLoop = true; // Enter evaluate number Loop controller
		boolean funcSet = true; // Is function set correctly
		String funcStr; // String to hold function
		String evalNumStr; // Number to evaluate
		double evalNum; // evalNumStr as double
		double funcVal; // function value at x = evalNum

		Expr funcExpr = new Expr("1/0");


		// Start function loop
		while(funcLoop) {

			do {
				System.out.println("Enter a function of x (e.g. \"x^3\", \"sin(x)+3*x\"): Press enter to quit");
				funcStr = TextIO.getlnString();
				if ( funcStr.length() == 0 ) {
					funcLoop = false;
					evalLoop = false;
					System.out.println("You entered nothing: assume you don't want to play anymore");
					break;
				} // end end-funcLoop check
				try {
					funcExpr = new Expr(funcStr);
					System.out.println("setting funcExpr");
					funcLoop = true;
					funcSet = true;
					evalLoop = true;
				} catch(IllegalArgumentException e) {
					funcSet = false;
					funcLoop = true;
					System.out.println("Couldn't parse function: " + e.getMessage());
					System.out.println("Try again");
					continue; // gp back to funcLoop start
				} // funcLegal try catch
			} while (!funcSet);

			// Start evaluation loop
			while (evalLoop) {
				System.out.println("Enter a value of x to evaulate the function "+funcStr);
				evalNumStr = TextIO.getlnString();
				if ( evalNumStr.length() == 0 ) { // go back to function entry
					evalLoop = false;
					funcSet = false;
					System.out.println("You entered nothing: you want to try a different function");
				} else { // continue evaluating function

					try {
						evalNum = Double.parseDouble(evalNumStr);
					}catch(NumberFormatException e) {
						System.out.println("Didn't recognise this as a number, try again");
						continue; // go back to evalLoop start
					} // end try..catch string-to-double

					// evaluate function
					funcVal = funcExpr.value(evalNum);

					// If NaN print message, else print value
					if ( Double.isNaN(funcVal) ) {
						System.out.printf("%s not defined for x = %f\n",funcStr,evalNum);
					} else
						System.out.printf("f(%.4f) = %f\n",evalNum,funcVal);


				} // end end-evalLoop check

			} // end evalLoop
		} // end funcLoop
		System.out.println("Bye! Thanks for playing!");

	}
}
