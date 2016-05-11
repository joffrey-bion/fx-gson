package org.hildan.fxgson;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.hildan.fxgson.adapters.ColorTypeAdapter;
import org.hildan.fxgson.adapters.FontTypeAdapter;

/**
 * A {@link TypeAdapterFactory} for JavaFX built-in types.
 */
public class JavaFxExtraTypeAdapterFactory implements TypeAdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> clazz = type.getRawType();

        if (Color.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new ColorTypeAdapter();
        }

        if (Font.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new FontTypeAdapter();
        }

        return null;
    }
}
