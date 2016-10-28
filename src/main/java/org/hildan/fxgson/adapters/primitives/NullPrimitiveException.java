package org.hildan.fxgson.adapters.primitives;

import org.jetbrains.annotations.NonNls;

/**
 * Thrown when a null value is found in place of a primitive type value.
 */
public class NullPrimitiveException extends RuntimeException {

    public NullPrimitiveException(@NonNls String message) {
        super(message);
    }
}
