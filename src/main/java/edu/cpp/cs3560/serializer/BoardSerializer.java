package edu.cpp.cs3560.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import edu.cpp.cs3560.model.Board;
import edu.cpp.cs3560.model.BoardCell;

import java.io.IOException;

public class BoardSerializer extends StdSerializer<Board>
{
    public BoardSerializer()
    {
	  this(null);
    }

    public BoardSerializer(Class<Board> t)
    {
	  super(t);
    }

    @Override
    public void serialize(
		Board board,
		JsonGenerator jgen,
		SerializerProvider provider)
		throws IOException
    {

	  // Start the JSON object for the Board
	  jgen.writeStartObject();
	  jgen.writeNumberField("rows", board.getRows());
	  jgen.writeNumberField("cols", board.getCols());
	  jgen.writeNumberField("levels", board.getLevel());
	  // --- 1. Serialize the 2D array of BoardCells as a single, flat array (Recommended) ---

	  // This approach avoids the 2D array structure in JSON, which is often cleaner.
	  // This is where you flatten your structure to match what the client (or deserializer) expects.

	  provider.defaultSerializeField("cells", board.getCells(), jgen);

	  BoardCell[][] cells = board.getCells(); // Assuming you have this getter

//	  // Iterate over the 2D array (4x4, 16 cells total)
//	  for (int r = 0; r < cells.length; r++) {
//		// The boundary check here MUST be '<' and not '<='
//		for (int c = 0; c < cells[r].length; c++) {
//
//		    // Get the BoardCell
//		    BoardCell cell = cells[r][c];
//
//		    // Have the standard serializer handle the serialization of the BoardCell
//		    // This ensures all fields within BoardCell are correctly serialized.
//		    jgen.writeObject(cell);
//		}
//	  }

	  jgen.writeEndArray(); // End of "allCards" array
	  // End the JSON object for the Board
	  jgen.writeEndObject();
    }
}

