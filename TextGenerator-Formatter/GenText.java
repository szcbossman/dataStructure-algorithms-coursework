// Name: Zicheng Song (Troy)
// USC loginid: 3607-8285-15
// CS 455 PA4
// Spring 2016

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;


/**
 * GenText class
 * 
 *GenText class processes command line arguments, handles all the error checking issues
 *and IO issues(open file and write to file)
 */
public class GenText {
	public static final boolean ON = true;
	public static final boolean OFF = false;

	public static void main(String[] args){
		
		genTextMaker(args);
	}
	
	/**
	 * the starter of the program, give various command line variable to two major error
	 * checking methods.
	 * @param args the command line arguments.
	 */
    private static void genTextMaker(String[] args){
    	
    	boolean debugMode = OFF;
    	String prefixLengthStr = null;
    	String numWordsStr = null;
    	String sourceFile = null;
    	String outFile = null;
    	
    	int prefixLength = 0;
    	int numWords = 0;
    	
    	commandLineIsComplete(debugMode, prefixLengthStr, numWordsStr, sourceFile, outFile, prefixLength, numWords, args);
    	
    }	
	/**
	 * helper method to check if all the command line arguments are there. 
	 * If debugMode is on, then there are altogether 5 arguments, if off, then 4.
	 * If one of them is missing, error C1 prompts, if prefixLength and/or numWords are/is not integer, err C2 prompts.
	 * @param debugMode       flag if debugMode is on.
	 * @param prefixLengthStr the String format of prefixLength directly from command line argument
	 * @param numWordsStr     the String format of numWords directly from command line argument
	 * @param sourceFile      sourceFile from command line argument
	 * @param outFile         outFile from command line argument
	 * @param prefixLength    the integer format converted from prefixLengthStr 
	 * @param numWords        the integer format converted from numWordsStr
	 * @param args            command line arguments
	 */
	private static void commandLineIsComplete(boolean debugMode, String prefixLengthStr, String numWordsStr, String sourceFile, 
			                                  String outFile, int prefixLength, int numWords, String[] args){
		try{
			
			if(args[0].equals("-d")){
				
				debugMode = ON;
			}
			
			if(debugMode == ON){
				
				prefixLengthStr = args[1];
				numWordsStr = args[2];
				sourceFile = args[3];
				outFile = args[4];
			}
            if(debugMode == OFF){
				
				prefixLengthStr = args[0];
				numWordsStr = args[1];
				sourceFile = args[2];
				outFile = args[3];
			}
            prefixLength = Integer.parseInt(prefixLengthStr);
            numWords = Integer.parseInt(numWordsStr);
		}
		 catch(ArrayIndexOutOfBoundsException errC1){
			System.out.println("ERR: one or more commandLine argument(s) is missing. ");
			System.exit(1);;
		 }
		 catch(NumberFormatException errC2){
			System.out.println("ERR: prefixLength and/or numWords may not be Integer. ");
			System.exit(1);
		 }
		
		constructIfCommandLineIsValid(debugMode, prefixLength, numWords, sourceFile, outFile);
	}
	
	/**
	 * helper method uses a small method to first check if command line arguments are valid, 
	 * if so, generate texts and construct file to write in. catch anything unexpected and 
	 * handle with corresponding Exceptions.
	 * rule for validity:
	 * 1.numWords >= 0        2.prefixLength >= 1       3.sourceFile can be found
	 * 4.prefixLength < number of words in sourceFile   5.texts can be written to outFile
	 * @param debugMode    flag if debugMode is on.
	 * @param prefixLength words length of prefix
	 * @param numWords     number of words is going to be generated.
	 * @param sourceFile   sourceFile name.
	 * @param outFile      outFile name.(existed or to create).
	 */
	private static void constructIfCommandLineIsValid(boolean debugMode, int prefixLength, int numWords, String sourceFile, String outFile){
		try{
			
			checkCommandLineValid(prefixLength, numWords, sourceFile, outFile);
			
			File source = new File(sourceFile);
			Scanner in = new Scanner(source);
			RandomTextGenerator generator = new RandomTextGenerator(debugMode, prefixLength, numWords, in);
			generator.sourceMapGenerator();
			ArrayList<String> rndWords = generator.randomWordsGenerator();
			RandomTextFormatter formatter = new RandomTextFormatter(rndWords);
			String formatedWords = formatter.formatRndWords();
			
			writeToOutFile(formatedWords, outFile);
		}
		catch(BadDataException errV1){
			int specificException = errV1.getException();
			if(specificException == BadDataException.numWordsLessThanZero){
				System.out.println("ERR: number of words is less than 0.");
				System.exit(numWords);
			}
			if(specificException == BadDataException.prefixLengthLessThanOne){
				System.out.println("ERR: prefix Length is less than 1.");
				System.exit(prefixLength);
			}
			if(specificException == BadDataException.prefixLengthLargerThanWords){
				System.out.println("ERR: prefix Length is larger than the number of words in source file.");
				System.exit(prefixLength);
			}
		}
		catch(FileNotFoundException errV2){
			System.out.println("ERR: input file " + sourceFile + " does not exist.");
			System.exit(2);
		}
		catch(SecurityException errV3){
			System.out.println("ERR: can not write to output file." + outFile);
		    System.exit(2);
		}
		catch(IOException errV4){
		    System.out.println("ERR: can not write to output file." + outFile);
		    System.exit(2);
		}
		
	}
	
	/**
	 * check command line is valid or not, can throw BadDataException for rule 1/2/4; 
	 * throw FileNotFoundException for rule 3 and throw SecurityException/IOException for rule 5.
	 * ***See RULES at above comments***
	 * @param prefixLength             words length of prefix
	 * @param numWords                 number of words is going to be generated.
	 * @param sourceFile               sourceFile name
	 * @param outFile                  outFile name(existed or to create)
	 * @throws BadDataException        if rule1/2/4 is violated, throw it.
	 * @throws IOException             if rule 5 is violated, throw it.
	 * @throws FileNotFoundException   if rule 3 is violated, throw it.
	 * @throws SecurityException       if rule 5 is violated, throw it.
	 */
	private static void checkCommandLineValid(int prefixLength, int numWords, String sourceFile, String outFile)
	                    throws BadDataException, IOException, FileNotFoundException, SecurityException {
		
		if(numWords < 0){
			
			throw new BadDataException(BadDataException.numWordsLessThanZero);
		}
		if(prefixLength < 1){
			
			throw new BadDataException(BadDataException.prefixLengthLessThanOne);
		}
		
		File source = new File(sourceFile);
		Scanner in = new Scanner(source);
		int wordsCounter = 0;
		while(in.hasNext()){
			
			in.next();
			wordsCounter++;
		}
		if(wordsCounter < prefixLength){
			
			throw new BadDataException(BadDataException.prefixLengthLargerThanWords);
		}
		
		File out = new File(outFile);
		out.createNewFile();
		if(out.canWrite() == false ){
			
			throw new SecurityException();
		}
	
	}
	
    /**
     * helper method to write generated and formated text into outFile if possible, can throw exceptions if can not.
     * @param formatedWords        formatted random text with maximum 80 characters per line
     * @param outFile              outFile name
     * @throws IOException         if rule 5 is violated, throw it.
     * @throws SecurityException   if rule 5 is violated, throw it.
     */
	private static void writeToOutFile(String formatedWords, String outFile) throws IOException, SecurityException{
		PrintWriter writer = new PrintWriter(outFile); 
		writer.print(formatedWords);
		writer.close();
	}

}	

