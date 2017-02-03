// Name: Zicheng Song(Troy)
// USC loginid: 3607-8285-15
// CS 455 PA1
// Spring 2016

import java.util.Random;

/**
   Drunkard class
       Represents a "drunkard" doing a random walk on a grid.
*/
public class Drunkard {

    // add private instance variables here:
    private ImPoint currentLocation;
    private int stepSize;
    private Random generator;
    

    /**
       Creates drunkard with given starting location and distance
       to move in a single step.
       @param startLoc starting location of drunkard
       @param theStepSize size of one step in the random walk
    */
    public Drunkard(ImPoint startLoc, int theStepSize) {
    this.currentLocation = startLoc;
    this.stepSize = theStepSize;
    this.generator = new Random();
    }


    /**
       Takes a step of length stepSize (see constructor) in one of
       the four compass directions.  Changes the current location of the
       drunkard.
    */
    public void takeStep() {
    
    int rand = 0;
    
    	rand  = generator.nextInt(4);
    	if     (rand % 4 == 0)
    	{
    		currentLocation = currentLocation.translate(0, -(stepSize)); //north  		
    	}
    	else if(rand % 4 == 1)
    	{
    		currentLocation = currentLocation.translate(stepSize, 0); //east
    	}
    	else if(rand % 4 == 2)
    	{
    		currentLocation = currentLocation.translate(0, stepSize); //south
    	}
    	else if(rand % 4 == 3)
    	{
    		currentLocation = currentLocation.translate(-(stepSize), 0); //west
    	}
    }


    /**
       gets the current location of the drunkard.
       @return an ImPoint object representing drunkard's current location
    */
    public ImPoint getCurrentLoc() {
	return currentLocation;  
    }

}
