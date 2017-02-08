package org.hildan.fxgson.adapters.primitives;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

import com.google.gson.TypeAdapter;

/**
 * An implementation of {@link PrimitivePropertyTypeAdapter} for JavaFX {@link FloatProperty}. It serializes the float
 * value of the property instead of the property itself.
 */
public class FloatPropertyTypeAdapter extends BasePrimitivePropertyTypeAdapter<Float, FloatProperty> {

    /**
     * Creates a new FloatPropertyTypeAdapter.
     *
     * @param crashOnNullValue
     *         if true, this adapter will throw {@link NullPrimitiveException} when reading a null value. If false, this
     *         adapter will create a new simple property using the default constructor instead.
     * @param delegate
     *         a delegate adapter to use for the inner value of the property
     */
    public FloatPropertyTypeAdapter(boolean crashOnNullValue, TypeAdapter<Float> delegate) {
        super(crashOnNullValue, delegate, FloatProperty::get, SimpleFloatProperty::new, SimpleFloatProperty::new);
    }
}
