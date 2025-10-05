package edu.cpp.cs3560.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class BoardTest {

    Board board;

    @BeforeEach
    void setUp()
    {
	  board = new Board(2,2, System.currentTimeMillis());
    }
    @Test
    void getRows()
    {
	  assertEquals(2,board.getRows());
    }

    @Test
    void getCols()
    {
	  assertEquals(2,board.getCols());
    }

    @Test
    void shuffleCards()
    {
	  ArrayList<Card> cards = new ArrayList<>();
	  for(int i = 0 ; i < board.getRows() ; i++)
	  {
		for(int j = 0 ; j < board.getCols() ; j++)
		{
		    cards.add(board.get(i,j));
		}
	  }
	  board.shuffleCards(System.currentTimeMillis());
	  for(int i = 0 ; i < board.getRows() ; i++)
	  {
		for(int j = 0 ; j < board.getCols() ; j++)
		{
		    assertNotEquals(cards.get(j),board.get(i,j));
		}
	  }
    }

    @Test
    void allMatched()
    {
	  for(int i = 0 ; i < board.getRows() ; i++)
	  {
		for(int j = 0 ; j < board.getCols() ; j++)
		{
		    board.get(i,j).markMatched();
		}
	  }
	  assertTrue(board.allMatched());
    }

    @Test
    void testToString()
    {
	  String expected = " " + board.get(0,0).toString() + " | " + board.get(0,1).toString()
		    + "\n------------+------------\n" + " " + board.get(1,0).toString() + " | "
		    + board.get(1,1).toString() + "\n\n";
	  assertEquals(expected,board.toString());
    }
}