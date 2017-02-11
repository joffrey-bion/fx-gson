package org.hildan.fxgson.adapters.properties;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

import com.google.gson.TypeAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * A basic {@link TypeAdapter} for JavaFX {@link ListProperty}. It serializes the list inside the property instead of
 * the property itself.
 */
public class ListPropertyTypeAdapter<T> extends PropertyTypeAdapter<ObservableList<T>, ListProperty<T>> {

    /**
     * Creates a new ListPropertyTypeAdapter.
     *
     * @param delegate
     *         a delegate adapter to use for the inner list value of the property
     */
    public ListPropertyTypeAdapter(TypeAdapter<ObservableList<T>> delegate) {
        super(delegate);
    }

    @NotNull
    @Override
    protected ListProperty<T> createProperty(ObservableList<T> deserializedValue) {
        return new SimpleListProperty<>(deserializedValue);
    }
}
