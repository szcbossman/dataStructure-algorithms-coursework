// Name: Zicheng Song (Troy)
// USC loginid: 3607-8285-15
// CS 455 PA4
// Spring 2016

import java.util.ListIterator;
import java.util.ArrayList;

/**
Prefix class

class that store current prefix with prefixLength and generate next 
prefix using methods shifIn, prefix representation is ArrayList.
*/
public class Prefix {

private ArrayList<String> prefix;
private int prefixLength;

/**
*construct Prefix
*
*store current Prefix and prefixLength.
*/
public Prefix(ArrayList<String> prefix, int prefixLength){
	
	this.prefix = prefix;
	this.prefixLength = prefixLength;
}

	/**
	 * returns a new prefix which shifts the current prefix by one String unit
	 * and add the nextWord to the last.
	 * 
	 * @param nextWord
	 *            the word chosen from successors using randomWordsGenerator().
	 * @return a new prefix(an Immutable prefix)
	 */
	public Prefix shiftIn(String nextWord) {

		ArrayList<String> newPrefix = new ArrayList<String>();
		ListIterator<String> iter = prefix.listIterator(1); // Trim first one
		while (iter.hasNext()) {

			newPrefix.add(iter.next());
		}
		newPrefix.add(nextWord);

		return new Prefix(newPrefix, this.prefixLength);
	}

	/**
	 * convert prefix(ArrayList of Strings) into String format so that the
	 * hashCode function can be inherited from String's. also for Debug display
	 * purpose.
	 * 
	 * @return String formatted Prefix
	 */
	public String toString() {

		String str = "";
		str = str + this.prefix.get(0);
		ListIterator<String> iter = prefix.listIterator(1);
		while (iter.hasNext()) {
           str = str + " " + iter.next();
		}
		return str;
	}

	/**
	 * override equals method for map.containsKey method. compare whether two
	 * String formatted prefix (this prefix and other prefix) are the same or
	 * not.
	 * 
	 * @return compare results
	 */
	@Override
	public boolean equals(Object obj) {

		Prefix otherPrefix = (Prefix) obj;
		return this.prefix.toString().equals(otherPrefix.prefix.toString());
	}

	/**
	 * override hashCode method to be compatible with equals method, also for
	 * map.containsKey method. return String formatted Prefix's hashCode.
	 */
	@Override
	public int hashCode() {

		return this.toString().hashCode();
	}

}