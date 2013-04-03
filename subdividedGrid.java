/*
 * This class is a subdividedGrid of the main sudoku puzzle.  It contains
 * N Boxes, where N is SIZE*SIZE.  
 * 
 * Variable naming conventions in this class:
 * 
 * i = row variable
 * j = column variable
 * k = primary counter variable
 * l = secondary counter variable
 * count = sum or running total variable
 */

/**********TO DO***********/
 /*
  * Clean up the code.  With the new solving algorithm
  * much of the checks and resets in this class are
  * totally unnecessary and not even ever called.  At all.
  * 
  * Also implementing the N variable would help make things
  * look neater and more organised.  Get on that.
 */


public class subdividedGrid {
	
	private int size;
	private int N = (size * size);
	private Box subdividedGrid[][];
	
	public subdividedGrid(int[][] Grid, int size) {
		this.size = size;										
		for (int k = 0; k < size; k++) {			
			for (int l = 0; l < size; l++) {
				subdividedGrid[k][l] = new Box(k, l, size, Grid);	//creates a new box at that index in the 
				}													//subdivided grid containing appropriate numbers
			}
		}
	
	public void setValue(int i, int j, int value) {
		subdividedGrid[i / size][j / size].setValue((i % size), (j % size), value);
	}
	
	public int getValue(int i, int j) {
		return subdividedGrid[i / size][j / size].getValue((i % size), (j % size));
	}
	
	public void remove(int value) {
		for (int i = 0; i < (size * size); i++) {
			for (int j = 0; j < (size * size); j++) {
				if (subdividedGrid[i / size][j / size].getValue((i % size), (j % size)) == value) {
					subdividedGrid[i / size][j / size].setValue((i % size), (j % size), 0);
				}
			}
		}
	}
	
	public void boxReset(int i, int j) {
		for (int k = 0; k < size; k++) {
			for (int l = 0; l < size; l++) {
				if (subdividedGrid[i][j].getValue(k, l) < 100) {
					subdividedGrid[i][j].setValue(k, l, 0);
				}
			}
		}
	}
	
	public void rowReset(int i) {
		for (int j = 0; j < (size * size); j++) {
			if (subdividedGrid[(i / size)][(j / size)].getValue((i % size), (j % size)) < 100) {
				subdividedGrid[(i / size)][(j / size)].setValue((i % size), (j % size), 0);
			}
		}
	}
	
	public void columnReset(int j) {
		for (int i = 0; i < (size * size); i++) {
			if (subdividedGrid[(i / size)][(j / size)].getValue((i % size), (j % size)) < 100) {
				subdividedGrid[(i / size)][(j / size)].setValue((i % size), (j % size), 0);
			}
		}
	}
	
	//Sets all of a given number in a grid to zero.
	
	public void numberReset(int number) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (checkBoxForValue(i, j, number)) {		//This needs fixing to account for numbers multiplied by 100.
					subdividedGrid[i][j].setValue(subdividedGrid[i][j].getiOfValue(number), subdividedGrid[i][j].getjOfValue(number), 0);
				}
			}
		}
	}
	
	/*
	 * These next three methods check an entire row or column for a specified value.
	 */
	
	public boolean checkRowForValue(int i, int k, int value) {
		for (int j = 0; j < size; j++) {
			if (subdividedGrid[k][j].checkThisRowForValue(i, value)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkColumnForValue(int j, int k, int value) {
		for (int i = 0; i < size; i++) {
			if (subdividedGrid[i][k].checkThisColumnForValue(j, value)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkBoxForValue(int i, int j, int value) {
		if (subdividedGrid[i][j].checkThisBoxForValue(value)) {
			return true;
		}
		return false;
	}
	
	//Returns an int of the total number of zeros in a row.
	
	public int countZerosInRow(int i, int k) {
		int count = 0;
		for (int j = 0; j < size; j++) {
			count = count + (subdividedGrid[k][j].countZerosInThisRow(i));
		}
		return count;
	}
	
	//Returns an int of the total number of zeros in a column.
	
	public int countZerosInColumn(int j, int k) {
		int count = 0;
		for (int i = 0; i < size; i++) {
			count = count + (subdividedGrid[i][k].countZerosInThisColumn(j));
		}
		return count;
	}
	
	//Returns an int of the total number of zeros in a box.
	
	public int countZerosInBox(int i, int j) {
		return subdividedGrid[i][j].countZerosInThisBox();
	}
	
	//Returns an int of the total number of a given value.
	
	public int countNumberOfGivenValue(int value) {
		int count = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (subdividedGrid[i][j].checkThisBoxForValue(value)) {
					count++;
				}
			}
		}
		return count;
	}
	
	//Creates an array holding the weights of the numbers.  Be careful of off by one errors.
	
	public int[] createNumberWeight() {
		int[] weight = new int[(size * size)];
		for (int i = 1; i == (size * size); i++) {
			weight[(i - 1)] = countNumberOfGivenValue(i);
		}
		return weight;
	}
	
	//Creates an array holding the weights of zeros in rows.
	
	public int[] createRowWeight() {
		int[] weight = new int[(size * size)];
		for (int k = 0; k < size; k++) {
			for (int i = 0; i < size; i++) {
				weight[i + (k * size)] = countZerosInRow(i, k);
			}
		}
		return weight;
	}
	
	//Creates an array holding the weights of zeros in columns.
	
	public int[] createColumnWeight() {
		int[] weight = new int[(size * size)];
		for (int k = 0; k < size; k++) {
			for (int j = 0; j < size; j++) {
				weight[j + (k * size)] = countZerosInColumn(j, k);
			}
		}
		return weight;
	}
	
	//Creates an array holding the weights of zeros in boxes.
	
	public int[] createBoxWeight() {
		int[] weight = new int[(size * size)];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				weight[j + (i * size)] = countZerosInBox(i, j);
			}
		}
		return weight;
	}
	
	//Checks to see if puzzle is complete
	
	public boolean solved() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (checkBoxForValue(i, j, 0)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isValidForValue(int i, int j, int value) {
		if (!checkRowForValue((i % size), (i / size), value) && !checkColumnForValue((j % size), (j / size), value) && !checkBoxForValue((i / 3), (j / 3), value)) {
			return true;
		}
		return false;
	}
	
	/*
	 * Counts how many different numbers could be placed in a specified cell.
	 */
	
	public int countValidNumbers(int i, int j) {
		int count = 0;
		for (int number = 1; number <= (size * size); number++) {
			if (isValidForValue(i, j, number)) {
				count++;
			}
		}
		return count;
	}
	
	//These two methods just get the location of the cell with the fewest options.
	//Should be able to optimize these things by having some kind of universal variable or something.
	//Maybe....
	
	public int getiOfFewestOptions() {
		int smallest = (size * size);
		int returni = 0;
		for (int i = 0; i < (size * size); i++) {
			for (int j = 0; j < (size * size); j++) {
				if (countValidNumbers(i, j) < smallest) {
					smallest = countValidNumbers(i,j);
					returni = i;
				}
			}
		}
		return returni;
	}
	
	public int getjOfFewestOptions() {
		int smallest = (size * size);
		int returnj = 0;
		for (int i = 0; i < (size * size); i++) {
			for (int j = 0; j < (size * size); j++) {
				if (countValidNumbers(i, j) < smallest) {
					smallest = countValidNumbers(i,j);
					returnj = j;
				}
			}
		}
		return returnj;
	}
}
	

