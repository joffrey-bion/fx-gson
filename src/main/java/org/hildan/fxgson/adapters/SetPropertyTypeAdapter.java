package org.hildan.fxgson.adapters;

import java.io.IOException;

import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.ObservableSet;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * A basic {@link TypeAdapter} for JavaFX {@link SetProperty}. It serializes the set inside the property instead of the
 * property itself.
 */
public class SetPropertyTypeAdapter<T> extends TypeAdapter<SetProperty<T>> {

    private final TypeAdapter<ObservableSet<T>> delegate;

    public SetPropertyTypeAdapter(TypeAdapter<ObservableSet<T>> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void write(JsonWriter out, SetProperty<T> value) throws IOException {
        delegate.write(out, value.getValue());
    }

    @Override
    public SetProperty<T> read(JsonReader in) throws IOException {
        return new SimpleSetProperty<>(delegate.read(in));
    }
}
