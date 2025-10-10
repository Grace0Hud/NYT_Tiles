package edu.cpp.cs3560.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import edu.cpp.cs3560.model.BoardCell;
import edu.cpp.cs3560.model.Card;

import java.io.IOException;

/**
 * Class to serialize a board cell
 * To prevent unwanted usage of certain functions.
 */
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

    /**
     * Overiding how the cell is serialized by json
     * @param cell a board cell
     * @param jgen  a json generator
     * @param provider a serializer provider
     * @throws IOException
     */
    @Override
    public void serialize(
		BoardCell cell,
		JsonGenerator jgen,
		SerializerProvider provider)
		throws IOException
    {

	  // Start the JSON object for the Board
	  jgen.writeStartObject();

	  jgen.writeNumberField("id", cell.getId());
	  jgen.writeNumberField("level", cell.getLevel());
	  jgen.writeBooleanField("isSelected", cell.isSelected());

	  //Serialize the 2D array of BoardCells as a single, flat array.
	  jgen.writeArrayFieldStart("cards");
	  Card[] cards = cell.getCards();
	  for (int r = 0; r < cards.length; r++)
	  {
		    jgen.writeObject(cards[r]);
	  }
	  jgen.writeEndArray();
	  // End the JSON object for the Board
	  jgen.writeEndObject();
    }
}

