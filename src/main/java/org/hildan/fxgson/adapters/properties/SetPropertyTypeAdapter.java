package org.hildan.fxgson.adapters.properties;

import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.ObservableSet;

import com.google.gson.TypeAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * A basic {@link TypeAdapter} for JavaFX {@link SetProperty}. It serializes the set inside the property instead of the
 * property itself.
 */
public class SetPropertyTypeAdapter<T> extends PropertyTypeAdapter<ObservableSet<T>, SetProperty<T>> {

    /**
     * Creates a new SetPropertyTypeAdapter.
     *
     * @param delegate
     *         a delegate adapter to use for the inner set value of the property
     */
    public SetPropertyTypeAdapter(TypeAdapter<ObservableSet<T>> delegate) {
        super(delegate);
    }

    @NotNull
    @Override
    protected SetProperty<T> createProperty(ObservableSet<T> deserializedValue) {
        return new SimpleSetProperty<>(deserializedValue);
    }
}
