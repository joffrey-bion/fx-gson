package org.hildan.fxgson.factories;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SetProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.hildan.fxgson.adapters.ListPropertyTypeAdapter;
import org.hildan.fxgson.adapters.MapPropertyTypeAdapter;
import org.hildan.fxgson.adapters.ObjectPropertyTypeAdapter;
import org.hildan.fxgson.adapters.SetPropertyTypeAdapter;
import org.hildan.fxgson.adapters.StringPropertyTypeAdapter;
import org.hildan.fxgson.adapters.primitives.BooleanPropertyTypeAdapter;
import org.hildan.fxgson.adapters.primitives.DoublePropertyTypeAdapter;
import org.hildan.fxgson.adapters.primitives.FloatPropertyTypeAdapter;
import org.hildan.fxgson.adapters.primitives.IntegerPropertyTypeAdapter;
import org.hildan.fxgson.adapters.primitives.LongPropertyTypeAdapter;

/**
 * A {@link TypeAdapterFactory} for JavaFX {@link Property} types.
 */
public class JavaFxPropertyTypeAdapterFactory implements TypeAdapterFactory {

    private final boolean crashOnNullPrimitives;

    /**
     * Creates a new JavaFxPropertyTypeAdapterFactory. This default factory crashes when asked to deserialize a null
     * JSON value for a primitive type property such as {@link IntegerProperty} or {@link BooleanProperty}.
     *
     * @see #JavaFxPropertyTypeAdapterFactory(boolean)
     */
    public JavaFxPropertyTypeAdapterFactory() {
        this(true);
    }

    /**
     * Creates a new JavaFxPropertyTypeAdapterFactory.
     *
     * @param crashOnNullPrimitives
     *         if true, null values in the JSON are not accepted for primitive properties like {@link IntegerProperty}
     *         or {@link BooleanProperty} during deserialization. If false, a property is created with a default value
     *         instead of crashing.
     */
    public JavaFxPropertyTypeAdapterFactory(boolean crashOnNullPrimitives) {
        this.crashOnNullPrimitives = crashOnNullPrimitives;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> clazz = type.getRawType();

        // this factory only handles JavaFX property types
        if (!Property.class.isAssignableFrom(clazz)) {
            return null;
        }

        // simple property types

        if (BooleanProperty.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new BooleanPropertyTypeAdapter(crashOnNullPrimitives);
        }
        if (IntegerProperty.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new IntegerPropertyTypeAdapter(crashOnNullPrimitives);
        }
        if (LongProperty.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new LongPropertyTypeAdapter(crashOnNullPrimitives);
        }
        if (FloatProperty.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new FloatPropertyTypeAdapter(crashOnNullPrimitives);
        }
        if (DoubleProperty.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new DoublePropertyTypeAdapter(crashOnNullPrimitives);
        }
        if (StringProperty.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new StringPropertyTypeAdapter();
        }

        // collection property types

        if (ListProperty.class.isAssignableFrom(clazz)) {
            TypeAdapter<?> delegate = gson.getAdapter(TypeHelper.withRawType(type, ObservableList.class));
            return new ListPropertyTypeAdapter(delegate);
        }
        if (SetProperty.class.isAssignableFrom(clazz)) {
            TypeAdapter<?> delegate = gson.getAdapter(TypeHelper.withRawType(type, ObservableSet.class));
            return new SetPropertyTypeAdapter(delegate);
        }
        if (MapProperty.class.isAssignableFrom(clazz)) {
            TypeAdapter<?> delegate = gson.getAdapter(TypeHelper.withRawType(type, ObservableMap.class));
            return new MapPropertyTypeAdapter(delegate);
        }

        // generic Property<?> type

        Type[] typeParams = ((ParameterizedType) type.getType()).getActualTypeArguments();
        Type param = typeParams[0];
        // null factory skipPast because the nested type argument might also be a Property
        TypeAdapter<?> delegate = gson.getAdapter(TypeToken.get(param));
        return (TypeAdapter<T>) new ObjectPropertyTypeAdapter<>(delegate);
    }
}
