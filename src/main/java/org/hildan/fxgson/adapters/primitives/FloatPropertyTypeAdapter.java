package org.hildan.fxgson.adapters.primitives;

import java.io.IOException;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * An implementation of {@link PrimitiveTypeAdapter} for JavaFX {@link FloatProperty}. It serializes the float value of
 * the property instead of the property itself.
 */
public class FloatPropertyTypeAdapter extends PrimitiveTypeAdapter<FloatProperty> {

    public FloatPropertyTypeAdapter(boolean crashOnNullValue) {
        super(crashOnNullValue);
    }

    @Override
    public void write(JsonWriter out, FloatProperty value) throws IOException {
        out.value(value.get());
    }

    @Override
    protected FloatProperty createDefaultValue() {
        return new SimpleFloatProperty();
    }

    @Override
    public FloatProperty readNonNullValue(JsonReader in) throws IOException {
        return new SimpleFloatProperty((float) in.nextDouble());
    }
}
