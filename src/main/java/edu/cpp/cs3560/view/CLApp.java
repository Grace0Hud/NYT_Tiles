package edu.cpp.cs3560.view;
import edu.cpp.cs3560.control.*;
import edu.cpp.cs3560.model.*;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class is largely used for testing. There is no different level functionality here.
 */
public class CLApp
{
    public static void main(String[] args)
    {
	  Scanner input = new Scanner(System.in);
	  GameModel model = new GameModel(4,4, System.currentTimeMillis(), 2);
	  GameController game = new GameController(model, input);
	  try{
		game.saveState();
	  }catch(Exception e){
		System.out.println(e);
	  }
	  try
	  {
		game.loadState("src/states/gameState.json");
		System.out.println(model);
	  } catch (IOException e)
	  {
		System.out.println(e);
	  }

    }
}
