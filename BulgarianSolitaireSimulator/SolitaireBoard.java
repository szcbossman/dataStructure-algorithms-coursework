// Name: Zicheng Song (Troy)
// USC loginid: 3607-8285-15
// CSCI455 PA2
// Spring 2016

import java.util.Random;
import java.util.Scanner;
/*
   class SolitaireBoard
   The board for Bulgarian Solitaire.  You can change what the total number of cards is for the game
   by changing NUM_FINAL_PILES, below.  Don't change CARD_TOTAL directly, because there are only some values
   for CARD_TOTAL that result in a game that terminates.
   (See comments below next to named constant declarations for more details on this.)
 */


public class SolitaireBoard {
   
   public static final int NUM_FINAL_PILES = 9;
   // number of piles in a final configuration
   // (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)
   
   public static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;
   // bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
   // see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
   // the above formula is the closed form for 1 + 2 + 3 + . . . + NUM_FINAL_PILES
   
   public static final int CARD_TOTAL_MULTIPLE = factorial(NUM_FINAL_PILES);
   // the factorial of card number in final pile
   // (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL_MULTIPLE will be 362880)
   
   public static final int INDEX_WRONG_INPUT = -1;
   
   /**
      Representation invariant:

      <put rep. invar. comment here>
     -- numOfPiles is the number of piles generated
     -- pileCard is the specific number of each pile
     -- 0 < numOfPiles < CARD_TOTAL
     -- 0 < pileCard[i] < CARD_TOTAL
     -- pileCard[0] + pileCard[1] +... +pileCard[i] = CARD_TOTAL
     -- 0 < i < numOfPiles
    */
   
   // <add instance variables here>
  private int   numOfPiles;
  private int[] pileCard;
   
   /**
     Creates a solitaire board with the given configuration.
     PRE: SolitaireBoard.isValidConfigString(numberString)
     @param pileCard The card number in each piles (Upper bound is CARD_TOTAL, since max pile is 45 if final pile is 9)
     @param numOfPiles The number of piles (since it's user prompt, String has to be converted into integers) 
   */
   public SolitaireBoard(String numberString) {
      
      pileCard = new int[CARD_TOTAL];
	  numOfPiles = converter(pileCard, numberString);
      
      assert isValidSolitaireBoard();   // sample assert statement (you will be adding more of these calls)
   }
 
   
   /**
      Creates a solitaire board with a random initial configuration.
      @param pileCard The card number in each piles (Upper bound is CARD_TOTAL, since max pile is 45 if final pile is 9)
      @param numOfPiles The number of piles (since it's machine initiation, the random generator is to generate the number of piles) 
   */
   public SolitaireBoard() {
	   
	   int rest = CARD_TOTAL;
	   Random generator = new Random();
	   numOfPiles = 0;
	   pileCard = new int[CARD_TOTAL];
	   
       while(rest > 0)
       {
    	   pileCard[numOfPiles] = generator.nextInt(rest) + 1; //say 0~44 so 1~45
    	   rest = rest - pileCard[numOfPiles];
    	   numOfPiles++;
       }
       
       assert isValidSolitaireBoard();

   }
  
   
    /**
      Plays one round of Bulgarian solitaire.  Updates the configuration according to the rules
      of Bulgarian solitaire: Takes one card from each pile, and puts them all together in a new pile.
      The old piles that are left will be in the same relative order as before, 
      and the new pile will be at the end.
    */
   public void playRound() {
       
	   // every pile -1
	   int add = this.numOfPiles;
	   for(int i = 0; i < this.numOfPiles; i++)
	   {
		   pileCard[i]--; 
	   }
	   
	   // delete 0s
	   int[] pileCardTemp = new int[CARD_TOTAL];
	   int   numOfPilesTemp = 0;
	   for(int i = 0; i < this.numOfPiles; i++)
	   {
		   if(this.pileCard[i] != 0)
		   {
			   pileCardTemp[numOfPilesTemp] = pileCard[i];
			   numOfPilesTemp++;
		   }
		
	   }
	   this.pileCard = pileCardTemp;
	   this.numOfPiles = numOfPilesTemp;
	   
	   // add last pile
	   this.pileCard[numOfPiles] = add;
	   this.numOfPiles++;
	   
	   assert isValidSolitaireBoard();
   }
   
    /**
      Returns true iff the current board is at the end of the game.  That is, there are NUM_FINAL_PILES
      piles that are of sizes 1, 2, 3, . . . , NUM_FINAL_PILES, in any order.
      The Algorithm and parameter is of O(n).
      @return boolean whether the round has reached the end
    */
   
