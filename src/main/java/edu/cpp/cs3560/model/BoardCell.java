package edu.cpp.cs3560.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.cpp.cs3560.serializer.BoardCellSerializer;

/**
 * Class to represent a specific cell in a board.
 */
@JsonSerialize(using = BoardCellSerializer.class)
public class BoardCell
{
    final int level;
    final int id;
    private final Card[] cards;
    private boolean selected;

    /**
     * Creates a board cell with the specified level.
     * @param level number of cards per cell.
     */
    public BoardCell(int level)
    {
	  this.id = 0;
	  this.level = level;
	  cards = new Card[level];
	  initializeCards();
    }

    /**
     * Creates a cell with a specified id.
     * @param level
     * @param id
     */
    public BoardCell(int level, int id)
    {
	  this.level = level;
	  cards = new Card[level];
	  this.id = id;
	  initializeCards();
    }

    /**
     * Constructor used for json seralization.
     * @param id the id of the cell
     * @param level the level of the cell
     * @param selected if the card was selected at last save.
     * @param cards all of the cards in the board cell.
     */
    @JsonCreator
    public BoardCell(
		@JsonProperty("id") int id,
		@JsonProperty("level") int level,
		@JsonProperty("isSelected") boolean selected,
		@JsonProperty("cards") Card[] cards)
    {
	  this.id = id;
	  this.selected = selected;
	  this.level = level;
	  this.cards = cards;
    }

    /**
     * Creates valid empty card objects.
     */
    private void initializeCards()
    {
	  for(int i = 0; i < level; i++)
	  {
		cards[i] = new Card(0);
	  }
    }

    /**
     * Simple getters and setters for various elements.
     */
    public int getId()
    {
	  return id;
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
	  return checkAllMatched();
    }

    /**
     * Resets a specific cell by unmatching all the cards.
     */
    public void resetCell()
    {
	  deSelectCell();
	  for(Card card : cards)
	  {
		card.setMatched(false);
	  }
    }

    /**
     * Checks if the cell has matching ids to another cell.
     *
     * @param other a board cell other than this one.
     * @return if the cell has a card which matches this cell.
     */
    @JsonIgnore
    public boolean matchCell(BoardCell other)
    {
	  if (other == null || other.level != level)
	  {
		return false;
	  }
	  Card[] otherCards = other.getCards();
	  for (int i = 0; i < other.getLevel(); i++)
	  {
		if (!otherCards[i].isMatched() && this.matchCard(otherCards[i].getId()))
		{
		    other.markCardMatched(i);
		    return true;
		}
	  }
	  return false;
    }

    /**
     * Checks to see if the cell has and id which matches the one provided.
     *
     * @param id the id of the card to check against.
     * @return if the cell has a card with a matching id.
     */
    @JsonIgnore
    public boolean matchCard(int id)
    {
	  for (Card card : cards)
	  {
		if (card.getId() == id && !card.isMatched())
		{
		    card.markMatched();
		    return true;
		}
	  }
	  return false;
    }


    /**
     * Checks if all the cards contained in the cell have been matched.
     * @return if all cards are matched.
     */
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

    /**
     *
     * @return a string representation of the cell.
     */
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
