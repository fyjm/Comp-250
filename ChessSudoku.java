<strong id=line-0 class="highlight-808483">package finalproject;
 
import java.util.*;
import java.io.*;
 
 
public class ChessSudoku
{
	/* SIZE is the size parameter of the Sudoku puzzle, and N is the square of the size.  For 
	 * a standard Sudoku puzzle, SIZE is 3 and N is 9. 
	 */
	public int SIZE, N;
 
	/* The grid contains all the numbers in the Sudoku puzzle.  Numbers which have
	 * not yet been revealed are stored as 0. 
	 */
	public int grid[][];
	private static final int EMPTY = 0; // empty cell
	/* Booleans indicating whether of not one or more of the chess rules should be 
	 * applied to this Sudoku. 
	 */
	public boolean knightRule;
	public boolean kingRule;
	public boolean queenRule;
 
	/**********************private helper methods add below*******************************/
 
	//backtracking recursion helper method
	private boolean check_classic_Sudoku(int[][] grid, int row, int col,int num) {
		//ArrayList<Integer> prob = get_prob(this.N);
		// horizontal check
		for (int i = 0; i < N; i++) {
			// Check if the number exists in current row
			if (grid[row][i] == num) {
				return false;
			}
		}
 
		// vertical check
		for (int j = 0; j < N; j++) {
			// Check if the number exists in current column
			if (grid[j][col] == num) {
				return false;
			}
		}
 
		// small square grid check
		int squareRowStart = row - row % SIZE;
		int squareColStart = col - col % SIZE;
 
		for (int x = squareRowStart; x < squareRowStart + SIZE; x++) {
			for (int y = squareColStart; y < squareColStart + SIZE; y++) {
				// Check if the number is already in current size x size square
				if (grid[x][y] == num) {
					return false;
				}
			}
		}
		return true;
	}
 
