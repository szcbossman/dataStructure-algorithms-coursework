// Name: Zicheng Song(Troy)
// USC loginid: 3607-8285-15
// CS 455 PA1
// Spring 2016

import java.lang.Math;

/**
DrunkardTester class
    Test all the methods in Drunkard class using different test parameters.
*/
public class DrunkardTester {
	
	private static final int TEST_STEP_TIMES_1 = 3;
	private static final int TEST_STEP_TIMES_2 = 15;
	private static final int TEST_STEP_SIZE_1  = 1;
	private static final int TEST_STEP_SIZE_2  = 6;
	private static final int TEST_START_LOC_X  = 3;
	private static final int TEST_START_LOC_Y  = 5;
	
	/**
    Test driver for Drunkard class.
    @param args not used
    */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    drunkardTest(TEST_STEP_TIMES_1, TEST_STEP_SIZE_1);
    drunkardTest(TEST_STEP_TIMES_2, TEST_STEP_SIZE_2);
	}

	/**
	Test all Drunkard methods on drunkard with specific numbers of steps and step size.
	@param testStepTimes number of steps the drunkard is to take 
    @param testStepSize  range of steps the drunkard is to take 
	*/
	private static void drunkardTest(int testStepTimes, int testStepSize){
		ImPoint testLocation = new ImPoint(TEST_START_LOC_X, TEST_START_LOC_Y);
		Drunkard drunkard = new Drunkard(testLocation, testStepSize);
		System.out.println("Drunkard starts at ("  + testLocation.getX() + "," + testLocation.getY() + "); ");
		System.out.println("step size is " + testStepSize);
		
		testGetCurrentLoc(drunkard);
		testTakeStep(drunkard, testStepTimes, testStepSize);
	}
	
	/**
	Test Drunkard's getCurrentLoc method
	@param drunkard object of Drunkard class with parameter above
	*/
   private static void testGetCurrentLoc(Drunkard drunkard){
	   System.out.println("get starting location[expected (3,5)]: (" + drunkard.getCurrentLoc().getX() + "," + drunkard.getCurrentLoc().getY() + ")");
	}
   
   /**
	Test Drunkard's takeStep method
    @param drunkard object of Drunkard class with parameter above
    @param testStepTimes number of step the drunkard is to take
    @param testStepSize  range of step the drunkard is to take
	*/
   private static void testTakeStep(Drunkard drunkard, int testStepTimes, int testStepSize){
	   for (int i = 0; i < testStepTimes; i++)
	{
	    ImPoint pointA = drunkard.getCurrentLoc(); 
		drunkard.takeStep();
		ImPoint pointB = drunkard.getCurrentLoc();
		
		if (pointA.getX()-pointB.getX() != 0 && pointA.getY()-pointB.getY() != 0)
		{
			System.out.println("Not a valid compass direction!");
		}
		else if (Math.sqrt((pointA.getX() - pointB.getX())*(pointA.getX() - pointB.getX()) + (pointA.getY() - pointB.getY())*(pointA.getY() - pointB.getY())) != testStepSize)
		{
			System.out.println("took step to (" + pointB.getX() + "," + pointB.getY() + "), FAILED, not a valid step");
		}
		else
		{
			System.out.println("took step to (" + pointB.getX() + "," + pointB.getY() + "), SUCCEEDED");
		}
	}
	   System.out.println(); 
	}
}
