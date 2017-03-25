package org.hildan.fxgson.factories;

import org.hildan.fxgson.test.TestUtils;
import org.junit.Test;

public class TypeHelperTest {

    @Test
    public void typeHelper_cantBeInstantiated() {
        TestUtils.assertCannotBeInstantiated(TypeHelper.class);
    }
}
