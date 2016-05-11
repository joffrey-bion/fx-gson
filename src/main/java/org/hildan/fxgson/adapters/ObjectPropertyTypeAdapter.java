package org.hildan.fxgson.adapters;

import java.io.IOException;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * A custom type adapter for JavaFX {@link Property}.
 */
public class ObjectPropertyTypeAdapter<T> extends TypeAdapter<Property<T>> {

    private final TypeAdapter<T> delegate;

    public ObjectPropertyTypeAdapter(TypeAdapter<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void write(JsonWriter out, Property<T> value) throws IOException {
        delegate.write(out, value.getValue());
    }

    @Override
    public Property<T> read(JsonReader in) throws IOException {
        return new SimpleObjectProperty<>(delegate.read(in));
    }
}
