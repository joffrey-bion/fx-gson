package org.hildan.fxgson.adapters;

import java.io.IOException;

import javafx.scene.paint.Color;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * A custom type adapter for JavaFX {@link Color}.
 */
public class ColorTypeAdapter extends TypeAdapter<Color> {

    @Override
    public void write(JsonWriter out, Color value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value("#" + value.toString().substring(2));
    }

    @Override
    public Color read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            return null;
        }
        return Color.web(in.nextString());
    }
}
