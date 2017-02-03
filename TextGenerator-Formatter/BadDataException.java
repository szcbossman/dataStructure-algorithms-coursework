// Name: Zicheng Song (Troy)
// USC loginid: 3607-8285-15
// CS 455 PA4
// Spring 2016

/**
BadDataException class

class extends as Exception to handle the command line argument validity issues as follows:
1. numWords < 0
2. prefixLength < 1
3. prefixLength > # of words in sourceFile
*/

public class BadDataException extends Exception{

	public static final int numWordsLessThanZero = 0;
	public static final int prefixLengthLessThanOne = 1;
	public static final int prefixLengthLargerThanWords = 2;
	
	private int specificException;
	
	/**
	construct BadDataException

	log the specific number of exception and let the GenText's checkCommandLineValid() to throw if possible.
	*/
	public BadDataException(int specificException){
		
		this.specificException = specificException;
	}
	
	/**
	return certain BadDataException number
    @return exception number
	*/
	public int getException(){
		
		return this.specificException;
	}
}
