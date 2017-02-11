package org.hildan.fxgson.adapters.properties;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.google.gson.TypeAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * A basic {@link TypeAdapter} for JavaFX {@link StringProperty}. It serializes the string inside the property instead
 * of the property itself.
 */
public class StringPropertyTypeAdapter extends PropertyTypeAdapter<String, StringProperty> {

    /**
     * Creates a new StringPropertyTypeAdapter.
     *
     * @param delegate
     *         a delegate adapter to use for the inner string value of the property
     */
    public StringPropertyTypeAdapter(TypeAdapter<String> delegate) {
        super(delegate);
    }

    @NotNull
    @Override
    protected StringProperty createProperty(String deserializedValue) {
        return new SimpleStringProperty(deserializedValue);
    }
}
