package org.hildan.fxgson.adapters;

import java.io.IOException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * A custom type adapter for JavaFX {@link IntegerProperty}.
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
