package org.hildan.fxgson.serializers;

import java.lang.reflect.Type;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A custom serializer/deserializer for JavaFX {@link StringProperty}.
 */
public class StringPropertySerializer implements JsonSerializer<StringProperty>, JsonDeserializer<StringProperty> {

    private static final int NULL_PLACEHOLDER = 0;

    @Override
    public JsonElement serialize(StringProperty property, Type type, JsonSerializationContext context) {
        if (property.get() != null) {
            return new JsonPrimitive(property.get());
        } else {
            return new JsonPrimitive(NULL_PLACEHOLDER);
        }
    }

    @Override
    public StringProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        if (isNullPlaceholder(json)) {
            return new SimpleStringProperty(null);
        }
        return new SimpleStringProperty(json.getAsString());
    }

    private static boolean isNullPlaceholder(JsonElement json) {
        try {
            return json.isJsonPrimitive() && NULL_PLACEHOLDER == json.getAsInt();
        } catch (ClassCastException | NumberFormatException e) {
            return false; // not an int, so definitely not the placeholder
        }
    }
}
