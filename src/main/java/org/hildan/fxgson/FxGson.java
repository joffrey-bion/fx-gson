package org.hildan.fxgson;

import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import org.hildan.fxgson.creators.ObservableListCreator;
import org.hildan.fxgson.creators.ObservableMapCreator;
import org.hildan.fxgson.creators.ObservableSetCreator;
import org.jetbrains.annotations.NotNull;

import com.google.gson.GsonBuilder;

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
     * Creates a {@link GsonBuilder} pre-configured to handle JavaFX core classes. This is a shorthand for
     * {@code new FxGsonBuilder().builder()}.
     *
     *
     * @return a pre-configured {@link GsonBuilder}
     */
    @NotNull
    public static GsonBuilder coreBuilder() {
        return new FxGsonBuilder().builder();
    }

    /**
     * Creates a {@link GsonBuilder} pre-configured to handle JavaFX properties, as well as other useful classes such as
     * {@link Font} and {@link Color}. This is a shorthand for {@code new FxGsonBuilder().withExtras().builder()}.
     *
     * @return a pre-configured {@link GsonBuilder}
     */
    @NotNull
    public static GsonBuilder fullBuilder() {
        return new FxGsonBuilder().withExtras().builder();
    }

    /**
     * Adds the core JavaFX classes adapters to an existing {@link GsonBuilder}. This is useful if you don't control the
     * instantiation of the {@link GsonBuilder}.
     *
     * @param builder
     *         the builder to add the type adapters to
     *
     * @return the given builder, so that it can be used in a Builder pattern
     */
    @NotNull
    public static GsonBuilder addCoreSerializers(@NotNull GsonBuilder builder) {
        return addCoreSerializers(builder, true);
    }

    /**
     * Adds the core JavaFX classes adapters to an existing {@link GsonBuilder}. This is useful if you don't control the
     * instantiation of the {@link GsonBuilder}.
     *
     * @param builder
     *         the builder to add the type adapters to
     * @param crashOnNullPrimitives
     *
     * @return the given builder, so that it can be used in a Builder pattern
     */
    @NotNull
    public static GsonBuilder addCoreSerializers(@NotNull GsonBuilder builder, boolean crashOnNullPrimitives) {
        // serialization of nulls is necessary to have properties with null values deserialized properly
        builder.serializeNulls()
                .registerTypeAdapter(ObservableList.class, new ObservableListCreator())
                .registerTypeAdapter(ObservableSet.class, new ObservableSetCreator())
                .registerTypeAdapter(ObservableMap.class, new ObservableMapCreator())
                .registerTypeAdapterFactory(new JavaFxPropertyTypeAdapterFactory(crashOnNullPrimitives));
        return builder;
    }

    /**
     * Adds the extra JavaFX classes adapters to an existing {@link GsonBuilder}. This is useful if you don't control
     * the instantiation of the {@link GsonBuilder}.
     *
     * @param builder
     *         the builder to add the type adapters to
     *
     * @return the given builder, so that it can be used in a Builder pattern
     */
    @NotNull
    public static GsonBuilder addExtraSerializers(@NotNull GsonBuilder builder) {
        return builder.registerTypeAdapterFactory(new JavaFxExtraTypeAdapterFactory());
    }
}