	/** algorithm learned from https://www.geeksforgeeks.org/warnsdorffs-algorithm-knights-tour-problem/ **/
	private boolean knightRule_check (int row, int col,int num){
 
		/* xMove[] and yMove[] define next move of Knight.
           xMove[] is for next value of x coordinate
           yMove[] is for next value of y coordinate */
		int xMove[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
		int yMove[] = { 1, 2, 2, 1, -1, -2, -2, -1 };
 
		int next_x, next_y;
 
		for (int k = 0; k < 8; k++) {//check at most 8 locations
			next_x = row + xMove[k];
			next_y = col + yMove[k];
			if (next_x >= 0 && next_y >= 0 && next_x < this.N && next_y < this.N){
				if(this.grid[next_x][next_y] == num)
					return false;
			}
		}
		return true;
	}
 
	private boolean kingRule_check ( int row, int col,int num){
 
		int xMove[] = { -1, 0, 1, -1, 1, -1, 0, 1};
		int yMove[] = { 1, 1, 1, 0, 0, -1, -1, -1};
		int next_x, next_y;
 
		for (int k = 0; k < 8; k++) {//check at most 8 locations
			next_x = row + xMove[k];
			next_y = col + yMove[k];
			if (next_x >= 0 && next_y >= 0 && next_x < this.N && next_y < this.N){
				if(this.grid[next_x][next_y] == num)
					return false;
			}
		}
		return true;
	}
 
	private boolean queenRule_check(int[][] grid, int row, int col, int num){
		if(num == 9){
			if(!check_classic_Sudoku(grid, row, col, 9)){
				return false;
			}
			/* Check upper diagonal on left side */
			for (int x = col, y = row; x >= 0 && y >= 0; x--, y--) {
				if (grid[y][x] == 9) {
					return false;
				}
			}
			/* Check lower diagonal on left side */
			for (int x = col, y = row; x >= 0 && y < (this.N); x--, y++) {
				if (grid[y][x] == 9) {
					return false;
				}
			}
			/* Check upper diagonal on right side */
			for (int x = col, y = row; x < (this.N) && y >= 0; x++, y--) {
				if (grid[y][x] == 9) {
					return false;
				}
			}
			/* Check lower diagonal on right side */
			for (int x = col, y = row; x < (this.N) && y < (this.N); x++, y++) {
				if (grid[y][x] == 9) {
					return false;
				}
			}
			return true;
		}
		return true;
	}
 
	private ArrayList get_prob (int N){
		ArrayList<Integer> prob = new ArrayList<Integer>(this.N);
		for (int i=0; i < this.N; i++){
			prob.add(i+1);
		}
		return prob;
	}
 
	//solve sudoku with only one solution
	/**Algorithm learned from https://www.geeksforgeeks.org/sudoku-backtracking-7/ by MohanDas**/
	private boolean solve_Sudoku(ChessSudoku input) {
		ArrayList<Integer> prob = get_prob(this.N);
		int row = -1;
		int col = -1;
		boolean isEmpty = true;
		for (int i = 0; i < this.N; i++) {
			for (int j = 0; j < this.N; j++) {
				if (input.grid[i][j] == EMPTY) {
					row = i;
					col = j;
					// still have remaining missing values in Sudoku
					isEmpty = false;
					break;
				}
			}
			if (!isEmpty) {
				break;
			}
		}
 
 
		// No empty space left
		if (isEmpty) {//base case
			return true;
		}
 
		// for each try
		for (int num: prob) {
			if (this.knightRule || this.kingRule || this.queenRule) {
				if (this.knightRule && check_classic_Sudoku(this.grid, row, col, num) && knightRule_check(row, col, num)) {
					this.grid[row][col] = num;
					if (solve_Sudoku(this))
						return true;
				} else if (this.kingRule && check_classic_Sudoku(this.grid, row, col, num) && kingRule_check(row, col, num)) {
					this.grid[row][col] = num;
					if (solve_Sudoku(this))
						return true;
				} else if (this.queenRule && check_classic_Sudoku(this.grid, row, col, num) && queenRule_check(this.grid, row, col, num)) {
					this.grid[row][col] = num;
					if (solve_Sudoku(this))
						return true;
				}// replace it
 
				this.grid[row][col] = EMPTY;
			} else if (check_classic_Sudoku(this.grid, row, col, num)) {
				this.grid[row][col] = num;
 
				// classic solve
				if (solve_Sudoku(this)) {
					return true;
				}// replace it
				this.grid[row][col] = EMPTY;
			}
 
		}
		return false;
	}
 
	private boolean solve_all_Sudoku(ChessSudoku input) {
		ArrayList<Integer> prob = get_prob(this.N);
		int row = -1;
		int col = -1;
		boolean isEmpty = true;
		for (int i = 0; i < this.N; i++) {
			for (int j = 0; j < this.N; j++) {
				if (this.grid[i][j] == EMPTY) {
					row = i;
					col = j;
					// still have remaining missing values in Sudoku
					isEmpty = false;
					break;
				}
			}
			if (!isEmpty) {
				break;
			}
		}
 
 
		// No empty space left
		if (isEmpty) {//base case
			return true;
		}
 
		// for each try
		for (int num: prob) {
			if(this.knightRule || this.kingRule || this.queenRule){
				if(this.knightRule && check_classic_Sudoku(this.grid,row,col,num) && knightRule_check(row,col,num)){
					this.grid[row][col] = num;
					if(solve_all_Sudoku(this))
						solutions.add(deepCopy(this));
				}
				else if (this.kingRule && check_classic_Sudoku(this.grid,row,col,num) && kingRule_check(row,col,num)){
					this.grid[row][col] = num;
					if(solve_all_Sudoku(this))
						solutions.add(deepCopy(this));
				}
				else if (this.queenRule && check_classic_Sudoku(this.grid,row,col,num) && queenRule_check(this.grid,row,col,num)){
					this.grid[row][col] = num;
					if(solve_all_Sudoku(this))
						solutions.add(deepCopy(this));
				}// replace it
				this.grid[row][col] = EMPTY;
			}
			else if (check_classic_Sudoku(this.grid,row,col,num)) {
				this.grid[row][col] = num;
				// classic solve
				if(solve_all_Sudoku(this)) {
					solutions.add(deepCopy(this));
				}
				this.grid[row][col] = EMPTY;
			}
		}
		this.grid[row][col] = EMPTY;
		return false;
	}
 
	private ChessSudoku deepCopy (ChessSudoku input){
		ChessSudoku Temp = new ChessSudoku(this.SIZE);
		for (int i = 0; i < N; i++) {
			Temp.grid[i] = Arrays.copyOf(this.grid[i],this.N);
		}
		return Temp;
	}
	/****************private helper methods add above**************************/
 
	// Field that stores the same Sudoku puzzle solved in all possible ways
	public HashSet<ChessSudoku> solutions = new HashSet<ChessSudoku>();
 
 
	/* The solve() method should remove all the unknown characters ('x') in the grid
	 * and replace them with the numbers in the correct range that satisfy the constraints
	 * of the Sudoku puzzle. If true is provided as input, the method should find finds ALL 
	 * possible solutions and store them in the field named solutions. */
	public void solve(boolean allSolutions) {
		/*
		 * ADD YOUR CODE HERE
		 */
 
		if(!allSolutions)
			solve_Sudoku(this);//one solution
		else if(allSolutions){
			//find all solutions of the board
			solve_all_Sudoku(this);
		}
 
	}
 
	/*****************************************************************************/
	/* NOTE: YOU SHOULD NOT HAVE TO MODIFY ANY OF THE METHODS BELOW THIS LINE. */
	/*****************************************************************************/
 
	/* Default constructor.  This will initialize all positions to the default 0
	 * value.  Use the read() function to load the Sudoku puzzle from a file or
	 * the standard input. */
	public ChessSudoku( int size ) {
		SIZE = size;
		N = size*size;
 
		grid = new int[N][N];
		for( int i = 0; i < N; i++ ) 
			for( int j = 0; j < N; j++ ) 
				grid[i][j] = 0;
	}
 
 
	/* readInteger is a helper function for the reading of the input file.  It reads
	 * words until it finds one that represents an integer. For convenience, it will also
	 * recognize the string "x" as equivalent to "0". */
	static int readInteger( InputStream in ) throws Exception {
		int result = 0;
		boolean success = false;
 
		while( !success ) {
			String word = readWord( in );
 
			try {
				result = Integer.parseInt( word );
				success = true;
			} catch( Exception e ) {
				// Convert 'x' words into 0's
				if( word.compareTo("x") == 0 ) {
					result = 0;
					success = true;
				}
				// Ignore all other words that are not integers
			}
		}
 
		return result;
	}
 
 
	/* readWord is a helper function that reads a word separated by white space. */
	static String readWord( InputStream in ) throws Exception {
		StringBuffer result = new StringBuffer();
		int currentChar = in.read();
		String whiteSpace = " \t\r\n";
		// Ignore any leading white space
		while( whiteSpace.indexOf(currentChar) > -1 ) {
			currentChar = in.read();
		}
 
		// Read all characters until you reach white space
		while( whiteSpace.indexOf(currentChar) == -1 ) {
			result.append( (char) currentChar );
			currentChar = in.read();
		}
		return result.toString();
	}
 
 
	/* This function reads a Sudoku puzzle from the input stream in.  The Sudoku
	 * grid is filled in one row at at time, from left to right.  All non-valid
	 * characters are ignored by this function and may be used in the Sudoku file
	 * to increase its legibility. */
	public void read( InputStream in ) throws Exception {
		for( int i = 0; i < N; i++ ) {
			for( int j = 0; j < N; j++ ) {
				grid[i][j] = readInteger( in );
			}
		}
	}
 
 
	/* Helper function for the printing of Sudoku puzzle.  This function will print
	 * out text, preceded by enough ' ' characters to make sure that the printint out
	 * takes at least width characters.  */
	void printFixedWidth( String text, int width ) {
		for( int i = 0; i < width - text.length(); i++ )
			System.out.print( " " );
		System.out.print( text );
	}
 
 
	/* The print() function outputs the Sudoku grid to the standard output, using
	 * a bit of extra formatting to make the result clearly readable. */
	public void print() {
		// Compute the number of digits necessary to print out each number in the Sudoku puzzle
		int digits = (int) Math.floor(Math.log(N) / Math.log(10)) + 1;
 
		// Create a dashed line to separate the boxes 
		int lineLength = (digits + 1) * N + 2 * SIZE - 3;
		StringBuffer line = new StringBuffer();
		for( int lineInit = 0; lineInit < lineLength; lineInit++ )
			line.append('-');
 
		// Go through the grid, printing out its values separated by spaces
		for( int i = 0; i < N; i++ ) {
			for( int j = 0; j < N; j++ ) {
				printFixedWidth( String.valueOf( grid[i][j] ), digits );
				// Print the vertical lines between boxes 
				if( (j < N-1) && ((j+1) % SIZE == 0) )
					System.out.print( " |" );
				System.out.print( " " );
			}
			System.out.println();
 
			// Print the horizontal line between boxes
			if( (i < N-1) && ((i+1) % SIZE == 0) )
				System.out.println( line.toString() );
		}
	}
 
 
	/* The main function reads in a Sudoku puzzle from the standard input, 
	 * unless a file name is provided as a run-time argument, in which case the
	 * Sudoku puzzle is loaded from that file.  It then solves the puzzle, and
	 * outputs the completed puzzle to the standard output. */
	public static void main( String args[] ) throws Exception {
		InputStream in = new FileInputStream("easy3x3.txt");
 
		// The first number in all Sudoku files must represent the size of the puzzle.  See
		// the example files for the file format.
		int puzzleSize = readInteger( in );
		if( puzzleSize > 100 || puzzleSize < 1 ) {
			System.out.println("Error: The Sudoku puzzle size must be between 1 and 100.");
			System.exit(-1);
		}
 
		ChessSudoku s = new ChessSudoku( puzzleSize );
		
		// You can modify these to add rules to your sudoku
		s.knightRule = false;
		s.kingRule = false;
		s.queenRule = false;
		
		// read the rest of the Sudoku puzzle
		s.read( in );
 
		System.out.println("Before the solve:");
		s.print();
		System.out.println();
 
		// Solve the puzzle by finding one solution.
		s.solve(false);
 
		// Print out the (hopefully completed!) puzzle
		System.out.println("After the solve:");
		s.print();
 
		/*
		for(ChessSudoku item: s.solutions) {
			item.print();
			System.out.println(" ");
 
		}
 
		 */
	}
}
