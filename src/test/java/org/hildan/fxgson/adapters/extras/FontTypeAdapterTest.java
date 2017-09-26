package org.hildan.fxgson.adapters.extras;

import java.io.IOException;
import java.io.StringReader;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import com.google.gson.stream.JsonReader;
import org.hildan.fxgson.adapters.extras.FontTypeAdapter.InvalidFontException;
import org.hildan.fxgson.test.Expectation;
import org.hildan.fxgson.test.TestUtils;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class FontTypeAdapterTest {

    private static class FontExpectation extends Expectation<Font> {
        FontExpectation(Font value, String json) {
            super(value, json);
        }
    }

    @DataPoints
    public static FontExpectation[] expectations() {
        // no exotic fonts as the test must pass on most machines
        return new FontExpectation[]{
                new FontExpectation(Font.font("System", FontWeight.NORMAL, 12.0), "\"System,Regular,12.0\""),
                new FontExpectation(Font.font("SansSerif", FontWeight.BOLD, 10.0), "\"SansSerif,Bold,10.0\""),
                new FontExpectation(Font.font("System", FontPosture.ITALIC, 10.0), "\"System,Italic,10.0\""),
                new FontExpectation(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 10.0),
                        "\"System,Bold Italic,10.0\""),
                new FontExpectation(Font.font("SansSerif", FontWeight.BOLD, FontPosture.ITALIC, 20.0),
                        "\"SansSerif,Bold Italic,20.0\""),
                new FontExpectation(null, "null"),
        };
    }

    @Theory
    public void write(FontExpectation expectation) throws IOException {
        TestUtils.testWrite(new FontTypeAdapter(), expectation);
    }

    @Theory
    public void read(FontExpectation expectation) throws IOException {
        TestUtils.testRead(new FontTypeAdapter(), expectation);
    }

    @Test(expected = InvalidFontException.class)
    public void read_throwsIfNot3Parts() throws IOException {
        JsonReader jsonReader = new JsonReader(new StringReader("\"not enough components\""));
        new FontTypeAdapter().read(jsonReader);
    }

    @Test(expected = InvalidFontException.class)
    public void read_throwsIfInvalidSize() throws IOException {
        JsonReader jsonReader = new JsonReader(new StringReader("\"third,is not a,size\""));
        new FontTypeAdapter().read(jsonReader);
    }
}
