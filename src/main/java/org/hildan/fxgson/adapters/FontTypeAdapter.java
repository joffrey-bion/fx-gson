package org.hildan.fxgson.adapters;

import java.io.IOException;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * A custom type adapter for JavaFX {@link Font}.
 */
public class FontTypeAdapter extends TypeAdapter<Font> {

    @Override
    public void write(JsonWriter out, Font value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        String family = value.getFamily();
        String weight = value.getStyle();
        double size = value.getSize();
        out.value(family + "," + weight + "," + size);
    }

    @Override
    public Font read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        String[] font = in.nextString().split(",");
        String family = font[0];
        FontWeight weight = FontWeight.findByName(font[1]);
        double size = Double.parseDouble(font[2]);
        return Font.font(family, weight, size);
    }
}
