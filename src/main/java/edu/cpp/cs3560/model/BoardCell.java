package edu.cpp.cs3560.model;

public class BoardCell
{
    final int level;
    private Card[] cards;
    private boolean selected;
    private boolean allMatched;

    public BoardCell(int level)
    {
	  this.level = level;
	  cards = new Card[level];
	  initializeCards();
    }

    private void initializeCards()
    {
	  for(int i = 0; i < level; i++)
	  {
		cards[i] = new Card(0);
	  }
    }
    public int getLevel()
    {
	  return level;
    }

    public int getIdAt(int level)
    {
	  return cards[level].getId();
    }

    public void setCardId(int level, int id)
    {
	  cards[level].setId(id);
    }
    public void setCardAt(int level, Card card)
    {
	  cards[level] = card;
    }
    public Card[] getCards()
    {
	  return cards;
    }
    public Card getCardAt(int level)
    {
	  return cards[level];
    }
    public void selectCell()
    {
	  this.selected = true;
    }

    public void deSelectCell()
    {
	  this.selected = false;
    }

    public boolean isSelected()
    {
	  return selected;
    }

    public boolean matchCell(BoardCell other)
    {
	  if (other == null || other.level != level)
	  {
		return false;
	  }
	  Card[] otherCards = other.getCards();
	  for(int i = 0; i < other.getLevel(); i++)
	  {
		if (this.matchCard(otherCards[i].getId()))
		{
		    other.markCardMatched(i);
		    return true;
		}
	  }
	  return false;
    }
    public boolean matchCard(int id)
    {
	  for(Card card : cards)
	  {
		if(card.getId() == id && !card.isMatched())
		{
		    card.markMatched();
		    return true;
		}
	  }
	  return false;
    }
    public boolean isCardMatched(int level)
    {
	  return cards[level].isMatched();
    }

    public void markCardMatched(int level)
    {
	  cards[level].markMatched();
    }

    public boolean isAllMatched()
    {
	  allMatched = checkAllMatched();
	  return allMatched;
    }

    private boolean checkAllMatched()
    {
	  for (Card card : cards)
	  {
		if (!card.isMatched())
		{
		    return false;
		}
	  }
	  return true;
    }

    public String toString()
    {
	  StringBuilder str = new StringBuilder();
	  for(Card card : cards)
	  {
		str.append(card.toString());
		if(level > 1)
		{
		    str.append(", ");
		}
	  }
	  return str.toString();
    }
}
