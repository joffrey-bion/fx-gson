package org.hildan.fxgson;

import java.util.Objects;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

import org.hildan.fxgson.TestClassesSimple.CustomObject;
import org.hildan.fxgson.TestClassesWithProp.WithObsList;
import org.hildan.fxgson.adapters.properties.ListPropertyTypeAdapter;
import org.jetbrains.annotations.NotNull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

class TestClassesCustom {

    static class CustomListProperty extends SimpleListProperty<CustomObject> {
        CustomListProperty() {
        }

        CustomListProperty(ObservableList<CustomObject> initialValue) {
            super(initialValue);
        }
    }

    static class WithCustomListProp {
        CustomListProperty prop = new CustomListProperty();

        WithCustomListProp() {
        }

        WithCustomListProp(CustomListProperty value) {
            this.prop = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithObsList that = (WithObsList) o;
            return Objects.equals(prop, that.list);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prop);
        }
    }

    public static class CustomFactory implements TypeAdapterFactory {

        @SuppressWarnings("unchecked")
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (CustomListProperty.class.equals(type.getRawType())) {
                TypeToken<ObservableList<CustomObject>> obsListType = new TypeToken<ObservableList<CustomObject>>() {};
                TypeAdapter<ObservableList<CustomObject>> delegate = gson.getAdapter(obsListType);
                return (TypeAdapter<T>) new CustomListPropertyAdapter(delegate, false);
            }
            return null;
        }
    }

    private static class CustomListPropertyAdapter extends ListPropertyTypeAdapter<CustomObject> {

        CustomListPropertyAdapter(TypeAdapter<ObservableList<CustomObject>> delegate, boolean throwOnNullProperty) {
            super(delegate, throwOnNullProperty);
        }

        @NotNull
        @Override
        protected ListProperty<CustomObject> createProperty(ObservableList<CustomObject> deserializedValue) {
            return new CustomListProperty(deserializedValue);
        }
    }
}
