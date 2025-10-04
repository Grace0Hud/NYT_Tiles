package edu.cpp.cs3560.model;
/**
 * Model for how an individual game proceeds.
 */
public class GameModel
{
    private final Board board;
    private int score = 0;
    private int numMoves = 0;
    private int incorrectMoves = 0;
    public GameModel(int rows, int cols, long seed)
    {
	  this.board = new Board(rows, cols, seed);
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
	  str.append("Score: " + score + "\n");
	  str.append("Total Moves: " + numMoves + "\n");
	  str.append("Number of Incorrect moves: " + incorrectMoves + "\n");
	  str.append(board.toString());
	  return str.toString();
    }
}
