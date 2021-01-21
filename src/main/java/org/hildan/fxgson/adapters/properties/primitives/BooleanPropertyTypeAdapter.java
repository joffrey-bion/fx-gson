package org.hildan.fxgson.adapters.properties.primitives;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;

import com.google.gson.TypeAdapter;
import org.hildan.fxgson.adapters.properties.NullPropertyException;
import org.jetbrains.annotations.NotNull;

/**
 * An implementation of {@link PrimitivePropertyTypeAdapter} for JavaFX {@link BooleanProperty}. It serializes the
 * boolean value of the property instead of the property itself.
 */
public class BooleanPropertyTypeAdapter extends PrimitivePropertyTypeAdapter<Boolean, BooleanProperty> {

    /**
     * Creates a new BooleanPropertyTypeAdapter.
     *
     * @param delegate
     *         a delegate adapter to use for the inner value of the property
     * @param throwOnNullProperty
     *         if true, this adapter will throw {@link NullPropertyException} when given a null {@link Property} to
     *         serialize
     * @param crashOnNullValue
     *         if true, this adapter will throw {@link NullPrimitiveException} when reading a null value. If false, this
     *         adapter will call {@link #createDefaultProperty()} instead.
     */
    public BooleanPropertyTypeAdapter(TypeAdapter<Boolean> delegate, boolean throwOnNullProperty,
                                      boolean crashOnNullValue) {
        super(delegate, throwOnNullProperty, crashOnNullValue);
    }

    @NotNull
    @Override
    protected Boolean extractPrimitiveValue(BooleanProperty property) {
        return property.get();
    }

    @NotNull
    @Override
    protected BooleanProperty createDefaultProperty() {
        return new SimpleBooleanProperty();
    }

    @NotNull
    @Override
    protected BooleanProperty wrapNonNullPrimitiveValue(@NotNull Boolean deserializedValue) {
        return new SimpleBooleanProperty(deserializedValue);
    }
}
