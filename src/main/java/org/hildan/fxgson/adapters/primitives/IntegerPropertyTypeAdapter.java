package org.hildan.fxgson.adapters.primitives;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import com.google.gson.TypeAdapter;

/**
 * An implementation of {@link PrimitiveTypeAdapter} for JavaFX {@link IntegerProperty}. It serializes the int value of
 * the property instead of the property itself.
 */
public class IntegerPropertyTypeAdapter extends BasePrimitivePropertyTypeAdapter<Integer, IntegerProperty> {

    /**
     * Creates a new IntegerPropertyTypeAdapter.
     *
     * @param crashOnNullValue
     *         if true, this adapter will throw {@link NullPrimitiveException} when reading a null value. If false, this
     *         adapter will create a new simple property using the default constructor instead.
     * @param delegate
     *         a delegate adapter to use for the inner value of the property
     */
    public IntegerPropertyTypeAdapter(boolean crashOnNullValue, TypeAdapter<Integer> delegate) {
        super(crashOnNullValue, delegate, IntegerProperty::get, SimpleIntegerProperty::new, SimpleIntegerProperty::new);
    }
}
