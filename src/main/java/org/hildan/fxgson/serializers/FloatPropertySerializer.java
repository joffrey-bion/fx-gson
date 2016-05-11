package org.hildan.fxgson.serializers;

import java.lang.reflect.Type;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A custom serializer/deserializer for JavaFX {@link FloatProperty}.
 */
public class FloatPropertySerializer implements JsonSerializer<FloatProperty>, JsonDeserializer<FloatProperty> {

    @Override
    public JsonElement serialize(FloatProperty property, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(property.get());
    }

    @Override
    public FloatProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        return new SimpleFloatProperty(json.getAsLong());
    }
}
