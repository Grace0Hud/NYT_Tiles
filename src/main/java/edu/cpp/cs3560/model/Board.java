package edu.cpp.cs3560.model;

import java.util.*;
import edu.cpp.cs3560.model.*;

/**
 * Class to keep track of board view, rules for the game, etc
 */
public class Board
{
    private final int rows,cols;
    private final BoardCell[][] grid;
    int level; //number of cards per tile in the board.
    List<Integer> ids = new ArrayList<>();

    public Board(int rows, int cols, long seed)
    {
	  if ((rows * cols) % 2 != 0) throw new IllegalArgumentException("Even number of cards required");
	  this.rows = rows; this.cols = cols; this.level = 1;
	  this.grid = new BoardCell[rows][cols];
	  // Create pair ids: 0..(nPairs-1) twice
	  int n = rows * cols * level;
	  //System.out.println("Test");
	  for (int i = 0; i < n / 2; i++) { ids.add(i); ids.add(i); }
	  // Need to shuffle cards to make sure the cards are in random orders each time.
	  initializeBoardCells();
	  shuffleCards(seed);
    }

    public Board(int rows, int cols, long seed, int level)
    {
	  if ((rows * cols) % 2 != 0) throw new IllegalArgumentException("Even number of cards required");
	  this.rows = rows; this.cols = cols; this.level = level;
	  this.grid = new BoardCell[rows][cols];
	  // Create pair ids: 0..(nPairs-1) twice
	  int n = rows * cols * level;
	  //System.out.println("n: " + n);
	  for (int i = 0; i < n / 2; i++)
	  {
		ids.add(i); ids.add(i);
	  }
	  // Need to shuffle cards to make sure the cards are in random orders each time.
	  initializeBoardCells();
	  shuffleCards(seed);
    }

    private void initializeBoardCells()
    {
	  for(int r = 0; r < rows; r++)
	  {
		for(int c = 0; c < cols; c++)
		{
		    grid[r][c] = new BoardCell(level);
		}
	  }
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public int getLevel()
    {
	  return level;
    }
    public void shuffleCards(long seed)
    {
	  // Need to shuffle cards to make sure the cards are in random orders each time.
	  Collections.shuffle(ids, new Random(seed));
	  System.out.println("Ids: " + ids.toString());
	  int count = 0;
	  for (int r = 0; r < rows; r++)
	  {
		for (int c = 0; c < cols; c++)
		{
		    //for multiple levels, we need to make sure that one spot on the board doesn't have two of the same id.
		    List<Integer> temp = new ArrayList<>(ids.subList(count, ids.size()));
		    //System.out.println("Temp at count = " + count + ":  " + temp.toString());
		    for (int l = 0; l < level; l++)
		    {
			  int id = temp.get(0);
			  grid[r][c].setCardAt(l,new Card(id));
			  ArrayList toRemove = new ArrayList();
			  toRemove.add(id);
			  temp.removeAll(toRemove);
			  //System.out.println("Tenp after removal: " + temp.toString());
			  //System.out.println("Ids after removal: " + ids.toString());
		    }
		    count++;
		}
	  }
    }


    public Card get(int r, int c) { return grid[r][c].getCardAt(0); }

    public Card[] getCardsAt(int r, int c){return grid[r][c].getCards();}

    public Card get(int r, int c, int l)
    {
	  return grid[r][c].getCardAt(l);
    }

    public boolean isCellSelected(int r, int c)
    {
	  return grid[r][c].isSelected();
    }

    public BoardCell getCellAt(int r, int c)
    {
	  return grid[r][c];
    }

    public void selectCell(int r, int c)
    {
	  grid[r][c].selectCell();
    }

    public void deSelectCell(int r, int c)
    {
	  grid[r][c].deSelectCell();
    }

    public boolean isCellmatched(int r, int c)
    {
	  return grid[r][c].isAllMatched();
    }

    public boolean allMatched()
    {
	  //make more efficient. Need to remove card and its matched card.
	  for (int r = 0; r < rows; r++)
	  {
		for (int c = 0; c < cols; c++)
		{
		    //System.out.println(grid[r][c].toString());
		    if (!grid[r][c].isAllMatched())
		    {
			  return false;
		    }
		}
	  }

	  return true;
    }
    public String toString()
    {
	  StringBuilder sb = new StringBuilder();
	  for (int r = 0; r < rows; r++) {
		for (int c = 0; c < cols; c++) {
		    sb.append(grid[r][c].toString());
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
