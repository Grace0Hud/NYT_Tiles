package edu.cpp.cs3560.model;

import java.util.*;

/**
 * Class to keep track of board view, rules for the game, etc
 */
public class Board
{
    private final int rows,cols;
    private final Card[][][] grid;
    int level;
    List<Integer> ids = new ArrayList<>();

    public Board(int rows, int cols, long seed)
    {
	  if ((rows * cols) % 2 != 0) throw new IllegalArgumentException("Even number of cards required");
	  this.rows = rows; this.cols = cols; this.level = 1;
	  this.grid = new Card[rows][cols][level];
	  // Create pair ids: 0..(nPairs-1) twice
	  int n = rows * cols * level;
	  System.out.println("Test");
	  for (int i = 0; i < n / 2; i++) { ids.add(i); ids.add(i); }
	  // Need to shuffle cards to make sure the cards are in random orders each time.
	  shuffleCards(seed);
    }

    public Board(int rows, int cols, long seed, int level)
    {
	  if ((rows * cols) % 2 != 0) throw new IllegalArgumentException("Even number of cards required");
	  this.rows = rows; this.cols = cols; this.level = level;
	  this.grid = new Card[rows][cols][level];
	  // Create pair ids: 0..(nPairs-1) twice
	  int n = rows * cols * level;
	  System.out.println("n: " + n);
	  for (int i = 0; i < n / 2; i++)
	  {
		ids.add(i); ids.add(i);
	  }
	  // Need to shuffle cards to make sure the cards are in random orders each time.
	  shuffleCards(seed);
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public void shuffleCards(long seed)
    {
	  // Need to shuffle cards to make sure the cards are in random orders each time.
	  Collections.shuffle(ids, new Random(seed));
	  //System.out.println(ids.toString());
	  int count = 0;
	  for (int r = 0; r < rows; r++)
	  {
		for (int c = 0; c < cols; c++)
		{
		    List<Integer> temp;
		    if(ids.size() > count)
		    {
			  temp = ids.subList(count, ids.size());
		    }else
		    {
			  temp = new ArrayList<>(ids);
		    }

		    //System.out.println(temp.toString());
		    for (int l = 0; l < level; l++)
		    {
			  int id = temp.get(0);
			  grid[r][c][l] = new Card(id);
			  temp.remove(temp.indexOf(id));
		    }
		    count++;
		}
	  }
    }

    public int getLevel()
    {
	  return level;
    }
    public Card get(int r, int c) { return grid[r][c][0]; }

    public Card get(int r, int c, int l)
    {
	  return grid[r][c][l];
    }
    public boolean allMatched()
    {
	  //make more efficient. Need to remove card and its matched card.
	  for (int r = 0; r < rows; r++)
		for (int c = 0; c < cols; c++)
		    for (int l = 0; l < level; l++)
		    	if (!grid[r][c][l].isMatched()) return false;
	  return true;
    }
    public String toString()
    {
	  StringBuilder sb = new StringBuilder();
	  for (int r = 0; r < rows; r++) {
		for (int c = 0; c < cols; c++) {
		    for(int l = 0; l < level; l++)
		    {
			  sb.append(" " + grid[r][c][l]);
		    }
		    if (c < cols - 1) sb.append(" |");
		}
		sb.append("\n");
		if (r < rows - 1) {
		    for (int i = 0; i < rows; i++) sb.append("------------" + (i < cols - 1 ? "+" : ""));
		}
		sb.append("\n");
	  }
	  return sb.toString();
    }
}
