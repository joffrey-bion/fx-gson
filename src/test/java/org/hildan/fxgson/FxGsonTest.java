package org.hildan.fxgson;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hildan.fxgson.TestClasses.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FxGsonTest {

    private static Gson coreGson;

    private static Gson extraGson;

    @BeforeClass
    public static void createGson() {
        coreGson = FxGson.coreBuilder().create();
        extraGson = FxGson.fullBuilder().create();
    }

    /**
     * Tests the serialization/deserialization of the given object with each of the provided {@link Gson}s.
     * <p>
     * This method checks that the object is the same after a serialization-deserialization cycle.
     * <p>
     * If an expected JSON is provided, this method checks if the serialized object gives the expected JSON.
     *
     * @param objClass
     *         the class of the object to test
     * @param objToTest
     *         the object to test
     * @param expectedJson
     *         the expected JSON representing the serialized objToTest, or null if this method should not test the
     *         serialized representation of the object
     * @param gsons
     *         the {@link Gson}s to use for serialization/deserialization tests
     * @param <B>
     *         the type of the object to test
     */
    private static <B> void testObject(Class<B> objClass, B objToTest, String expectedJson, Gson... gsons) {
        for (Gson gson : gsons) {
            assertEquals("Incorrect JSON generated", expectedJson, gson.toJson(objToTest));

            B deserialized = gson.fromJson(expectedJson, objClass);
            assertEquals("Incorrect deserialized value", objToTest, deserialized);
        }
    }

    /**
     * Tests the serialization/deserialization of an inner value of an object (for the given value) with each of the
     * provided {@link Gson}s.
     * <p>
     * This method checks that the value is the same after a serialization-deserialization cycle.
     * If an expected JSON is provided, this method also checks if the serialized object gives the expected JSON.
     *
     * @param baseClass
     *         the class of object to test
     * @param valueToTest
     *         the value to test
     * @param expectedJson
     *         the expected JSON representing the serialized object (with the value inside), or null if this method
     *         should not test the serialized representation of the object
     * @param getter
     *         a function to access the field within an object of baseClass
     * @param setter
     *         a function to set the field within an object of baseClass
     * @param gsons
     *         the {@link Gson}s to use for serialization/deserialization tests
     * @param <B>
     *         the type of the object containing the field to test
     * @param <V>
     *         the type of the value to test inside the object
     */
    private static <B, V> void testValue(Class<B> baseClass, V valueToTest, String expectedJson, Function<B, V> getter,
                                         BiConsumer<B, V> setter, Gson... gsons) {
        try {
            B baseObj = baseClass.newInstance();
            setter.accept(baseObj, valueToTest);

            for (Gson gson : gsons) {
                if (expectedJson != null) {
                    assertEquals("Incorrect JSON generated", expectedJson, gson.toJson(baseObj));
                } else {
                    expectedJson = gson.toJson(baseObj);
                }

                B deserialized = gson.fromJson(expectedJson, baseClass);
                assertEquals("Incorrect deserialized value", valueToTest, getter.apply(deserialized));
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Cannot run the test on class '" + baseClass.getSimpleName() + "'", e);
        }
    }

    /**
     * Tests the serialization/deserialization of a field inside an object for the given value. Uses both the core
     * {@link GsonBuilder} and the one with extras.
     * <p>
     * This method checks that the value is the same after a serialization-deserialization cycle.
     * If an expected JSON is provided, this method also checks if the serialized object gives the expected JSON.
     *
     * @param baseClass
     *         the class of object to test
     * @param valueToTest
     *         the value that will be injected inside an object and whose (de)serialization should be checked
     * @param expectedJson
     *         the expected JSON representing the serialized object (with the value inside), or null if this method
     *         should not test the serialized representation of the object
     * @param getter
     *         a function to access the current value within an object of baseClass
     * @param setter
     *         a function to set a value within an object of baseClass
     * @param <B>
     *         the type of parent object to test
     * @param <V>
     *         the type of the value to test inside the object
     */
    private <B, V> void testValue(Class<B> baseClass, V valueToTest, String expectedJson, Function<B, V> getter,
                                  BiConsumer<B, V> setter) {
        testValue(baseClass, valueToTest, expectedJson, getter, setter, coreGson, extraGson);
    }

    /**
     * Tests the serialization/deserialization of the given value of a {@link Property} inside an object. The property
     * is checked not to be null after deserialization. Uses both the core {@link GsonBuilder} and the one with extras.
     *
     * @param baseClass
     *         the class of object to test
     * @param valueToTest
     *         the value that will be injected inside the object's property and whose (de)serialization should be
     *         checked
     * @param expectedJson
     *         the expected JSON representing the serialized object (with the property inside), or null if this method
     *         should not test the serialized representation of the object
     * @param getProperty
     *         a function to access the tested object's property
     * @param gson
     *         the {@link Gson} to use for serialization/deserialization
     * @param <B>
     *         the type of parent object to test
     * @param <V>
     *         the type of the value to test inside the object
     */
    private static <B, V> void testProperty(Class<B> baseClass, V valueToTest, String expectedJson,
                                            Function<B, Property<V>> getProperty, Gson gson) {
        Function<B, V> valueGetter = obj -> {
            Property<V> prop = getProperty.apply(obj);
            assertNotNull("The property itself should not be null (only its content may)", prop);
            return prop.getValue();
        };

        BiConsumer<B, V> valueSetter = (obj, value) -> getProperty.apply(obj).setValue(value);

        testValue(baseClass, valueToTest, expectedJson, valueGetter, valueSetter, gson);
    }

    /**
     * Tests the serialization/deserialization of the given value of a {@link Property} inside an object. The property
     * is checked not to be null after deserialization. Uses both the core {@link GsonBuilder} and the one with extras.
     *
     * @param baseClass
     *         the class of object to test
     * @param valueToTest
     *         the value that will be injected inside the object's property and whose (de)serialization should be
     *         checked
     * @param expectedJson
     *         the expected JSON representing the serialized object (with the property inside), or null if this method
     *         should not test the serialized representation of the object
     * @param getProperty
     *         a function to access the tested object's property
     * @param <B>
     *         the type of parent object to test
     * @param <V>
     *         the type of the value to test inside the object
     */
    private <B, V> void testProperty(Class<B> baseClass, V valueToTest, String expectedJson,
                                     Function<B, Property<V>> getProperty) {
        testProperty(baseClass, valueToTest, expectedJson, getProperty, coreGson);
        testProperty(baseClass, valueToTest, expectedJson, getProperty, extraGson);
    }

    @Test
    public void testBooleanProperty() {
        testProperty(BaseWithBoolean.class, true, "{\"bool\":true}", o -> o.bool);
        testProperty(BaseWithBoolean.class, false, "{\"bool\":false}", o -> o.bool);
    }

    @Test
    public void testIntegerProperty() {
        testProperty(BaseWithInteger.class, 0, "{\"num\":0}", o -> o.num);
        testProperty(BaseWithInteger.class, 5, "{\"num\":5}", o -> o.num);
        testProperty(BaseWithInteger.class, -3, "{\"num\":-3}", o -> o.num);
    }

    @Test
    public void testLongProperty() {
        testProperty(BaseWithLong.class, 0L, "{\"num\":0}", o -> o.num);
        testProperty(BaseWithLong.class, 5L, "{\"num\":5}", o -> o.num);
        testProperty(BaseWithLong.class, -3L, "{\"num\":-3}", o -> o.num);
    }

    @Test
    public void testFloatProperty() {
        testProperty(BaseWithFloat.class, 0f, "{\"num\":0.0}", o -> o.num);
        testProperty(BaseWithFloat.class, 2.5f, "{\"num\":2.5}", o -> o.num);
        testProperty(BaseWithFloat.class, -3.5f, "{\"num\":-3.5}", o -> o.num);
    }

    @Test
    public void testDoubleProperty() {
        testProperty(BaseWithDouble.class, 0d, "{\"num\":0.0}", o -> o.num);
        testProperty(BaseWithDouble.class, 2.5d, "{\"num\":2.5}", o -> o.num);
        testProperty(BaseWithDouble.class, -3.5d, "{\"num\":-3.5}", o -> o.num);
    }

    @Test
    public void testStringProperty() {
        testProperty(BaseWithString.class, "myValue", "{\"str\":\"myValue\"}", o -> o.str);
        testProperty(BaseWithString.class, "", "{\"str\":\"\"}", o -> o.str);
        testProperty(BaseWithString.class, null, "{\"str\":null}", o -> o.str);
    }

    @Test
    public void testObjectProperty() {
        CustomObject obj = new CustomObject("myValue");
        testProperty(BaseWithComplexObject.class, obj, "{\"obj\":{\"name\":\"myValue\"}}", o -> o.obj);
        testProperty(BaseWithComplexObject.class, null, "{\"obj\":null}", o -> o.obj);
    }

    @Test
    public void testList() {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        ObservableList<CustomObject> listEmpty = FXCollections.observableArrayList();
        ObservableList<CustomObject> listOne = FXCollections.observableArrayList(one);
        ObservableList<CustomObject> listTwo = FXCollections.observableArrayList(one, two);

        Function<BaseWithList, ObservableList<CustomObject>> getter = obj -> obj.list;
        BiConsumer<BaseWithList, ObservableList<CustomObject>> setter = (obj, list) -> obj.list = list;

        testValue(BaseWithList.class, null, "{\"list\":null}", getter, setter);
        testValue(BaseWithList.class, listEmpty, "{\"list\":[]}", getter, setter);
        testValue(BaseWithList.class, listOne, "{\"list\":[{\"name\":\"myObj1\"}]}", getter, setter);
        testValue(BaseWithList.class, listTwo, "{\"list\":[{\"name\":\"myObj1\"},{\"name\":\"myObj2\"}]}", getter,
                setter);
    }

    @Test
    public void testSet() {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        ObservableSet<CustomObject> setEmpty = FXCollections.emptyObservableSet();
        ObservableSet<CustomObject> setOne = FXCollections.observableSet(one);
        ObservableSet<CustomObject> setTwo = FXCollections.observableSet(one, two);

        Function<BaseWithSet, ObservableSet<CustomObject>> getter = obj -> obj.set;
        BiConsumer<BaseWithSet, ObservableSet<CustomObject>> setter = (obj, set) -> obj.set = set;

        testValue(BaseWithSet.class, null, "{\"set\":null}", getter, setter);
        testValue(BaseWithSet.class, setEmpty, "{\"set\":[]}", getter, setter);
        testValue(BaseWithSet.class, setOne, "{\"set\":[{\"name\":\"myObj1\"}]}", getter, setter);
        // do not check a particular JSON because the order is non-deterministic
        testValue(BaseWithSet.class, setTwo, null, getter, setter);
    }

    @Test
    public void testMapStr() {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        ObservableMap<String, CustomObject> mapEmpty = FXCollections.emptyObservableMap();
        ObservableMap<String, CustomObject> mapOne = FXCollections.observableHashMap();
        mapOne.put("key1", one);
        ObservableMap<String, CustomObject> mapTwo = FXCollections.observableHashMap();
        mapTwo.put("key1", one);
        mapTwo.put("key2", two);

        Function<BaseWithMapStr, ObservableMap<String, CustomObject>> getter = obj -> obj.map;
        BiConsumer<BaseWithMapStr, ObservableMap<String, CustomObject>> setter = (obj, map) -> obj.map = map;

        testValue(BaseWithMapStr.class, null, "{\"map\":null}", getter, setter);
        testValue(BaseWithMapStr.class, mapEmpty, "{\"map\":{}}", getter, setter);
        testValue(BaseWithMapStr.class, mapOne, "{\"map\":{\"key1\":{\"name\":\"myObj1\"}}}", getter, setter);
        testValue(BaseWithMapStr.class, mapTwo,
                "{\"map\":{\"key1\":{\"name\":\"myObj1\"},\"key2\":{\"name\":\"myObj2\"}}}", getter, setter);
    }

    @Test
    public void testMapInt() {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        ObservableMap<Integer, CustomObject> mapEmpty = FXCollections.emptyObservableMap();
        ObservableMap<Integer, CustomObject> mapOne = FXCollections.observableHashMap();
        mapOne.put(1, one);
        ObservableMap<Integer, CustomObject> mapTwo = FXCollections.observableHashMap();
        mapTwo.put(1, one);
        mapTwo.put(2, two);

        Function<BaseWithMapInt, ObservableMap<Integer, CustomObject>> getter = obj -> obj.map;
        BiConsumer<BaseWithMapInt, ObservableMap<Integer, CustomObject>> setter = (obj, map) -> obj.map = map;

        testValue(BaseWithMapInt.class, null, "{\"map\":null}", getter, setter);
        testValue(BaseWithMapInt.class, mapEmpty, "{\"map\":{}}", getter, setter);
        testValue(BaseWithMapInt.class, mapOne, "{\"map\":{\"1\":{\"name\":\"myObj1\"}}}", getter, setter);
        testValue(BaseWithMapInt.class, mapTwo, "{\"map\":{\"1\":{\"name\":\"myObj1\"},\"2\":{\"name\":\"myObj2\"}}}",
                getter, setter);
    }

    @Test
    public void testCustomTreeMapStr() {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        Map<String, CustomObject> mapEmpty = new TreeMap<>();
        Map<String, CustomObject> mapOne = new TreeMap<>();
        mapOne.put("key1", one);
        Map<String, CustomObject> mapTwo = new TreeMap<>();
        mapTwo.put("key1", one);
        mapTwo.put("key2", two);

        ObservableMap<String, CustomObject> mapEmptyObs = FXCollections.observableMap(mapEmpty);
        ObservableMap<String, CustomObject> mapOneObs = FXCollections.observableMap(mapOne);
        ObservableMap<String, CustomObject> mapTwoObs = FXCollections.observableMap(mapTwo);

        Function<BaseWithMapStr, ObservableMap<String, CustomObject>> getter = obj -> obj.map;
        BiConsumer<BaseWithMapStr, ObservableMap<String, CustomObject>> setter = (obj, map) -> obj.map = map;

        testValue(BaseWithMapStr.class, null, "{\"map\":null}", getter, setter);
        testValue(BaseWithMapStr.class, mapEmptyObs, "{\"map\":{}}", getter, setter);
        testValue(BaseWithMapStr.class, mapOneObs, "{\"map\":{\"key1\":{\"name\":\"myObj1\"}}}", getter, setter);
        testValue(BaseWithMapStr.class, mapTwoObs,
                "{\"map\":{\"key1\":{\"name\":\"myObj1\"},\"key2\":{\"name\":\"myObj2\"}}}", getter, setter);
    }

    @Test
    public void testCustomTreeMapInt() {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        Map<Integer, CustomObject> mapEmpty = new TreeMap<>();
        Map<Integer, CustomObject> mapOne = new TreeMap<>();
        mapOne.put(1, one);
        Map<Integer, CustomObject> mapTwo = new TreeMap<>();
        mapTwo.put(1, one);
        mapTwo.put(2, two);

        ObservableMap<Integer, CustomObject> mapEmptyObs = FXCollections.observableMap(mapEmpty);
        ObservableMap<Integer, CustomObject> mapOneObs = FXCollections.observableMap(mapOne);
        ObservableMap<Integer, CustomObject> mapTwoObs = FXCollections.observableMap(mapTwo);

        Function<BaseWithMapInt, ObservableMap<Integer, CustomObject>> getter = obj -> obj.map;
        BiConsumer<BaseWithMapInt, ObservableMap<Integer, CustomObject>> setter = (obj, map) -> obj.map = map;

        testValue(BaseWithMapInt.class, null, "{\"map\":null}", getter, setter);
        testValue(BaseWithMapInt.class, mapEmptyObs, "{\"map\":{}}", getter, setter);
        testValue(BaseWithMapInt.class, mapOneObs, "{\"map\":{\"1\":{\"name\":\"myObj1\"}}}", getter, setter);
        testValue(BaseWithMapInt.class, mapTwoObs,
                "{\"map\":{\"1\":{\"name\":\"myObj1\"},\"2\":{\"name\":\"myObj2\"}}}", getter, setter);
    }
}