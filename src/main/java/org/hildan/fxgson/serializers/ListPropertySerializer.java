package org.hildan.fxgson.serializers;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A custom serializer/deserializer for JavaFX {@link ListProperty}.
 */
public class ListPropertySerializer implements JsonSerializer<ListProperty<?>>, JsonDeserializer<ListProperty<?>> {

    private static final String NULL_PLACEHOLDER = "null";

    @Override
    public JsonElement serialize(ListProperty<?> property, Type type, JsonSerializationContext context) {
        Object value = property.getValue();
        // FIXME this is the only workaround I could think of so far for this Gson bug:
        // https://github.com/google/gson/issues/171
        // Ongoing stackoverflow to tackle this problem
        // http://stackoverflow.com/questions/35260490/gson-custom-deserializer-not-called-for-null
        return context.serialize(value != null ? value : NULL_PLACEHOLDER);
    }

    @Override
    public ListProperty<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        if (isNullPlaceholder(json)) {
            return new SimpleListProperty<>(null);
        }
        Type[] typeArguments = ((ParameterizedType) typeOfT).getActualTypeArguments();
        if (typeArguments.length < 1) {
            throw new IllegalArgumentException("Deserializing a ListProperty without type parameter");
        }
        Type elementType = typeArguments[0];
        Type listType = TypeHelper.newParametrizedType(ObservableList.class, elementType);
        ObservableList<?> list = context.deserialize(json, listType);
        return new SimpleListProperty<>(list);
    }

    private static boolean isNullPlaceholder(JsonElement json) {
        try {
            return json.isJsonPrimitive() && NULL_PLACEHOLDER.equals(json.getAsString());
        } catch (ClassCastException e) {
            return false; // not a string, so definitely not the placeholder
        }
    }
}
