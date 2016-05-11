package org.hildan.fxgson.adapters;

import java.io.IOException;
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
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * A custom type adapter for JavaFX {@link StringProperty}.
 */
public class StringPropertyTypeAdapter extends TypeAdapter<StringProperty> {

    @Override
    public void write(JsonWriter out, StringProperty value) throws IOException {
        out.value(value.getValue());
    }

    @Override
    public StringProperty read(JsonReader in) throws IOException {
        boolean isNull = in.peek() == JsonToken.NULL;
        return new SimpleStringProperty(isNull ? null : in.nextString());
    }
}
