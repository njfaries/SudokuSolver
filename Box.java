
/*
 * This class is a box subsection of the sudoku grid.
 */

public class Box {
	
	private int size;
	private int[][] box;
	
	//Constructor creates 2D array of dimensions appropriate for sudoku and fills it with
	//the numbers in the specified section of the sudoku grid.
	
	public Box(int k, int l, int size, int[][] Grid) {		//k, l are passed from the subdividedGrid class
		this.size = size;
		this.box = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.box[i][j] = Grid[i + (k*size)][j + (l*size)];	//pulls values from specified location in the main grid.
			}
		}
	}
	
	//Checks a given row for a specified value (including modified original grid values)
	
	public boolean checkThisRowForValue(int i, int value) {
		for (int j = 0; j < size; j++) {
			if (this.box[i][j] == value || this.box[i][j] == (value * 100)) {
				return true;
			}
		}
		return false;
	}
	
	//Checks a given column for a specified value (including modified original grid values)
	
	public boolean checkThisColumnForValue(int j, int value) {
		for (int i = 0; i < size; i++) {
			if (this.box[i][j] == value || this.box[i][j] == (value * 100)) {
				return true;
			}
		}
		return false;
	}
	
	//Checks the whole box for a specified value (including modified original grid values)
	
	public boolean checkThisBoxForValue(int value) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (this.box[i][j] == value || this.box[i][j] == (value * 100)) {
					return true;
				}
			}
		}
		return false;
	}
	
	//Sets value of specified index
	
	public void setValue(int i, int j, int value) {
		this.box[i][j] = value;
	}
	
	//Gets value of specified index
	
	public int getValue(int i, int j) {
		return this.box[i][j];
	}	
}
