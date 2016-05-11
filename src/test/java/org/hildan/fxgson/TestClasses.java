package org.hildan.fxgson;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

class TestClasses {

    static class BaseWithBoolean {
        BooleanProperty bool = new SimpleBooleanProperty();
    }

    static class BaseWithInteger {
        IntegerProperty num = new SimpleIntegerProperty();
    }

    static class BaseWithLong {
        LongProperty num = new SimpleLongProperty();
    }

    static class BaseWithFloat {
        FloatProperty num = new SimpleFloatProperty();
    }

    static class BaseWithDouble {
        DoubleProperty num = new SimpleDoubleProperty();
    }

    static class BaseWithString {
        StringProperty str = new SimpleStringProperty();
    }

    static class BaseWithComplexObject {
        Property<CustomObject> obj = new SimpleObjectProperty<>();
    }

    static class BaseWithList {
        ObservableList<CustomObject> list;
    }

    static class BaseWithSet {
        ObservableSet<CustomObject> list;
    }

    static class BaseWithMapInt {
        ObservableMap<Integer, CustomObject> map;
    }

    static class BaseWithMapStr {
        ObservableMap<String, CustomObject> map;
    }

    static class CustomObject {

        String name;

        CustomObject(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CustomObject customObject = (CustomObject)o;
            return name != null ? name.equals(customObject.name) : customObject.name == null;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }
}
