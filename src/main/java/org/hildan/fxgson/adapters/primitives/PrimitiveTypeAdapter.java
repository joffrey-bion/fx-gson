package org.hildan.fxgson.adapters.primitives;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * An abstract base for {@link TypeAdapter}s of primitive values. By default, it throws {@link NullPrimitiveException}
 * when used to deserialize a null JSON value (which is illegal for a primitive). It can be configured to instantiate a
 * default value instead in this case.
 *
 * @param <I>
 *         the primitive type inside the property
 * @param <P>
 *         the type that this adapter can serialize/deserialize
 */
public abstract class PrimitiveTypeAdapter<I, P> extends TypeAdapter<P> {

    private final boolean crashOnNullValue;

    private final TypeAdapter<I> delegate;

    /**
     * Creates a new PrimitiveTypeAdapter.
     *
     * @param crashOnNullValue
     *         if true, this adapter will throw {@link NullPrimitiveException} when reading a null value. If false, this
     *         adapter will call {@link #createDefaultProperty()} instead.
     * @param delegate
     *         a delegate adapter to use for the inner value of the property
     */
    public PrimitiveTypeAdapter(boolean crashOnNullValue, TypeAdapter<I> delegate) {
        this.crashOnNullValue = crashOnNullValue;
        this.delegate = delegate;
    }

    @Override
    public void write(JsonWriter out, P value) throws IOException {
        delegate.write(out, extractPrimitiveValue(value));
    }

    @Override
    public P read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            if (crashOnNullValue) {
                String error = "Illegal null value for a primitive type at path " + in.getPath();
                throw new NullPrimitiveException(error);
            } else {
                return createDefaultProperty();
            }
        } else {
            return wrapNonNullPrimitiveValue(delegate.read(in));
        }
    }

    protected abstract I extractPrimitiveValue(P property);

    /**
     * Creates a default T value. This is used when this adapter deserializes a null value from the input JSON, but only
     * if this adapter is set not to crash.
     *
     * @return a default value to use when null is found
     */
    protected abstract P createDefaultProperty();

    /**
     * Wraps the deserialized primitive value in a Property object of the right type. The next value of the reader is
     * guaranteed to be non-null when this method is called.
     *
     * @param deserializedValue
     *         the deserialized inner primitive value of the property
     *
     * @return a new property object containing the given value
     */
    protected abstract P wrapNonNullPrimitiveValue(I deserializedValue);
}
