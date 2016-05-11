package org.hildan.fxgson.adapters;

import java.io.IOException;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * A custom type adapter for JavaFX {@link LongProperty}.
 */
public class LongPropertyTypeAdapter extends TypeAdapter<LongProperty> {

    @Override
    public void write(JsonWriter out, LongProperty value) throws IOException {
        out.value(value.get());
    }

    @Override
    public LongProperty read(JsonReader in) throws IOException {
        return new SimpleLongProperty(in.nextLong());
    }
}
