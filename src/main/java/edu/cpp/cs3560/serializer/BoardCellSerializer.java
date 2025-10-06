package edu.cpp.cs3560.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import edu.cpp.cs3560.model.BoardCell;
import edu.cpp.cs3560.model.Card;

import java.io.IOException;

public class BoardCellSerializer extends StdSerializer<BoardCell>
{
    public BoardCellSerializer()
    {
	  this(null);
    }

    public BoardCellSerializer(Class<BoardCell> t)
    {
	  super(t);
    }

    @Override
    public void serialize(
		BoardCell cell,
		JsonGenerator jgen,
		SerializerProvider provider)
		throws IOException
    {

	  // Start the JSON object for the Board
	  jgen.writeStartObject();

	  jgen.writeNumberField("level", cell.getLevel());
	  jgen.writeBooleanField("isSelected", cell.isSelected());

	  // --- 1. Serialize the 2D array of BoardCells as a single, flat array (Recommended) ---

	  // This approach avoids the 2D array structure in JSON, which is often cleaner.
	  // This is where you flatten your structure to match what the client (or deserializer) expects.

	  jgen.writeArrayFieldStart("cards"); // <-- This name might fix your previous error reference!

	  Card[] cards = cell.getCards(); // Assuming you have this getter

	  // Iterate over the 2D array (4x4, 16 cells total)
	  for (int r = 0; r < cards.length; r++)
	  {
		    jgen.writeObject(cards[r]);
	  }

	  jgen.writeEndArray(); // End of "allCards" array

	  // --- 2. (Optional) Add other simple Board fields here if needed ---
	  //
	  // jgen.writeNumberField("cols", board.getCols());

	  // End the JSON object for the Board
	  jgen.writeEndObject();
    }
}

