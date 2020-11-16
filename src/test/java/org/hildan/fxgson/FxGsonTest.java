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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import org.hildan.fxgson.TestClassesCustom.CustomFactory;
import org.hildan.fxgson.TestClassesCustom.WithCustomListProp;
import org.hildan.fxgson.adapters.properties.NullPropertyException;
import org.hildan.fxgson.adapters.properties.primitives.NullPrimitiveException;
import org.hildan.fxgson.factories.JavaFxPropertyTypeAdapterFactory;
import org.hildan.fxgson.test.TestUtils;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static org.hildan.fxgson.TestClassesExtra.WithColor;
import static org.hildan.fxgson.TestClassesExtra.WithFont;
import static org.hildan.fxgson.TestClassesSimple.CustomObject;
import static org.hildan.fxgson.TestClassesSimple.WithBoolean;
import static org.hildan.fxgson.TestClassesSimple.WithCustomObject;
import static org.hildan.fxgson.TestClassesSimple.WithDouble;
import static org.hildan.fxgson.TestClassesSimple.WithFloat;
import static org.hildan.fxgson.TestClassesSimple.WithInteger;
import static org.hildan.fxgson.TestClassesSimple.WithList;
import static org.hildan.fxgson.TestClassesSimple.WithLong;
import static org.hildan.fxgson.TestClassesSimple.WithMapInt;
import static org.hildan.fxgson.TestClassesSimple.WithMapStr;
import static org.hildan.fxgson.TestClassesSimple.WithSet;
import static org.hildan.fxgson.TestClassesSimple.WithString;
import static org.hildan.fxgson.TestClassesWithProp.WithBooleanProp;
import static org.hildan.fxgson.TestClassesWithProp.WithColorProp;
import static org.hildan.fxgson.TestClassesWithProp.WithDoubleProp;
import static org.hildan.fxgson.TestClassesWithProp.WithFloatProp;
import static org.hildan.fxgson.TestClassesWithProp.WithFontProp;
import static org.hildan.fxgson.TestClassesWithProp.WithGenericProp;
import static org.hildan.fxgson.TestClassesWithProp.WithIntegerProp;
import static org.hildan.fxgson.TestClassesWithProp.WithListProp;
import static org.hildan.fxgson.TestClassesWithProp.WithLongProp;
import static org.hildan.fxgson.TestClassesWithProp.WithMapIntProp;
import static org.hildan.fxgson.TestClassesWithProp.WithMapStrProp;
import static org.hildan.fxgson.TestClassesWithProp.WithObjectProp;
import static org.hildan.fxgson.TestClassesWithProp.WithObsList;
import static org.hildan.fxgson.TestClassesWithProp.WithObsMapInt;
import static org.hildan.fxgson.TestClassesWithProp.WithObsMapStr;
import static org.hildan.fxgson.TestClassesWithProp.WithObsSet;
import static org.hildan.fxgson.TestClassesWithProp.WithSetProp;
import static org.hildan.fxgson.TestClassesWithProp.WithStringProp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

@RunWith(Theories.class)
public class FxGsonTest {

    @DataPoints({"all", "strictProperties", "strictPrimitives"})
    public static Gson[] strictGsons() {
        Gson gson1 = FxGson.create();
        Gson gson2 = FxGson.coreBuilder().create();
        Gson gson3 = new FxGsonBuilder().create();
        Gson gson4 = FxGson.addFxSupport(new GsonBuilder()).create();
        Gson gson5 = new FxGsonBuilder(new GsonBuilder()).create();
        Gson gson6 = new FxGsonBuilder().builder()
                                        .registerTypeAdapterFactory(new JavaFxPropertyTypeAdapterFactory())
                                        .create();
        return new Gson[] {gson1, gson2, gson3, gson4, gson5, gson6};
    }

    @DataPoints({"all", "extra", "strictProperties", "strictPrimitives"})
    public static Gson[] extraGsons() {
        Gson gson1 = FxGson.createWithExtras();
        Gson gson2 = FxGson.fullBuilder().create();
        Gson gson3 = new FxGsonBuilder().withExtras().create();
        Gson gson4 = new FxGsonBuilder(new GsonBuilder()).withExtras().create();
        return new Gson[] {gson1, gson2, gson3, gson4};
    }

    @DataPoints({"all", "strictProperties", "safePrimitives"})
    public static Gson[] safePrimitivesGsons() {
        Gson gson1 = new FxGsonBuilder().acceptNullPrimitives().create();
        Gson gson2 = new FxGsonBuilder(new GsonBuilder()).acceptNullPrimitives().create();
        return new Gson[] {gson1, gson2};
    }

    @DataPoints({"all", "strictProperties", "safePrimitives", "extra"})
    public static Gson[] safePrimitivesAndExtraGsons() {
        Gson gson1 = new FxGsonBuilder().acceptNullPrimitives().withExtras().create();
        Gson gson2 = new FxGsonBuilder(new GsonBuilder()).acceptNullPrimitives().withExtras().create();
        return new Gson[] {gson1, gson2};
    }

    @DataPoints({"all", "safeProperties", "strictPrimitives"})
    public static Gson[] safePropertiesGsons() {
        Gson gson1 = new FxGsonBuilder().acceptNullProperties().create();
        Gson gson2 = new FxGsonBuilder(new GsonBuilder()).acceptNullProperties().create();
        return new Gson[] {gson1, gson2};
    }

