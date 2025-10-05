package edu.cpp.cs3560.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardCellTest
{
    BoardCell cell;

    @BeforeEach
    void setUp()
    {
	  cell = new BoardCell(2);
    }

    @Test
    void getLevel()
    {
	  assertEquals(2,cell.getLevel());
    }

    @Test
    void setCardId()
    {
	  cell.setCardId(0, 3);
	  assertEquals(3, cell.getIdAt(0));
    }

    @Test
    void setCardAt()
    {
        Card card = new Card(3);
        cell.setCardAt(0,card);
        assertEquals(card,cell.getCardAt(0));
    }

    @Test
    void getCards()
    {
        Card[] cards = cell.getCards();
        assertEquals(2,cards.length);
    }

    @Test
    void getCardAt()
    {
        Card card = new Card(3);
        cell.setCardAt(0, card);
        assertEquals(card,cell.getCardAt(0));
    }

    @Test
    void selectCell()
    {
        assertFalse(cell.isSelected());
        cell.selectCell();
        assertTrue(cell.isSelected());
    }

    @Test
    void deSelectCell()
    {
        cell.selectCell();
        assertTrue(cell.isSelected());
        cell.deSelectCell();
        assertFalse(cell.isSelected());
    }

    @Test
    void matchCell()
    {
        BoardCell cell2 = new BoardCell(2);
        assertTrue(cell.matchCell(cell2));
        assertTrue(cell.getCardAt(0).isMatched());
        assertTrue(cell2.getCardAt(0).isMatched());
    }

    @Test
    void matchCard()
    {
        assertTrue(cell.matchCard(0));
        assertTrue(cell.isCardMatched(0));
        assertFalse(cell.isCardMatched(1));
    }

    @Test
    void markCardMatched()
    {
        cell.markCardMatched(0);
        assertTrue(cell.isCardMatched(0));
    }

    @Test
    void isAllMatched()
    {
        assertFalse(cell.isAllMatched());
        cell.markCardMatched(0);
        cell.markCardMatched(1);
        assertTrue(cell.isAllMatched());
    }

    @Test
    void testToString()
    {
    }
}