package org.hildan.fxgson.adapters.properties.primitives;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;

import com.google.gson.TypeAdapter;
import org.hildan.fxgson.adapters.properties.NullPropertyException;

/**
 * An implementation of {@link PrimitivePropertyTypeAdapter} for JavaFX {@link IntegerProperty}. It serializes the int
 * value of the property instead of the property itself.
 */
public class IntegerPropertyTypeAdapter extends PrimitivePropertyTypeAdapter<Integer, IntegerProperty> {

    /**
     * Creates a new IntegerPropertyTypeAdapter.
     *
     * @param delegate
     *         a delegate adapter to use for the inner value of the property
     * @param throwOnNullProperty
     *         if true, this adapter will throw {@link NullPropertyException} when given a null {@link Property} to
     *         serialize
     * @param crashOnNullValue
     *         if true, this adapter will throw {@link NullPrimitiveException} when reading a null value. If false, this
     *         adapter will create a new simple property using the default constructor instead.
     */
    public IntegerPropertyTypeAdapter(TypeAdapter<Integer> delegate, boolean throwOnNullProperty,
                                      boolean crashOnNullValue) {
        super(delegate, throwOnNullProperty, crashOnNullValue);
    }

    @Override
    protected Integer extractPrimitiveValue(IntegerProperty property) {
        return property.getValue();
    }

    @Override
    protected IntegerProperty createDefaultProperty() {
        return new SimpleIntegerProperty();
    }

    @Override
    protected IntegerProperty wrapNonNullPrimitiveValue(Integer deserializedValue) {
        return new SimpleIntegerProperty(deserializedValue);
    }
}
