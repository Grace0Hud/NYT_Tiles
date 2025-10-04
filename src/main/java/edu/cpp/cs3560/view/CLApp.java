package edu.cpp.cs3560.view;
import edu.cpp.cs3560.control.*;
import edu.cpp.cs3560.model.*;

import java.util.Scanner;

public class CLApp
{
    public static void main(String[] args)
    {
	  Scanner input = new Scanner(System.in);
	  GameModel model = new GameModel(4,4, System.currentTimeMillis());
	  GameController game = new GameController(model, input);
	  game.runGame();
    }
}
