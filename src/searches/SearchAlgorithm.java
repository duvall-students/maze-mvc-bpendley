package searches;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import application.Maze;

public abstract class SearchAlgorithm {
	
	protected Maze maze;					// The maze being solved
	protected Point goal;					// The goal Point - will let us know when search is successful
	protected Collection<Point> data;		// Data structure used to keep "fringe" points
	protected boolean searchOver = false;	// Is search done?
	protected boolean searchResult = false;	// Was it successful?
	protected Point current;				// Current point being explored

	public SearchAlgorithm(Maze mazeBlocks, Point startPoint, Point goalPoint) {
		maze = mazeBlocks;
		goal = goalPoint;
		current = startPoint;
		maze.markPath(current);
	}
	
	/*
	 * Algorithm for Breadth-First Search
	 */
	public boolean step(){
		// Don't keep computing after goal is reached or determined impossible.
		if(searchOver){
			searchOverTask();
			return searchResult;
		}
		// Find possible next steps
		Collection<Point> neighbors = getNeighbors();
		// Choose one to be a part of the path
		Point next = chooseNeighbor(neighbors);
		// mark the next step
		if(next!=null){
			maze.markPath(next);
			recordLink(next);
		}
		// if no next step is found, mark current 
		// state "visited" and take off queue.
		else{	
			ifNextIsVoid();
		}
		resetCurrent();
		checkSearchOver();
		return searchResult;	
	}
	
	/*
	 * Tells me when the search is over.
	 */
	protected boolean isGoal(Point square){
		return square!= null && square.equals(goal);
	}
	
	protected void searchOverTask() {}
	
	protected void ifNextIsVoid() {
		maze.markVisited(current);
	}
	
	/*
	 * This method defines which "neighbors" or next points can be reached in the maze from
	 * the current one.  
	 * 
	 * In this method, the neighbors are defined as the four squares immediately to the north, south,
	 * east, and west of the current point, but only if they are in the bounds of the maze.  It does 
	 * NOT check to see if the point is a wall, or visited.  
	 * 
	 * Any other definition of "neighbor" indicates the search subclass should override this method.
	 */
	protected Collection<Point> getNeighbors(){
		List<Point> maybeNeighbors = new ArrayList<>();
		maybeNeighbors.add(new Point(current.x-1,current.y));
		maybeNeighbors.add(new Point(current.x+1,current.y));
		maybeNeighbors.add(new Point(current.x,current.y+1));
		maybeNeighbors.add(new Point(current.x,current.y-1));
		List<Point> neighbors = new ArrayList<>();
		for(Point p: maybeNeighbors){
			if(maze.inBounds(p)){
				neighbors.add(p);
			}
		}
		return neighbors;
	}
	
	
	/*
	 * This method defines the neighbor that gets chosen as the newest "fringe" member
	 * 
	 * It chooses the first point it finds that is empty.
	 */
	protected Point chooseNeighbor(Collection<Point> neighbors){
		for(Point p: neighbors){
			if(maze.get(p)==Maze.EMPTY){
				return p;
			}
		}
		return null;
	}
	
	protected abstract void recordLink(Point next);
	
	protected abstract void resetCurrent();
	
	/*
	 * Search is over and unsuccessful if there are no more fringe points to consider.
	 * Search is over and successful if the current point is the same as the goal.
	 */
	protected void checkSearchOver(){
		if(data!= null && data.isEmpty()) {
			searchOver = true;
			searchResult = false;
		}
		if(isGoal(current)){
			searchOver = true;
			searchResult = true;
		}
	}
	

}
