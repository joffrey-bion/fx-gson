package org.hildan.fxgson.adapters.primitives;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import com.google.gson.TypeAdapter;

/**
 * An implementation of {@link PrimitiveTypeAdapter} for JavaFX {@link BooleanProperty}. It serializes the boolean value
 * of the property instead of the property itself.
 */
public class BooleanPropertyTypeAdapter extends BasePrimitivePropertyTypeAdapter<Boolean, BooleanProperty> {

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
        super(crashOnNullValue, delegate, BooleanProperty::get, SimpleBooleanProperty::new, SimpleBooleanProperty::new);
    }
}
