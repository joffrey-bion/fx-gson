package org.hildan.fxgson.serializers;

import java.lang.reflect.Type;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A custom serializer/deserializer for JavaFX {@link BooleanProperty}.
 */
public class BooleanPropertySerializer implements JsonSerializer<BooleanProperty>, JsonDeserializer<BooleanProperty> {

    @Override
    public JsonElement serialize(BooleanProperty property, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(property.get());
    }

    @Override
    public BooleanProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        return new SimpleBooleanProperty(json.getAsBoolean());
    }
}
