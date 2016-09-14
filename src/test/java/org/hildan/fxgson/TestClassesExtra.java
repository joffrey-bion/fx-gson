package org.hildan.fxgson;

import java.util.Objects;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

class TestClassesExtra {

    static class WithFont {
        Font font;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithFont that = (WithFont) o;
            return Objects.equals(font, that.font);
        }

        @Override
        public int hashCode() {
            return Objects.hash(font);
        }
    }

    static class WithColor {
        Color color;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WithColor that = (WithColor) o;
            return Objects.equals(color, that.color);
        }

        @Override
        public int hashCode() {
            return Objects.hash(color);
        }
    }
}
