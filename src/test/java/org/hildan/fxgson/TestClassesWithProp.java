package org.hildan.fxgson;

import java.util.Objects;

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

import org.hildan.fxgson.TestClassesSimple.CustomObject;

class TestClassesWithProp {

    private static boolean propEquals(Property<?> p1, Property<?> p2) {
        if (p1 == null) {
            return p2 == null;
        }
        return p2 != null && Objects.equals(p1.getValue(), p2.getValue());
    }

    static class WithBooleanProp {
        BooleanProperty prop = new SimpleBooleanProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithBooleanProp that = (WithBooleanProp) o;
            return propEquals(prop, that.prop);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prop);
        }
    }

    static class WithIntegerProp {
        IntegerProperty prop = new SimpleIntegerProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithIntegerProp that = (WithIntegerProp) o;
            return propEquals(prop, that.prop);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prop);
        }
    }

    static class WithLongProp {
        LongProperty prop = new SimpleLongProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithLongProp that = (WithLongProp) o;
            return propEquals(prop, that.prop);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prop);
        }
    }

    static class WithFloatProp {
        FloatProperty prop = new SimpleFloatProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithFloatProp that = (WithFloatProp) o;
            return propEquals(prop, that.prop);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prop);
        }
    }

    static class WithDoubleProp {
        DoubleProperty prop = new SimpleDoubleProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithDoubleProp that = (WithDoubleProp) o;
            return propEquals(prop, that.prop);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prop);
        }
    }

    static class WithStringProp {
        StringProperty prop = new SimpleStringProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithStringProp that = (WithStringProp) o;
            return propEquals(prop, that.prop);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prop);
        }
    }

    static class WithGenericProp {
        Property<CustomObject> prop = new SimpleObjectProperty<>();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithGenericProp that = (WithGenericProp) o;
            return propEquals(prop, that.prop);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prop);
        }
    }

    static class WithObsList {
        ObservableList<CustomObject> list;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithObsList that = (WithObsList) o;
            return Objects.equals(list, that.list);
        }

        @Override
        public int hashCode() {
            return Objects.hash(list);
        }
    }

    static class WithObsSet {
        ObservableSet<CustomObject> set;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithObsSet that = (WithObsSet) o;
            return Objects.equals(set, that.set);
        }

        @Override
        public int hashCode() {
            return Objects.hash(set);
        }
    }

    static class WithObsMapInt {
        ObservableMap<Integer, CustomObject> map;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithObsMapInt that = (WithObsMapInt) o;
            return Objects.equals(map, that.map);
        }

        @Override
        public int hashCode() {
            return Objects.hash(map);
        }
    }

    static class WithObsMapStr {
        ObservableMap<String, CustomObject> map;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithObsMapStr that = (WithObsMapStr) o;
            return Objects.equals(map, that.map);
        }

        @Override
        public int hashCode() {
            return Objects.hash(map);
        }
    }
}
