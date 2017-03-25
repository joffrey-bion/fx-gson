package org.hildan.fxgson.test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class TestUtils {

    public static <T> void testWrite(TypeAdapter<T> adapter, Expectation<T> expectation) throws IOException {
        StringWriter strWriter = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(strWriter);
        adapter.write(jsonWriter, expectation.object);
        assertEquals(expectation.json, strWriter.toString());
    }

    public static <T> void testRead(TypeAdapter<T> adapter, Expectation<T> expectation) throws IOException {
        StringReader strReader = new StringReader(expectation.json);
        JsonReader jsonReader = new JsonReader(strReader);
        T deserializedValue = adapter.read(jsonReader);
        assertEquals(expectation.object, deserializedValue);
    }

    public static void assertCannotBeInstantiated(Class<?> cls) {
        try {
            final Constructor<?> c = cls.getDeclaredConstructors()[0];
            c.setAccessible(true);
            c.newInstance();
            fail();
        } catch (InvocationTargetException ite) {
            Throwable targetException = ite.getTargetException();
            assertNotNull(targetException);
            assertEquals(targetException.getClass(), InstantiationException.class);
        } catch (IllegalAccessException e) {
            fail("the constructor is made accessible in the test, this should not happen");
        } catch (InstantiationException e) {
            fail("this test is not expected to be run on abstract classes");
        }
    }
}
