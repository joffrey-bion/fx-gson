package org.hildan.fxgson.adapters.primitives;

import java.io.IOException;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * An implementation of {@link PrimitiveTypeAdapter} for JavaFX {@link DoubleProperty}. It serializes the double value
 * of the property instead of the property itself.
 */
public class DoublePropertyTypeAdapter extends PrimitiveTypeAdapter<DoubleProperty> {

    public DoublePropertyTypeAdapter(boolean crashOnNullValue) {
        super(crashOnNullValue);
    }

    @Override
    public void write(JsonWriter out, DoubleProperty value) throws IOException {
        out.value(value.get());
    }

    @Override
    protected DoubleProperty createDefaultValue() {
        return new SimpleDoubleProperty();
    }

    @Override
    public DoubleProperty readNonNullValue(JsonReader in) throws IOException {
        return new SimpleDoubleProperty(in.nextDouble());
    }
}
