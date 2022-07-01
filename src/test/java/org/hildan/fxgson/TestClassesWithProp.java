package org.hildan.fxgson;

import java.util.Objects;
import java.util.function.BiFunction;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import org.hildan.fxgson.TestClassesSimple.CustomObject;

class TestClassesWithProp {

    private static boolean propEquals(Property<?> p1, Property<?> p2) {
        return propEquals(p1, p2, Objects::equals);
    }

    private static <T, U> boolean propEquals(Property<? extends T> p1, Property<? extends U> p2,
                                             BiFunction<? super T, ? super U, Boolean> equals) {
        if (p1 == p2) {
            return true;
        }
        if (p1 == null || p2 == null) {
            return false;
        }
        T v1 = p1.getValue();
        U v2 = p2.getValue();
        return p1.getName().equals(p2.getName()) && equals.apply(v1, v2);
    }

    static class WithBooleanProp {
        BooleanProperty prop = new SimpleBooleanProperty();

        WithBooleanProp() {
        }

        WithBooleanProp(boolean value) {
            this.prop = new SimpleBooleanProperty(value);
        }

        WithBooleanProp(String name, boolean value) {
            this.prop = new SimpleBooleanProperty(this, name, value);
        }

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

        WithIntegerProp() {
        }

        WithIntegerProp(int value) {
            this.prop = new SimpleIntegerProperty(value);
        }

        WithIntegerProp(String name, int value) {
            this.prop = new SimpleIntegerProperty(this, name, value);
        }

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

        WithLongProp() {
        }

        WithLongProp(long value) {
            this.prop = new SimpleLongProperty(value);
        }

        WithLongProp(String name, long value) {
            this.prop = new SimpleLongProperty(this, name, value);
        }

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

        WithFloatProp() {
        }

        WithFloatProp(float value) {
            this.prop = new SimpleFloatProperty(value);
        }

        WithFloatProp(String name, float value) {
            this.prop = new SimpleFloatProperty(this, name, value);
        }

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

        WithDoubleProp() {
        }

        WithDoubleProp(double value) {
            this.prop = new SimpleDoubleProperty(value);
        }

        WithDoubleProp(String name, double value) {
            this.prop = new SimpleDoubleProperty(this, name, value);
        }

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

        WithStringProp() {
        }

        WithStringProp(String value) {
            this.prop = new SimpleStringProperty(value);
        }

        WithStringProp(String name, String value) {
            this.prop = new SimpleStringProperty(this, name, value);
        }

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

    static class WithObjectProp {
        ObjectProperty<CustomObject> prop = new SimpleObjectProperty<>();

        WithObjectProp() {
        }

        WithObjectProp(CustomObject value) {
            this.prop = new SimpleObjectProperty<>(value);
        }

        WithObjectProp(String name, CustomObject value) {
            this.prop = new SimpleObjectProperty<>(this, name, value);
        }

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

    static class WithGenericProp {
        Property<CustomObject> prop = new SimpleObjectProperty<>();

        WithGenericProp() {
        }

        WithGenericProp(CustomObject value) {
            this.prop = new SimpleObjectProperty<>(value);
        }

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

    static class WithPropOfGenericProp {
        Property<Property<CustomObject>> prop = new SimpleObjectProperty<>(new SimpleObjectProperty<>());

        WithPropOfGenericProp() {
        }

        WithPropOfGenericProp(CustomObject value) {
            this.prop = new SimpleObjectProperty<>(new SimpleObjectProperty<>(value));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithPropOfGenericProp that = (WithPropOfGenericProp) o;
            return propEquals(prop, that.prop, TestClassesWithProp::propEquals);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prop);
        }
    }

    static class WithFontProp {
        Property<Font> prop = new SimpleObjectProperty<>();

        WithFontProp() {
        }

        WithFontProp(Font value) {
            this.prop = new SimpleObjectProperty<>(value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithFontProp that = (WithFontProp) o;
            return propEquals(prop, that.prop);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prop);
        }
    }

    static class WithColorProp {
        Property<Color> prop = new SimpleObjectProperty<>();

        WithColorProp() {
        }

        WithColorProp(Color value) {
            this.prop = new SimpleObjectProperty<>(value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithColorProp that = (WithColorProp) o;
            return propEquals(prop, that.prop);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prop);
        }
    }

    static class WithObsList {
        ObservableList<CustomObject> list;

        WithObsList() {
        }

        WithObsList(ObservableList<CustomObject> value) {
            this.list = value;
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
            return Objects.equals(list, that.list);
        }

        @Override
        public int hashCode() {
            return Objects.hash(list);
        }
    }

    static class WithObsSet {
        ObservableSet<CustomObject> set;

        WithObsSet() {
        }

        WithObsSet(ObservableSet<CustomObject> value) {
            this.set = value;
        }

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

        WithObsMapInt() {
        }

        WithObsMapInt(ObservableMap<Integer, CustomObject> value) {
            this.map = value;
        }

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

        WithObsMapStr() {
        }

        WithObsMapStr(ObservableMap<String, CustomObject> value) {
            this.map = value;
        }

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

    static class WithListProp {
        ListProperty<CustomObject> prop = new SimpleListProperty<>();

        WithListProp() {
        }

        WithListProp(ObservableList<CustomObject> value) {
            this.prop = new SimpleListProperty<>(value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithListProp that = (WithListProp) o;
            return propEquals(prop, that.prop);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prop);
        }
    }

    static class WithSetProp {
        SetProperty<CustomObject> prop = new SimpleSetProperty<>();

        WithSetProp() {
        }

        WithSetProp(ObservableSet<CustomObject> value) {
            this.prop = new SimpleSetProperty<>(value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithSetProp that = (WithSetProp) o;
            return propEquals(prop, that.prop);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prop);
        }
    }

    static class WithMapIntProp {
        MapProperty<Integer, CustomObject> prop = new SimpleMapProperty<>();

        WithMapIntProp() {
        }

        WithMapIntProp(ObservableMap<Integer, CustomObject> value) {
            this.prop = new SimpleMapProperty<>(value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithMapIntProp that = (WithMapIntProp) o;
            return propEquals(prop, that.prop);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prop);
        }
    }

    static class WithMapStrProp {
        MapProperty<String, CustomObject> prop = new SimpleMapProperty<>();

        WithMapStrProp() {
        }

        WithMapStrProp(ObservableMap<String, CustomObject> value) {
            this.prop = new SimpleMapProperty<>(value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithMapStrProp that = (WithMapStrProp) o;
            return propEquals(prop, that.prop);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prop);
        }
    }
}
