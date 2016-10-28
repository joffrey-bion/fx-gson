package org.hildan.fxgson.adapters;

import java.io.IOException;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * A basic {@link TypeAdapter} for JavaFX {@link ListProperty}. It serializes the list inside the property instead of
 * the property itself.
 */
public class ListPropertyTypeAdapter<T> extends TypeAdapter<ListProperty<T>> {

    private final TypeAdapter<ObservableList<T>> delegate;

    public ListPropertyTypeAdapter(TypeAdapter<ObservableList<T>> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void write(JsonWriter out, ListProperty<T> value) throws IOException {
        delegate.write(out, value.getValue());
    }

    @Override
    public ListProperty<T> read(JsonReader in) throws IOException {
        return new SimpleListProperty<>(delegate.read(in));
    }
}
