package org.hildan.fxgson.adapters;

import java.io.IOException;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * A custom type adapter for JavaFX {@link DoubleProperty}.
 */
public class DoublePropertyTypeAdapter extends TypeAdapter<DoubleProperty> {

    @Override
    public void write(JsonWriter out, DoubleProperty value) throws IOException {
        out.value(value.get());
    }

    @Override
    public DoubleProperty read(JsonReader in) throws IOException {
        return new SimpleDoubleProperty(in.nextDouble());
    }
}
