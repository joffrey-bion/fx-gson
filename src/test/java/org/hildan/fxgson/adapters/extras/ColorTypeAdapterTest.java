package org.hildan.fxgson.adapters.extras;

import java.io.IOException;
import java.io.StringReader;

import javafx.scene.paint.Color;

import com.google.gson.stream.JsonReader;
import org.hildan.fxgson.adapters.extras.ColorTypeAdapter.InvalidColorException;
import org.hildan.fxgson.test.Expectation;
import org.hildan.fxgson.test.TestUtils;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class ColorTypeAdapterTest {

    private static class ColorExpectation extends Expectation<Color> {
        ColorExpectation(Color value, String json) {
            super(value, json);
        }
    }

    @DataPoints
    public static Expectation<Color>[] expectations() {
        return new ColorExpectation[]{
                new ColorExpectation(Color.BLUE, "\"#0000ffff\""),
                new ColorExpectation(Color.RED, "\"#ff0000ff\""),
                new ColorExpectation(null, "null"),
        };
    }

    @Theory
    public void write(ColorExpectation expectation) throws IOException {
        TestUtils.testWrite(new ColorTypeAdapter(), expectation);
    }

    @Theory
    public void read(ColorExpectation expectation) throws IOException {
        TestUtils.testRead(new ColorTypeAdapter(), expectation);
    }

    @Test(expected = InvalidColorException.class)
    public void read_invalid() throws IOException {
        JsonReader jsonReader = new JsonReader(new StringReader("\"not a color\""));
        new ColorTypeAdapter().read(jsonReader);
    }
}
