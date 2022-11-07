package application;

import java.awt.Point;

import searches.BFS;
import searches.DFS;
import searches.Greedy;
import searches.Magic;
import searches.RandomWalk;
import searches.SearchAlgorithm;
import searches.SearchFactory;

public class MazeController {
	
	/* 
	 * Logic of the program
	 */
	// The search algorithms
//	private Greedy greedy;				
//	private BFS bfs;
//	private DFS dfs;
//	private RandomWalk rand;
//	private Magic magic;
	private SearchAlgorithm search;		// This string tells which algorithm is currently chosen.  Anything other than 
	// the implemented search class names will result in no search happening.

	// Where to start and stop the search
	private Point start;
	private Point goal;
	private MazeDisplay md;

	// The maze to search
	private Maze maze;

	public MazeController(int numRows, int numColumns, MazeDisplay md) {
		start = new Point(1,1);
		goal = new Point(numRows-2, numColumns-2);
		maze = new Maze(numRows, numColumns);
		this.md = md;
	}
	
	/*
	 * Re-create the maze from scratch.
	 * When this happens, we should also stop the search.
	 */
	public void newMaze() {
		maze.createMaze(maze.getNumRows(),maze.getNumCols());
		md.redraw();
	}
	
	/*
	 * Does a step in the search regardless of pause status
	 */
	public void doOneStep(double elapsedTime){
		if(search != null) search.step();
		md.redraw();
	}
	
	public void startSearch(String searchType) {
		maze.reColorMaze();
		
		search = new SearchFactory().makeSearch(searchType, maze, start, goal);
		
		// Restart the search.  Since I don't know 
		// which one, I'll restart all of them.
		
//		bfs = new BFS(maze, start, goal);	// start in upper left and end in lower right corner
//		dfs = new DFS(maze, start, goal);
//		greedy = new Greedy(maze, start, goal);
//		rand = new RandomWalk(maze, start, goal);
//		magic = new Magic(maze, start, goal);
	}


	public int getCellState(Point position) {
		return maze.get(position);
	}
	


}
