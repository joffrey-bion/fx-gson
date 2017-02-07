package org.hildan.fxgson.factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class TypeHelperTest {

    @Test
    public void typeHelper_cantBeInstantiated() throws IllegalAccessException, InstantiationException {
        final Class<?> cls = TypeHelper.class;
        final Constructor<?> c = cls.getDeclaredConstructors()[0];
        c.setAccessible(true);
        try {
            c.newInstance();
            fail();
        } catch (InvocationTargetException ite) {
            Throwable targetException = ite.getTargetException();
            assertNotNull(targetException);
            assertEquals(targetException.getClass(), InstantiationException.class);
        }
    }

}