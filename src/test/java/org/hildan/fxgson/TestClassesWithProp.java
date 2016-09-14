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
        BooleanProperty bool = new SimpleBooleanProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithBooleanProp that = (WithBooleanProp) o;
            return propEquals(bool, that.bool);
        }

        @Override
        public int hashCode() {
            return Objects.hash(bool);
        }
    }

    static class WithIntegerProp {
        IntegerProperty num = new SimpleIntegerProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithIntegerProp that = (WithIntegerProp) o;
            return propEquals(num, that.num);
        }

        @Override
        public int hashCode() {
            return Objects.hash(num);
        }
    }

    static class WithLongProp {
        LongProperty num = new SimpleLongProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithLongProp that = (WithLongProp) o;
            return propEquals(num, that.num);
        }

        @Override
        public int hashCode() {
            return Objects.hash(num);
        }
    }

    static class WithFloatProp {
        FloatProperty num = new SimpleFloatProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithFloatProp that = (WithFloatProp) o;
            return propEquals(num, that.num);
        }

        @Override
        public int hashCode() {
            return Objects.hash(num);
        }
    }

    static class WithDoubleProp {
        DoubleProperty num = new SimpleDoubleProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithDoubleProp that = (WithDoubleProp) o;
            return propEquals(num, that.num);
        }

        @Override
        public int hashCode() {
            return Objects.hash(num);
        }
    }

    static class WithStringProp {
        StringProperty str = new SimpleStringProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithStringProp that = (WithStringProp) o;
            return propEquals(str, that.str);
        }

        @Override
        public int hashCode() {
            return Objects.hash(str);
        }
    }

    static class WithGenericProp {
        Property<CustomObject> obj = new SimpleObjectProperty<>();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithGenericProp that = (WithGenericProp) o;
            return propEquals(obj, that.obj);
        }

        @Override
        public int hashCode() {
            return Objects.hash(obj);
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
