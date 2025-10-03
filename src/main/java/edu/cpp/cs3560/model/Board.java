package edu.cpp.cs3560.model;

import java.util.*;

/**
 * Class to keep track of board view, rules for the game, etc
 */
public class Board
{
    private final int rows,cols;
    private final Card[][] grid;

    public Board(int rows, int cols, long seed) {
	  if ((rows * cols) % 2 != 0) throw new IllegalArgumentException("Even number of cards required");
	  this.rows = rows; this.cols = cols;
	  this.grid = new Card[rows][cols];
	  // Create pair ids: 0..(nPairs-1) twice
	  int n = rows * cols;
	  List<Integer> ids = new ArrayList<>();
	  for (int i = 0; i < n / 2; i++) { ids.add(i); ids.add(i); }
	  // Need to shuffle cards to make sure the cards are in random orders each time.
	  Collections.shuffle(ids, new Random(seed));
	  Iterator<Integer> it = ids.iterator();
	  for (int r = 0; r < rows; r++)
		for (int c = 0; c < cols; c++)
		    grid[r][c] = new Card(it.next());
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public Card get(int r, int c) { return grid[r][c]; }

    public boolean allMatched()
    {
	  //make more efficient. Need to remove card and its matched card.
	  for (int r = 0; r < rows; r++)
		for (int c = 0; c < cols; c++)
		    if (!grid[r][c].isMatched()) return false;
	  return true;
    }
    public String toString()
    {
	  StringBuilder sb = new StringBuilder();
	  for (int r = 0; r < rows; r++) {
		for (int c = 0; c < cols; c++) {
		    sb.append(" " + grid[r][c]);
		    if (c < cols - 1) sb.append(" |");
		}
		sb.append("\n");
		if (r < rows - 1) {
		    for (int i = 0; i < rows; i++) sb.append("-----------" + (i < cols - 1 ? "+" : ""));
		}
		sb.append("\n");
	  }
	  sb.append("]");
	  return sb.toString();
    }
}
