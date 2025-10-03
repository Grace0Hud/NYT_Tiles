package edu.cpp.cs3560.control;
import edu.cpp.cs3560.model.*;

public class GameController
{
    private final GameModel model;
    private Card firstPick = null;
    private boolean inputLocked = false;

    public GameController(GameModel model)
    {
	  this.model = model;
    }

    public boolean isInputLocked()
    {
	  return inputLocked;
    }

    public GameModel getModel()
    {
	  return model;
    }

    /**
     * Called by the UI when the user clicks a card.
     */
    public void onCardClicked(int r, int c, Runnable onMismatchDelay)
    {
	  //not accepting input at this time, return.
	  if (inputLocked) return;

	  Card card = model.getBoard().get(r, c);
	  //not a valid match card. Do nothing and return.
	  if (card.isMatched()) return;
	  //increment move count
	  model.incrementMoves();

	  if (firstPick == null) //if this is the first pick, set this card as the first pick.
	  {
		firstPick = card;
	  } else //if this is the second pick.
	  {
		// Compare
		if (firstPick.getId() == card.getId()) //there is a match
		{
		    firstPick.markMatched();
		    card.markMatched();
		    model.incrementScore();
		    firstPick = null;
		} else //not a match.
		{
		    // Temporarily lock input; UI should call onMismatchDelay.run() after ~700ms
		    inputLocked = true;
		    Card a = firstPick, b = card;
		    firstPick = null;
		    model.incrementIncorr();
		    onMismatchDelay.run(); // UI schedules the actual delay
		    // After delay, UI must call controller.resolveMismatch(a,b)
		}
	  }
    }

    /**
     * UI calls this after the delay expires.
     */
    public void resolveMismatch(Card a, Card b)
    {

	  inputLocked = false;
    }
    public boolean isWin() { return model.getBoard().allMatched(); }

}
