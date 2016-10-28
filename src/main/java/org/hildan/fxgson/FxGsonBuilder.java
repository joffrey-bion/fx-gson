package org.hildan.fxgson;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hildan.fxgson.creators.ObservableListCreator;
import org.hildan.fxgson.creators.ObservableMapCreator;
import org.hildan.fxgson.creators.ObservableSetCreator;
import org.hildan.fxgson.factories.JavaFxExtraTypeAdapterFactory;
import org.hildan.fxgson.factories.JavaFxPropertyTypeAdapterFactory;

public class FxGsonBuilder {

    private final GsonBuilder builder;

    private boolean crashOnNullPrimitives = true;

    private boolean includeExtras = false;

    public FxGsonBuilder() {
        this(new GsonBuilder());
    }

    public FxGsonBuilder(GsonBuilder sourcebuilder) {
        this.builder = sourcebuilder;
    }

    public GsonBuilder builder() {
        // serialization of nulls is necessary to have properties with null values deserialized properly
        builder.serializeNulls()
               .registerTypeAdapter(ObservableList.class, new ObservableListCreator())
               .registerTypeAdapter(ObservableSet.class, new ObservableSetCreator())
               .registerTypeAdapter(ObservableMap.class, new ObservableMapCreator())
               .registerTypeAdapterFactory(new JavaFxPropertyTypeAdapterFactory(crashOnNullPrimitives));
        if (includeExtras) {
            builder.registerTypeAdapterFactory(new JavaFxExtraTypeAdapterFactory());
        }
        return builder;
    }

    public Gson create() {
        return builder().create();
    }

    public FxGsonBuilder acceptNullPrimitives() {
        crashOnNullPrimitives = false;
        return this;
    }

    public FxGsonBuilder withExtras() {
        includeExtras = true;
        return this;
    }
}