   public boolean isDone() {
      int multiple = 1;  
      
      if(this.numOfPiles != NUM_FINAL_PILES) { return false; }
      //Big-O(n):
      for(int i = 0; i < this.numOfPiles; i++)
      {
    	  multiple = this.pileCard[i] * multiple;
      }
      if (multiple != CARD_TOTAL_MULTIPLE) { return false; }
      
      assert isValidSolitaireBoard();
     
      return true;     
   }

   
    /**
      Returns current board configuration as a string with the format of
      a space-separated list of numbers with no leading or trailing spaces.
      The numbers represent the number of cards in each non-empty pile.
      @return currentPileCard return the current configuration of cards in pile
    */
   public String configString() {

      String currentPileCard = this.pileCard[0] + "";
      for (int i = 1; i < this.numOfPiles; i++)
      {
    	  currentPileCard = currentPileCard + " " + this.pileCard[i];
      }
	   
      assert isValidSolitaireBoard();
      
      return currentPileCard;   
   }
   
   
   /**
      Returns true iff configString is a space-separated list of numbers that
      is a valid Bulgarian solitaire board with card total SolitaireBoard.CARD_TOTAL
      @param  configString  the String from user prompt.
      @return isValidConfig return if user prompt String is legal.
   */
   public static boolean isValidConfigString(String configString) {

	 int[] pileCard = new int[CARD_TOTAL];  
	 int   numOfPiles = converter(pileCard, configString);
	 
	 boolean isValidConfig = isValidConfiguration(pileCard, numOfPiles);
      
     return isValidConfig;  
      
   }


    /**
      Returns true iff the solitaire board data is in a valid state
      (See representation invariant comment for more details.)
      @return isValidSB return if every public method is of legal configuration.
    */
   private boolean isValidSolitaireBoard() {
      
      boolean isValidSB = isValidConfiguration(this.pileCard,this.numOfPiles);
	   
      return isValidSB;  

   }
   

    // <add any additional private methods here>
   
   /**
   convert the String into array of Integers, can be used to handle 
   both user prompt String and constructor String. Virtually has two return value.
   @param  pileCard       The card number of each pile, the target array. Update the Instance Variable because array is passed as reference.
   @param  input          User input String, the source String.
   @return numOfPilesTemp The number of piles that actually returns to the Instance Variable.
   */
    private static int converter(int[] pileCard, String input)
    {
    	int numOfPilesTemp = 0;
    	int numOfPilesTempStr = 0;
    	String pileCardStr[] = new String[CARD_TOTAL];
    	Scanner in1 = new Scanner(input);
        Scanner in2 = new Scanner(input);
    	
        while(in1.hasNext())
    	{
    		pileCardStr[numOfPilesTempStr] = in1.next();
    		numOfPilesTempStr++;
    	}
    	while(in2.hasNextInt())
        {
      	  pileCard[numOfPilesTemp] = in2.nextInt();
      	  numOfPilesTemp++;
        }
    	if(numOfPilesTempStr != numOfPilesTemp)
    	{
    		numOfPilesTemp = INDEX_WRONG_INPUT; 
    	}
    	
        return numOfPilesTemp;
    }

    /**
    Check if Instance Variable/User prompt Variable is of legal configuration as Representation Invariant said above. 
    Do the actual work for .isValidString() and .isValidSolitaireBoard().
    @param  pileCard   The card number of each pile.
    @param  numOfPiles The number of piles.
    @return boolean    Whether the current configuration passed this test.
    */
    private static boolean isValidConfiguration(int[] pileCard, int numOfPiles)
    {
    	int total = 0;
    	
    	// 0 < numOfPiles < CARD_TOTAL
    	if(numOfPiles <= 0 || numOfPiles > CARD_TOTAL) { return false; }  
    	
        // 0 < pileCard[i] < CARD_TOTAL
    	for(int i = 0; i < numOfPiles; i++)
    	{
    	   if(pileCard[i] <= 0 || pileCard[i] > CARD_TOTAL) { return false; }
    	}
    	
    	// pileCard[0] + pileCard[1] +... +pileCard[i] = CARD_TOTAL
    	for(int i = 0; i < numOfPiles; i++)
    	{
    		total = total + pileCard[i];
    	}
    	if(total != CARD_TOTAL) { return false; }
    	
    	
    	return true;
    }
    
    /**
    Calculate the factorial of given number.
    @param  numbers The number to be calculated.
    @return multi   the calculation result.
    */ 
    private static int factorial(int numbers)
    {
    	int multi = 1;
    	for(int i = 1; i <= numbers; i++)
    	{
    		multi = multi * i;
    	}
    	return multi;
    }
    
}