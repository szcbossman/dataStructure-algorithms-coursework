// Name: Zicheng Song(Troy)
// USC loginid: 3607-8285-15
// CS 455 PA1
// Spring 2016

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
RandomWalkComponent class extends as JComponent class
    To initialize the parameter of graphics component and to guide the 2D line drawing using drunkard object.
*/
public class RandomWalkComponent extends JComponent{
    
	private static final int START_PIXEL_LOCATION = 200;
	private static final int STEP_SIZE = 5;
	
	private int stepTimes;
	private ImPoint startLocation;
	private int stepSize;
	
	/**
	RandomWalkComponent constructor
	    Creates a RandomWalkComponent using the parameter scanned in RandomWalkViewer by user.
	    @param stepTimesIn the number of step the drunkard is about to take inserted by user in Viewer.
	*/	
	public RandomWalkComponent(int stepTimesIn){
		this.stepTimes = stepTimesIn;
		this.startLocation = new ImPoint(START_PIXEL_LOCATION, START_PIXEL_LOCATION);
		this.stepSize = STEP_SIZE;
	}

	/**
	    draw the drunkard's step line using drunkard object to locate the front and the rear end of the line.
	*/
	public void paintComponent(Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;
		
		Drunkard drunkard = new Drunkard(this.startLocation, this.stepSize); 
		for (int i = 0; i < stepTimes; i++)
		{
			ImPoint pointFrom = drunkard.getCurrentLoc();
			drunkard.takeStep();
			ImPoint pointTo   = drunkard.getCurrentLoc();
			int pointFromX = pointFrom.getX();
			int pointFromY = pointFrom.getY();
			int pointToX   = pointTo.getX();
			int pointToY   = pointTo.getY();
			g2.drawLine(pointFromX, pointFromY, pointToX, pointToY);
		}
	}

}
