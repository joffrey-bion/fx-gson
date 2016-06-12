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

class TestClasses {

    private static boolean propEquals(Property<?> p1, Property<?> p2) {
        if (p1 == null) {
            return p2 == null;
        }
        return p2 != null && Objects.equals(p1.getValue(), p2.getValue());
    }

    static class BaseWithBoolean {
        BooleanProperty bool = new SimpleBooleanProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BaseWithBoolean that = (BaseWithBoolean) o;
            return propEquals(bool, that.bool);
        }

        @Override
        public int hashCode() {
            return Objects.hash(bool);
        }
    }

    static class BaseWithInteger {
        IntegerProperty num = new SimpleIntegerProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BaseWithInteger that = (BaseWithInteger) o;
            return propEquals(num, that.num);
        }

        @Override
        public int hashCode() {
            return Objects.hash(num);
        }
    }

    static class BaseWithLong {
        LongProperty num = new SimpleLongProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BaseWithLong that = (BaseWithLong) o;
            return propEquals(num, that.num);
        }

        @Override
        public int hashCode() {
            return Objects.hash(num);
        }
    }

    static class BaseWithFloat {
        FloatProperty num = new SimpleFloatProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BaseWithFloat that = (BaseWithFloat) o;
            return propEquals(num, that.num);
        }

        @Override
        public int hashCode() {
            return Objects.hash(num);
        }
    }

    static class BaseWithDouble {
        DoubleProperty num = new SimpleDoubleProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BaseWithDouble that = (BaseWithDouble) o;
            return propEquals(num, that.num);
        }

        @Override
        public int hashCode() {
            return Objects.hash(num);
        }
    }

    static class BaseWithString {
        StringProperty str = new SimpleStringProperty();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BaseWithString that = (BaseWithString) o;
            return propEquals(str, that.str);
        }

        @Override
        public int hashCode() {
            return Objects.hash(str);
        }
    }

    static class BaseWithComplexObject {
        Property<CustomObject> obj = new SimpleObjectProperty<>();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BaseWithComplexObject that = (BaseWithComplexObject) o;
            return propEquals(obj, that.obj);
        }

        @Override
        public int hashCode() {
            return Objects.hash(obj);
        }
    }

    static class BaseWithList {
        ObservableList<CustomObject> list;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BaseWithList that = (BaseWithList) o;
            return Objects.equals(list, that.list);
        }

        @Override
        public int hashCode() {
            return Objects.hash(list);
        }
    }

    static class BaseWithSet {
        ObservableSet<CustomObject> set;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BaseWithSet that = (BaseWithSet) o;
            return Objects.equals(set, that.set);
        }

        @Override
        public int hashCode() {
            return Objects.hash(set);
        }
    }

    static class BaseWithMapInt {
        ObservableMap<Integer, CustomObject> map;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BaseWithMapInt that = (BaseWithMapInt) o;
            return Objects.equals(map, that.map);
        }

        @Override
        public int hashCode() {
            return Objects.hash(map);
        }
    }

    static class BaseWithMapStr {
        ObservableMap<String, CustomObject> map;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BaseWithMapStr that = (BaseWithMapStr) o;
            return Objects.equals(map, that.map);
        }

        @Override
        public int hashCode() {
            return Objects.hash(map);
        }
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
            CustomObject that = (CustomObject) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