    @DataPoints({"all", "safeProperties", "strictPrimitives", "extra"})
    public static Gson[] safePropertiesAndExtraGsons() {
        Gson gson1 = new FxGsonBuilder().acceptNullProperties().withExtras().create();
        Gson gson2 = new FxGsonBuilder(new GsonBuilder()).acceptNullProperties().withExtras().create();
        return new Gson[] {gson1, gson2};
    }

    @DataPoints({"all", "safeProperties", "safePrimitives"})
    public static Gson[] safePropertiesAndPrimitivesGsons() {
        Gson gson1 = new FxGsonBuilder().acceptNullProperties().acceptNullPrimitives().create();
        Gson gson2 = new FxGsonBuilder(new GsonBuilder()).acceptNullProperties().acceptNullPrimitives().create();
        return new Gson[] {gson1, gson2};
    }

    @DataPoints({"all", "safeProperties", "safePrimitives", "extra"})
    public static Gson[] safePropertiesAndPrimitivesAndExtraGsons() {
        Gson gson1 = new FxGsonBuilder().acceptNullProperties().acceptNullPrimitives().withExtras().create();
        Gson gson2 = new FxGsonBuilder(new GsonBuilder()).acceptNullProperties()
                                                         .acceptNullPrimitives()
                                                         .withExtras()
                                                         .create();
        return new Gson[] {gson1, gson2};
    }

    @DataPoints({"all", "strictProperties", "strictPrimitives", "specialFloat"})
    public static Gson[] specialFloatGsons() {
        Gson gson1 = FxGson.coreBuilder().serializeSpecialFloatingPointValues().create();
        Gson gson2 = new FxGsonBuilder().builder().serializeSpecialFloatingPointValues().create();
        Gson gson3 = new FxGsonBuilder(new GsonBuilder()).builder().serializeSpecialFloatingPointValues().create();
        return new Gson[] {gson1, gson2, gson3};
    }

    @DataPoints({"all", "strictProperties", "strictPrimitives", "custom"})
    public static Gson[] gsonsWithCustomListProp() {
        Gson gson1 = FxGson.coreBuilder().registerTypeAdapterFactory(new CustomFactory()).create();
        Gson gson2 = new FxGsonBuilder().builder().registerTypeAdapterFactory(new CustomFactory()).create();
        Gson gson3 = new FxGsonBuilder(new GsonBuilder().registerTypeAdapterFactory(new CustomFactory())).create();
        return new Gson[] {gson1, gson2, gson3};
    }

    @DataPoints({"all", "strictProperties", "strictPrimitives", "custom", "extra"})
    public static Gson[] gsonsWithCustomListPropAndExtras() {
        Gson gson1 = FxGson.fullBuilder().registerTypeAdapterFactory(new CustomFactory()).create();
        Gson gson2 = new FxGsonBuilder().withExtras()
                                        .builder()
                                        .registerTypeAdapterFactory(new CustomFactory())
                                        .create();
        GsonBuilder customBuilder = new GsonBuilder().registerTypeAdapterFactory(new CustomFactory());
        Gson gson3 = new FxGsonBuilder(customBuilder).withExtras().create();
        return new Gson[] {gson1, gson2, gson3};
    }

    @Test
    public void fxGson_cantBeInstantiated() throws IllegalAccessException, InstantiationException {
        TestUtils.assertCannotBeInstantiated(FxGson.class);
    }

    /**
     * Tests the serialization/deserialization of an inner value of an object (for the given value) with the provided
     * {@link Gson}.
     * <p>
     * This method checks that the value is the same after a serialization-deserialization cycle. If an expected JSON is
     * provided, this method also checks if the serialized object gives the expected JSON.
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
     * @param gson
     *         the {@link Gson} to use for serialization/deserialization tests
     * @param <B>
     *         the type of the object containing the field to test
     * @param <V>
     *         the type of the value to test inside the object
     */
    private static <B, V> void testValue(Class<B> baseClass, V valueToTest, String expectedJson, Function<B, V> getter,
            BiConsumer<B, V> setter, Gson gson) {
        try {
            B baseObj = baseClass.newInstance();
            setter.accept(baseObj, valueToTest);

            if (expectedJson != null) {
                assertEquals("Incorrect JSON generated", expectedJson, gson.toJson(baseObj));
            } else {
                expectedJson = gson.toJson(baseObj);
            }

            B deserialized = gson.fromJson(expectedJson, baseClass);
            assertEquals("Incorrect deserialized value", valueToTest, getter.apply(deserialized));
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Cannot run the test on class '" + baseClass.getSimpleName() + "'", e);
        }
    }

    /**
     * Tests the deserialization of an inner value of an object with each of the provided {@link Gson}.
     *
     * @param baseClass
     *         the class of object to test
     * @param inputJson
     *         the input JSON to test
     * @param expectedValue
     *         the expected deserialized value of the field to test
     * @param getter
     *         a function to access the field to test within an object of baseClass
     * @param gson
     *         the {@link Gson} to use for serialization/deserialization tests
     * @param <B>
     *         the type of the object containing the field to test
     * @param <V>
     *         the type of the value to test inside the object
     */
    private static <B, V> void testDeserialize(Class<B> baseClass, String inputJson, V expectedValue,
            Function<B, V> getter, Gson gson) {
        B deserialized = gson.fromJson(inputJson, baseClass);
        assertEquals("Incorrect deserialized value", expectedValue, getter.apply(deserialized));
    }

