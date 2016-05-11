package org.hildan.fxgson.serializers;

import java.lang.reflect.Type;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A custom serializer/deserializer for JavaFX {@link DoubleProperty}.
 */
public class DoublePropertySerializer implements JsonSerializer<DoubleProperty>, JsonDeserializer<DoubleProperty> {

    @Override
    public JsonElement serialize(DoubleProperty property, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(property.get());
    }

    @Override
    public DoubleProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        return new SimpleDoubleProperty(json.getAsDouble());
    }
}
