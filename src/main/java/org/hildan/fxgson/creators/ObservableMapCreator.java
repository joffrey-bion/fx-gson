package org.hildan.fxgson.creators;

import java.lang.reflect.Type;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import com.google.gson.InstanceCreator;

/**
 * An {@link InstanceCreator} for observable maps using {@link FXCollections}.
 */
public class ObservableMapCreator implements InstanceCreator<ObservableMap<?, ?>> {

    public ObservableMap<?, ?> createInstance(Type type) {
        // No need to use a parametrized map since the actual instance will have the raw type anyway.
        return FXCollections.observableHashMap();
    }
}