    /**
     * Tests the serialization of an inner value of an object with each of the provided {@link Gson}.
     *
     * @param baseClass
     *         the class of object to test
     * @param expectedJson
     *         the expected output JSON
     * @param inputValue
     *         the input value of the field to test
     * @param setter
     *         a function to set the input value within an object of baseClass
     * @param gson
     *         the {@link Gson} to use for serialization/deserialization tests
     * @param <B>
     *         the type of the object containing the field to test
     * @param <V>
     *         the type of the value to test inside the object
     */
    private static <B, V> void testSerialize(Class<B> baseClass, String expectedJson, V inputValue,
            BiConsumer<B, V> setter, Gson gson) {
        try {
            B baseObj = baseClass.newInstance();
            setter.accept(baseObj, inputValue);

            assertEquals("Incorrect JSON generated", expectedJson, gson.toJson(baseObj));
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Cannot run the test on class '" + baseClass.getSimpleName() + "'", e);
        }
    }

    /**
     * Tests the serialization/deserialization of the given value of a {@link Property} inside an object. The property
     * is checked not to be null after deserialization.
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

    @Theory
    public void testBooleanProperty(@FromDataPoints("all") Gson gson) {
        testProperty(WithBooleanProp.class, true, "{\"prop\":true}", o -> o.prop, gson);
        testProperty(WithBooleanProp.class, false, "{\"prop\":false}", o -> o.prop, gson);
    }

    @Theory
    public void testNullPrimitivesFail_boolean(@FromDataPoints("strictPrimitives") Gson gson) {
        assertThrows(NullPrimitiveException.class, () -> {
            testDeserialize(WithBooleanProp.class, "{\"prop\":null}", null, o -> o.prop.get(), gson);
        });
    }

    @Theory
    public void testNullPrimitivesFail_int(@FromDataPoints("strictPrimitives") Gson gson) {
        assertThrows(NullPrimitiveException.class, () -> {
            testDeserialize(WithIntegerProp.class, "{\"prop\":null}", null, o -> o.prop.get(), gson);
        });
    }

    @Theory
    public void testNullPrimitivesFail_long(@FromDataPoints("strictPrimitives") Gson gson) {
        assertThrows(NullPrimitiveException.class, () -> {
            testDeserialize(WithLongProp.class, "{\"prop\":null}", null, o -> o.prop.get(), gson);
        });
    }

    @Theory
    public void testNullPrimitivesFail_float(@FromDataPoints("strictPrimitives") Gson gson) {
        assertThrows(NullPrimitiveException.class, () -> {
            testDeserialize(WithFloatProp.class, "{\"prop\":null}", null, o -> o.prop.get(), gson);
        });
    }

    @Theory
    public void testNullPrimitivesFail_double(@FromDataPoints("strictPrimitives") Gson gson) {
        assertThrows(NullPrimitiveException.class, () -> {
            testDeserialize(WithDoubleProp.class, "{\"prop\":null}", null, o -> o.prop.get(), gson);
        });
    }

    @Theory
    public void testNullPrimitivesDefault(@FromDataPoints("safePrimitives") Gson gson) {
        testDeserialize(WithBooleanProp.class, "{\"prop\":null}", false, o -> o.prop.get(), gson);
        testDeserialize(WithIntegerProp.class, "{\"prop\":null}", 0, o -> o.prop.get(), gson);
        testDeserialize(WithLongProp.class, "{\"prop\":null}", 0L, o -> o.prop.get(), gson);
        testDeserialize(WithFloatProp.class, "{\"prop\":null}", 0f, o -> o.prop.get(), gson);
        testDeserialize(WithDoubleProp.class, "{\"prop\":null}", 0d, o -> o.prop.get(), gson);
    }

    @Theory
    public void testNullPropertiesFail_boolean(@FromDataPoints("strictProperties") Gson gson) {
        assertThrows(NullPropertyException.class, () -> {
            testSerialize(WithBooleanProp.class, "{\"prop\":null}", (BooleanProperty) null, (o, v) -> o.prop = v, gson);
        });
    }

    @Theory
    public void testNullPropertiesFail_int(@FromDataPoints("strictProperties") Gson gson) {
        assertThrows(NullPropertyException.class, () -> {
            testSerialize(WithIntegerProp.class, "{\"prop\":null}", (IntegerProperty) null, (o, v) -> o.prop = v, gson);
        });
    }

    @Theory
    public void testNullPropertiesFail_long(@FromDataPoints("strictProperties") Gson gson) {
        assertThrows(NullPropertyException.class, () -> {
            testSerialize(WithLongProp.class, "{\"prop\":null}", (LongProperty) null, (o, v) -> o.prop = v, gson);
        });
    }

    @Theory
    public void testNullPropertiesFail_float(@FromDataPoints("strictProperties") Gson gson) {
        assertThrows(NullPropertyException.class, () -> {
            testSerialize(WithFloatProp.class, "{\"prop\":null}", (FloatProperty) null, (o, v) -> o.prop = v, gson);
        });
    }

    @Theory
    public void testNullPropertiesFail_double(@FromDataPoints("strictProperties") Gson gson) {
        assertThrows(NullPropertyException.class, () -> {
            testSerialize(WithDoubleProp.class, "{\"prop\":null}", (DoubleProperty) null, (o, v) -> o.prop = v, gson);
        });
    }

    @Theory
    public void testNullPropertiesAccepted(@FromDataPoints("safeProperties") Gson gson) {
        testSerialize(WithBooleanProp.class, "{\"prop\":null}", (BooleanProperty) null, (o, v) -> o.prop = v, gson);
        testSerialize(WithIntegerProp.class, "{\"prop\":null}", (IntegerProperty) null, (o, v) -> o.prop = v, gson);
        testSerialize(WithLongProp.class, "{\"prop\":null}", (LongProperty) null, (o, v) -> o.prop = v, gson);
        testSerialize(WithFloatProp.class, "{\"prop\":null}", (FloatProperty) null, (o, v) -> o.prop = v, gson);
        testSerialize(WithDoubleProp.class, "{\"prop\":null}", (DoubleProperty) null, (o, v) -> o.prop = v, gson);
    }

    @Theory
    public void testIntegerProperty(@FromDataPoints("all") Gson gson) {
        testProperty(WithIntegerProp.class, 0, "{\"prop\":0}", o -> o.prop, gson);
        testProperty(WithIntegerProp.class, 5, "{\"prop\":5}", o -> o.prop, gson);
        testProperty(WithIntegerProp.class, -3, "{\"prop\":-3}", o -> o.prop, gson);
    }

    @Theory
    public void testLongProperty(@FromDataPoints("all") Gson gson) {
        testProperty(WithLongProp.class, 0L, "{\"prop\":0}", o -> o.prop, gson);
        testProperty(WithLongProp.class, 5L, "{\"prop\":5}", o -> o.prop, gson);
        testProperty(WithLongProp.class, -3L, "{\"prop\":-3}", o -> o.prop, gson);
    }

    @Theory
    public void testFloatProperty(@FromDataPoints("all") Gson gson) {
        testProperty(WithFloatProp.class, 0f, "{\"prop\":0.0}", o -> o.prop, gson);
        testProperty(WithFloatProp.class, 2.5f, "{\"prop\":2.5}", o -> o.prop, gson);
        testProperty(WithFloatProp.class, -3.5f, "{\"prop\":-3.5}", o -> o.prop, gson);
    }

    @Theory
    public void testFloatProperty_specialValues(@FromDataPoints("specialFloat") Gson gson) {
        testProperty(WithFloatProp.class, Float.NaN, "{\"prop\":NaN}", o -> o.prop, gson);
        testProperty(WithFloatProp.class, Float.POSITIVE_INFINITY, "{\"prop\":Infinity}", o -> o.prop, gson);
        testProperty(WithFloatProp.class, Float.NEGATIVE_INFINITY, "{\"prop\":-Infinity}", o -> o.prop, gson);
    }

    @Theory
    public void testDoubleProperty(@FromDataPoints("all") Gson gson) {
        testProperty(WithDoubleProp.class, 0d, "{\"prop\":0.0}", o -> o.prop, gson);
        testProperty(WithDoubleProp.class, 2.5d, "{\"prop\":2.5}", o -> o.prop, gson);
        testProperty(WithDoubleProp.class, -3.5d, "{\"prop\":-3.5}", o -> o.prop, gson);
    }

    @Theory
    public void testDoubleProperty_specialValues(@FromDataPoints("specialFloat") Gson gson) {
        testProperty(WithDoubleProp.class, Double.NaN, "{\"prop\":NaN}", o -> o.prop, gson);
        testProperty(WithDoubleProp.class, Double.POSITIVE_INFINITY, "{\"prop\":Infinity}", o -> o.prop, gson);
        testProperty(WithDoubleProp.class, Double.NEGATIVE_INFINITY, "{\"prop\":-Infinity}", o -> o.prop, gson);
    }

    @Theory
    public void testStringProperty(@FromDataPoints("all") Gson gson) {
        testProperty(WithStringProp.class, "myValue", "{\"prop\":\"myValue\"}", o -> o.prop, gson);
        testProperty(WithStringProp.class, "", "{\"prop\":\"\"}", o -> o.prop, gson);
        testProperty(WithStringProp.class, null, "{\"prop\":null}", o -> o.prop, gson);
    }

    @Theory
    public void testObjectProperty(@FromDataPoints("all") Gson gson) {
        CustomObject obj = new CustomObject("myValue");
        testProperty(WithObjectProp.class, obj, "{\"prop\":{\"name\":\"myValue\"}}", o -> o.prop, gson);
        testProperty(WithObjectProp.class, null, "{\"prop\":null}", o -> o.prop, gson);
    }

    @Theory
    public void testGenericProperty(@FromDataPoints("all") Gson gson) {
        CustomObject obj = new CustomObject("myValue");
        testProperty(WithGenericProp.class, obj, "{\"prop\":{\"name\":\"myValue\"}}", o -> o.prop, gson);
        testProperty(WithGenericProp.class, null, "{\"prop\":null}", o -> o.prop, gson);
    }

    @Theory
    public void testListProperty(@FromDataPoints("all") Gson gson) {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        ObservableList<CustomObject> listEmpty = FXCollections.observableArrayList();
        ObservableList<CustomObject> listOne = FXCollections.observableArrayList(one);
        ObservableList<CustomObject> listTwo = FXCollections.observableArrayList(one, two);

        testProperty(WithListProp.class, null, "{\"prop\":null}", o -> o.prop, gson);
        testProperty(WithListProp.class, listEmpty, "{\"prop\":[]}", o -> o.prop, gson);
        testProperty(WithListProp.class, listOne, "{\"prop\":[{\"name\":\"myObj1\"}]}", o -> o.prop, gson);
        testProperty(WithListProp.class, listTwo, "{\"prop\":[{\"name\":\"myObj1\"},{\"name\":\"myObj2\"}]}",
            o -> o.prop, gson);
    }

    @Theory
    public void testObservableList(@FromDataPoints("all") Gson gson) {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        ObservableList<CustomObject> listEmpty = FXCollections.observableArrayList();
        ObservableList<CustomObject> listOne = FXCollections.observableArrayList(one);
        ObservableList<CustomObject> listTwo = FXCollections.observableArrayList(one, two);

        Function<WithObsList, ObservableList<CustomObject>> getter = o -> o.list;
        BiConsumer<WithObsList, ObservableList<CustomObject>> setter = (o, l) -> o.list = l;

        testValue(WithObsList.class, null, "{\"list\":null}", getter, setter, gson);
        testValue(WithObsList.class, listEmpty, "{\"list\":[]}", getter, setter, gson);
        testValue(WithObsList.class, listOne, "{\"list\":[{\"name\":\"myObj1\"}]}", getter, setter, gson);
        testValue(WithObsList.class, listTwo, "{\"list\":[{\"name\":\"myObj1\"},{\"name\":\"myObj2\"}]}", getter,
            setter, gson);
    }

    @Theory
    public void testCustomListProperty(@FromDataPoints("custom") Gson gson) {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        ObservableList<CustomObject> listEmpty = FXCollections.observableArrayList();
        ObservableList<CustomObject> listOne = FXCollections.observableArrayList(one);
        ObservableList<CustomObject> listTwo = FXCollections.observableArrayList(one, two);

        testProperty(WithCustomListProp.class, null, "{\"prop\":null}", o -> o.prop, gson);
        testProperty(WithCustomListProp.class, listEmpty, "{\"prop\":[]}", o -> o.prop, gson);
        testProperty(WithCustomListProp.class, listOne, "{\"prop\":[{\"name\":\"myObj1\"}]}", o -> o.prop, gson);
        testProperty(WithCustomListProp.class, listTwo, "{\"prop\":[{\"name\":\"myObj1\"},{\"name\":\"myObj2\"}]}",
            o -> o.prop, gson);
    }

    @Theory
    public void testSetProperty(@FromDataPoints("all") Gson gson) {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        ObservableSet<CustomObject> setEmpty = FXCollections.emptyObservableSet();
        ObservableSet<CustomObject> setOne = FXCollections.observableSet(one);
        ObservableSet<CustomObject> setTwo = FXCollections.observableSet(one, two);

        testProperty(WithSetProp.class, null, "{\"prop\":null}", o -> o.prop, gson);
        testProperty(WithSetProp.class, setEmpty, "{\"prop\":[]}", o -> o.prop, gson);
        testProperty(WithSetProp.class, setOne, "{\"prop\":[{\"name\":\"myObj1\"}]}", o -> o.prop, gson);
        // do not check a particular JSON because the order is non-deterministic
        testProperty(WithSetProp.class, setTwo, null, o -> o.prop, gson);
    }

    @Theory
    public void testObservableSet(@FromDataPoints("all") Gson gson) {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        ObservableSet<CustomObject> setEmpty = FXCollections.emptyObservableSet();
        ObservableSet<CustomObject> setOne = FXCollections.observableSet(one);
        ObservableSet<CustomObject> setTwo = FXCollections.observableSet(one, two);

        Function<WithObsSet, ObservableSet<CustomObject>> getter = o -> o.set;
        BiConsumer<WithObsSet, ObservableSet<CustomObject>> setter = (o, s) -> o.set = s;

        testValue(WithObsSet.class, null, "{\"set\":null}", getter, setter, gson);
        testValue(WithObsSet.class, setEmpty, "{\"set\":[]}", getter, setter, gson);
        testValue(WithObsSet.class, setOne, "{\"set\":[{\"name\":\"myObj1\"}]}", getter, setter, gson);
        // do not check a particular JSON because the order is non-deterministic
        testValue(WithObsSet.class, setTwo, null, getter, setter, gson);
    }

    @Theory
    public void testMapStrProperty(@FromDataPoints("all") Gson gson) {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        ObservableMap<String, CustomObject> mapEmpty = FXCollections.emptyObservableMap();
        ObservableMap<String, CustomObject> mapOne = FXCollections.observableHashMap();
        mapOne.put("key1", one);
        ObservableMap<String, CustomObject> mapTwo = FXCollections.observableHashMap();
        mapTwo.put("key1", one);
        mapTwo.put("key2", two);

        testProperty(WithMapStrProp.class, null, "{\"prop\":null}", o -> o.prop, gson);
        testProperty(WithMapStrProp.class, mapEmpty, "{\"prop\":{}}", o -> o.prop, gson);
        testProperty(WithMapStrProp.class, mapOne, "{\"prop\":{\"key1\":{\"name\":\"myObj1\"}}}", o -> o.prop, gson);
        testProperty(WithMapStrProp.class, mapTwo,
                "{\"prop\":{\"key1\":{\"name\":\"myObj1\"},\"key2\":{\"name\":\"myObj2\"}}}", o -> o.prop, gson);
    }

    @Theory
    public void testObservableMapStr(@FromDataPoints("all") Gson gson) {
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

        testValue(WithObsMapStr.class, null, "{\"map\":null}", getter, setter, gson);
        testValue(WithObsMapStr.class, mapEmpty, "{\"map\":{}}", getter, setter, gson);
        testValue(WithObsMapStr.class, mapOne, "{\"map\":{\"key1\":{\"name\":\"myObj1\"}}}", getter, setter, gson);
        testValue(WithObsMapStr.class, mapTwo,
                "{\"map\":{\"key1\":{\"name\":\"myObj1\"},\"key2\":{\"name\":\"myObj2\"}}}", getter, setter, gson);
    }

    @Theory
    public void testMapIntProperty(@FromDataPoints("all") Gson gson) {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        ObservableMap<Integer, CustomObject> mapEmpty = FXCollections.emptyObservableMap();
        ObservableMap<Integer, CustomObject> mapOne = FXCollections.observableHashMap();
        mapOne.put(1, one);
        ObservableMap<Integer, CustomObject> mapTwo = FXCollections.observableHashMap();
        mapTwo.put(1, one);
        mapTwo.put(2, two);

        testProperty(WithMapIntProp.class, null, "{\"prop\":null}", o -> o.prop, gson);
        testProperty(WithMapIntProp.class, mapEmpty, "{\"prop\":{}}", o -> o.prop, gson);
        testProperty(WithMapIntProp.class, mapOne, "{\"prop\":{\"1\":{\"name\":\"myObj1\"}}}", o -> o.prop, gson);
        testProperty(WithMapIntProp.class, mapTwo,
                "{\"prop\":{\"1\":{\"name\":\"myObj1\"},\"2\":{\"name\":\"myObj2\"}}}", o -> o.prop, gson);
    }

    @Theory
    public void testObservableMapInt(@FromDataPoints("all") Gson gson) {
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

        testValue(WithObsMapInt.class, null, "{\"map\":null}", getter, setter, gson);
        testValue(WithObsMapInt.class, mapEmpty, "{\"map\":{}}", getter, setter, gson);
        testValue(WithObsMapInt.class, mapOne, "{\"map\":{\"1\":{\"name\":\"myObj1\"}}}", getter, setter, gson);
        testValue(WithObsMapInt.class, mapTwo, "{\"map\":{\"1\":{\"name\":\"myObj1\"},\"2\":{\"name\":\"myObj2\"}}}",
                getter, setter, gson);
    }

    @Theory
    public void testCustomTreeMapStrProperty(@FromDataPoints("all") Gson gson) {
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

        testProperty(WithMapStrProp.class, null, "{\"prop\":null}", o -> o.prop, gson);
        testProperty(WithMapStrProp.class, mapEmptyObs, "{\"prop\":{}}", o -> o.prop, gson);
        testProperty(WithMapStrProp.class, mapOneObs, "{\"prop\":{\"key1\":{\"name\":\"myObj1\"}}}", o -> o.prop, gson);
        testProperty(WithMapStrProp.class, mapTwoObs,
                "{\"prop\":{\"key1\":{\"name\":\"myObj1\"},\"key2\":{\"name\":\"myObj2\"}}}", o -> o.prop, gson);
    }

    @Theory
    public void testCustomObservableTreeMapStr(@FromDataPoints("all") Gson gson) {
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

        testValue(WithObsMapStr.class, null, "{\"map\":null}", getter, setter, gson);
        testValue(WithObsMapStr.class, mapEmptyObs, "{\"map\":{}}", getter, setter, gson);
        testValue(WithObsMapStr.class, mapOneObs, "{\"map\":{\"key1\":{\"name\":\"myObj1\"}}}", getter, setter, gson);
        testValue(WithObsMapStr.class, mapTwoObs,
                "{\"map\":{\"key1\":{\"name\":\"myObj1\"},\"key2\":{\"name\":\"myObj2\"}}}", getter, setter, gson);
    }

    @Theory
    public void testCustomTreeMapIntProperty(@FromDataPoints("all") Gson gson) {
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

        testProperty(WithMapIntProp.class, null, "{\"prop\":null}", o -> o.prop, gson);
        testProperty(WithMapIntProp.class, mapEmptyObs, "{\"prop\":{}}", o -> o.prop, gson);
        testProperty(WithMapIntProp.class, mapOneObs, "{\"prop\":{\"1\":{\"name\":\"myObj1\"}}}", o -> o.prop, gson);
        testProperty(WithMapIntProp.class, mapTwoObs,
                "{\"prop\":{\"1\":{\"name\":\"myObj1\"},\"2\":{\"name\":\"myObj2\"}}}", o -> o.prop, gson);
    }

    @Theory
    public void testCustomObservableTreeMapInt(@FromDataPoints("all") Gson gson) {
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

        testValue(WithObsMapInt.class, null, "{\"map\":null}", getter, setter, gson);
        testValue(WithObsMapInt.class, mapEmptyObs, "{\"map\":{}}", getter, setter, gson);
        testValue(WithObsMapInt.class, mapOneObs, "{\"map\":{\"1\":{\"name\":\"myObj1\"}}}", getter, setter, gson);
        testValue(WithObsMapInt.class, mapTwoObs, "{\"map\":{\"1\":{\"name\":\"myObj1\"},\"2\":{\"name\":\"myObj2\"}}}",
                getter, setter, gson);
    }

    @Theory
    public void testBoolean(@FromDataPoints("all") Gson gson) {
        testValue(WithBoolean.class, true, "{\"value\":true}", o -> o.value, (o, v) -> o.value = v, gson);
        testValue(WithBoolean.class, false, "{\"value\":false}", o -> o.value, (o, v) -> o.value = v, gson);
    }

    @Theory
    public void testInteger(@FromDataPoints("all") Gson gson) {
        testValue(WithInteger.class, 0, "{\"value\":0}", o -> o.value, (o, v) -> o.value = v, gson);
        testValue(WithInteger.class, 5, "{\"value\":5}", o -> o.value, (o, v) -> o.value = v, gson);
        testValue(WithInteger.class, -3, "{\"value\":-3}", o -> o.value, (o, v) -> o.value = v, gson);
    }

    @Theory
    public void testLong(@FromDataPoints("all") Gson gson) {
        testValue(WithLong.class, 0L, "{\"value\":0}", o -> o.value, (o, v) -> o.value = v, gson);
        testValue(WithLong.class, 5L, "{\"value\":5}", o -> o.value, (o, v) -> o.value = v, gson);
        testValue(WithLong.class, -3L, "{\"value\":-3}", o -> o.value, (o, v) -> o.value = v, gson);
    }

    @Theory
    public void testFloat(@FromDataPoints("all") Gson gson) {
        testValue(WithFloat.class, 0f, "{\"value\":0.0}", o -> o.value, (o, v) -> o.value = v, gson);
        testValue(WithFloat.class, 2.5f, "{\"value\":2.5}", o -> o.value, (o, v) -> o.value = v, gson);
        testValue(WithFloat.class, -3.5f, "{\"value\":-3.5}", o -> o.value, (o, v) -> o.value = v, gson);
    }

    @Theory
    public void testDouble(@FromDataPoints("all") Gson gson) {
        testValue(WithDouble.class, 0d, "{\"value\":0.0}", o -> o.value, (o, v) -> o.value = v, gson);
        testValue(WithDouble.class, 2.5d, "{\"value\":2.5}", o -> o.value, (o, v) -> o.value = v, gson);
        testValue(WithDouble.class, -3.5d, "{\"value\":-3.5}", o -> o.value, (o, v) -> o.value = v, gson);
    }

    @Theory
    public void testString(@FromDataPoints("all") Gson gson) {
        testValue(WithString.class, "myValue", "{\"value\":\"myValue\"}", o -> o.value, (o, v) -> o.value = v, gson);
        testValue(WithString.class, "", "{\"value\":\"\"}", o -> o.value, (o, v) -> o.value = v, gson);
        testValue(WithString.class, null, "{\"value\":null}", o -> o.value, (o, v) -> o.value = v, gson);
    }

    @Theory
    public void testObject(@FromDataPoints("all") Gson gson) {
        CustomObject obj = new CustomObject("myValue");
        testValue(WithCustomObject.class, obj, "{\"value\":{\"name\":\"myValue\"}}", o -> o.value,
            (o, v) -> o.value = v, gson);
        testValue(WithCustomObject.class, null, "{\"value\":null}", o -> o.value, (o, v) -> o.value = v, gson);
    }

    @Theory
    public void testList(@FromDataPoints("all") Gson gson) {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        List<CustomObject> listEmpty = Collections.emptyList();
        List<CustomObject> listOne = Collections.singletonList(one);
        List<CustomObject> listTwo = Arrays.asList(one, two);

        Function<WithList, List<CustomObject>> getter = o -> o.list;
        BiConsumer<WithList, List<CustomObject>> setter = (o, l) -> o.list = l;

        testValue(WithList.class, null, "{\"list\":null}", getter, setter, gson);
        testValue(WithList.class, listEmpty, "{\"list\":[]}", getter, setter, gson);
        testValue(WithList.class, listOne, "{\"list\":[{\"name\":\"myObj1\"}]}", getter, setter, gson);
        testValue(WithList.class, listTwo, "{\"list\":[{\"name\":\"myObj1\"},{\"name\":\"myObj2\"}]}", getter, setter,
                gson);
    }

    @Theory
    public void testSet(@FromDataPoints("all") Gson gson) {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        Set<CustomObject> setEmpty = Collections.emptySet();
        Set<CustomObject> setOne = Collections.singleton(one);
        Set<CustomObject> setTwo = new HashSet<>(Arrays.asList(one, two));

        Function<WithSet, Set<CustomObject>> getter = o -> o.set;
        BiConsumer<WithSet, Set<CustomObject>> setter = (o, s) -> o.set = s;

        testValue(WithSet.class, null, "{\"set\":null}", getter, setter, gson);
        testValue(WithSet.class, setEmpty, "{\"set\":[]}", getter, setter, gson);
        testValue(WithSet.class, setOne, "{\"set\":[{\"name\":\"myObj1\"}]}", getter, setter, gson);
        // do not check a particular JSON because the order is non-deterministic
        testValue(WithSet.class, setTwo, null, getter, setter, gson);
    }

    @Theory
    public void testMapStr(@FromDataPoints("all") Gson gson) {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        Map<String, CustomObject> mapEmpty = Collections.emptyMap();
        Map<String, CustomObject> mapOne = Collections.singletonMap("key1", one);
        Map<String, CustomObject> mapTwo = new HashMap<>();
        mapTwo.put("key1", one);
        mapTwo.put("key2", two);

        Function<WithMapStr, Map<String, CustomObject>> getter = o -> o.map;
        BiConsumer<WithMapStr, Map<String, CustomObject>> setter = (o, m) -> o.map = m;

        testValue(WithMapStr.class, null, "{\"map\":null}", getter, setter, gson);
        testValue(WithMapStr.class, mapEmpty, "{\"map\":{}}", getter, setter, gson);
        testValue(WithMapStr.class, mapOne, "{\"map\":{\"key1\":{\"name\":\"myObj1\"}}}", getter, setter, gson);
        testValue(WithMapStr.class, mapTwo, "{\"map\":{\"key1\":{\"name\":\"myObj1\"},\"key2\":{\"name\":\"myObj2\"}}}",
                getter, setter, gson);
    }

    @Theory
    public void testMapInt(@FromDataPoints("all") Gson gson) {
        CustomObject one = new CustomObject("myObj1");
        CustomObject two = new CustomObject("myObj2");

        Map<Integer, CustomObject> mapEmpty = Collections.emptyMap();
        Map<Integer, CustomObject> mapOne = Collections.singletonMap(1, one);
        Map<Integer, CustomObject> mapTwo = new HashMap<>();
        mapTwo.put(1, one);
        mapTwo.put(2, two);

        Function<WithMapInt, Map<Integer, CustomObject>> getter = o -> o.map;
        BiConsumer<WithMapInt, Map<Integer, CustomObject>> setter = (o, m) -> o.map = m;

        testValue(WithMapInt.class, null, "{\"map\":null}", getter, setter, gson);
        testValue(WithMapInt.class, mapEmpty, "{\"map\":{}}", getter, setter, gson);
        testValue(WithMapInt.class, mapOne, "{\"map\":{\"1\":{\"name\":\"myObj1\"}}}", getter, setter, gson);
        testValue(WithMapInt.class, mapTwo, "{\"map\":{\"1\":{\"name\":\"myObj1\"},\"2\":{\"name\":\"myObj2\"}}}",
                getter, setter, gson);
    }

    @Theory
    public void testFont(@FromDataPoints("extra") Gson gson) {
        String family = "SansSerif";
        FontWeight weight = FontWeight.findByName("Regular");
        double size = 11.0;
        Font font = Font.font(family, weight, size);

        Function<WithFont, Font> getter = o -> o.font;
        BiConsumer<WithFont, Font> setter = (o, f) -> o.font = f;

        testValue(WithFont.class, null, "{\"font\":null}", getter, setter, gson);
        testValue(WithFont.class, font, "{\"font\":\"SansSerif,Regular,11.0\"}", getter, setter, gson);
    }

    @Theory
    public void testFontProperty(@FromDataPoints("extra") Gson gson) {
        String family = "SansSerif";
        FontWeight weight = FontWeight.findByName("Regular");
        double size = 11.0;
        Font font = Font.font(family, weight, size);

        testProperty(WithFontProp.class, null, "{\"prop\":null}", o -> o.prop, gson);
        testProperty(WithFontProp.class, font, "{\"prop\":\"SansSerif,Regular,11.0\"}", o -> o.prop, gson);
    }

    @Theory
    public void testColor(@FromDataPoints("extra") Gson gson) {
        Function<WithColor, Color> getter = o -> o.color;
        BiConsumer<WithColor, Color> setter = (o, c) -> o.color = c;

        testValue(WithColor.class, null, "{\"color\":null}", getter, setter, gson);
        testValue(WithColor.class, Color.RED, "{\"color\":\"#ff0000ff\"}", getter, setter, gson);
        testValue(WithColor.class, Color.BLUE, "{\"color\":\"#0000ffff\"}", getter, setter, gson);
    }

    @Theory
    public void testColorProperty(@FromDataPoints("extra") Gson gson) {
        testProperty(WithColorProp.class, null, "{\"prop\":null}", o -> o.prop, gson);
        testProperty(WithColorProp.class, Color.RED, "{\"prop\":\"#ff0000ff\"}", o -> o.prop, gson);
        testProperty(WithColorProp.class, Color.BLUE, "{\"prop\":\"#0000ffff\"}", o -> o.prop, gson);
    }
}
