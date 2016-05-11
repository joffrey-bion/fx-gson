package org.hildan.fxgson.serializers;

import java.lang.reflect.Type;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A custom serializer/deserializer for JavaFX {@link LongProperty}.
 */
public class LongPropertySerializer implements JsonSerializer<LongProperty>, JsonDeserializer<LongProperty> {

    @Override
    public JsonElement serialize(LongProperty property, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(property.get());
    }

    @Override
    public LongProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        return new SimpleLongProperty(json.getAsLong());
    }
}
