package edu.cpp.cs3560.control;
import edu.cpp.cs3560.model.*;

import java.util.Scanner;

/**
 * Controller for a single game of nyt tile matc.
 */
public class GameController
{
    private final GameModel model; //model of the game.
    private BoardCell firstPick; //first cell chosen.
    private boolean inputLocked = false; //if the game is accepting input at this time.
    int difficulty = 1; //difficulty level, corresponding to the number of items that need to be matched each tile.
    Scanner input; //and input for command line app.

    /**
     * Constructor for a game controller.
     * @param model model for the game.
     */
    public GameController(GameModel model)
    {
	  this.model = model;
    }

    /**
     * Constructor for a game controller, including a difficulty setting.
     * @param model model of the game.
     * @param difficulty corresponding to the number of items that need to be matched each tile.
     */
    public GameController(GameModel model, int difficulty)
    {
	  this.model = model;
	  this.difficulty = difficulty;
    }

    /**
     * Constructor for a game controller in a CL app.
     * @param model model of the game.
     * @param input a scanner to get information.
     */
    public GameController(GameModel model, Scanner input)
    {
	  this.model = model;
	  this.input = input;
    }

    /**
     *
     * @return if the input is currently locked.
     */
    public boolean isInputLocked()
    {
	  return inputLocked;
    }

    /**
     *
     * @return the game model.
     */
    public GameModel getModel()
    {
	  return model;
    }

    /**
     * Called by the UI when the user clicks a card.
     * @param r the row of the card clicked
     * @param c the column of the card clicked.
     * @param onMismatchDelay
     */
    public void onCardClicked(int r, int c, Runnable onMismatchDelay)
    {
	  //not accepting input at this time, return.
	  if (inputLocked) return;

	  BoardCell cell = model.getBoard().getCellAt(r,c);
	  Card[] cards = cell.getCards();
	  //not a valid match card. Do nothing and return.
	  if (model.getBoard().isCellSelected(r,c) || model.getBoard().isCellmatched(r,c)) return;
	  //increment move count
	  model.incrementMoves();
	  cell.selectCell();

	  if (firstPick == null) //if this is the first pick, set this card as the first pick.
	  {
		firstPick = cell;
	  } else //if this is the second pick.
	  {
		// Compare
		if (firstPick.matchCell(cell)) //there is a match
		{
		    model.incrementScore();
		    firstPick.deSelectCell();
		    cell.deSelectCell();
		    firstPick = null;
		} else //not a match.
		{
		    // Temporarily lock input; UI should call onMismatchDelay.run() after ~700ms
		    inputLocked = true;
		    BoardCell a = firstPick, b = cell;
		    firstPick = null;
		    model.incrementIncorr();
		    onMismatchDelay.run(); // UI schedules the actual delay
		    // After delay, UI must call controller.resolveMismatch(a,b)
		}
	  }
    }

    /**
     * Called by command line game when the user clicks a card.
     * @param r the row of the card clicked
     * @param c the column of the card clicked.
     */
    public void onCardClicked(int r, int c)
    {
	  //not accepting input at this time, return.
	  if (inputLocked) return;

	  BoardCell cell = model.getBoard().getCellAt(r,c);
	  Card[] cards = cell.getCards();
	  //not a valid match card. Do nothing and return.
	  if (model.getBoard().isCellSelected(r,c) || model.getBoard().isCellmatched(r,c)) return;
	  //increment move count
	  model.incrementMoves();
	  cell.selectCell();

	  if (firstPick == null) //if this is the first pick, set this card as the first pick.
	  {
		firstPick = cell;
	  } else //if this is the second pick.
	  {
		// Compare
		if (firstPick.matchCell(cell)) //there is a match
		{
		    model.incrementScore();
		    firstPick = null;
		} else //not a match.
		{
		    // Temporarily lock input; UI should call onMismatchDelay.run() after ~700ms
		    inputLocked = true;
		    BoardCell a = firstPick, b = cell;
		    firstPick = null;
		    model.incrementIncorr();
		    // After delay, UI must call controller.resolveMismatch(a,b)
		}
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
		//onCardClicked(r, c);
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
    public void resolveMismatch(BoardCell a, BoardCell b)
    {
	  a.deSelectCell();
	  b.deSelectCell();
	  inputLocked = false;
    }
    public boolean isWin() { return model.getBoard().allMatched(); }

}
