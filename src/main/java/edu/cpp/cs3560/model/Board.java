package edu.cpp.cs3560.model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.cpp.cs3560.serializer.BoardSerializer;

/**
 * Class to keep track of board view, rules for the game, etc
 */
@JsonSerialize(using = BoardSerializer.class)
public class Board
{
    private final int rows, cols; //number of rows and columns.
    private final BoardCell[][] grid; // a grid with cells in each section.
    private final int level; //number of cards per tile in the board.
    private List<Integer> ids = new ArrayList<>(); // ids assigned to cards in the board.

    /**
     * Constructor to create a board with a certain number of rows and columns.
     * Level is at one by default.
     *
     * @param rows the number of rows.
     * @param cols the number of columns
     * @param seed a seed for the random numbers.
     */
    public Board(int rows, int cols, long seed)
    {
	  if ((rows * cols) % 2 != 0)
	  {
		throw new IllegalArgumentException("Even number of cards required");
	  }
	  this.rows = rows;
	  this.cols = cols;
	  this.level = 1;
	  this.grid = new BoardCell[rows][cols];
	  // Create pair ids: 0..(nPairs-1) twice
	  int n = rows * cols * level;
	  //System.out.println("Test");
	  for (int i = 0; i < n / 2; i++)
	  {
		ids.add(i);
		ids.add(i);
	  }
	  // Need to shuffle cards to make sure the cards are in random orders each time.
	  initializeBoardCells();
	  shuffleCards(seed);
    }

    /**
     * Constructor to create a board with a certain number of rows and columns.
     * Adds on a certain "depth" to the ids stored in the cards.
     *
     * @param rows  the number of rows
     * @param cols  the number of columns
     * @param seed  a seed to generate random numbers.
     * @param level the number of cards per cell.
     */
    public Board(int rows, int cols, long seed, int level)
    {
	  if ((rows * cols) % 2 != 0)
	  {
		throw new IllegalArgumentException("Even number of cards required");
	  }
	  this.rows = rows;
	  this.cols = cols;
	  this.level = level;
	  this.grid = new BoardCell[rows][cols];
	  // Create pair ids: 0..(nPairs-1) twice
	  int n = rows * cols;
	  //System.out.println("n: " + n);
	  for (int i = 0; i < n / 2; i++)
	  {
		for (int j = 0; j < level; j++)
		{
		    ids.add(i);
		    ids.add(i);
		}
	  }
	  System.out.println("Number of ids: " + ids.size());
	  // Need to shuffle cards to make sure the cards are in random orders each time.
	  initializeBoardCells();
	  shuffleCards(seed);
    }

    /**
     * Constructor used by a json reader when loading in a game
     * @param rows number of rows
     * @param cols number of columns
     * @param level level of difficulty.
     * @param cells the board cells to add to the board.
     */
    @JsonCreator
    public Board(
		@JsonProperty("rows") int rows,
		@JsonProperty("cols") int cols,
		@JsonProperty("level") int level,
		@JsonProperty("cells") BoardCell[][] cells)
    {
	  this.rows = rows;
	  this.cols = cols;
	  this.level = level;
	  this.grid = new BoardCell[rows][cols];
	  for (int i = 0; i < rows; i++)
	  {
		System.arraycopy(cells[i], 0, grid[i], 0, cols);
	  }
    }

    /**
     * initializes the cells of the board with empty objects.
     */
    private void initializeBoardCells()
    {
	  int count = 0;
	  for (int r = 0; r < rows; r++)
	  {
		for (int c = 0; c < cols; c++)
		{
		    //System.out.println("Initializing board cell " + count);
		    grid[r][c] = new BoardCell(level, count);
		    count++;
		}
	  }
    }

    /**
     * Getters for rows, column, level.
     * @return
     */
    public int getRows()
    {
	  return rows;
    }

    public int getCols()
    {
	  return cols;
    }

    public int getLevel()
    {
	  return level;
    }

