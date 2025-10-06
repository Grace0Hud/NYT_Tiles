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
    void multiLevelUniqueIds()
    {
	  Board board2 = new Board(2,2,System.currentTimeMillis(), 2);
	  System.out.println(board2);
	  for(int i = 0 ; i < board2.getRows() ; i++)
	  {
		for(int j = 0 ; j < board2.getCols() ; j++)
		{
		    Card[] cards = board2.getCardsAt(i,j);
		    for(int k = 0 ; k < cards.length-1; k++)
		    {
			  Card card = cards[k];
			  for(int l = k+1; l < cards.length-1 ; l++)
			  {
				assertNotEquals(card,cards[l]);
			  }
		    }
		}
	  }
    }

    @Test
    void multiLevelAllMatch()
    {
	  Board board2 = new Board(2,2,System.currentTimeMillis(), 2);
	  System.out.println(board2);
	  for(int i = 0 ; i < board2.getRows() ; i++)
	  {
		for(int j = 0 ; j < board2.getCols() ; j++)
		{
		    Card[] cards = board2.getCardsAt(i,j);
		}
	  }
    }

    @Test
    void multiLevelCheckAllIds()
    {
	  Board board2 = new Board(4,4,System.currentTimeMillis(), 3);
	  System.out.println(board2);
    }
    @Test
    void testToString()
    {
	  String expected = board.get(0,0).toString() + " |" + board.get(0,1).toString()
		    + "\n------------+------------\n" + board.get(1,0).toString() + " |"
		    + board.get(1,1).toString() + "\n\n";
	  assertEquals(expected,board.toString());
    }
}