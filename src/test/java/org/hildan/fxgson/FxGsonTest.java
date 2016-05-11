package org.hildan.fxgson;

import java.util.function.BiConsumer;
import java.util.function.Function;

import javafx.beans.property.Property;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import static org.hildan.fxgson.TestClasses.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FxGsonTest {

    private static final int STUB_INT_ZERO = 0;

    private static final int STUB_INT_POS = 5;

    private static final int STUB_INT_NEG = -3;

    private static final long STUB_LONG_ZERO = 0L;

    private static final long STUB_LONG_POS = 5L;

    private static final long STUB_LONG_NEG = -3L;

    private static final float STUB_FLOAT_ZERO = 0.0F;

    private static final float STUB_FLOAT_POS = 2.5F;

    private static final float STUB_FLOAT_NEG = -3.5F;

    private static final double STUB_DOUBLE_ZERO = 0.0;

    private static final double STUB_DOUBLE_POS = 2.5;

    private static final double STUB_DOUBLE_NEG = -3.5F;

    private static final String STUB_STR_NULL = null;

    private static final String STUB_STR_VALUE = "strvalue";

    private static final String STUB_STR_ONE = "one";

    private static final String STUB_STR_TWO = "two";

    private static final String JSON_NUMBER_ZERO = "{\"num\":0}";

    private static final String JSON_NUMBER_POS = "{\"num\":5}";

    private static final String JSON_NUMBER_NEG = "{\"num\":-3}";

    private static final String JSON_DECIMAL_ZERO = "{\"num\":0.0}";

    private static final String JSON_DECIMAL_POS = "{\"num\":2.5}";

    private static final String JSON_DECIMAL_NEG = "{\"num\":-3.5}";

    private static final String JSON_STR_NULL = "{\"str\":null}";

    private static final String JSON_STR_VALUE = "{\"str\":\"strvalue\"}";

    private static final String JSON_SUB_NULL = "{\"obj\":null}";

    private static final String JSON_SUB_VALUE = "{\"obj\":{\"name\":\"strvalue\"}}";

    private static final String JSON_LIST_NULL = "{\"list\":null}";

    private static final String JSON_LIST_EMPTY = "{\"list\":[]}";

    private static final String JSON_LIST_ONE_ELT = "{\"list\":[{\"name\":\"one\"}]}";

    private static final String JSON_LIST_MANY_ELT = "{\"list\":[{\"name\":\"one\"},{\"name\":\"two\"}]}";

    private static final String JSON_MAP_NULL = "{\"map\":null}";

    private static final String JSON_MAP_EMPTY = "{\"map\":{}}";

    private static final String JSON_MAP_STR_ONE_ELT = "{\"map\":{\"one\":{\"name\":\"one\"}}}";

    private static final String JSON_MAP_STR_MANY_ELT = "{\"map\":{\"one\":{\"name\":\"one\"},\"two\":{\"name\":\"two\"}}}";

    private static final String JSON_MAP_INT_ONE_ELT = "{\"map\":{\"1\":{\"name\":\"one\"}}}";

    private static final String JSON_MAP_INT_MANY_ELT = "{\"map\":{\"1\":{\"name\":\"one\"},\"2\":{\"name\":\"two\"}}}";

    private Gson coreGson;

    private Gson extraGson;

    @Before
    public void createGson() {
        coreGson = FxGson.coreBuilder().create();
        extraGson = FxGson.fullBuilder().create();
    }

    private static <B, V> void testSerialization(Class<B> baseClass, B baseObj, V valueToTest, String expectedJson,
            Function<B, V> getValue, BiConsumer<B, V> setValue, Gson gson) {
        setValue.accept(baseObj, valueToTest);
        assertEquals("Incorrect JSON generated", expectedJson, gson.toJson(baseObj));

        B deserialized = gson.fromJson(expectedJson, baseClass);
        assertEquals("Incorrect deserialized value", valueToTest, getValue.apply(deserialized));
    }

    private <B, V> void testSerialization(Class<B> baseClass, B baseObj, V valueToTest, String expectedJson,
            Function<B, V> getValue, BiConsumer<B, V> setValue) {
        testSerialization(baseClass, baseObj, valueToTest, expectedJson, getValue, setValue, coreGson);
        testSerialization(baseClass, baseObj, valueToTest, expectedJson, getValue, setValue, extraGson);
    }

    private static <B, V> void testProperty(Class<B> baseClass, B baseObj, V valueToTest, String expectedJson,
            Function<B, Property<V>> getProperty, Gson gson) {
        getProperty.apply(baseObj).setValue(valueToTest);
        assertEquals("Incorrect JSON generated", expectedJson, gson.toJson(baseObj));

        B deserialized = gson.fromJson(expectedJson, baseClass);
        assertNotNull("The property itself should not be null (only its content may)", getProperty.apply(deserialized));
        assertEquals("Incorrect deserialized value", valueToTest, getProperty.apply(deserialized).getValue());
    }

    private <B, V> void testProperty(Class<B> baseClass, B baseObj, V valueToTest, String expectedJson,
            Function<B, Property<V>> getProperty) {
        testProperty(baseClass, baseObj, valueToTest, expectedJson, getProperty, coreGson);
        testProperty(baseClass, baseObj, valueToTest, expectedJson, getProperty, extraGson);
    }

    @Test
    public void testBooleanProperty() {
        BaseWithBoolean obj = new BaseWithBoolean();
        testProperty(BaseWithBoolean.class, obj, true, "{\"bool\":true}", o -> o.bool);
        testProperty(BaseWithBoolean.class, obj, false, "{\"bool\":false}", o -> o.bool);
    }

    @Test
    public void testIntegerProperty() {
        BaseWithInteger obj = new BaseWithInteger();
        testProperty(BaseWithInteger.class, obj, STUB_INT_ZERO, JSON_NUMBER_ZERO, o -> o.num);
        testProperty(BaseWithInteger.class, obj, STUB_INT_POS, JSON_NUMBER_POS, o -> o.num);
        testProperty(BaseWithInteger.class, obj, STUB_INT_NEG, JSON_NUMBER_NEG, o -> o.num);
    }

    @Test
    public void testLongProperty() {
        BaseWithLong obj = new BaseWithLong();
        testProperty(BaseWithLong.class, obj, STUB_LONG_ZERO, JSON_NUMBER_ZERO, o -> o.num);
        testProperty(BaseWithLong.class, obj, STUB_LONG_POS, JSON_NUMBER_POS, o -> o.num);
        testProperty(BaseWithLong.class, obj, STUB_LONG_NEG, JSON_NUMBER_NEG, o -> o.num);
    }

    @Test
    public void testFloatProperty() {
        BaseWithFloat obj = new BaseWithFloat();
        testProperty(BaseWithFloat.class, obj, STUB_FLOAT_ZERO, JSON_DECIMAL_ZERO, o -> o.num);
        testProperty(BaseWithFloat.class, obj, STUB_FLOAT_POS, JSON_DECIMAL_POS, o -> o.num);
        testProperty(BaseWithFloat.class, obj, STUB_FLOAT_NEG, JSON_DECIMAL_NEG, o -> o.num);
    }

    @Test
    public void testDoubleProperty() {
        BaseWithDouble obj = new BaseWithDouble();
        testProperty(BaseWithDouble.class, obj, STUB_DOUBLE_ZERO, JSON_DECIMAL_ZERO, o -> o.num);
        testProperty(BaseWithDouble.class, obj, STUB_DOUBLE_POS, JSON_DECIMAL_POS, o -> o.num);
        testProperty(BaseWithDouble.class, obj, STUB_DOUBLE_NEG, JSON_DECIMAL_NEG, o -> o.num);
    }

    @Test
    public void testStringProperty() {
        testProperty(BaseWithString.class, new BaseWithString(), STUB_STR_VALUE, JSON_STR_VALUE, o -> o.str);
        testProperty(BaseWithString.class, new BaseWithString(), STUB_STR_NULL, JSON_STR_NULL, o -> o.str);
    }

    @Test
    public void testObjectProperty() {
        CustomObject obj = new CustomObject(STUB_STR_VALUE);
        testProperty(BaseWithComplexObject.class, new BaseWithComplexObject(), obj, JSON_SUB_VALUE, o -> o.obj);
        testProperty(BaseWithComplexObject.class, new BaseWithComplexObject(), null, JSON_SUB_NULL, o -> o.obj);
    }

    @Test
    public void testList() {
        CustomObject one = new CustomObject(STUB_STR_ONE);
        CustomObject two = new CustomObject(STUB_STR_TWO);
        ObservableList<CustomObject> listEmpty = FXCollections.observableArrayList();
        ObservableList<CustomObject> listOne = FXCollections.observableArrayList(one);
        ObservableList<CustomObject> listTwo = FXCollections.observableArrayList(one, two);

        testSerialization(BaseWithList.class, new BaseWithList(), null, JSON_LIST_NULL, o -> o.list,
                (o, l) -> o.list = l);
        testSerialization(BaseWithList.class, new BaseWithList(), listEmpty, JSON_LIST_EMPTY, o -> o.list,
                (o, l) -> o.list = l);
        testSerialization(BaseWithList.class, new BaseWithList(), listOne, JSON_LIST_ONE_ELT, o -> o.list,
                (o, l) -> o.list = l);
        testSerialization(BaseWithList.class, new BaseWithList(), listTwo, JSON_LIST_MANY_ELT, o -> o.list,
                (o, l) -> o.list = l);
    }

    @Test
    public void testSet() {
        CustomObject one = new CustomObject(STUB_STR_ONE);
        ObservableSet<CustomObject> listEmpty = FXCollections.emptyObservableSet();
        ObservableSet<CustomObject> listOne = FXCollections.observableSet(one);

        testSerialization(BaseWithSet.class, new BaseWithSet(), null, JSON_LIST_NULL, o -> o.list,
                (o, l) -> o.list = l);
        testSerialization(BaseWithSet.class, new BaseWithSet(), listEmpty, JSON_LIST_EMPTY, o -> o.list,
                (o, l) -> o.list = l);
        testSerialization(BaseWithSet.class, new BaseWithSet(), listOne, JSON_LIST_ONE_ELT, o -> o.list,
                (o, l) -> o.list = l);
    }

    @Test
    public void testMapStr() {
        CustomObject one = new CustomObject(STUB_STR_ONE);
        CustomObject two = new CustomObject(STUB_STR_TWO);
        ObservableMap<String, CustomObject> listEmpty = FXCollections.emptyObservableMap();
        ObservableMap<String, CustomObject> listOne = FXCollections.observableHashMap();
        listOne.put(STUB_STR_ONE, one);
        ObservableMap<String, CustomObject> listTwo = FXCollections.observableHashMap();
        listTwo.put(STUB_STR_ONE, one);
        listTwo.put(STUB_STR_TWO, two);

        testSerialization(BaseWithMapStr.class, new BaseWithMapStr(), null, JSON_MAP_NULL, o -> o.map,
                (o, l) -> o.map = l);
        testSerialization(BaseWithMapStr.class, new BaseWithMapStr(), listEmpty, JSON_MAP_EMPTY, o -> o.map,
                (o, l) -> o.map = l);
        testSerialization(BaseWithMapStr.class, new BaseWithMapStr(), listOne, JSON_MAP_STR_ONE_ELT, o -> o.map,
                (o, l) -> o.map = l);
        testSerialization(BaseWithMapStr.class, new BaseWithMapStr(), listTwo, JSON_MAP_STR_MANY_ELT, o -> o.map,
                (o, l) -> o.map = l);
    }

    @Test
    public void testMapInt() {
        CustomObject one = new CustomObject(STUB_STR_ONE);
        CustomObject two = new CustomObject(STUB_STR_TWO);
        ObservableMap<Integer, CustomObject> listEmpty = FXCollections.emptyObservableMap();
        ObservableMap<Integer, CustomObject> listOne = FXCollections.observableHashMap();
        listOne.put(1, one);
        ObservableMap<Integer, CustomObject> listTwo = FXCollections.observableHashMap();
        listTwo.put(1, one);
        listTwo.put(2, two);

        testSerialization(BaseWithMapInt.class, new BaseWithMapInt(), null, JSON_MAP_NULL, o -> o.map,
                (o, l) -> o.map = l);
        testSerialization(BaseWithMapInt.class, new BaseWithMapInt(), listEmpty, JSON_MAP_EMPTY, o -> o.map,
                (o, l) -> o.map = l);
        testSerialization(BaseWithMapInt.class, new BaseWithMapInt(), listOne, JSON_MAP_INT_ONE_ELT, o -> o.map,
                (o, l) -> o.map = l);
        testSerialization(BaseWithMapInt.class, new BaseWithMapInt(), listTwo, JSON_MAP_INT_MANY_ELT, o -> o.map,
                (o, l) -> o.map = l);
    }
}