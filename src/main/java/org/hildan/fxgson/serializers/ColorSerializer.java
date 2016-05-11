package org.hildan.fxgson.serializers;

import java.lang.reflect.Type;

import javafx.scene.paint.Color;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A custom serializer/deserializer for JavaFX {@link Color}s. It represents them as a hexadecimal string preceded
 * by #, such as #00FF00 for pure green.
 */
public class ColorSerializer implements JsonSerializer<Color>, JsonDeserializer<Color> {

    @Override
    public JsonElement serialize(Color color, Type type, JsonSerializationContext context) {
        return new JsonPrimitive("#" + color.toString().substring(2));
    }

    @Override
    public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        return Color.web(json.getAsString());
    }
}
