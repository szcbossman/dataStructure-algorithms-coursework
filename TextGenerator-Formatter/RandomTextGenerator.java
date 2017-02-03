// Name: Zicheng Song (Troy)
// USC loginid: 3607-8285-15
// CS 455 PA4
// Spring 2016

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
//import java.util.Set;
import java.util.Random;
import java.util.Scanner;

/**
 * RandomTextGenerator class
 * 
 *the logic of generating random words. construct generator with sufficient parameters then
 *pre-allocate source words into maps and generate random words and format them.
 */
public class RandomTextGenerator {

public static final int DEBUG_SEED = 1;
public static final boolean ON = true;

/**
 * representation invariants:
 * -- 1 <= prefixLength < number of words in source File.
 * -- numbWords > 0
 */
private Map<Prefix, LinkedList<String>> sourceFileMap;
private Prefix currentPrefix;
private Random rndNum;
private boolean DebugMode;
private int prefixLength;
private int numWords;
private Scanner in;

/**
 * construct RandomTextGenerator object with sufficient parameters
 * @param DebugMode    debugMode on or off, if ON, generate rndNum with fixed seed.
 * @param prefixLength words length of prefix.
 * @param numWords     number of words to be generated.
 * @param sourceFileIn the Scanner in of sourceFile
 */
public RandomTextGenerator(boolean DebugMode, int prefixLength, int numWords, Scanner sourceFileIn){
	
	this.DebugMode = DebugMode;
	this.prefixLength = prefixLength;
	this.numWords = numWords;
	this.in = sourceFileIn;
	this.currentPrefix = null;
	if(this.DebugMode == ON) { this.rndNum = new Random(1); }
	else                     { this.rndNum = new Random(); }
}

	/**
	 * generate hash map to store prefix and successors of sourceFile before
	 * doing further generating work. The representation of map is: Key: Prefix
	 * (an ArrayList of String) Value: a linkedList of String (to maintain those
	 * same prefix successors)
	 */
	public void sourceMapGenerator() {

		this.sourceFileMap = new HashMap<Prefix, LinkedList<String>>();
		String nextWord;
		ArrayList<String> initialPrefix = new ArrayList<String>();

		for (int i = 0; i < this.prefixLength; i++) {

			initialPrefix.add(in.next());
		}
		Prefix prefix = new Prefix(initialPrefix, this.prefixLength);

		while (in.hasNext()) {

			nextWord = in.next();
			LinkedList<String> wordHashedValue = new LinkedList<String>();
			if (this.sourceFileMap.containsKey(prefix)) {

				wordHashedValue = this.sourceFileMap.get(prefix);
			}
			wordHashedValue.add(nextWord);
			this.sourceFileMap.put(prefix, wordHashedValue);
			prefix = prefix.shiftIn(nextWord);
		}

		LinkedList<String> wordHashedValueEOF = new LinkedList<String>();
		if (this.sourceFileMap.containsKey(prefix)) {

			wordHashedValueEOF = this.sourceFileMap.get(prefix);
		}
		wordHashedValueEOF.add(null); // tag the last one as null to be checked
										// during generation
		this.sourceFileMap.put(prefix, wordHashedValueEOF);

	}

	/**
	 * generate random words using prepared hashMap and updated Prefix. use
	 * newInitialPrefix to choose the new prefix at first or encounters EOF.
	 * 
	 * @return random text as ArrayList of Strings.
	 */
	public ArrayList<String> randomWordsGenerator() {
		ArrayList<String> rndWords = new ArrayList<String>();

		this.currentPrefix = newInitialPrefix();
		if (this.DebugMode == ON) {

			System.out.println("DEBUG: chose a new initial prefix: " + this.currentPrefix); // debug:
																							// toString
		}

		for (int j = 0; j < this.numWords; j++) {

			if (this.DebugMode == ON) {

				System.out.println("DEBUG: prefix: " + this.currentPrefix);
			}

			LinkedList<String> successors = new LinkedList<String>();
			successors = this.sourceFileMap.get(this.currentPrefix);
			if (this.DebugMode == ON) {

				System.out.println("DEBUG: successor(s): " + successors);
			}

			String words = successors.get(this.rndNum.nextInt(successors.size()));
			while (words == null) { // reaches EOF

				this.currentPrefix = newInitialPrefix();
				if (this.DebugMode == ON) {

					System.out.println("DEBUG: word chosen from successors reaches: <END OF FILE>");
					System.out.println("DEBUG: chose a new initial prefix: " + this.currentPrefix);
					System.out.println("DEBUG: prefix: " + this.currentPrefix);
				}
				successors = this.sourceFileMap.get(this.currentPrefix);
				if (this.DebugMode == ON) {

					System.out.println("DEBUG: successors: " + successors);
				}
				words = successors.get(this.rndNum.nextInt(successors.size()));
			}
			if (this.DebugMode == ON) {

				System.out.println("DEBUG: word generated: " + words);
			}
			rndWords.add(words);
			this.currentPrefix = this.currentPrefix.shiftIn(words);
		}
		return rndWords;
	}

	/**
	 * choose newPrefix at first of generation or reaches EOF
	 * 
	 * @return a new prefix
	 */
	private Prefix newInitialPrefix() {

		ArrayList<Prefix> prefixChoices = new ArrayList<Prefix>(this.sourceFileMap.keySet());
		return prefixChoices.get(this.rndNum.nextInt(this.sourceFileMap.size()));
	}

}
