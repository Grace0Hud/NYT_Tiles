package edu.cpp.cs3560.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import edu.cpp.cs3560.model.Board;
import edu.cpp.cs3560.model.BoardCell;

import java.io.IOException;

/**
 * Used to serialize the board class for Json to prevent unwanted functionality.
 */
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

    /**
     * overriding default serialization
     * @param board  a board.
     * @param jgen
     * @param provider
     * @throws IOException
     */
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
	  jgen.writeNumberField("level", board.getLevel());
	  //we can use the default serializer here because its just a 1d array.
	  provider.defaultSerializeField("cells", board.getCells(), jgen);
	  jgen.writeEndArray(); // End of "allCards" array
	  // End the JSON object for the Board
	  jgen.writeEndObject();
    }
}

