package org.hildan.fxgson;

import java.lang.reflect.Type;

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
import javafx.beans.property.SimpleStringProperty;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hildan.fxgson.TestClassesSimple.WithBoolean;
import org.hildan.fxgson.TestClassesSimple.WithDouble;
import org.hildan.fxgson.TestClassesSimple.WithFloat;
import org.hildan.fxgson.TestClassesSimple.WithInteger;
import org.hildan.fxgson.TestClassesSimple.WithLong;
import org.hildan.fxgson.TestClassesSimple.WithString;
import org.hildan.fxgson.TestClassesWithProp.WithBooleanProp;
import org.hildan.fxgson.TestClassesWithProp.WithDoubleProp;
import org.hildan.fxgson.TestClassesWithProp.WithFloatProp;
import org.hildan.fxgson.TestClassesWithProp.WithIntegerProp;
import org.hildan.fxgson.TestClassesWithProp.WithLongProp;
import org.hildan.fxgson.TestClassesWithProp.WithStringProp;
import org.hildan.fxgson.adapters.properties.NullPropertyException;
import org.hildan.fxgson.adapters.properties.primitives.NullPrimitiveException;
import org.hildan.fxgson.factories.JavaFxPropertyTypeAdapterFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Theories.class)
public class FxGsonSimpleTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static Gson vanillaGson;

    private static Gson vanillaGsonSpecialFloat;

    @BeforeClass
    public static void setUp() throws Exception {
        vanillaGson = new Gson();
        vanillaGsonSpecialFloat = new GsonBuilder().serializeSpecialFloatingPointValues().create();
    }

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
        return new Gson[]{gson1, gson2, gson3, gson4, gson5, gson6};
    }

    @DataPoints({"all", "extra", "strictProperties", "strictPrimitives"})
    public static Gson[] extraGsons() {
        Gson gson1 = FxGson.createWithExtras();
        Gson gson2 = FxGson.fullBuilder().create();
        Gson gson3 = new FxGsonBuilder().withExtras().create();
        Gson gson4 = new FxGsonBuilder(new GsonBuilder()).withExtras().create();
        return new Gson[]{gson1, gson2, gson3, gson4};
    }

    @DataPoints({"all", "strictProperties", "safePrimitives"})
    public static Gson[] safePrimitivesGsons() {
        Gson gson1 = new FxGsonBuilder().acceptNullPrimitives().create();
        Gson gson2 = new FxGsonBuilder(new GsonBuilder()).acceptNullPrimitives().create();
        return new Gson[]{gson1, gson2};
    }

    @DataPoints({"all", "strictProperties", "safePrimitives", "extra"})
    public static Gson[] safePrimitivesAndExtraGsons() {
        Gson gson1 = new FxGsonBuilder().acceptNullPrimitives().withExtras().create();
        Gson gson2 = new FxGsonBuilder(new GsonBuilder()).acceptNullPrimitives().withExtras().create();
        return new Gson[]{gson1, gson2};
    }

    @DataPoints({"all", "safeProperties", "strictPrimitives"})
    public static Gson[] safePropertiesGsons() {
        Gson gson1 = new FxGsonBuilder().acceptNullProperties().create();
        Gson gson2 = new FxGsonBuilder(new GsonBuilder()).acceptNullProperties().create();
        return new Gson[]{gson1, gson2};
    }

    @DataPoints({"all", "safeProperties", "strictPrimitives", "extra"})
    public static Gson[] safePropertiesAndExtraGsons() {
        Gson gson1 = new FxGsonBuilder().acceptNullProperties().withExtras().create();
        Gson gson2 = new FxGsonBuilder(new GsonBuilder()).acceptNullProperties().withExtras().create();
        return new Gson[]{gson1, gson2};
    }

    @DataPoints({"all", "safeProperties", "safePrimitives"})
    public static Gson[] safePropertiesAndPrimitivesGsons() {
        Gson gson1 = new FxGsonBuilder().acceptNullProperties().acceptNullPrimitives().create();
        Gson gson2 = new FxGsonBuilder(new GsonBuilder()).acceptNullProperties().acceptNullPrimitives().create();
        return new Gson[]{gson1, gson2};
    }

    @DataPoints({"all", "safeProperties", "safePrimitives", "extra"})
    public static Gson[] safePropertiesAndPrimitivesAndExtraGsons() {
        Gson gson1 = new FxGsonBuilder().acceptNullProperties().acceptNullPrimitives().withExtras().create();
        Gson gson2 = new FxGsonBuilder(new GsonBuilder()).acceptNullProperties()
                                                         .acceptNullPrimitives()
                                                         .withExtras()
                                                         .create();
        return new Gson[]{gson1, gson2};
    }

    @DataPoints({"all", "specialFloat"})
    public static Gson[] specialFLoatGsons() {
        Gson gson1 = FxGson.coreBuilder().serializeSpecialFloatingPointValues().create();
        Gson gson2 = new FxGsonBuilder().builder().serializeSpecialFloatingPointValues().create();
        Gson gson3 = new FxGsonBuilder(new GsonBuilder()).builder().serializeSpecialFloatingPointValues().create();
        return new Gson[]{gson1, gson2, gson3};
    }

    @DataPoints
    public static boolean[] bools() {
        return new boolean[]{true, false};
    }

    @DataPoints
    public static int[] ints() {
        return new int[]{-3, 0, 42};
    }

    @DataPoints
    public static long[] longs() {
        return new long[]{-3, 0, 42};
    }

    @DataPoints
    public static float[] floats() {
        return new float[]{-3.5f, 0f, 42.3f};
    }

    @DataPoints
    public static double[] doubles() {
        return new double[]{-3.5d, 0d, 42.3d};
    }

    @DataPoints
    public static String[] strings() {
        return new String[]{null, "", " ", "test"};
    }

    @DataPoints
    public static Class<?>[] primitivePropertyClasses() {
        return new Class<?>[]{
                BooleanProperty.class,
                IntegerProperty.class,
                LongProperty.class,
                FloatProperty.class,
                DoubleProperty.class
        };
    }

    private static <T> void testSerialization(Gson gson, T value, Property<?> propValue, Object wrapper,
                                              Object propWrapper) {
        String vJson = vanillaGson.toJson(value);
        assertEquals(vJson, gson.toJson(value));
        assertEquals(vJson, gson.toJson(propValue));
        assertEquals(String.format("{\"value\":%s}", vJson), gson.toJson(wrapper));
        assertEquals(String.format("{\"prop\":%s}", vJson), gson.toJson(propWrapper));
    }

    private static <T> void testDeserialization(Gson gson, T value, Property<?> propValue, Object wrapper,
                                                Object propWrapper, Type type) {
        String vJson = vanillaGson.toJson(value);
        assertEquals(value, gson.fromJson(vJson, type));
        // properties don't override equals, so we need to compare the values
        assertEquals(propValue.getValue(), gson.fromJson(vJson, propValue.getClass()).getValue());
        assertEquals(wrapper, gson.fromJson(String.format("{\"value\":%s}", vJson), wrapper.getClass()));
        assertEquals(propWrapper, gson.fromJson(String.format("{\"prop\":%s}", vJson), propWrapper.getClass()));
    }

    private static <T> void testBothWays(Gson gson, @NotNull T value, Property<?> propValue, Object wrapper,
                                         Object propWrapper) {
        testSerialization(gson, value, propValue, wrapper, propWrapper);
        testDeserialization(gson, value, propValue, wrapper, propWrapper, value.getClass());
    }

    private static <T> void testBothWays(Gson gson, @Nullable T value, Property<?> propValue, Object wrapper,
                                         Object propWrapper, Type typeOfValue) {
        testSerialization(gson, value, propValue, wrapper, propWrapper);
        testDeserialization(gson, value, propValue, wrapper, propWrapper, typeOfValue);
    }

    @Theory
    public void test_boolean(boolean value, @FromDataPoints("all") Gson gson) {
        testBothWays(gson, value, new SimpleBooleanProperty(value), new WithBoolean(value), new WithBooleanProp(value));
    }

    @Theory
    public void test_int(int value, @FromDataPoints("all") Gson gson) {
        testBothWays(gson, value, new SimpleIntegerProperty(value), new WithInteger(value), new WithIntegerProp(value));
    }

    @Theory
    public void test_long(long value, @FromDataPoints("all") Gson gson) {
        testBothWays(gson, value, new SimpleLongProperty(value), new WithLong(value), new WithLongProp(value));
    }

    @Theory
    public void test_float(float value, @FromDataPoints("all") Gson gson) {
        testBothWays(gson, value, new SimpleFloatProperty(value), new WithFloat(value), new WithFloatProp(value));
    }

    @Theory
    public void test_double(double value, @FromDataPoints("all") Gson gson) {
        testBothWays(gson, value, new SimpleDoubleProperty(value), new WithDouble(value), new WithDoubleProp(value));
    }

    @Theory
    public void test_strings(String value, @FromDataPoints("all") Gson gson) {
        testBothWays(gson, value, new SimpleStringProperty(value), new WithString(value), new WithStringProp(value),
                String.class);
    }

    @Theory
    public void testNullPrimitivesFail(Class<?> primitivePropertyClass, @FromDataPoints("strictPrimitives") Gson gson) {
        thrown.expect(NullPrimitiveException.class);
        gson.fromJson("null", primitivePropertyClass);
    }

    @Theory
    public void testNullPropertiesFail_boolean(@FromDataPoints("strictProperties") Gson gson) {
        thrown.expect(NullPropertyException.class);
        WithBooleanProp propContainer = new WithBooleanProp();
        propContainer.prop = null;
        gson.toJson(propContainer);
    }

    @Theory
    public void testNullPropertiesFail_int(@FromDataPoints("strictProperties") Gson gson) {
        thrown.expect(NullPropertyException.class);
        WithIntegerProp propContainer = new WithIntegerProp();
        propContainer.prop = null;
        gson.toJson(propContainer);
    }

    @Theory
    public void testNullPropertiesFail_long(@FromDataPoints("strictProperties") Gson gson) {
        thrown.expect(NullPropertyException.class);
        WithLongProp propContainer = new WithLongProp();
        propContainer.prop = null;
        gson.toJson(propContainer);
    }

    @Theory
    public void testNullPropertiesFail_float(@FromDataPoints("strictProperties") Gson gson) {
        thrown.expect(NullPropertyException.class);
        WithFloatProp propContainer = new WithFloatProp();
        propContainer.prop = null;
        gson.toJson(propContainer);
    }

    @Theory
    public void testNullPropertiesFail_double(@FromDataPoints("strictProperties") Gson gson) {
        thrown.expect(NullPropertyException.class);
        WithDoubleProp propContainer = new WithDoubleProp();
        propContainer.prop = null;
        gson.toJson(propContainer);
    }

    public static void assertPropertyEquals(Property<?> expected, Property<?> actual) {
        // Properties don't override equals, so we need to check inner values manually
        assertEquals(expected.getClass(), actual.getClass());
        assertEquals(expected.getValue(), actual.getValue());
    }
}
