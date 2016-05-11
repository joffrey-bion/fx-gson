package org.hildan.fxgson.serializers;

import java.lang.reflect.Type;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A custom serializer/deserializer for JavaFX {@link IntegerProperty}.
 */
public class IntegerPropertySerializer implements JsonSerializer<IntegerProperty>, JsonDeserializer<IntegerProperty> {

    @Override
    public JsonElement serialize(IntegerProperty property, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(property.get());
    }

    @Override
    public IntegerProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        return new SimpleIntegerProperty(json.getAsInt());
    }
}
