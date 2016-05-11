package org.hildan.fxgson.adapters;

import java.io.IOException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * A custom type adapter for JavaFX {@link IntegerProperty}.
 */
public class IntegerPropertyTypeAdapter extends TypeAdapter<IntegerProperty> {

    @Override
    public void write(JsonWriter out, IntegerProperty value) throws IOException {
        out.value(value.get());
    }

    @Override
    public IntegerProperty read(JsonReader in) throws IOException {
        return new SimpleIntegerProperty(in.nextInt());
    }
}
