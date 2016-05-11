package org.hildan.fxgson.serializers;

import java.lang.reflect.Type;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A custom serializer/deserializer for JavaFX {@link Font}s. In JSON, Fonts are represented by a comma-separated
 * list of strings following the pattern "family,weight,size".
 */
public class FontSerializer implements JsonSerializer<Font>, JsonDeserializer<Font> {

    @Override
    public JsonElement serialize(Font font, Type type, JsonSerializationContext context) {
        String family = font.getFamily();
        String weight = font.getStyle();
        double size = font.getSize();
        return new JsonPrimitive(family + "," + weight + "," + size);
    }

    @Override
    public Font deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        String[] font = json.getAsString().split(",");
        String family = font[0];
        FontWeight weight = FontWeight.findByName(font[1]);
        double size = Double.parseDouble(font[2]);
        return Font.font(family, weight, size);
    }
}
