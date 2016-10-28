package org.hildan.fxgson.adapters.extras;

import java.io.IOException;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * A basic {@link TypeAdapter} for JavaFX {@link Font}. It serializes a font as a string "family,style,size". For
 * instance:
 * <ul>
 *     <li>The standard Arial font in 11pt is serialized as {@code "Arial,Regular,11.0"}</li>
 *     <li>The Sans Serif font in 14pt bold gives {@code "SansSerif,Bold,14.0"}</li>
 * </ul>
 * This adapter supports null values.
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
