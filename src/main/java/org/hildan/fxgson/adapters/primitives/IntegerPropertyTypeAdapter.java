package org.hildan.fxgson.adapters.primitives;

import java.io.IOException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * An implementation of {@link PrimitiveTypeAdapter} for JavaFX {@link IntegerProperty}. It serializes the int value of
 * the property instead of the property itself.
 */
public class IntegerPropertyTypeAdapter extends PrimitiveTypeAdapter<IntegerProperty> {

    public IntegerPropertyTypeAdapter(boolean crashOnNullValue) {
        super(crashOnNullValue);
    }

    @Override
    public void write(JsonWriter out, IntegerProperty value) throws IOException {
        out.value(value.get());
    }

    @Override
    protected IntegerProperty createDefaultValue() {
        return new SimpleIntegerProperty();
    }

    @Override
    public IntegerProperty readNonNullValue(JsonReader in) throws IOException {
        return new SimpleIntegerProperty(in.nextInt());
    }
}
