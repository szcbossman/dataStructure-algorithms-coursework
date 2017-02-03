// Name: Zicheng Song(Troy)
// USC loginid: 3607-8285-15
// CS 455 PA1
// Spring 2016

import javax.swing.JFrame;
import java.util.Scanner;

/**
RandomWalkViewer class
    prompt user to insert number of steps and call RandomWalkComponent to draw the line on the frame.
*/
public class RandomWalkViewer {

	private static final int SIZE = 400;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    JFrame frame = new JFrame();
    Scanner in = new Scanner(System.in);
    System.out.print("Please enter number of steps that the drunkard is about to take: ");
    
    int stepNumber = in.nextInt();
      while (stepNumber <= 0)
       {
          System.out.println("ERROR: Number entered must be greater than 0!"); 	
          System.out.print("Please enter number of steps that the drunkard is about to take: ");
          stepNumber = in.nextInt();
       }
    
    frame.setSize(SIZE,SIZE);
    frame.setTitle("Random Walk");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    RandomWalkComponent component = new RandomWalkComponent(stepNumber);
	frame.add(component);
    frame.setVisible(true);
    
    in.close();
	}

}
