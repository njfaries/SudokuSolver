import java.util.*;
import java.io.*;



class Sudoku
{
    /* SIZE is the size parameter of the Sudoku puzzle, and N is the square of the size.  For 
     * a standard Sudoku puzzle, SIZE is 3 and N is 9. */
    int SIZE, N;

    /* The grid contains all the numbers in the Sudoku puzzle.  Numbers which have
     * not yet been revealed are stored as 0. */
    int Grid[][];
    Box subdividedGrid[][];
    
    


    /* The solve() method should remove all the unknown characters ('x') in the Grid
     * and replace them with the numbers from 1-9 that satisfy the Sudoku puzzle. */
    
    /********************************************************/
    /************************To do***************************/
    /********************************************************/
   
    /*
     * Clean up the bleeding code.  I wrote a new solving algorithm
     * that, if it works properly (once I get stuff in place to test,
     * I will), should solve any size of sudoku.  Therefore, there is
     * a crapload of stuff that is just totally unnecessary.
     * 
     * Additionally, perhaps creating some ADT to create an initial
     * storing of possible numbers for each empty space at first, and
     * just modifying these as necessary for each number placed will
     * speed up solving time.
     */
     
    
    
    /*
     * Naming conventions:
     * i = row/primary counting variable
     * j = column/secondary counting variable
     * k = range of rows variable
     * l = range of columns variable
     */
    
    Stack<Integer> bigBacktrack = new Stack<Integer>();
    Stack<Integer> rowBacktrack = new Stack<Integer>();
    Stack<Integer> columnBacktrack = new Stack<Integer>();
    Stack<Integer> backtrackCounter = new Stack<Integer>();
    subdividedGrid sub = new subdividedGrid(Grid, SIZE);
    int total = 0;
    int iteration = 0;
    Random r = new Random();
    
    public void solve() {
        int i, j;    									//Declaring variables.
        System.out.println("Hello");
        for (i = 0; i < Grid.length; i++) {				//Multiplies every value in the grid by 100.  This makes preset numbers easy to find.
        	for (j = 0; j < Grid.length; j++) {			//The checks for valid placements take into account the given number as well as the 
        		Grid[i][j] *= 100;						//number multiplied by 100, but the backtracking methods do not.  Thus the original
        	}											//grid will always remain intact.
        }
        sub = new subdividedGrid(Grid, SIZE); 			//creates subdivided grid with updated sudoku values
        while (!sub.solved()) {							//while loop keeps checking for solved condition.
        	placeComplex(sub.getiOfFewestOptions(), sub.getjOfFewestOptions());
        }
        System.out.println("Solved");
   
    }
    
    /*
     * This is it here.  The prize.  This baby should do all the
     * work.  It calls a handful of check methods from the subdividedGrid
     * class, but the main bulk is just in recursively calling the method
     * on the next cell.  That process can probably be optimised as well
     * and there are comments discussing this in the subdividedGrid class.
     */
    
    public void placeComplex(int i, int j) {
    	for (int number = 1; number <= N; number++) {
    		if (sub.isValidForValue(i, j, number)) {
    			sub.setValue(i, j, number);
    		}
    	}
    	if (sub.getValue(i, j) == 0) {
    		return;
    	} else if (!sub.solved()) {
    		placeComplex(sub.getiOfFewestOptions(), sub.getjOfFewestOptions());
    	}
    }
    
    //Temporary, basic method just to complete the assignment
    
    //This method is now all but obselete.  it is remaining in code until more advanced algorithm
    //is confirmed to be fully operational.
    /*
    public void placeBasic(int value) {
    	int i, j, k, l;
    	i = r.nextInt(SIZE); j = r.nextInt(SIZE);
    	k = r.nextInt(SIZE); l = r.nextInt(SIZE);
    	while (sub.countNumberOfGivenValue(value) != 9) {
    		while (sub.checkBoxForValue(i, j, value)) {
    			i = r.nextInt(SIZE); j = r.nextInt(SIZE);
    		} 
    		while (sub.getValue(i, j, k, l) != 0 || !sub.isValidForValue((k + (SIZE * i)), (l + (SIZE * j)), value)) {  //fix these parameters...if necessary.  Might not need this method at all.
    			k = r.nextInt(SIZE); l = r.nextInt(SIZE);
    			this.total++;
    			if (total > 10) {
    				sub.remove(value);
    				sub.remove(value - 1);
    				this.total = 0;
    				iteration = 0;
    				placeBasic(value - 1);
    			}
    		}
    	}
    	sub.setValue((k + (SIZE * i)), (l + (SIZE * j)), value);
    	iteration++;
    	if (iteration != 9) {	//need to fix iteration counter carrying over through recursive calls.
    		placeBasic(value);
    	} else if (value != 9 && iteration == 9); {
    		iteration = 0;
    		placeBasic(value + 1);
    	}
    	
    } */
    
