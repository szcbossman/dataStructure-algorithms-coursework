// Name: Zicheng Song (Troy)
// USC loginid: 3607-8285-15
// CS 455 PA4
// Spring 2016

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * RandomTextFormatter class
 * 
 * the class makes generated random words into max-80-char-per-line formatted random words.
 */
public class RandomTextFormatter {

	public static final int MAX_CHAR_PERLINE = 80;
	
	private ArrayList<String> rndWords;
	/**
	 * construct RandomTextFormatter object with rndWords
	 * @param rndWords the unFormatted random words.
	 */
	public RandomTextFormatter(ArrayList<String> rndWords){
		
		this.rndWords = rndWords;
	}
	
	/**
	 * returns formatted random words for writing into outFile
	 * @return formatted random words with maximum 80 characters per line.
	 */
	public String formatRndWords(){
		
		int charCounter = 0;
		String formatedWords = "";
		ListIterator<String> iter = this.rndWords.listIterator();
		
		while(iter.hasNext()){
			String words = iter.next();
			
			charCounter = charCounter + words.length(); //not length+1 because the Line last one should not be a space
			if(charCounter < MAX_CHAR_PERLINE){
				
				formatedWords = formatedWords + words + " ";
				charCounter++;
			}
			if(charCounter == MAX_CHAR_PERLINE){
				
				formatedWords = formatedWords + words + "\n";
				charCounter = 0;
			}
			if(charCounter > MAX_CHAR_PERLINE){
				
				formatedWords = formatedWords + "\n";
				charCounter = 0;
				formatedWords = formatedWords + words + " ";
				charCounter = words.length() + 1;
			}	
		}
	    return formatedWords;	
	}
}
