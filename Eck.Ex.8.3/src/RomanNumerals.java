/** Eck Exercise 8.3
 * Create classs to represent roman numerals
 * Hold integer (arabic) and string (RN) representations
 * Check entered strings are valid: throw exceptions if not
 * @author jd07
 *
 */
public class RomanNumerals {

	public static void main(String[] args) {

		String romanTest = "MCMDLM";
		int arabicTest = 3999;

		System.out.println("Enter a number, either Roman or Arabic:");

		Number num1 = new Number(arabicTest);
		Number num2 = new Number(romanTest);

		System.out.println("Here are the results");
		System.out.println(num1);
		System.out.println(num2);

	}//end main

	static class Number {
		private int arabic;
		private String roman;
		private String[] romS = { "M","CM", "D","CD","C","XC","L","XL","X","IX","V","VI","I"};
		private int[] romV =    {1000, 900, 500, 400,100, 90,  50,  40, 10,  9,   5,   4,  1};
		
		// Checks used for roman order
		private int newVal = Integer.MAX_VALUE;
		private int preVal = Integer.MAX_VALUE;


		// Constructor for roman string input
		public Number(String num) {
			this.arabic = convertRoman2Arabic(num);
			this.roman = num;
		}

		// Contructor for arabic int input
		public Number(int num) {
			if (num < 1 || num > 3999) 
				throw new NumberFormatException("Only accept values between 1 & 3999");
			this.roman = convertArabic2Roman(num);
			this.arabic = num;
		}

		// Returns int value of single roman string
		private int getRomVal(String c) {
			int val = -1;
			for (int i = 0; i < romS.length; i++) {
				if ( ( c.compareTo(romS[i])) == 0 ) {
					val = romV[i];
					break;
				}
			}
			
			return val;
		} //end getRomSidx

		// Convert Roman numbveral to  Int value
		private int convertRoman2Arabic(String rom) {
			int numVal = 0;			
			int nc = 0;
			char c1, c2;
			String s1, s2;

			while (nc < rom.length()) {
				c1 = rom.charAt(nc);
				s1 = Character.toString(c1);
				preVal = newVal;
				// final character left (no peeking allowed)
				if ( nc == rom.length()-1) {					
					newVal =  getRomVal( s1 );
					
				} else {

					// ...otherwise continue....
					c2 = rom.charAt(nc+1);
					s2 = Character.toString(c2);

					if ( getRomVal(s1) < getRomVal(s2) ) {
						// If value of c1 < value of following value then treat as pair
						newVal = getRomVal( s1+s2 );
						nc += 2; // increment character index of input by 2
					} else {
						// else simple single character value
						newVal =  getRomVal( s1 ); 
						nc++; // increment character index of input by 1
					} // end if double vale
					
				} // end if end of string
				
				if (newVal < 1 ||  (preVal < newVal) )  {
					throw new NumberFormatException("Roman Numerals were not provided in correct order: could not calculate values");
				}
				numVal += newVal; // update numVal

			}

			return numVal;
		} // end convertRoman2Arabic

		/** Look through romV
		 * 	returnString: StringBuilder
		 * 	while num > 0
		 * 		Find highest index while num > romV[i]
		 * 			num =  num - romV[i];
		 * 	 		returnString.add(romC[i]);
		 * 
		 * 
		 * @param num integer
		 * @return string representation (roman numberals) of integer num
		 */
		private String convertArabic2Roman(int num) {
			StringBuilder romStr = new StringBuilder();
			while (num > 0 ) {
				// find index where num is always > romV[i]
				for (int i = 0; i < romV.length; i++) {
					//					System.out.printf("i:%d\tn=%d\n",i,num);
					if (num >= romV[i]) {
						num -= romV[i];
						romStr.append(romS[i]);
						break;
					}
				}				
			}
			return romStr.toString();
		} // end convertArabic2Roman

		public String toString() {
			return "Roman: " + this.roman + "; Arabic: " + this.arabic;
		}


	} // end class number

}
