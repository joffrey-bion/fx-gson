package org.hildan.fxgson.serializers;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A helper to handle {@link Type} objects creation when dealing with deserialization of generic types.
 */
class TypeHelper {

    @NotNull
    static ParameterizedType newParametrizedType(@NotNull Type rawType, @NotNull Type... typeArguments) {
        return new CustomParameterizedType(rawType, null, typeArguments);
    }

    private static class CustomParameterizedType implements ParameterizedType {

        private Type rawType;

        private Type ownerType;

        private Type[] typeArguments;

        private CustomParameterizedType(@NotNull Type rawType, @Nullable Type ownerType,
                                        @NotNull Type... typeArguments) {
            this.rawType = rawType;
            this.ownerType = ownerType;
            this.typeArguments = typeArguments;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return typeArguments;
        }

        @Override
        public Type getRawType() {
            return rawType;
        }

        @Override
        public Type getOwnerType() {
            return ownerType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CustomParameterizedType that = (CustomParameterizedType) o;
            return Objects.equals(rawType, that.rawType) &&
                    Objects.equals(ownerType, that.ownerType) &&
                    Arrays.equals(typeArguments, that.typeArguments);
        }

        @Override
        public int hashCode() {
            return Objects.hash(rawType, ownerType, typeArguments);
        }
    }
}
