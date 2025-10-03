package edu.cpp.cs3560.view;
import edu.cpp.cs3560.control.*;
import edu.cpp.cs3560.model.*;
public class CLApp
{
    public static void main(String[] args)
    {
	  GameModel model = new GameModel(4,4, System.currentTimeMillis());
	  GameController game = new GameController(model);
	  System.out.println(game.getModel());
    }
}
