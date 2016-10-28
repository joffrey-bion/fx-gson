package org.hildan.fxgson.adapters;

import java.io.IOException;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.ObservableMap;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * A basic {@link TypeAdapter} for JavaFX {@link MapProperty}. It serializes the map inside the property instead of the
 * property itself.
 */
public class MapPropertyTypeAdapter<K, V> extends TypeAdapter<MapProperty<K, V>> {

    private final TypeAdapter<ObservableMap<K, V>> delegate;

    public MapPropertyTypeAdapter(TypeAdapter<ObservableMap<K, V>> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void write(JsonWriter out, MapProperty<K, V> value) throws IOException {
        delegate.write(out, value.getValue());
    }

    @Override
    public MapProperty<K, V> read(JsonReader in) throws IOException {
        return new SimpleMapProperty<>(delegate.read(in));
    }
}
