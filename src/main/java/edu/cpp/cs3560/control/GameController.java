package edu.cpp.cs3560.control;
import edu.cpp.cs3560.model.*;

import java.util.Scanner;

/**
 * Controller
 */
public class GameController
{
    private final GameModel model;
    private Card firstPick = null;
    private boolean inputLocked = false;
    Scanner input;

    public GameController(GameModel model)
    {
	  this.model = model;
    }

    public GameController(GameModel model, Scanner input)
    {
	  this.model = model;
	  this.input = input;
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
     * Called by command line game.
     */
    private void onCardClicked(int r, int c)
    {
	  //not accepting input at this time, return.
	  if (inputLocked) return;
	  Card card = model.getBoard().get(r, c);
	  //not a valid match card. Do nothing and return.
	  if (card.isMatched()) return;
	  //increment move count
	  model.incrementMoves();
	  card.setSelected(true);

	  if (firstPick == null) //if this is the first pick, set this card as the first pick.
	  {
		firstPick = card;
	  } else //if this is the second pick.
	  {
		// Compare
		if (firstPick.getId() == card.getId()) //there is a match
		{
		    firstPick.setMatched(true);
		    card.setMatched(true);
		    model.incrementScore();
		} else //not a match.
		{
		    // Temporarily lock input; UI should call onMismatchDelay.run() after ~700ms
		    inputLocked = true;
		    Card a = firstPick, b = card;
		    model.incrementIncorr();
		    // After delay, UI must call controller.resolveMismatch(a,b)
		}
		firstPick.setSelected(false);
		card.setSelected(false);
		firstPick = null;
	  }
    }

    /**
     * To run the command line game.
     */
    public void runGame()
    {
	  System.out.println("******* WELCOME TO NY Times Tile Match *******\n");
	  System.out.println("Type \"end\" to end the game.\n");
	  System.out.println(model.toString());
	  while(!model.getBoard().allMatched())
	  {
		System.out.print("Please select a card (r c): ");
		int r = input.nextInt();
		int c = input.nextInt();
		onCardClicked(r, c);
		System.out.println(model.toString());
	  }
	  System.out.println("******* GAME OVER *******\n");
	  if(model.getBoard().allMatched())
	  {
		System.out.println("You won!");
	  }
	  else {
		System.out.println("You lost!");
	  }

	  System.out.println("Play again? (y/n): ");
	  String answer = input.next();
	  if(answer.equals("y"))
	  {
		model.getBoard().shuffleCards(System.currentTimeMillis());
		runGame();
	  }else
	  {
		System.exit(0);
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
