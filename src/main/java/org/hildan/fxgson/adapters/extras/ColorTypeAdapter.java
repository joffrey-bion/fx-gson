package org.hildan.fxgson.adapters.extras;

import java.io.IOException;

import javafx.scene.paint.Color;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * A basic {@link TypeAdapter} for JavaFX {@link Color}. It serializes a color as its RGBA hexadecimal string
 * representation preceded by #. Supports null values.
 */
public class ColorTypeAdapter extends TypeAdapter<Color> {

    @Override
    public void write(JsonWriter out, Color value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        int red = (int) Math.round(value.getRed() * 255.0);
        int green = (int) Math.round(value.getGreen() * 255.0);
        int blue = (int) Math.round(value.getBlue() * 255.0);
        int opacity = (int) Math.round(value.getOpacity() * 255.0);
        String colorStr = String.format("#%02x%02x%02x%02x", red, green, blue, opacity);
        out.value(colorStr);
    }

    @Override
    public Color read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        return Color.web(in.nextString());
    }
}
