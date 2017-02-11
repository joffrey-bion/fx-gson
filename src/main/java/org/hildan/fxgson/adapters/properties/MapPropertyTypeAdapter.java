package org.hildan.fxgson.adapters.properties;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.ObservableMap;

import com.google.gson.TypeAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * A basic {@link TypeAdapter} for JavaFX {@link MapProperty}. It serializes the map inside the property instead of the
 * property itself.
 */
public class MapPropertyTypeAdapter<K, V> extends PropertyTypeAdapter<ObservableMap<K, V>, MapProperty<K, V>> {

    /**
     * Creates a new MapPropertyTypeAdapter.
     *
     * @param delegate
     *         a delegate adapter to use for the inner map value of the property
     */
    public MapPropertyTypeAdapter(TypeAdapter<ObservableMap<K, V>> delegate) {
        super(delegate);
    }

    @NotNull
    @Override
    protected MapProperty<K, V> createProperty(ObservableMap<K, V> deserializedValue) {
        return new SimpleMapProperty<>(deserializedValue);
    }
}
