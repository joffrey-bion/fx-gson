package org.hildan.fxgson;

import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import com.google.gson.GsonBuilder;
import org.hildan.fxgson.creators.ObservableListCreator;
import org.hildan.fxgson.creators.ObservableMapCreator;
import org.hildan.fxgson.creators.ObservableSetCreator;
import org.jetbrains.annotations.NotNull;

/**
 * Creates pre-configured {@link GsonBuilder}s that handle nicely JavaFX-specific classes.
 * <p>
 * What we mean by nicely here is that the serialized version of the properties is simply the value that was inside
 * each property, not the actual property internal fields, so that it is human-readable and still useful.
 * <p>
 * In the doc of this class, we distinguish the core JavaFX classes from the extra classes:
 * <ul>
 *     <li>Core JavaFX classes: the {@link Property} class and its subclasses, the {@link ObservableList},
 *     {@link ObservableMap}, {@link ObservableSet}.</li>
 *     <li>Extra JavaFX classes: {@link Color}, {@link Font}</li>
 * </ul>
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
        return builder().registerTypeAdapterFactory(new JavaFxExtraTypeAdapterFactory());
    }

    @NotNull
    public static GsonBuilder addPropertySerializers(@NotNull GsonBuilder builder) {
        // serialization of nulls is necessary to have properties with null values deserialized properly
        builder.serializeNulls()
               .registerTypeAdapter(ObservableList.class, new ObservableListCreator())
               .registerTypeAdapter(ObservableSet.class, new ObservableSetCreator())
               .registerTypeAdapter(ObservableMap.class, new ObservableMapCreator())
               .registerTypeAdapterFactory(new JavaFxPropertyTypeAdapterFactory());
        return builder;
    }
}
