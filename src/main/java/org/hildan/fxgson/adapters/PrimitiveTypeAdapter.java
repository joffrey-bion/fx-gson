package org.hildan.fxgson.adapters;

import java.io.IOException;

import org.hildan.fxgson.error.NullPrimitiveException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public abstract class PrimitiveTypeAdapter<T> extends TypeAdapter<T> {

    private final boolean crashOnNullValue;

    public PrimitiveTypeAdapter(boolean crashOnNullValue) {
        this.crashOnNullValue = crashOnNullValue;
    }

    @Override
    public T read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            if (crashOnNullValue) {
                throw new NullPrimitiveException("Null value found for a primitive JavaFX property type");
            } else {
                return createDefaultValue();
            }
        }
        else {
            return readNonNullValue(in);
        }
    }

    protected abstract T createDefaultValue();

    protected abstract T readNonNullValue(JsonReader in) throws IOException;
}
