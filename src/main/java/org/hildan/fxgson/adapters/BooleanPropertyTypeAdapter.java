package org.hildan.fxgson.adapters;

import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * A custom type adapter for JavaFX {@link BooleanProperty}.
 */
public class BooleanPropertyTypeAdapter extends PrimitiveTypeAdapter<BooleanProperty> {

    public BooleanPropertyTypeAdapter(boolean crashOnNullValue) {
        super(crashOnNullValue);
    }

    @Override
    public void write(JsonWriter out, BooleanProperty value) throws IOException {
        out.value(value.get());
    }

    @Override
    protected BooleanProperty createDefaultValue() {
        return new SimpleBooleanProperty();
    }

    @Override
    public BooleanProperty readNonNullValue(JsonReader in) throws IOException {
        return new SimpleBooleanProperty(in.nextBoolean());
    }
}
