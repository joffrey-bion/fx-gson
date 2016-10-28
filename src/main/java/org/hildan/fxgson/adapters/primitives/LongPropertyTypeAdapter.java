package org.hildan.fxgson.adapters.primitives;

import java.io.IOException;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * An implementation of {@link PrimitiveTypeAdapter} for JavaFX {@link LongProperty}. It serializes the long value of
 * the property instead of the property itself.
 */
public class LongPropertyTypeAdapter extends PrimitiveTypeAdapter<LongProperty> {

    public LongPropertyTypeAdapter(boolean crashOnNullValue) {
        super(crashOnNullValue);
    }

    @Override
    public void write(JsonWriter out, LongProperty value) throws IOException {
        out.value(value.get());
    }

    @Override
    protected LongProperty createDefaultValue() {
        return new SimpleLongProperty();
    }

    @Override
    public LongProperty readNonNullValue(JsonReader in) throws IOException {
        return new SimpleLongProperty(in.nextLong());
    }
}
