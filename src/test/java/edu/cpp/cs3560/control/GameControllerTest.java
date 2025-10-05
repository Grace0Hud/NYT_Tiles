package edu.cpp.cs3560.control;

import edu.cpp.cs3560.model.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest
{

    GameModel model;
    GameController controller;
    @BeforeEach
    void setUp()
    {
	  model = new GameModel(2,2,System.currentTimeMillis());
	  controller = new GameController(model);
    }
    @Test
    void isInputLocked()
    {
        assertFalse(controller.isInputLocked());
    }

    @Test
    void getModel()
    {
	  assertEquals(model,controller.getModel());
    }

    @Test
    void onCardClicked()
    {
        //controller.onCardClicked(0,0);
        assertTrue(model.getBoard().get(0,0).isSelected());
    }

    @Test
    void resolveMismatch()
    {

    }

    @Test
    void isWin()
    {

    }
}