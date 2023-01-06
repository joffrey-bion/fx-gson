/**
 * FXGson module, to serialize JavaFX properties as their plain values using Gson.
 */
module org.hildan.fxgson {
    requires transitive com.google.gson;
    requires javafx.base; // for Property classes
    requires static javafx.graphics; // for Color and Font classes
    requires static org.jetbrains.annotations;

    exports org.hildan.fxgson;
    exports org.hildan.fxgson.adapters.extras;
    exports org.hildan.fxgson.adapters.properties;
    exports org.hildan.fxgson.adapters.properties.primitives;
    exports org.hildan.fxgson.creators;
    exports org.hildan.fxgson.factories;

    // necessary for reflective accesses from Gson on JDK13+
    opens org.hildan.fxgson to com.google.gson;
    opens org.hildan.fxgson.adapters.extras to com.google.gson;
    opens org.hildan.fxgson.adapters.properties to com.google.gson;
    opens org.hildan.fxgson.adapters.properties.primitives to com.google.gson;
    opens org.hildan.fxgson.creators to com.google.gson;
    opens org.hildan.fxgson.factories to com.google.gson;
}
