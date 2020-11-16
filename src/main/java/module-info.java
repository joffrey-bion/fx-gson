module org.hildan.fxgson {
    requires transitive com.google.gson;
    requires transitive javafx.base; // for Property classes
    requires transitive javafx.graphics; // for Color and Font classes
    requires org.jetbrains.annotations;

    exports org.hildan.fxgson;
    exports org.hildan.fxgson.adapters.extras;
    exports org.hildan.fxgson.adapters.properties;
    exports org.hildan.fxgson.adapters.properties.primitives;
    exports org.hildan.fxgson.creators;
    exports org.hildan.fxgson.factories;
}
