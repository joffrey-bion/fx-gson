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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.hildan.fxgson.adapters.properties.ListPropertyTypeAdapter;
import org.hildan.fxgson.adapters.properties.MapPropertyTypeAdapter;
import org.hildan.fxgson.adapters.properties.NullPropertyException;
import org.hildan.fxgson.adapters.properties.ObjectPropertyTypeAdapter;
import org.hildan.fxgson.adapters.properties.SetPropertyTypeAdapter;
import org.hildan.fxgson.adapters.properties.StringPropertyTypeAdapter;
import org.hildan.fxgson.adapters.properties.primitives.BooleanPropertyTypeAdapter;
import org.hildan.fxgson.adapters.properties.primitives.DoublePropertyTypeAdapter;
import org.hildan.fxgson.adapters.properties.primitives.FloatPropertyTypeAdapter;
import org.hildan.fxgson.adapters.properties.primitives.IntegerPropertyTypeAdapter;
import org.hildan.fxgson.adapters.properties.primitives.LongPropertyTypeAdapter;
import org.hildan.fxgson.adapters.properties.primitives.NullPrimitiveException;

/**
 * A {@link TypeAdapterFactory} for all JavaFX {@link Property} types. It serializes the value of a property instead of
 * the property object itself. It handles all primitive property types, the collection properties {@link ListProperty},
 * {@link SetProperty}, and {@link MapProperty}, as well as the base {@link Property} itself.
 * <p>
 * During deserialization, the type adapters of this factory instantiate the "Simple" implementation of the relevant
 * {@link Property} subclass. For instance, to deserialize an {@link IntegerProperty}, the {@link
 * IntegerPropertyTypeAdapter} will create a {@link SimpleIntegerProperty}. If a JSON contains a null value for a
 * primitive property, a {@link NullPrimitiveException} is thrown when trying to deserialize it, unless this factory is
 * configured otherwise using the constructor {@link #JavaFxPropertyTypeAdapterFactory(boolean, boolean)}.
 * <p>
 * During serialization, properties are expected to be non null by default, unless configured otherwise using the
 * constructor {@link #JavaFxPropertyTypeAdapterFactory(boolean, boolean)}.
 */
public class JavaFxPropertyTypeAdapterFactory implements TypeAdapterFactory {

    private final boolean strictProperties;

    private final boolean strictPrimitives;

    /**
     * Creates a new JavaFxPropertyTypeAdapterFactory. This default factory forbids null properties and null values for
     * primitive properties.
     *
     * @see #JavaFxPropertyTypeAdapterFactory(boolean, boolean)
     */
    public JavaFxPropertyTypeAdapterFactory() {
        this(true, true);
    }

    /**
     * Creates a new JavaFxPropertyTypeAdapterFactory.
     *
     * @param throwOnNullProperties
     *         if true, this adapter will throw {@link NullPropertyException} when given a null {@link Property} to
     *         serialize
     * @param throwOnNullPrimitives
     *         if true, null values in the JSON are not accepted for primitive properties like {@link IntegerProperty}
     *         or {@link BooleanProperty} during deserialization, and cause a {@link NullPrimitiveException} to be
     */
    public JavaFxPropertyTypeAdapterFactory(boolean throwOnNullProperties, boolean throwOnNullPrimitives) {
        this.strictProperties = throwOnNullProperties;
        this.strictPrimitives = throwOnNullPrimitives;
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
            return (TypeAdapter<T>) new BooleanPropertyTypeAdapter(gson.getAdapter(boolean.class), strictProperties,
                    strictPrimitives);
        }
        if (IntegerProperty.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new IntegerPropertyTypeAdapter(gson.getAdapter(int.class), strictProperties,
                    strictPrimitives);
        }
        if (LongProperty.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new LongPropertyTypeAdapter(gson.getAdapter(long.class), strictProperties,
                    strictPrimitives);
        }
        if (FloatProperty.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new FloatPropertyTypeAdapter(gson.getAdapter(float.class), strictProperties,
                    strictPrimitives);
        }
        if (DoubleProperty.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new DoublePropertyTypeAdapter(gson.getAdapter(double.class), strictProperties,
                    strictPrimitives);
        }
        if (StringProperty.class.isAssignableFrom(clazz)) {
            return (TypeAdapter<T>) new StringPropertyTypeAdapter(gson.getAdapter(String.class), strictProperties);
        }

        // collection property types

        if (ListProperty.class.isAssignableFrom(clazz)) {
            TypeAdapter<?> delegate = gson.getAdapter(TypeHelper.withRawType(type, ObservableList.class));
            return new ListPropertyTypeAdapter(delegate, strictProperties);
        }
        if (SetProperty.class.isAssignableFrom(clazz)) {
            TypeAdapter<?> delegate = gson.getAdapter(TypeHelper.withRawType(type, ObservableSet.class));
            return new SetPropertyTypeAdapter(delegate, strictProperties);
        }
        if (MapProperty.class.isAssignableFrom(clazz)) {
            TypeAdapter<?> delegate = gson.getAdapter(TypeHelper.withRawType(type, ObservableMap.class));
            return new MapPropertyTypeAdapter(delegate, strictProperties);
        }

        // generic Property<?> type

        Type[] typeParams = ((ParameterizedType) type.getType()).getActualTypeArguments();
        Type param = typeParams[0];
        // null factory skipPast because the nested type argument might also be a Property
        TypeAdapter<?> delegate = gson.getAdapter(TypeToken.get(param));
        return (TypeAdapter<T>) new ObjectPropertyTypeAdapter<>(delegate, strictProperties);
    }
}
