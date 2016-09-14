package org.hildan.fxgson;

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
import org.hildan.fxgson.adapters.BooleanPropertyTypeAdapter;
import org.hildan.fxgson.adapters.DoublePropertyTypeAdapter;
import org.hildan.fxgson.adapters.FloatPropertyTypeAdapter;
import org.hildan.fxgson.adapters.IntegerPropertyTypeAdapter;
import org.hildan.fxgson.adapters.ListPropertyTypeAdapter;
import org.hildan.fxgson.adapters.LongPropertyTypeAdapter;
import org.hildan.fxgson.adapters.MapPropertyTypeAdapter;
import org.hildan.fxgson.adapters.ObjectPropertyTypeAdapter;
import org.hildan.fxgson.adapters.SetPropertyTypeAdapter;
import org.hildan.fxgson.adapters.StringPropertyTypeAdapter;

import static org.hildan.fxgson.TypeHelper.withRawType;

/**
 * A {@link TypeAdapterFactory} for JavaFX {@link Property} types.
 */
public class JavaFxPropertyTypeAdapterFactory implements TypeAdapterFactory {

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
            return (TypeAdapter<T>) new BooleanPropertyTypeAdapter();
        }
        if (IntegerProperty.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new IntegerPropertyTypeAdapter();
        }
        if (LongProperty.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new LongPropertyTypeAdapter();
        }
        if (FloatProperty.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new FloatPropertyTypeAdapter();
        }
        if (DoubleProperty.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new DoublePropertyTypeAdapter();
        }
        if (StringProperty.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new StringPropertyTypeAdapter();
        }

        // collection property types

        if (ListProperty.class.isAssignableFrom(clazz)) {
            TypeAdapter<?> delegate = gson.getAdapter(withRawType(type, ObservableList.class));
            return new ListPropertyTypeAdapter(delegate);
        }
        if (SetProperty.class.isAssignableFrom(clazz)) {
            TypeAdapter<?> delegate = gson.getAdapter(withRawType(type, ObservableSet.class));
            return new SetPropertyTypeAdapter(delegate);
        }
        if (MapProperty.class.isAssignableFrom(clazz)) {
            TypeAdapter<?> delegate = gson.getAdapter(withRawType(type, ObservableMap.class));
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
