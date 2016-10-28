package org.hildan.fxgson;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hildan.fxgson.adapters.primitives.NullPrimitiveException;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hildan.fxgson.TestClassesExtra.WithColor;
import static org.hildan.fxgson.TestClassesExtra.WithFont;
import static org.hildan.fxgson.TestClassesSimple.*;
import static org.hildan.fxgson.TestClassesWithProp.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FxGsonTest {

    private static Gson[] allGsons;

    private static Gson[] extraGsons;

    private static Gson[] safeGsons;

    private static Gson[] strictGsons;

    @BeforeClass
    public static void createGsons() {
        // initialize Gsons in all possible ways
        Gson coreGson1 = FxGson.create();
        Gson coreGson2 = FxGson.coreBuilder().create();
        Gson coreGson3 = new FxGsonBuilder().create();
        Gson coreGson4 = FxGson.addFxSupport(new GsonBuilder()).create();
        Gson coreGson5 = new FxGsonBuilder(new GsonBuilder()).create();

        Gson extraGson1 = FxGson.createWithExtras();
        Gson extraGson2 = FxGson.fullBuilder().create();
        Gson extraGson3 = new FxGsonBuilder().withExtras().create();
        Gson extraGson4 = new FxGsonBuilder(new GsonBuilder()).withExtras().create();

        Gson coreGsonSafe1 = new FxGsonBuilder().acceptNullPrimitives().create();
        Gson coreGsonSafe2 = new FxGsonBuilder(new GsonBuilder()).acceptNullPrimitives().create();
        Gson extraGsonSafe1 = new FxGsonBuilder().acceptNullPrimitives().withExtras().create();
        Gson extraGsonSafe2 = new FxGsonBuilder(new GsonBuilder()).acceptNullPrimitives().withExtras().create();

        allGsons = new Gson[]{coreGson1, coreGson2, coreGson3, coreGson4, coreGson5, extraGson1, extraGson2, extraGson3,
                extraGson4, coreGsonSafe1, coreGsonSafe2, extraGsonSafe1, extraGsonSafe2};
        strictGsons =
                new Gson[]{coreGson1, coreGson2, coreGson3, coreGson4, coreGson5, extraGson1, extraGson2, extraGson3,
                        extraGson4};
        extraGsons = new Gson[]{extraGson1, extraGson2, extraGson3, extraGson4, extraGsonSafe1, extraGsonSafe2};
        safeGsons = new Gson[]{coreGsonSafe1, coreGsonSafe2, extraGsonSafe1, extraGsonSafe2};
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
     *         the value of the field to test
     * @param expectedJson
     *         the expected JSON representing the serialized object (with the value inside), or null if this method
     *         should not test the serialized representation of the object
     * @param getter
     *         a function to access the field to test within an object of baseClass
     * @param setter
     *         a function to set the field to test within an object of baseClass
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
        testValue(baseClass, valueToTest, expectedJson, getter, setter, allGsons);
    }

    /**
     * Tests the deserialization of an inner value of an object with each of the provided {@link Gson}s.
     *
     * @param baseClass
     *         the class of object to test
     * @param inputJson
     *         the input JSON to test
     * @param expectedValue
     *         the expected deserialized value of the field to test
     * @param getter
     *         a function to access the field to test within an object of baseClass
     * @param gsons
     *         the {@link Gson}s to use for serialization/deserialization tests
     * @param <B>
     *         the type of the object containing the field to test
     * @param <V>
     *         the type of the value to test inside the object
     */
    private static <B, V> void testDeserialize(Class<B> baseClass, String inputJson, V expectedValue,
                                               Function<B, V> getter, Gson... gsons) {
        for (Gson gson : gsons) {
            B deserialized = gson.fromJson(inputJson, baseClass);
            assertEquals("Incorrect deserialized value", expectedValue, getter.apply(deserialized));
        }
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
     * @param gsons
     *         the {@link Gson}s to use for serialization/deserialization
     * @param <B>
     *         the type of parent object to test
     * @param <V>
     *         the type of the value to test inside the object
     */
    private static <B, V> void testProperty(Class<B> baseClass, V valueToTest, String expectedJson,
                                            Function<B, Property<V>> getProperty, Gson... gsons) {
        for (Gson gson : gsons) {
            Function<B, V> valueGetter = obj -> {
                Property<V> prop = getProperty.apply(obj);
                assertNotNull("The property itself should not be null (only its content may)", prop);
                return prop.getValue();
            };

            BiConsumer<B, V> valueSetter = (obj, value) -> getProperty.apply(obj).setValue(value);

            testValue(baseClass, valueToTest, expectedJson, valueGetter, valueSetter, gson);
        }
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
        testProperty(baseClass, valueToTest, expectedJson, getProperty, allGsons);
    }

    @Test
    public void testBooleanProperty() {
        testProperty(WithBooleanProp.class, true, "{\"prop\":true}", o -> o.prop);
        testProperty(WithBooleanProp.class, false, "{\"prop\":false}", o -> o.prop);
    }

    @Test(expected = NullPrimitiveException.class)
    public void testNullPrimitivesFail() {
        testDeserialize(WithBooleanProp.class, "{\"prop\":null}", null, o -> o.prop.get(), strictGsons);
        testDeserialize(WithIntegerProp.class, "{\"prop\":null}", null, o -> o.prop.get(), strictGsons);
        testDeserialize(WithLongProp.class, "{\"prop\":null}", null, o -> o.prop.get(), strictGsons);
        testDeserialize(WithFloatProp.class, "{\"prop\":null}", null, o -> o.prop.get(), strictGsons);
        testDeserialize(WithDoubleProp.class, "{\"prop\":null}", null, o -> o.prop.get(), strictGsons);
    }

    @Test
    public void testNullPrimitivesDefault() {
        testDeserialize(WithBooleanProp.class, "{\"prop\":null}", false, o -> o.prop.get(), safeGsons);
        testDeserialize(WithIntegerProp.class, "{\"prop\":null}", 0, o -> o.prop.get(), safeGsons);
        testDeserialize(WithLongProp.class, "{\"prop\":null}", 0L, o -> o.prop.get(), safeGsons);
        testDeserialize(WithFloatProp.class, "{\"prop\":null}", 0f, o -> o.prop.get(), safeGsons);
        testDeserialize(WithDoubleProp.class, "{\"prop\":null}", 0d, o -> o.prop.get(), safeGsons);
    }

    @Test
    public void testIntegerProperty() {
        testProperty(WithIntegerProp.class, 0, "{\"prop\":0}", o -> o.prop);
        testProperty(WithIntegerProp.class, 5, "{\"prop\":5}", o -> o.prop);
        testProperty(WithIntegerProp.class, -3, "{\"prop\":-3}", o -> o.prop);
    }

    @Test
    public void testLongProperty() {
        testProperty(WithLongProp.class, 0L, "{\"prop\":0}", o -> o.prop);
        testProperty(WithLongProp.class, 5L, "{\"prop\":5}", o -> o.prop);
        testProperty(WithLongProp.class, -3L, "{\"prop\":-3}", o -> o.prop);
    }

    @Test
    public void testFloatProperty() {
        testProperty(WithFloatProp.class, 0f, "{\"prop\":0.0}", o -> o.prop);
        testProperty(WithFloatProp.class, 2.5f, "{\"prop\":2.5}", o -> o.prop);
        testProperty(WithFloatProp.class, -3.5f, "{\"prop\":-3.5}", o -> o.prop);
    }

    @Test
    public void testDoubleProperty() {
        testProperty(WithDoubleProp.class, 0d, "{\"prop\":0.0}", o -> o.prop);
        testProperty(WithDoubleProp.class, 2.5d, "{\"prop\":2.5}", o -> o.prop);
        testProperty(WithDoubleProp.class, -3.5d, "{\"prop\":-3.5}", o -> o.prop);
    }

    @Test
    public void testStringProperty() {
        testProperty(WithStringProp.class, "myValue", "{\"prop\":\"myValue\"}", o -> o.prop);
        testProperty(WithStringProp.class, "", "{\"prop\":\"\"}", o -> o.prop);
        testProperty(WithStringProp.class, null, "{\"prop\":null}", o -> o.prop);
    }

    @Test
    public void testObjectProperty() {
        CustomObject obj = new CustomObject("myValue");
        testProperty(WithGenericProp.class, obj, "{\"prop\":{\"name\":\"myValue\"}}", o -> o.prop);
        testProperty(WithGenericProp.class, null, "{\"prop\":null}", o -> o.prop);
    }

    @Test
    public void testObservableList() {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        ObservableList<CustomObject> listEmpty = FXCollections.observableArrayList();
        ObservableList<CustomObject> listOne = FXCollections.observableArrayList(one);
        ObservableList<CustomObject> listTwo = FXCollections.observableArrayList(one, two);

        Function<WithObsList, ObservableList<CustomObject>> getter = o -> o.list;
        BiConsumer<WithObsList, ObservableList<CustomObject>> setter = (o, l) -> o.list = l;

        testValue(WithObsList.class, null, "{\"list\":null}", getter, setter);
        testValue(WithObsList.class, listEmpty, "{\"list\":[]}", getter, setter);
        testValue(WithObsList.class, listOne, "{\"list\":[{\"name\":\"myObj1\"}]}", getter, setter);
        testValue(WithObsList.class, listTwo, "{\"list\":[{\"name\":\"myObj1\"},{\"name\":\"myObj2\"}]}", getter,
                setter);
    }

    @Test
    public void testObservableSet() {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        ObservableSet<CustomObject> setEmpty = FXCollections.emptyObservableSet();
        ObservableSet<CustomObject> setOne = FXCollections.observableSet(one);
        ObservableSet<CustomObject> setTwo = FXCollections.observableSet(one, two);

        Function<WithObsSet, ObservableSet<CustomObject>> getter = o -> o.set;
        BiConsumer<WithObsSet, ObservableSet<CustomObject>> setter = (o, s) -> o.set = s;

        testValue(WithObsSet.class, null, "{\"set\":null}", getter, setter);
        testValue(WithObsSet.class, setEmpty, "{\"set\":[]}", getter, setter);
        testValue(WithObsSet.class, setOne, "{\"set\":[{\"name\":\"myObj1\"}]}", getter, setter);
        // do not check a particular JSON because the order is non-deterministic
        testValue(WithObsSet.class, setTwo, null, getter, setter);
    }

    @Test
    public void testObservableMapStr() {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        ObservableMap<String, CustomObject> mapEmpty = FXCollections.emptyObservableMap();
        ObservableMap<String, CustomObject> mapOne = FXCollections.observableHashMap();
        mapOne.put("key1", one);
        ObservableMap<String, CustomObject> mapTwo = FXCollections.observableHashMap();
        mapTwo.put("key1", one);
        mapTwo.put("key2", two);

        Function<WithObsMapStr, ObservableMap<String, CustomObject>> getter = o -> o.map;
        BiConsumer<WithObsMapStr, ObservableMap<String, CustomObject>> setter = (o, m) -> o.map = m;

        testValue(WithObsMapStr.class, null, "{\"map\":null}", getter, setter);
        testValue(WithObsMapStr.class, mapEmpty, "{\"map\":{}}", getter, setter);
        testValue(WithObsMapStr.class, mapOne, "{\"map\":{\"key1\":{\"name\":\"myObj1\"}}}", getter, setter);
        testValue(WithObsMapStr.class, mapTwo,
                "{\"map\":{\"key1\":{\"name\":\"myObj1\"},\"key2\":{\"name\":\"myObj2\"}}}", getter, setter);
    }

    @Test
    public void testObservableMapInt() {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        ObservableMap<Integer, CustomObject> mapEmpty = FXCollections.emptyObservableMap();
        ObservableMap<Integer, CustomObject> mapOne = FXCollections.observableHashMap();
        mapOne.put(1, one);
        ObservableMap<Integer, CustomObject> mapTwo = FXCollections.observableHashMap();
        mapTwo.put(1, one);
        mapTwo.put(2, two);

        Function<WithObsMapInt, ObservableMap<Integer, CustomObject>> getter = o -> o.map;
        BiConsumer<WithObsMapInt, ObservableMap<Integer, CustomObject>> setter = (o, m) -> o.map = m;

        testValue(WithObsMapInt.class, null, "{\"map\":null}", getter, setter);
        testValue(WithObsMapInt.class, mapEmpty, "{\"map\":{}}", getter, setter);
        testValue(WithObsMapInt.class, mapOne, "{\"map\":{\"1\":{\"name\":\"myObj1\"}}}", getter, setter);
        testValue(WithObsMapInt.class, mapTwo, "{\"map\":{\"1\":{\"name\":\"myObj1\"},\"2\":{\"name\":\"myObj2\"}}}",
                getter, setter);
    }

    @Test
    public void testCustomObservableTreeMapStr() {
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

        Function<WithObsMapStr, ObservableMap<String, CustomObject>> getter = o -> o.map;
        BiConsumer<WithObsMapStr, ObservableMap<String, CustomObject>> setter = (o, m) -> o.map = m;

        testValue(WithObsMapStr.class, null, "{\"map\":null}", getter, setter);
        testValue(WithObsMapStr.class, mapEmptyObs, "{\"map\":{}}", getter, setter);
        testValue(WithObsMapStr.class, mapOneObs, "{\"map\":{\"key1\":{\"name\":\"myObj1\"}}}", getter, setter);
        testValue(WithObsMapStr.class, mapTwoObs,
                "{\"map\":{\"key1\":{\"name\":\"myObj1\"},\"key2\":{\"name\":\"myObj2\"}}}", getter, setter);
    }

    @Test
    public void testCustomObservableTreeMapInt() {
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

        Function<WithObsMapInt, ObservableMap<Integer, CustomObject>> getter = o -> o.map;
        BiConsumer<WithObsMapInt, ObservableMap<Integer, CustomObject>> setter = (o, m) -> o.map = m;

        testValue(WithObsMapInt.class, null, "{\"map\":null}", getter, setter);
        testValue(WithObsMapInt.class, mapEmptyObs, "{\"map\":{}}", getter, setter);
        testValue(WithObsMapInt.class, mapOneObs, "{\"map\":{\"1\":{\"name\":\"myObj1\"}}}", getter, setter);
        testValue(WithObsMapInt.class, mapTwoObs, "{\"map\":{\"1\":{\"name\":\"myObj1\"},\"2\":{\"name\":\"myObj2\"}}}",
                getter, setter);
    }

    @Test
    public void testBoolean() {
        testValue(WithBoolean.class, true, "{\"value\":true}", o -> o.value, (o, v) -> o.value = v);
        testValue(WithBoolean.class, false, "{\"value\":false}", o -> o.value, (o, v) -> o.value = v);
    }

    @Test
    public void testInteger() {
        testValue(WithInteger.class, 0, "{\"value\":0}", o -> o.value, (o, v) -> o.value = v);
        testValue(WithInteger.class, 5, "{\"value\":5}", o -> o.value, (o, v) -> o.value = v);
        testValue(WithInteger.class, -3, "{\"value\":-3}", o -> o.value, (o, v) -> o.value = v);
    }

    @Test
    public void testLong() {
        testValue(WithLong.class, 0L, "{\"value\":0}", o -> o.value, (o, v) -> o.value = v);
        testValue(WithLong.class, 5L, "{\"value\":5}", o -> o.value, (o, v) -> o.value = v);
        testValue(WithLong.class, -3L, "{\"value\":-3}", o -> o.value, (o, v) -> o.value = v);
    }

    @Test
    public void testFloat() {
        testValue(WithFloat.class, 0f, "{\"value\":0.0}", o -> o.value, (o, v) -> o.value = v);
        testValue(WithFloat.class, 2.5f, "{\"value\":2.5}", o -> o.value, (o, v) -> o.value = v);
        testValue(WithFloat.class, -3.5f, "{\"value\":-3.5}", o -> o.value, (o, v) -> o.value = v);
    }

    @Test
    public void testDouble() {
        testValue(WithDouble.class, 0d, "{\"value\":0.0}", o -> o.value, (o, v) -> o.value = v);
        testValue(WithDouble.class, 2.5d, "{\"value\":2.5}", o -> o.value, (o, v) -> o.value = v);
        testValue(WithDouble.class, -3.5d, "{\"value\":-3.5}", o -> o.value, (o, v) -> o.value = v);
    }

    @Test
    public void testString() {
        testValue(WithString.class, "myValue", "{\"value\":\"myValue\"}", o -> o.value, (o, v) -> o.value = v);
        testValue(WithString.class, "", "{\"value\":\"\"}", o -> o.value, (o, v) -> o.value = v);
        testValue(WithString.class, null, "{\"value\":null}", o -> o.value, (o, v) -> o.value = v);
    }

    @Test
    public void testObject() {
        CustomObject obj = new CustomObject("myValue");
        testValue(WithCustomObject.class, obj, "{\"value\":{\"name\":\"myValue\"}}", o -> o.value,
                (o, v) -> o.value = v);
        testValue(WithCustomObject.class, null, "{\"value\":null}", o -> o.value, (o, v) -> o.value = v);
    }

    @Test
    public void testList() {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        List<CustomObject> listEmpty = Collections.emptyList();
        List<CustomObject> listOne = Collections.singletonList(one);
        List<CustomObject> listTwo = Arrays.asList(one, two);

        Function<WithList, List<CustomObject>> getter = o -> o.list;
        BiConsumer<WithList, List<CustomObject>> setter = (o, l) -> o.list = l;

        testValue(WithList.class, null, "{\"list\":null}", getter, setter);
        testValue(WithList.class, listEmpty, "{\"list\":[]}", getter, setter);
        testValue(WithList.class, listOne, "{\"list\":[{\"name\":\"myObj1\"}]}", getter, setter);
        testValue(WithList.class, listTwo, "{\"list\":[{\"name\":\"myObj1\"},{\"name\":\"myObj2\"}]}", getter, setter);
    }

    @Test
    public void testSet() {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        Set<CustomObject> setEmpty = Collections.emptySet();
        Set<CustomObject> setOne = Collections.singleton(one);
        Set<CustomObject> setTwo = new HashSet<>(Arrays.asList(one, two));

        Function<WithSet, Set<CustomObject>> getter = o -> o.set;
        BiConsumer<WithSet, Set<CustomObject>> setter = (o, s) -> o.set = s;

        testValue(WithSet.class, null, "{\"set\":null}", getter, setter);
        testValue(WithSet.class, setEmpty, "{\"set\":[]}", getter, setter);
        testValue(WithSet.class, setOne, "{\"set\":[{\"name\":\"myObj1\"}]}", getter, setter);
        // do not check a particular JSON because the order is non-deterministic
        testValue(WithSet.class, setTwo, null, getter, setter);
    }

    @Test
    public void testMapStr() {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        Map<String, CustomObject> mapEmpty = Collections.emptyMap();
        Map<String, CustomObject> mapOne = Collections.singletonMap("key1", one);
        Map<String, CustomObject> mapTwo = new HashMap<>();
        mapTwo.put("key1", one);
        mapTwo.put("key2", two);

        Function<WithMapStr, Map<String, CustomObject>> getter = o -> o.map;
        BiConsumer<WithMapStr, Map<String, CustomObject>> setter = (o, m) -> o.map = m;

        testValue(WithMapStr.class, null, "{\"map\":null}", getter, setter);
        testValue(WithMapStr.class, mapEmpty, "{\"map\":{}}", getter, setter);
        testValue(WithMapStr.class, mapOne, "{\"map\":{\"key1\":{\"name\":\"myObj1\"}}}", getter, setter);
        testValue(WithMapStr.class, mapTwo, "{\"map\":{\"key1\":{\"name\":\"myObj1\"},\"key2\":{\"name\":\"myObj2\"}}}",
                getter, setter);
    }

    @Test
    public void testMapInt() {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        Map<Integer, CustomObject> mapEmpty = Collections.emptyMap();
        Map<Integer, CustomObject> mapOne = Collections.singletonMap(1, one);
        Map<Integer, CustomObject> mapTwo = new HashMap<>();
        mapTwo.put(1, one);
        mapTwo.put(2, two);

        Function<WithMapInt, Map<Integer, CustomObject>> getter = o -> o.map;
        BiConsumer<WithMapInt, Map<Integer, CustomObject>> setter = (o, m) -> o.map = m;

        testValue(WithMapInt.class, null, "{\"map\":null}", getter, setter);
        testValue(WithMapInt.class, mapEmpty, "{\"map\":{}}", getter, setter);
        testValue(WithMapInt.class, mapOne, "{\"map\":{\"1\":{\"name\":\"myObj1\"}}}", getter, setter);
        testValue(WithMapInt.class, mapTwo, "{\"map\":{\"1\":{\"name\":\"myObj1\"},\"2\":{\"name\":\"myObj2\"}}}",
                getter, setter);
    }

    @Test
    public void testFont() {
        String family = "SansSerif";
        FontWeight weight = FontWeight.findByName("Regular");
        double size = 11.0;
        Font font = Font.font(family, weight, size);

        Function<WithFont, Font> getter = o -> o.font;
        BiConsumer<WithFont, Font> setter = (o, f) -> o.font = f;

        testValue(WithFont.class, null, "{\"font\":null}", getter, setter, extraGsons);
        testValue(WithFont.class, font, "{\"font\":\"SansSerif,Regular,11.0\"}", getter, setter, extraGsons);
    }

    @Test
    public void testFontProperty() {
        String family = "SansSerif";
        FontWeight weight = FontWeight.findByName("Regular");
        double size = 11.0;
        Font font = Font.font(family, weight, size);

        testProperty(WithFontProp.class, null, "{\"prop\":null}", o -> o.prop, extraGsons);
        testProperty(WithFontProp.class, font, "{\"prop\":\"SansSerif,Regular,11.0\"}", o -> o.prop, extraGsons);
    }

    @Test
    public void testColor() {
        Function<WithColor, Color> getter = o -> o.color;
        BiConsumer<WithColor, Color> setter = (o, c) -> o.color = c;

        testValue(WithColor.class, null, "{\"color\":null}", getter, setter, extraGsons);
        testValue(WithColor.class, Color.RED, "{\"color\":\"#ff0000ff\"}", getter, setter, extraGsons);
        testValue(WithColor.class, Color.BLUE, "{\"color\":\"#0000ffff\"}", getter, setter, extraGsons);
    }

    @Test
    public void testColorProperty() {
        testProperty(WithColorProp.class, null, "{\"prop\":null}", o -> o.prop, extraGsons);
        testProperty(WithColorProp.class, Color.RED, "{\"prop\":\"#ff0000ff\"}", o -> o.prop, extraGsons);
        testProperty(WithColorProp.class, Color.BLUE, "{\"prop\":\"#0000ffff\"}", o -> o.prop, extraGsons);
    }
}