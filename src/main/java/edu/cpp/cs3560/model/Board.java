package edu.cpp.cs3560.model;

import java.util.*;
import edu.cpp.cs3560.model.*;

/**
 * Class to keep track of board view, rules for the game, etc
 */
public class Board
{
    private final int rows,cols; //number of rows and columns.
    private final BoardCell[][] grid; // a grid with cells in each section.
    int level; //number of cards per tile in the board.
    List<Integer> ids = new ArrayList<>(); // ids assigned to cards in the board.

    /**
     * Constructor to create a board with a certain number of rows and columns.
     * Level is at one by default.
     * @param rows the number of rows.
     * @param cols the number of columns
     * @param seed a seed for the random numbers.
     */
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

    /**
     * Constructor to create a board with a certain number of rows and columns.
     * Adds on a certain "depth" to the ids stored in the cards.
     * @param rows the number of rows
     * @param cols the number of columns
     * @param seed a seed to generate random numbers.
     * @param level the number of cards per cell.
     */
    public Board(int rows, int cols, long seed, int level)
    {
	  if ((rows * cols) % 2 != 0) throw new IllegalArgumentException("Even number of cards required");
	  this.rows = rows; this.cols = cols; this.level = level;
	  this.grid = new BoardCell[rows][cols];
	  // Create pair ids: 0..(nPairs-1) twice
	  int n = rows * cols;
	  //System.out.println("n: " + n);
	  for (int i = 0; i < n/2; i++)
	  {
		for(int j =0; j < level; j++)
		{
		    ids.add(i); ids.add(i);
		}
	  }
	  System.out.println("Number of ids: " + ids.size());
	  // Need to shuffle cards to make sure the cards are in random orders each time.
	  initializeBoardCells();
	  shuffleCards(seed);
    }

    public Card[] getAllCards()
    {
	  int n = 0;
	  Card[] cards = new Card[rows*cols];
	  for(int i = 0; i < rows; i++)
	  {
		for(int j = 0; j < cols; j++)
		{
		    Card[] localCards = grid[i][j].getCards();
		    for(int k = 0; k < localCards.length; k++)
		    {
			  cards[n] = localCards[k];
			  n++;
		    }
		}
	  }
	  return cards;
    }

    /**
     * initializes the cells of the board with empty objects.
     */
    private void initializeBoardCells()
    {
	  int count = 0;
	  for(int r = 0; r < rows; r++)
	  {
		for(int c = 0; c < cols; c++)
		{
		    //System.out.println("Initializing board cell " + count);
		    grid[r][c] = new BoardCell(level, count);
		    count++;
		}
	  }
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public int getLevel()
    {
	  return level;
    }

    /**
     * Shuffles the card ids.
     * For boards with level > 1, ensures that cards in the same cell do not have the same id.
     * @param seed a seed to generate random numbers.
     */
    public void shuffleCards(long seed) {

	  // --- STEP 1: PREPARE THE MASTER ID LIST ---
	  // The 'ids' list should already contain 6 repetitions of each of your 8 unique IDs,
	  // totaling 48 elements. We use this list as the master pool.

	  // --- STEP 2: SHUFFLE THE MASTER LIST ---
	  // Shuffle the entire list to randomize the distribution across the board.
	  Collections.shuffle(ids, new Random(seed));

	  // --- STEP 3 & 4: ASSIGN IDS IN BLOCKS WITH LOCAL VALIDATION ---

	  int totalCells = rows * cols;
	  int currentIdIndex = 0;

	  for (int r = 0; r < rows; r++) {
		for (int c = 0; c < cols; c++) {

		    // This list will hold the 'level' (3) unique IDs for the current cell
		    int[] idList = new int[level];

		    // A Set is used for fast, easy checking of local duplicates.
		    Set<Integer> assignedIds = new HashSet<>();

		    for (int k = 0; k < level; k++) {

			  // Get the next ID from the master shuffled list
			  int candidateId = ids.get(currentIdIndex);

			  // --- LOCAL VALIDATION LOOP ---
			  // If the candidateId is already in the 'assignedIds' set for this cell,
			  // we must swap it with an ID later in the master list.
			  while (assignedIds.contains(candidateId)) {

				// The simplest fix is to swap the current repeating ID
				// with an ID from the END of the unassigned portion of the master list.

				// 1. Find the index of the last unassigned ID:
				int lastUnassignedIndex = ids.size() - 1;

				// 2. Perform the swap:
				// This moves the duplicate ID away, and brings a new, unseen ID into play.
				Collections.swap(ids, currentIdIndex, lastUnassignedIndex);

				// 3. Update the candidateId after the swap (it now points to the new ID)
				candidateId = ids.get(currentIdIndex);

				// Note: This swap means the ID that was moved to the end might violate
				// a future cell's constraint, but the overall distribution remains uniform.
			  }

			  // The candidateId is now guaranteed to be unique for this cell.
			  idList[k] = candidateId;
			  assignedIds.add(candidateId);
			  // Move to the next ID in the master list
			  currentIdIndex++;
		    }

		    // --- ASSIGN TO CELL ---
		    // You'll need to call a method here to store the idList array in your card/cell object
		    // e.g., cards[r][c].setIds(idList);
		    for(int i = 0; i < idList.length; i++)
		    {
			  grid[r][c].setCardAt(i, new Card(idList[i]));
		    }

		    // DEBUGGING: Print the cell's IDs and ensure uniqueness
		    System.out.println("Cell (" + r + ", " + c + ") assigned IDs: " + Arrays.toString(idList));
		}
	  }
	  System.out.println(toString());
    }

    /**
     * Used to get a specific card in a cell with only one level.
     * @param r number of rows.
     * @param c number of columns.
     * @return the card at the specified row and column.
     */
    public Card get(int r, int c) { return grid[r][c].getCardAt(0); }

    /**
     * Used to get all of the cards from a specific cell.
     * @param r row
     * @param c column
     * @return an array of cards.
     */
    public Card[] getCardsAt(int r, int c){return grid[r][c].getCards();}

    /**
     * Used to get a specific card from a specific board cell.
     * @param r row
     * @param c column
     * @param l level
     * @return the card asked for.
     */
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

    /**
     * Checks if every cell in the board has all cards matched.
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
     * @return a string representation of the board.
     */
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
