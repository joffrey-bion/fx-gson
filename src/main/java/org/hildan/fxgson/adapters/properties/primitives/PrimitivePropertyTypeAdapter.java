package org.hildan.fxgson.adapters.properties.primitives;

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
public abstract class PrimitivePropertyTypeAdapter<I, P> extends TypeAdapter<P> {

    private final TypeAdapter<I> delegate;

    private final boolean crashOnNullValue;

    /**
     * Creates a new PrimitivePropertyTypeAdapter.
     *
     * @param crashOnNullValue
     *         if true, this adapter will throw {@link NullPrimitiveException} when reading a null value. If false, this
     *         adapter will call {@link #createDefaultProperty()} instead.
     * @param delegate
     *         a delegate adapter to use for the inner value of the property
     */
    public PrimitivePropertyTypeAdapter(boolean crashOnNullValue, TypeAdapter<I> delegate) {
        this.delegate = delegate;
        this.crashOnNullValue = crashOnNullValue;
    }

    @Override
    public void write(JsonWriter out, P property) throws IOException {
        if (property == null) {
            out.nullValue();
            return;
        }
        delegate.write(out, extractPrimitiveValue(property));
    }

    @Override
    public P read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            if (crashOnNullValue) {
                throw new NullPrimitiveException(in.getPath());
            } else {
                return createDefaultProperty();
            }
        } else {
            return wrapNonNullPrimitiveValue(delegate.read(in));
        }
    }

    /**
     * Gets the current value of the given property.
     *
     * @param property
     *         the property to get the value for
     *
     * @return the current value of the given property
     */
    protected abstract I extractPrimitiveValue(P property);

    /**
     * Creates a default property object. This is used when this adapter deserializes a null value from the input JSON,
     * but only if this adapter is set not to crash (see {@link #PrimitivePropertyTypeAdapter(boolean, TypeAdapter)}).
     *
     * @return a default value to use when null is found
     */
    protected abstract P createDefaultProperty();

    /**
     * Wraps the deserialized primitive value in a Property object of the right type.
     *
     * @param deserializedValue
     *         the deserialized inner primitive value of the property, may not be null
     *
     * @return a new property object containing the given value
     */
    protected abstract P wrapNonNullPrimitiveValue(I deserializedValue);
}
