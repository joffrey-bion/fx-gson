package org.hildan.fxgson.creators;

import java.lang.reflect.Type;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import com.google.gson.InstanceCreator;

/**
 * An {@link InstanceCreator} for observable sets using {@link FXCollections}.
 */
public class ObservableSetCreator implements InstanceCreator<ObservableSet<?>> {

    public ObservableSet<?> createInstance(Type type) {
        // No need to use a parametrized set since the actual instance will have the raw type anyway.
        return FXCollections.observableSet();
    }
}
