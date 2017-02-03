// Name: Zicheng Song (Troy)
// USC loginid: 3607-8285-15
// CS 455 PA3
// Spring 2016

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.swing.JComponent;
import java.awt.Color;

/**
   MazeComponent class
   
   A component that displays the maze and path through it if one has been found.
*/
public class MazeComponent extends JComponent
{
   private Maze maze;
   
   private static final int START_X = 10;     // where to start drawing maze in frame
   private static final int START_Y = 10;
   private static final int BOX_WIDTH = 20;   // width and height of one maze unit
   private static final int BOX_HEIGHT = 20;
   private static final int INSET = 2;        // how much smaller on each side to make entry/exit inner box
   
   
   /**
      Constructs the component.
      @param maze  The maze to display
   */
   public MazeComponent(Maze maze) 
   {   
      this.maze = maze;
   }

   
   /**
     Draws the current state of maze including the path through it if one has
     been found.
     @param g the graphics context
   */
   public void paintComponent(Graphics g)
   {
	   g.drawRect(START_X, START_Y, maze.numCols()*BOX_WIDTH, maze.numRows()*BOX_HEIGHT);
	   for(int i = 0; i < maze.numRows(); i++)
	   {
		   for(int j = 0; j < maze.numCols(); j++)
		   {
			   if(maze.hasWallAt(new MazeCoord(i,j)))
			   {
				   g.fillRect(START_X + j*BOX_WIDTH, START_Y + i*BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
			   }
		   }
	   }
	   g.setColor(Color.YELLOW);
	   g.fillRect(maze.getEntryLoc().getCol()*BOX_WIDTH + INSET + START_X, maze.getEntryLoc().getRow()*BOX_HEIGHT + INSET + START_Y, BOX_WIDTH - INSET*2, BOX_HEIGHT - INSET*2);
	   g.setColor(Color.GREEN);
	   g.fillRect(maze.getExitLoc().getCol()*BOX_WIDTH + INSET + START_X, maze.getExitLoc().getRow()*BOX_HEIGHT + INSET + START_Y, BOX_WIDTH - INSET*2, BOX_HEIGHT - INSET*2);
	   
	   if(maze.getPath().isEmpty() == false)
	   {
		   drawPath(g, maze.getPath());
	   }
   }
   
   /**
      Draws the path of searched result if there is one(if path is not empty)
      @param g the graphics context
      @param path the searched result path
    */
   private void drawPath(Graphics g, LinkedList<MazeCoord> path){
	   
	   ListIterator<MazeCoord> iter = path.listIterator();
	   MazeCoord temp = iter.next();
	   int x1;
	   int y1;
	   int x2;
	   int y2;
	   
	   while(iter.hasNext())
	   {
		   x1 = (temp.getCol()*BOX_WIDTH + BOX_WIDTH/2 + START_X);
		   y1 = (temp.getRow()*BOX_HEIGHT +BOX_HEIGHT/2 + START_Y);
		   temp = iter.next();
		   x2 = (temp.getCol()*BOX_WIDTH + BOX_WIDTH/2 + START_X);
		   y2 = (temp.getRow()*BOX_HEIGHT +BOX_HEIGHT/2 + START_Y);
		   g.setColor(Color.BLUE);
		   g.drawLine(x1, y1, x2, y2);
		   
	   }
   }
   
}


