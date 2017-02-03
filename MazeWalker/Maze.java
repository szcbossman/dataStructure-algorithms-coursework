// Name: Zicheng Song (Troy)
// USC loginid: 3607-8285-15
// CS 455 PA3
// Spring 2016

import java.util.LinkedList;


/**
   Maze class

   Stores information about a maze and can find a path through the maze
   (if there is one).
   
   Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
   (parameters to constructor), and the path:
     -- no outer walls given in mazeData -- search assumes there is a virtual 
        border around the maze (i.e., the maze path can't go outside of the maze
        boundaries)
     -- start location for a path is maze coordinate startLoc
     -- exit location is maze coordinate exitLoc
     -- mazeData input is a 2D array of booleans, where true means there is a wall
        at that location, and false means there isn't (see public FREE / WALL 
        constants below) 
     -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
     -- only travel in 4 compass directions (no diagonal paths)
     -- can't travel through walls
 */

public class Maze {
   
   public static final boolean FREE = false;
   public static final boolean WALL = true;
   public static final boolean SEARCHED = true;
   public static final boolean UNSEARCHED = false;
   
   /**
   Representation invariant:
  
  -- mazeData is the data that store the maze file data wall/freeSpcae(true/false).
  -- 0 < mazeData.length <= number of row in certain data file
  -- 0 < mazeData[0].length <= number of column in certain data file
  -- visited is the flag array that stores the searched area of maze(searched/unSearched).
  -- 0 < visited.length <= mazeData.length
  -- 0 < visited[0].length <= mazeData[0].length
  -- startLoc is the initial search location in maze
  -- 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
  -- 0 <= endLoc.getRow() < mazeData.length   and 0 <= endLoc.getCol() < mazeData[0].length
  -- path is the linkedList to store the search path if available
  -- 0 <= path.size()
  */
   
   private boolean[][] mazeData;
   private boolean[][] visited;
   private MazeCoord startLoc;
   private MazeCoord endLoc;
   private LinkedList<MazeCoord> path;

   /**
      Constructs a maze.
      @param mazeData the maze to search.  See general Maze comments for what
      goes in this array.
      @param startLoc the location in maze to start the search (not necessarily on an edge)
      @param endLoc the "exit" location of the maze (not necessarily on an edge)
      PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
         and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length

    */
   public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord endLoc)
   {
	   this.mazeData = mazeData;
	   this.startLoc = startLoc;
	   this.endLoc   = endLoc;
	   this.visited  = new boolean[mazeData.length][mazeData[0].length];
	   this.path     = new LinkedList<MazeCoord>();
	   this.path.clear();
   }


   /**
   Returns the number of rows in the maze
   @return number of rows
   */
   public int numRows() {
      return this.mazeData.length;   
   }

   
   /**
   Returns the number of columns in the maze
   @return number of columns
   */   
   public int numCols() {
      return this.mazeData[0].length;   
   } 
 
   
   /**
      Returns true iff there is a wall at this location
      @param loc the location in maze coordinates
      @return whether there is a wall here
      PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
   */
   public boolean hasWallAt(MazeCoord loc) {
	   if(this.mazeData[loc.getRow()][loc.getCol()] == WALL)
	   {
		     return true;
	   }
	   else{ return false; }   
   }
   

   /**
      Returns the entry location of this maze.
      @return the start location row and column
    */
   public MazeCoord getEntryLoc() {
      return this.startLoc;   
   }
   
   
   /**
      Returns the exit location of this maze.
      @return the end location row and column
   */
   public MazeCoord getExitLoc() {
      return this.endLoc;   
   }

   
   /**
      Returns the path through the maze. First element is starting location, and
      last element is exit location.  If there was not path, or if this is called
      before search, returns empty list.

      @return the maze path
    */
   public LinkedList<MazeCoord> getPath() {

      return this.path;   

   }


   /**
      Find a path through the maze if there is one.  Client can access the
      path found via getPath method.
      It also initiate the visited array to default and clear the path for future use.
      @return whether path was found.
    */
   public boolean search()  {  
	  
	  for(int i = 0; i < this.mazeData.length; i++)
	  {
		  for(int j = 0; j < this.mazeData[0].length; j++)
		  {
			  this.visited[i][j] = UNSEARCHED;
		  }
	  }
	  this.path.clear();
      return helperRoutine(this.startLoc);  

   }
   /**
      The private helper function of backTracking algorithm recursion that does the real jab
      @param loc  The location that every helper function recursion takes in.(First one is start location, Last one is end location)  
      @return whether the path is legal and can be found in the end
    */
   private boolean helperRoutine(MazeCoord loc){
	   
	   //Base
	   if(this.hasWallAt(loc)) { return false; }
	   if(this.visited[loc.getRow()][loc.getCol()] == SEARCHED) { return false; }
	   if(loc.equals(this.endLoc))
	   {
		   this.path.addFirst(loc);
		   return true;
	   }
	   //Recursion
	   this.visited[loc.getRow()][loc.getCol()] = SEARCHED;
	   
	   if(loc.getRow() > 0) //north
	   {
		   if(helperRoutine(new MazeCoord(loc.getRow() - 1, loc.getCol())))
		   {
			   this.path.addFirst(loc);
			   return true;
		   }
	   }
	   if(loc.getCol() < this.numCols() - 1) //east
	   {
		   if(helperRoutine(new MazeCoord(loc.getRow(), loc.getCol() + 1)))
		   {
			   this.path.addFirst(loc);
			   return true;
		   }
	   }
	   if(loc.getRow() < this.numRows() - 1) //south
	   {
		   if(helperRoutine(new MazeCoord(loc.getRow() + 1, loc.getCol())))
		   {
			   this.path.addFirst(loc);
			   return true;
		   }
	   }
	   if(loc.getCol() > 0) //west
	   {
		   if(helperRoutine(new MazeCoord(loc.getRow(), loc.getCol() - 1)))
		   {
			   this.path.addFirst(loc);
			   return true;
		   }
	   }
	   
	   return false;
   }

}