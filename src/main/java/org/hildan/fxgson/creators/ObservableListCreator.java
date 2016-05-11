package org.hildan.fxgson.creators;

import java.lang.reflect.Type;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.google.gson.InstanceCreator;

/**
 * An {@link InstanceCreator} for observable lists using {@link FXCollections}.
 */
public class ObservableListCreator implements InstanceCreator<ObservableList<?>> {

    public ObservableList<?> createInstance(Type type) {
        // No need to use a parametrized list since the actual instance will have the raw type anyway.
        return FXCollections.observableArrayList();
    }
}
