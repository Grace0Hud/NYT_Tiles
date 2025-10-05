package edu.cpp.cs3560.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest
{
    Card card;

    @BeforeEach
    void setUp()
    {
	  card = new Card(1);
    }

    @Test
    void getId()
    {
	  assertEquals(1, card.getId());
    }

    @Test
    void setId()
    {
	  card.setId(2);
	  assertEquals(2, card.getId());
    }

    @Test
    void setSelected()
    {
	  card.setSelected(true);
	  assertTrue(card.isSelected());
	  card.setSelected(false);
	  assertFalse(card.isSelected());
    }

    @Test
    void isSelected()
    {
	  assertFalse(card.isSelected());
    }

    @Test
    void isMatched()
    {
	  assertFalse(card.isMatched());
    }

    @Test
    void markMatched()
    {
	  card.markMatched();
	  assertTrue(card.isMatched());
    }

    @Test
    void setMatched()
    {
	  card.setMatched(true);
	  assertTrue(card.isMatched());
	  card.setMatched(false);
	  assertFalse(card.isMatched());
    }

    @Test
    void testToString()
    {
	  String expected = " ID: 1- nm";
	  assertEquals(expected, card.toString());
    }
}