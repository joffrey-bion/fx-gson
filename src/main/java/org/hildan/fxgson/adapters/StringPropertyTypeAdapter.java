package org.hildan.fxgson.adapters;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * A basic {@link TypeAdapter} for JavaFX {@link StringProperty}. It serializes the string inside the property instead
 * of the property itself.
 */
public class StringPropertyTypeAdapter extends TypeAdapter<StringProperty> {

    private final TypeAdapter<String> delegate;

    public StringPropertyTypeAdapter(TypeAdapter<String> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void write(JsonWriter out, StringProperty value) throws IOException {
        delegate.write(out, value.getValue());
    }

    @Override
    public StringProperty read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return new SimpleStringProperty(null);
        } else {
            return new SimpleStringProperty(delegate.read(in));
        }
    }
}
