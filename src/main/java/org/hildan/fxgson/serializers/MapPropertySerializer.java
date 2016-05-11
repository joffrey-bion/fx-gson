package org.hildan.fxgson.serializers;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A custom serializer/deserializer for JavaFX {@link MapProperty}.
 */
public class MapPropertySerializer implements JsonSerializer<MapProperty<?, ?>>, JsonDeserializer<MapProperty<?, ?>> {

    private static final String NULL_PLACEHOLDER = "null";

    @Override
    public JsonElement serialize(MapProperty<?, ?> property, Type type, JsonSerializationContext context) {
        Object value = property.getValue();
        // FIXME this is the only workaround I could think of so far for this Gson bug:
        // https://github.com/google/gson/issues/171
        // Ongoing stackoverflow to tackle this problem
        // http://stackoverflow.com/questions/35260490/gson-custom-deserializer-not-called-for-null
        return context.serialize(value != null ? value : NULL_PLACEHOLDER);
    }

    @Override
    public MapProperty<?, ?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        if (isNullPlaceholder(json)) {
            return new SimpleMapProperty<>(null);
        }
        Type[] typeArguments = ((ParameterizedType) typeOfT).getActualTypeArguments();
        if (typeArguments.length < 2) {
            throw new IllegalArgumentException("Deserializing a MapProperty with less than 2 type parameters");
        }
        Type keyType = typeArguments[0];
        Type valueType = typeArguments[1];
        Type listType = TypeHelper.newParametrizedType(ObservableMap.class, keyType, valueType);
        ObservableMap<?, ?> list = context.deserialize(json, listType);
        return new SimpleMapProperty<>(list);
    }

    private static boolean isNullPlaceholder(JsonElement json) {
        try {
            return json.isJsonPrimitive() && NULL_PLACEHOLDER.equals(json.getAsString());
        } catch (ClassCastException e) {
            return false; // not a string, so definitely not the placeholder
        }
    }
}
