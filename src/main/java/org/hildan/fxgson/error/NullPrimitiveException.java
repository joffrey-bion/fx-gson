package org.hildan.fxgson.error;

import org.jetbrains.annotations.NonNls;

public class NullPrimitiveException extends RuntimeException {

    public NullPrimitiveException(@NonNls String message) {
        super(message);
    }
}