    /**
     * reset each of the cells in the board.
     */
    public void resetBoard()
    {
	  for (BoardCell[] cells : grid)
	  {
		for (BoardCell cell : cells)
		{
		    cell.resetCell();
		}
	  }
	  shuffleCards(System.currentTimeMillis());
    }

    /**
     * Shuffles the card ids.
     * For boards with level > 1, ensures that cards in the same cell do not have the same id.
     *
     * @param seed a seed to generate random numbers.
     */
    public void shuffleCards(long seed)
    {
	  // Shuffle the entire list to randomize the distribution across the board.
	  Collections.shuffle(ids, new Random(seed));

	  //assigning ids.
	  int currentIdIndex = 0;
	  for (int r = 0; r < rows; r++)
	  {
		for (int c = 0; c < cols; c++)
		{

		    // This list will hold the 'level' (3) unique IDs for the current cell
		    int[] idList = new int[level];

		    // Helps check for dups
		    Set<Integer> assignedIds = new HashSet<>();

		    for (int k = 0; k < level; k++)
		    {

			  // Get the next ID from the master shuffled list
			  int candidateId = ids.get(currentIdIndex);
			  // If the candidateId is already in the 'assignedIds' set for this cell,
			  // we must swap it with an ID later in the master list.
			  while (assignedIds.contains(candidateId))
			  {
				// 1. Find the index of the last unassigned ID:
				int lastUnassignedIndex = ids.size() - 1;

				// 2. Perform the swap:
				Collections.swap(ids, currentIdIndex, lastUnassignedIndex);

				// 3. Update the candidateId after the swap (it now points to the new ID)
				candidateId = ids.get(currentIdIndex);
			  }
			  // The candidateId is now guaranteed to be unique for this cell.
			  idList[k] = candidateId;
			  assignedIds.add(candidateId);
			  // Move to the next ID in the master list
			  currentIdIndex++;
		    }

		    //assign the ids to each cell.
		    for (int i = 0; i < idList.length; i++)
		    {
			  grid[r][c].setCardAt(i, new Card(idList[i]));
		    }
		}
	  }
	  //System.out.println(toString());
    }

    /**
     * Used to get a specific card in a cell with only one level.
     *
     * @param r number of rows.
     * @param c number of columns.
     * @return the card at the specified row and column.
     */
    public Card get(int r, int c)
    {
	  return grid[r][c].getCardAt(0);
    }

    /**
     * Used to get all of the cards from a specific cell.
     *
     * @param r row
     * @param c column
     * @return an array of cards.
     */
    public Card[] getCardsAt(int r, int c)
    {
	  return grid[r][c].getCards();
    }

    /**
     * Used to get a specific card from a specific board cell.
     *
     * @param r row
     * @param c column
     * @param l level
     * @return the card asked for.
     */
    public Card get(int r, int c, int l)
    {
	  return grid[r][c].getCardAt(l);
    }

    public BoardCell[][] getCells()
    {
	  return grid;
    }

    public boolean isCellSelected(int r, int c)
    {
	  return grid[r][c].isSelected();
    }

    public BoardCell getCellAt(int r, int c)
    {
	  return grid[r][c];
    }

    public boolean isCellmatched(int r, int c)
    {
	  return grid[r][c].isAllMatched();
    }

    /**
     * Checks if every cell in the board has all cards matched.
     *
     * @return if all cells are fully matched.
     */
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

    /**
     * Prints out the board.
     *
     * @return a string representation of the board.
     */
    public String toString()
    {
	  StringBuilder sb = new StringBuilder();
	  for (int r = 0; r < rows; r++)
	  {
		for (int c = 0; c < cols; c++)
		{
		    sb.append(grid[r][c].toString());
		    if (c < cols - 1)
		    {
			  sb.append(" |");
		    }
		}
		sb.append("\n");
		if (r < rows - 1)
		{
		    for (int i = 0; i < rows; i++)
		    {
			  sb.append("------------" + (i < cols - 1 ? "+" : ""));
		    }
		}
		sb.append("\n");
	  }
	  return sb.toString();
    }
}
