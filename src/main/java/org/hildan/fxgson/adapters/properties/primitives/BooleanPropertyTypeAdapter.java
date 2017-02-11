package org.hildan.fxgson.adapters.properties.primitives;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import com.google.gson.TypeAdapter;

/**
 * An implementation of {@link PrimitivePropertyTypeAdapter} for JavaFX {@link BooleanProperty}. It serializes the
 * boolean value of the property instead of the property itself.
 */
public class BooleanPropertyTypeAdapter extends PrimitivePropertyTypeAdapter<Boolean, BooleanProperty> {

    /**
     * Creates a new BooleanPropertyTypeAdapter.
     *
     * @param crashOnNullValue
     *         if true, this adapter will throw {@link NullPrimitiveException} when reading a null value. If false, this
     *         adapter will call {@link #createDefaultProperty()} instead.
     * @param delegate
     *         a delegate adapter to use for the inner value of the property
     */
    public BooleanPropertyTypeAdapter(boolean crashOnNullValue, TypeAdapter<Boolean> delegate) {
        super(crashOnNullValue, delegate);
    }

    @Override
    protected Boolean extractPrimitiveValue(BooleanProperty property) {
        return property.get();
    }

    @Override
    protected BooleanProperty createDefaultProperty() {
        return new SimpleBooleanProperty();
    }

    @Override
    protected BooleanProperty wrapNonNullPrimitiveValue(Boolean deserializedValue) {
        return new SimpleBooleanProperty(deserializedValue);
    }
}
