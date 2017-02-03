// Name: Zicheng Song (Troy)
// USC loginid: 3607-8285-15
// CSCI455 PA2
// Spring 2016

import java.util.Scanner;
 /**
 //The BulgarianSolitaireSimulator uses the public methods in SolitaireBoard to play one game 
   and uses public static method to check if the user prompt is legal. 
 //The main program uses startSimulator method to start the game with both boolean variable put in 
   in order to select different modes of the program. 
 */

public class BulgarianSolitaireSimulator {

   public static void main(String[] args) {
     
      boolean singleStep = false;
      boolean userConfig = false;

      for (int i = 0; i < args.length; i++) {
         if (args[i].equals("-u")) {
            userConfig = true;
         }
         else if (args[i].equals("-s")) {
            singleStep = true;
         }
      }

      // <add code here>
      startSimulator(singleStep, userConfig);
   }
   
    // <add private static methods here>

   /**
   According to the mode of whether uses userPormpt to make different SolitaireBoard object and uses playRound to play this game.
   @param  singleStep Boolean variable to judge whether stop at one run or not, passes to playRound method. 
   @param  userConfig Boolean variable to judge whether initiate configuration from user or machine.
   */ 
   private static void startSimulator(boolean singleStep, boolean userConfig)
   {
	   SolitaireBoard game;
	   if(userConfig == true)
	   {
		   game = new SolitaireBoard( userPrompt() );
	   }
	   else
	   {
		   game = new SolitaireBoard();
	   }
	   
	   playRound(game, singleStep);
   }
   
   /**
   If selected userConfig, then do what the requirement says: Prompt from user and do the error checking 
   via SolitaireBoard.isValidConfigString(), then return the user configuration in String. 
   @return userLine return the user configuration of SoliraireBoard.
   */ 
   private static String userPrompt()
   {
	   System.out.println("You will be entering the initial configuration of cards (ie., how many in each pile)");
	   System.out.println("please enter a space-separated list of positive integers followed by newline: ");
	   Scanner in = new Scanner(System.in);
	   String userLine = in.nextLine();
	   
	   while(SolitaireBoard.isValidConfigString(userLine) == false)
	   {
		   System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be " + SolitaireBoard.CARD_TOTAL);
		   System.out.println("please enter a space-separated list of positive integers followed by newline: ");
		   userLine = in.nextLine();
	   }
	   
	   return userLine;
   }
   
   /**
   According to the mode of whether uses singleStep to continue play rounds of game until finished.
   @param  game       The object that the previous method initiated. 
   @param  singleStep Boolean variable to judge whether stop at one run or not.
   */ 
   private static void playRound(SolitaireBoard game, Boolean singleStep)
   {
	   int round = 1;
	   System.out.println("Initial configuration: " + game.configString());
	   
	   while(game.isDone() == false)
	   {
	     game.playRound();
	     System.out.println("[" + round + "]" + "Current configuration: " + game.configString());
	     round++;
	     
	     if(singleStep == true)
	     {
	    	 System.out.print("<Type Return to continue...>");
	    	 Scanner in = new Scanner(System.in);
	    	 in.nextLine();
	     }  
	   }
	   System.out.println("Done!");
   }
  
}
