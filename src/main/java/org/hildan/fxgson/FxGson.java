package org.hildan.fxgson;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import com.google.gson.GsonBuilder;
import org.hildan.fxgson.creators.ObservableListCreator;
import org.hildan.fxgson.serializers.BooleanPropertySerializer;
import org.hildan.fxgson.serializers.ColorSerializer;
import org.hildan.fxgson.serializers.DoublePropertySerializer;
import org.hildan.fxgson.serializers.FloatPropertySerializer;
import org.hildan.fxgson.serializers.FontSerializer;
import org.hildan.fxgson.serializers.IntegerPropertySerializer;
import org.hildan.fxgson.serializers.LongPropertySerializer;
import org.hildan.fxgson.serializers.PropertySerializer;
import org.hildan.fxgson.serializers.StringPropertySerializer;
import org.jetbrains.annotations.NotNull;

/**
 * Creates a pre-configured {@link GsonBuilder} that handles properly JavaFX properties.
 * <p>
 * Basically, this class creates a {@link GsonBuilder} initialized with custom serializers for JavaFX types. These
 * custom serializers make sure the serialized version of the property simply contains the value of the property, so
 * that it is human-readable and still useful.
 */
public class FxGson {

    /**
     * Creates a {@link GsonBuilder} pre-configured to handle JavaFX properties.
     *
     * @return a pre-configured {@link GsonBuilder}
     */
    @NotNull
    public static GsonBuilder builder() {
        return addPropertySerializers(new GsonBuilder());
    }

    /**
     * Creates a {@link GsonBuilder} pre-configured to handle JavaFX properties, as well as other useful classes such as
     * {@link Font} and {@link Color}.
     *
     * @return a pre-configured {@link GsonBuilder}
     */
    @NotNull
    public static GsonBuilder fullBuilder() {
        return builder().registerTypeAdapter(Color.class, new ColorSerializer())
                        .registerTypeAdapter(Font.class, new FontSerializer());
    }

    @NotNull
    public static GsonBuilder addPropertySerializers(@NotNull GsonBuilder builder) {
        // serialization of nulls is necessary to have properties with null values deserialized properly
        builder.serializeNulls()
               .registerTypeAdapter(ObservableList.class, new ObservableListCreator())
               .registerTypeAdapter(StringProperty.class, new StringPropertySerializer())
               .registerTypeAdapter(DoubleProperty.class, new DoublePropertySerializer())
               .registerTypeAdapter(FloatProperty.class, new FloatPropertySerializer())
               .registerTypeAdapter(BooleanProperty.class, new BooleanPropertySerializer())
               .registerTypeAdapter(IntegerProperty.class, new IntegerPropertySerializer())
               .registerTypeAdapter(LongProperty.class, new LongPropertySerializer())
               .registerTypeAdapter(Property.class, new PropertySerializer());
        return builder;
    }
}
