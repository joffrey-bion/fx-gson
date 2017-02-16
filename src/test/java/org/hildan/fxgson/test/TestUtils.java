package org.hildan.fxgson.test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import static org.junit.Assert.assertEquals;

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
}
