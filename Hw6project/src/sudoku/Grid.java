package sudoku;

import java.util.*;


public class Grid 
{
	private int[][]	values;
	
	//
	// DON'T CHANGE THIS.
	//
	// Constructs a Grid instance from a string[] as provided by TestGridSupplier.
	// See TestGridSupplier for examples of input.
	// Dots in input strings represent 0s in values[][].
	//Dots in input strings represent 0s in values[][].
	public Grid(String[] rows)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
		{
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i=0; i<9; i++)
			{
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}
	
	public String toString()
	{
		String s = "";
		for (int j=0; j<9; j++)
		{
			for (int i=0; i<9; i++)
			{
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char)('0' + n);
			}
			s += "\n";
		}
		return s;
	}

	//
	// DON'T CHANGE THIS.
	// Copy ctor. Duplicates its source. Youâ€™ll call this 9 times in next9Grids.
	//
	Grid(Grid src)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
			for (int i=0; i<9; i++)
				values[j][i] = src.values[j][i];
	}	
	
	public void setValue(int coOrd1, int coOrd2, int get) 
	{
		this.values[coOrd2][coOrd1] = get;
	}

	//
	// COMPLETE THIS
	//
	//
	// Finds an empty member of values[][]. Returns an array list of 9 grids that look like the current grid,
	// except the empty member contains 1, 2, 3 .... 9. Returns null if the current grid is full. Donâ€™t change
	// â€œthisâ€� grid. Build 9 new grids.
	// 
	//
	// Example: if this grid = 1........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//
	// Then the returned array list would contain:
	//
	// 11.......          12.......          13.......          14.......    and so on     19.......
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	//
	public ArrayList<Grid> next9Grids()
	{		
		int xOfNextEmptyCell = 0;
		int yOfNextEmptyCell = 0;

		boolean try34 = false;															
		
		// Find x,y of an empty cell.
		for (int line=0; line<9; line++)
		{	
			for (int stLine=0; stLine<9; stLine++)
			{
				if (!try34 && (values[line][stLine] == 0)) {									
					try34 = true;
					xOfNextEmptyCell = stLine; 
					yOfNextEmptyCell = line; 
				}
			}
		}
		
		// Check if no empty cell has been found
		if (!try34)
		{
			return null;
		}
		
		
		ArrayList<Grid> grids = new ArrayList<Grid>();

		// Create 9 new grids as described in the comments above. Add them to grids.
		for (int testR = 1; testR<=9; testR++)
		{
			Grid newGrid = new Grid(this);
			
			newGrid.setValue(xOfNextEmptyCell, yOfNextEmptyCell, testR); 
			//newGrid.values[xOfNextEmptyCell][yOfNextEmptyCell] = testR;
			grids.add(newGrid);
		}

		return grids;
	}
	
	//
	// COMPLETE THIS
	//
	// Returns true if this grid is legal. A grid is legal if no row, column, or
	// 3x3 block contains a repeated 1, 2, 3, 4, 5, 6, 7, 8, or 9.
	//
	public boolean isLegal()
	{	
		for (int horizontalLine = 0; horizontalLine < values[0].length; horizontalLine++) 
		{
			if (!isLegalRow(horizontalLine))
				return false;
		}
		for (int verticalLine = 0; verticalLine < values[0].length; verticalLine++)
			if (!isLegalCol(verticalLine))
				return false;		
		int[][] bl = new int[3][3];
		for (int vertStart = 0; vertStart < values[0].length; vertStart += 3) 
		{
			for (int horizontalStart = 0; horizontalStart < values[0].length; horizontalStart += 3) 
			{
				
				for (int horizontalBlock = vertStart; horizontalBlock < 3; horizontalBlock++) 
				{
					for (int vertBlock = horizontalStart; vertBlock < 3; vertBlock++) 
					{
						bl[horizontalBlock][vertBlock] = values[vertStart + horizontalBlock][horizontalStart + vertBlock];
					}
				}				
				
				if (!isLegalBlock(bl))
					return false;
			}
		}
		return true;
	}

	public boolean isLegalRow(int horizontalLine) 
	{
		int[] horizontalLineValue = values[horizontalLine];		
		return !checkRepeat(horizontalLineValue);
	}
	
	public boolean isLegalCol(int verticalLine) 
	{
		int[] verticalLineValue = new int[values[0].length];	
		
		for (int horizontalLine = 0; horizontalLine < values[0].length; horizontalLine++) 
		{
			verticalLineValue[horizontalLine] = values[horizontalLine][verticalLine];
		}
		
		return !checkRepeat(verticalLineValue);
	}
	
	public boolean isLegalBlock(int[][] block) 
	{
		int[] valueOfBlock = new int[9];
		int indexValue = 0;		
		for (int j = 0; j < block[0].length; j++) 
		{
			for (int i = 0; i < block[0].length; i++) 
			{
				valueOfBlock[indexValue] = block[j][i];
				indexValue++;
			}
		}		
		return !checkRepeat(valueOfBlock);		
	} 
	
	public boolean checkRepeat(int[] findingDupli) 
	{
		//copying all the elements of the single line to check for duplicates
        int[] duplicatesRemoved = new int[findingDupli.length];		

		for (int i = 0; i<duplicatesRemoved.length ;i++)
		{
			duplicatesRemoved[i]=findingDupli[i];
		}
		Arrays.sort(duplicatesRemoved);				
		for (int i = 0; i < 8; i++) 
		{
			if ((duplicatesRemoved[i] != 0) && (duplicatesRemoved[i] == duplicatesRemoved[i+1]))
			{				
				return true;
			}
		}		
		return false;
	}

	public boolean isFull()
	{
		for (int j = 0; j < 9; j++) 
		{
			for (int i = 0; i < 9; i++)
			{
				if (values[j][i] == 0)
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean equals(Object x)
	{
		Grid that = (Grid)x;
		for (int j = 0; j < 9; j++) 
		{
			for (int i = 0; i < 9; i++) 
			{
				if (this.values[j][i] != that.values[j][i]) 
				{
					return false;
				}
			}
		}
		
		return true;																	
	}
}