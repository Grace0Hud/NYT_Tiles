package edu.cpp.cs3560.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class GameModelTest {

    GameModel model;
    @BeforeEach
    void setUp()
    {
	  model = new GameModel(2,2,System.currentTimeMillis());
    }

    @Test
    void getBoard()
    {
	  Board board = model.getBoard();
	  assertNotNull(board);
	  assertEquals(2,board.getRows());
	  assertEquals(2,board.getCols());
    }

    @Test
    void getScore()
    {
	  assertEquals(0,model.getScore());
    }

    @Test
    void getNumMoves()
    {
	  assertEquals(0,model.getNumMoves());
    }

    @Test
    void incrementScore()
    {
	  model.incrementScore();
	  assertEquals(1,model.getScore());
    }

    @Test
    void incrementMoves()
    {
	  model.incrementMoves();
	  assertEquals(1,model.getNumMoves());
    }

    @Test
    void getIncorrectMoves()
    {
	  assertEquals(0,model.getIncorrectMoves());
    }

    @Test
    void incrementIncorr()
    {
	  model.incrementIncorr();
	  assertEquals(1,model.getIncorrectMoves());
    }

    @Test
    void testToString()
    {
	  String expected = "Score: 0\nTotal Moves: 0\nIncorrect Moves: 0\n" + model.getBoard().toString();
	  assertEquals(expected,model.toString());
    }
}