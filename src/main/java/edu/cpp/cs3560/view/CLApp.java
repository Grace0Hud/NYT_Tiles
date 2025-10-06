package edu.cpp.cs3560.view;
import edu.cpp.cs3560.control.*;
import edu.cpp.cs3560.model.*;

import java.io.IOException;
import java.util.Scanner;

public class CLApp
{
    public static void main(String[] args)
    {
	  Scanner input = new Scanner(System.in);
	  GameModel model = new GameModel(4,4, System.currentTimeMillis(), 2);
	  GameController game = new GameController(model, input);
	  try
	  {
		game.loadState("src/states/gameState.json");
	  } catch (IOException e)
	  {
		System.out.println("Error loading game state");
	  }
//	  try{
//		game.saveState();
//	  }catch(Exception e){
//		System.out.println(e);
//	  }
    }
}
