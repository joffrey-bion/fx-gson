package org.hildan.fxgson.adapters;

import java.io.IOException;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * A custom type adapter for JavaFX {@link FloatProperty}.
 */
public class FloatPropertyTypeAdapter extends TypeAdapter<FloatProperty> {

    @Override
    public void write(JsonWriter out, FloatProperty value) throws IOException {
        out.value(value.get());
    }

    @Override
    public FloatProperty read(JsonReader in) throws IOException {
        return new SimpleFloatProperty((float) in.nextDouble());
    }
}
