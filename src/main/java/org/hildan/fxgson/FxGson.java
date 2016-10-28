package org.hildan.fxgson;

import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Creates pre-configured {@link GsonBuilder}s that handle nicely JavaFX-specific classes.
 * <p>
 * What we mean by nicely here is that the serialized version of the properties is simply the value that was inside
 * each property, not the actual property internal fields, so that it is human-readable and still useful.
 * <p>
 * In the doc of this class, we distinguish the core JavaFX classes from the extra classes:
 * <ul>
 * <li>Core JavaFX classes: the {@link Property} class and its subclasses, the {@link ObservableList},
 * {@link ObservableMap}, {@link ObservableSet}.</li>
 * <li>Extra JavaFX classes: {@link Color}, {@link Font}</li>
 * </ul>
 */
public class FxGson {

    /**
     * Creates a {@link Gson} pre-configured to handle JavaFX core classes. This is a shorthand for
     * {@code new FxGsonBuilder().create()}.
     *
     * @return a new pre-configured {@link Gson}
     */
    @NotNull
    public static Gson create() {
        return new FxGsonBuilder().create();
    }

    /**
     * Creates a {@link Gson} pre-configured to handle JavaFX properties, as well as other useful classes such as
     * {@link Font} and {@link Color}. This is a shorthand for {@code new FxGsonBuilder().withExtras().create()}.
     *
     * @return a new pre-configured {@link Gson}
     */
    @NotNull
    public static Gson createWithExtras() {
        return new FxGsonBuilder().withExtras().create();
    }

    /**
     * Creates a {@link GsonBuilder} pre-configured to handle JavaFX core classes. This is a shorthand for
     * {@code new FxGsonBuilder().builder()}.
     *
     * @return a new pre-configured {@link GsonBuilder}
     */
    @NotNull
    public static GsonBuilder coreBuilder() {
        return new FxGsonBuilder().builder();
    }

    /**
     * Creates a {@link GsonBuilder} pre-configured to handle JavaFX properties, as well as other useful classes such as
     * {@link Font} and {@link Color}. This is a shorthand for {@code new FxGsonBuilder().withExtras().builder()}.
     *
     * @return a new pre-configured {@link GsonBuilder}
     */
    @NotNull
    public static GsonBuilder fullBuilder() {
        return new FxGsonBuilder().withExtras().builder();
    }

    /**
     * Adds the core JavaFX classes adapters to an existing {@link GsonBuilder}. This is useful if you don't control the
     * instantiation of the {@link GsonBuilder}. This is a shorthand for {@code new FxGsonBuilder(builder).builder()}.
     *
     * @param builder
     *         the builder to add the type adapters to
     *
     * @return the given builder, so that it can be used in a Builder pattern
     */
    @NotNull
    public static GsonBuilder addFxSupport(@NotNull GsonBuilder builder) {
        return new FxGsonBuilder(builder).builder();
    }
}
