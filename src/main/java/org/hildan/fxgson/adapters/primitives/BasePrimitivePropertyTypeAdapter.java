package org.hildan.fxgson.adapters.primitives;

import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gson.TypeAdapter;

class BasePrimitivePropertyTypeAdapter<I, P> extends PrimitiveTypeAdapter<I, P> {

    private final Function<P, I> extractPrimitiveValue;

    private final Supplier<P> createDefaultProperty;

    private final Function<I, P> wrapPrimitiveValue;

    /**
     * Creates a new PrimitiveTypeAdapter.
     *
     * @param crashOnNullValue
     *         if true, this adapter will throw {@link NullPrimitiveException} when reading a null value. If false, this
     *         adapter will call {@link #createDefaultProperty()} instead.
     * @param delegate
     */
    public BasePrimitivePropertyTypeAdapter(boolean crashOnNullValue, TypeAdapter<I> delegate,
            Function<P, I> extractPrimitiveValue, Supplier<P> createDefaultProperty,
            Function<I, P> wrapPrimitiveValue) {
        super(crashOnNullValue, delegate);
        this.extractPrimitiveValue = extractPrimitiveValue;
        this.createDefaultProperty = createDefaultProperty;
        this.wrapPrimitiveValue = wrapPrimitiveValue;
    }

    @Override
    protected I extractPrimitiveValue(P property) {
        return extractPrimitiveValue.apply(property);
    }

    @Override
    protected P createDefaultProperty() {
        return createDefaultProperty.get();
    }

    @Override
    protected P wrapNonNullPrimitiveValue(I deserializedValue) {
        return wrapPrimitiveValue.apply(deserializedValue);
    }
}
