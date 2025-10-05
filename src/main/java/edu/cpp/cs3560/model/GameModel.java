package edu.cpp.cs3560.model;
/**
 * Model for how an individual game proceeds.
 */
public class GameModel
{
    private final Board board; //the board for gameplay.
    private int score = 0; //the current score.
    private int numMoves = 0; //the number of moves that have been taken.
    private int incorrectMoves = 0;
    public GameModel(int rows, int cols, long seed)
    {
	  this.board = new Board(rows, cols, seed);
    }

    public GameModel(int rows, int cols, long seed, int level)
    {
	  this.board = new Board(rows, cols, seed, level);
    }
    public Board getBoard() { return board; }
    public int getScore() { return score; }
    public int getNumMoves() { return numMoves; }
    public void incrementScore() { score++; }
    public void incrementMoves() { numMoves++; }

    public int getIncorrectMoves()
    {
	  return incorrectMoves;
    }

    public void incrementIncorr()
    {
	  incorrectMoves++;
    }

    @Override
    public String toString()
    {
	  StringBuilder str = new StringBuilder();
	  str.append("Level: " + board.getLevel() + "    ");
	  str.append("Score: " + score + "    ");
	  str.append("Total Moves: " + numMoves + "    ");
	  str.append("Incorrect Moves: " + incorrectMoves + "    ");
	  //str.append(board.toString());
	  return str.toString();
    }
}
