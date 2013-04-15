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


public class subdividedGrid {
	
	private int size;
	private Box subdividedGrid[][];
	
	public subdividedGrid(int[][] Grid, int size) {
		this.size = size;		
		this.subdividedGrid = new Box[size][size];
		//System.out.println(this.size);
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
	
	/*
	 * These next three methods check an entire row, column, or box for a specified value.
	 * They are important in determining the validity of placing a number in a given cell.
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
	
	//Uses nested ifs for efficiency; if one of the earlier conditions fail the rest don't need to be checked.
	
	public boolean isValidForValue(int i, int j, int value) {
		if (getValue(i, j) == 0) {
			if (!checkRowForValue((i % size), (i / size), value)) {
				if (!checkColumnForValue((j % size), (j / size), value)) {
					if (!checkBoxForValue((i / 3), (j / 3), value)) {
						return true;
					}
				}
			}
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
	
	public int getiOfFewestOptions() {
		int smallest = (size * size);
		int returni = 0;
		for (int i = 0; i < (size * size); i++) {
			for (int j = 0; j < (size * size); j++) {
				if (getValue(i, j) == 0) {
					if (countValidNumbers(i, j) < smallest) {
						smallest = countValidNumbers(i,j);
						returni = i;
					}
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
				if (getValue(i, j) == 0) {
					if (countValidNumbers(i, j) < smallest) {
						smallest = countValidNumbers(i,j);
						returnj = j;
					}
				}	
			}
		}
		return returnj;
	}
}
	

