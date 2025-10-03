package edu.cpp.cs3560.model;

/**
 * Class to track a single card.
 */
public class Card
{
    private int id; //used to identify matching cards.
    private boolean matched; //used to check if card has already been matched.

    /**
     * Creates a card with the specified ID. Cards are face down and unmatched as a default.
     * @param id the ID of the card.
     */
    public Card(int id)
    {
	  this.id=id;
	  this.matched=false;
    }
    public Card(int id, boolean matched)
    {
	  this.id = id;
	  this.matched = matched;
    }
    public int getId()
    {
	  return id;
    }
    public void setId(int id)
    {
	  this.id = id;
    }

    public boolean isMatched()
    {
	  return matched;
    }
    public void markMatched(){ matched = true;}

    public String toString()
    {
	  StringBuilder str = new StringBuilder();
	  str.append("ID: " + id );
	  if(matched)
	  {
		str.append("- m");
	  }else
	  {
		str.append("- nm");
	  }
	  return str.toString();
    }
}
