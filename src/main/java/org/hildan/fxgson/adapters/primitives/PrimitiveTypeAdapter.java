package org.hildan.fxgson.adapters.primitives;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

/**
 * An abstract base for {@link TypeAdapter}s of primitive values. By default, it throws {@link NullPrimitiveException}
 * when used to deserialize a null JSON value (which is illegal for a primitive). It can be configured to instantiate a
 * default value instead in this case.
 *
 * @param <T>
 *         the type that this adapter can serialize/deserialize
 */
public abstract class PrimitiveTypeAdapter<T> extends TypeAdapter<T> {

    private final boolean crashOnNullValue;

    /**
     * Creates a new PrimitiveTypeAdapter.
     *
     * @param crashOnNullValue
     *         if true, this adapter will throw {@link NullPrimitiveException} when reading a null value. If false, this
     *         adapter will call {@link #createDefaultValue()} instead.
     */
    public PrimitiveTypeAdapter(boolean crashOnNullValue) {
        this.crashOnNullValue = crashOnNullValue;
    }

    @Override
    public T read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            if (crashOnNullValue) {
                String error = "Illegal null value for a primitive type at path " + in.getPath();
                throw new NullPrimitiveException(error);
            } else {
                return createDefaultValue();
            }
        } else {
            return readNonNullValue(in);
        }
    }

    /**
     * Creates a default T value. This is used when this adapter deserializes a null value from the input JSON, but only
     * if this adapter is set not to crash.
     *
     * @return a default value to use when null is found
     */
    protected abstract T createDefaultValue();

    /**
     * Reads one JSON value and converts is to a Java object. The next value of the reader is guaranteed to be non-null
     * when this method is called.
     *
     * @param in
     *         the current reader
     *
     * @return the converted Java object
     *
     * @throws IOException
     *         if an I/O error occurs when reading the JSON
     */
    protected abstract T readNonNullValue(JsonReader in) throws IOException;
}
