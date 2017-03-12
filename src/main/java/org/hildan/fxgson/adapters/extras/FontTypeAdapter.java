package org.hildan.fxgson.adapters.extras;

import java.io.IOException;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * A basic {@link TypeAdapter} for JavaFX {@link Font}. It serializes a font as a string "family,style,size". For
 * instance:
 * <ul>
 * <li>The standard Arial font in 11pt is serialized as {@code "Arial,Regular,11.0"}</li>
 * <li>The Sans Serif font in 14pt bold gives {@code "SansSerif,Bold,14.0"}</li>
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
        String style = value.getStyle();
        double size = value.getSize();
        out.value(family + "," + style + "," + size);
    }

    @Override
    public Font read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        String value = in.nextString();
        String path = in.getPath();

        String[] components = splitComponents(value, path);
        String family = components[0];
        String style = components[1];
        String sizeStr = components[2];

        FontWeight weight = extractWeight(style);
        FontPosture posture = extractPosture(style);
        double size = extractSize(sizeStr, path);

        return Font.font(family, weight, posture, size);
    }

    private static String[] splitComponents(String value, String path) {
        String[] font = value.split(",");
        if (font.length < 3) {
            throw InvalidFontException.missingComponent(value, path);
        }
        return font;
    }

    private static FontWeight extractWeight(String style) {
        for (String styleWord : style.split("\\s")) {
            FontWeight weight = FontWeight.findByName(styleWord);
            if (weight != null && weight != FontWeight.NORMAL) {
                return weight;
            }
        }
        return FontWeight.NORMAL;
    }

    private static FontPosture extractPosture(String style) {
        for (String styleWord : style.split("\\s")) {
            FontPosture posture = FontPosture.findByName(styleWord);
            if (posture != null && posture != FontPosture.REGULAR) {
                return posture;
            }
        }
        return FontPosture.REGULAR;
    }

    private static double extractSize(String size, String path) {
        try {
            return Double.parseDouble(size);
        } catch (NumberFormatException e) {
            throw InvalidFontException.invalidSize(size, path, e);
        }
    }

    public static class InvalidFontException extends RuntimeException {

        InvalidFontException(String message) {
            super(message);
        }

        InvalidFontException(String message, Throwable cause) {
            super(message, cause);
        }

        static InvalidFontException missingComponent(String value, String path) {
            return new InvalidFontException("Missing component in the font at path " + path + ", got '" + value + "'");
        }

        static InvalidFontException invalidSize(String value, String path, Throwable cause) {
            return new InvalidFontException("Invalid size for the font at path " + path + ", got '" + value + "'",
                    cause);
        }
    }
}
