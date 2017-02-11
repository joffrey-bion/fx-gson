package org.hildan.fxgson.adapters.properties.primitives;

/**
 * Thrown when a null value is found in place of a primitive type value, and the adapter was not configured to accept
 * null values.
 */
public class NullPrimitiveException extends RuntimeException {

    public NullPrimitiveException(String pathInJson) {
        super("Illegal null value for a primitive type at path " + pathInJson);
    }
}
