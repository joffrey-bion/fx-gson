package org.hildan.fxgson;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

class TestClassesSimple {

    static class WithBoolean {
        boolean value;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithBoolean that = (WithBoolean) o;
            return value == that.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    static class WithInteger {
        int value;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithInteger that = (WithInteger) o;
            return value == that.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    static class WithLong {
        long value;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithLong that = (WithLong) o;
            return value == that.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    static class WithFloat {
        float value;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithFloat that = (WithFloat) o;
            return value == that.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    static class WithDouble {
        double value;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithDouble that = (WithDouble) o;
            return value == that.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    static class WithString {
        String value;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithString that = (WithString) o;
            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    static class WithCustomObject {
        CustomObject value;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithCustomObject that = (WithCustomObject) o;
            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    static class WithList {
        List<CustomObject> list;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithList that = (WithList) o;
            return Objects.equals(list, that.list);
        }

        @Override
        public int hashCode() {
            return Objects.hash(list);
        }
    }

    static class WithSet {
        Set<CustomObject> set;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithSet that = (WithSet) o;
            return Objects.equals(set, that.set);
        }

        @Override
        public int hashCode() {
            return Objects.hash(set);
        }
    }

    static class WithMapInt {
        Map<Integer, CustomObject> map;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithMapInt that = (WithMapInt) o;
            return Objects.equals(map, that.map);
        }

        @Override
        public int hashCode() {
            return Objects.hash(map);
        }
    }

    static class WithMapStr {
        Map<String, CustomObject> map;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithMapStr that = (WithMapStr) o;
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
