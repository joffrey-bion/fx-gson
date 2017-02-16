package org.hildan.fxgson.test;

public class Expectation<T> {
    public final T object;
    public final String json;

    public Expectation(T object, String json) {
        this.object = object;
        this.json = json;
    }

    @Override
    public String toString() {
        return "Expectation{" + "object=" + object + ", json='" + json + '\'' + '}';
    }
}
