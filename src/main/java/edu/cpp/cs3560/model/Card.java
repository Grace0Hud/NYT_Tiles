package edu.cpp.cs3560.model;

/**
 * Class to track a single card.
 */
public class Card
{
    private int id; //used to identify matching cards.
    private boolean matched; //used to check if card has already been matched.
    private boolean selected;

    /**
     * Creates a card with the specified ID. Cards are face down and unmatched as a default.
     *
     * @param id the ID of the card.
     */
    public Card(int id)
    {
	  this.id = id;
	  this.matched = false;
	  this.selected = false;
    }

    public Card(int id, boolean matched)
    {
	  this.id = id;
	  this.matched = matched;
	  this.selected = false;
    }

    public int getId()
    {
	  return id;
    }

    public void setId(int id)
    {
	  this.id = id;
    }

    public void setSelected(boolean selected)
    {
	  this.selected = selected;
    }

    public boolean isSelected()
    {
	  return selected;
    }

    public boolean isMatched()
    {
	  return matched;
    }

    public void markMatched()
    {
	  this.matched = true;
    }
    public void setMatched(boolean matched)
    {
	  this.matched = matched;
    }

    public String toString()
    {
	  StringBuilder str = new StringBuilder();
	  if (selected) {str.append("*");}else{str.append(" ");}
		str.append("ID: " + id);
		if (matched)
		{
		    str.append("-  m");
		} else
		{
		    str.append("- nm");
		}
		return str.toString();
    }
}
