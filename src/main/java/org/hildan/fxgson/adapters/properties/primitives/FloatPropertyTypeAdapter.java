package org.hildan.fxgson.adapters.properties.primitives;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

import com.google.gson.TypeAdapter;

/**
 * An implementation of {@link PrimitivePropertyTypeAdapter} for JavaFX {@link FloatProperty}. It serializes the float
 * value of the property instead of the property itself.
 */
public class FloatPropertyTypeAdapter extends PrimitivePropertyTypeAdapter<Float, FloatProperty> {

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
        super(crashOnNullValue, delegate);
    }

    @Override
    protected Float extractPrimitiveValue(FloatProperty property) {
        return property.get();
    }

    @Override
    protected FloatProperty createDefaultProperty() {
        return new SimpleFloatProperty();
    }

    @Override
    protected FloatProperty wrapNonNullPrimitiveValue(Float deserializedValue) {
        return new SimpleFloatProperty(deserializedValue);
    }
}