    /*
     * Looks to see if the given spot is a zero
     */
    
    public boolean isZero(int i, int j) {
    	if (Grid[i][j] == 0) {
    		return true;
    	}
    	return false;
    }
    
    
    
   
    
   
    
    /************************************/
    /*********INCOMPLETE METHODS*********/ 
    /************************************/
    
    
    
    
    
   
    	
   
    
    public int optionsFill(int k, int l, int select) {			//l is only used if we are filling a box
    	int count = 0;
    	switch(select) {
    	case 1: count = sub.countZerosInRow((k % SIZE), (k / SIZE));
    			return (count * count);
    	case 2: count = sub.countZerosInColumn((k % SIZE), (k / SIZE));
    			return (count * count);
    	case 3: count = sub.countZerosInBox(k, l);
    			return (count * count);
    	default: return 0;
    	}
    }

    //Returns the number of options for placing numbers
    
    public int optionsNumber(int number) {
    	int count = 0;
    	return count;
    }
    
    


    /*****************************************************************************/
    /* NOTE: YOU SHOULD NOT HAVE TO MODIFY ANY OF THE FUNCTIONS BELOW THIS LINE. */
    /*****************************************************************************/
 
    /* Default constructor.  This will initialize all positions to the default 0
     * value.  Use the read() function to load the Sudoku puzzle from a file or
     * the standard input. */
    public Sudoku( int size )
    {
        SIZE = size;
        N = size*size;

        Grid = new int[N][N];
        subdividedGrid = new Box[SIZE][SIZE]; 	//added to handle extra class
        for( int i = 0; i < N; i++ ) 
            for( int j = 0; j < N; j++ ) 
                Grid[i][j] = 0;
    }


    /* readInteger is a helper function for the reading of the input file.  It reads
     * words until it finds one that represents an integer. For convenience, it will also
     * recognize the string "x" as equivalent to "0". */
    static int readInteger( InputStream in ) throws Exception
    {
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
    static String readWord( InputStream in ) throws Exception
    {
        StringBuffer result = new StringBuffer();
        int currentChar = in.read();
        String whiteSpace = " \t\n";

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
    public void read( InputStream in ) throws Exception
    {
        for( int i = 0; i < N; i++ ) {
            for( int j = 0; j < N; j++ ) {
                Grid[i][j] = readInteger( in );
            }
        }
    }


    /* Helper function for the printing of Sudoku puzzle.  This function will print
     * out text, preceded by enough ' ' characters to make sure that the printint out
     * takes at least width characters.  */
    void printFixedWidth( String text, int width )
    {
        for( int i = 0; i < width - text.length(); i++ )
            System.out.print( " " );
        System.out.print( text );
    }


    /* The print() function outputs the Sudoku grid to the standard output, using
     * a bit of extra formatting to make the result clearly readable. */
    public void print()
    {
        // Compute the number of digits necessary to print out each number in the Sudoku puzzle
        int digits = (int) Math.floor(Math.log(N) / Math.log(10)) + 1;

        // Create a dashed line to separate the boxes 
        int lineLength = (digits + 1) * N + 2 * SIZE - 3;
        StringBuffer line = new StringBuffer();
        for( int lineInit = 0; lineInit < lineLength; lineInit++ )
            line.append('-');

        // Go through the Grid, printing out its values separated by spaces
        for( int i = 0; i < N; i++ ) {
            for( int j = 0; j < N; j++ ) {
                printFixedWidth( String.valueOf( Grid[i][j] ), digits );
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
    public static void main( String args[] ) throws Exception
    {
    	System.out.println("Enter name of sudoku txt file: ");
        InputStream in;
        if( args.length > 0 ) {
            in = new FileInputStream( args[0] );
            System.out.println("I'm in here!");
        }
        else {
        	in = System.in;
        	System.out.println("or here...");
        }
            

        // The first number in all Sudoku files must represent the size of the puzzle.  See
        // the example files for the file format.
        int puzzleSize = readInteger( in );
        if( puzzleSize > 100 || puzzleSize < 1 ) {
            System.out.println("Error: The Sudoku puzzle size must be between 1 and 100.");
            System.exit(-1);
        }

        Sudoku s = new Sudoku( puzzleSize );

        // read the rest of the Sudoku puzzle
        s.read( in );

        // Solve the puzzle.  We don't currently check to verify that the puzzle can be
        // successfully completed.  You may add that check if you want to, but it is not
        // necessary.
        s.solve();

        // Print out the (hopefully completed!) puzzle
        s.print();
    }
}

