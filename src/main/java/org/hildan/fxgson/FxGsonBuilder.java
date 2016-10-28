package org.hildan.fxgson;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import org.hildan.fxgson.creators.ObservableListCreator;
import org.hildan.fxgson.creators.ObservableMapCreator;
import org.hildan.fxgson.creators.ObservableSetCreator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FxGsonBuilder {

    private final GsonBuilder builder = new GsonBuilder();

    private boolean crashOnNullPrimitives = true;

    private boolean includeExtras = false;

    public GsonBuilder builder() {
        builder.serializeNulls()
                .registerTypeAdapter(ObservableList.class, new ObservableListCreator())
                .registerTypeAdapter(ObservableSet.class, new ObservableSetCreator())
                .registerTypeAdapter(ObservableMap.class, new ObservableMapCreator());
        builder.registerTypeAdapterFactory(new JavaFxPropertyTypeAdapterFactory(crashOnNullPrimitives));
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
