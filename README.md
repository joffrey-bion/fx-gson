# FX Gson

[![Maven central version](https://img.shields.io/maven-central/v/org.hildan.fxgson/fx-gson.svg)](http://mvnrepository.com/artifact/org.hildan.fxgson/fx-gson)
[![Build Status](https://github.com/joffrey-bion/fx-gson/actions/workflows/build.yml/badge.svg)](https://github.com/joffrey-bion/fx-gson/actions/workflows/build.yml)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/joffrey-bion/fx-gson/blob/master/LICENSE)

FX Gson is a set of type adapters for [Google Gson](https://github.com/google/gson) to serialize JavaFX properties as 
their values, and deserialize values into properties.

FX Gson simply removes the property "wrapping" and delegates the serialization of the value to the Gson. This means that 
any configuration you add to Gson regarding a type will be taken into account when serializing a property of that type. 
This is true for objects and primitives.

## Why use FX Gson?

In JavaFX, POJOs usually contain `Property` objects instead of primitives. When serialized, we usually don't want to
see the internals of such `Property` objects in the produced JSON, but rather the actual value held by the property.

For instance, suppose the `Person` class is defined like this:

```java
public class Person {
    private final StringProperty firstName;
    private final StringProperty lastName;

    public Person(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
    }
    
    // getters, setters, and property getters are omitted for brevity
}
```
    
Here is how `new Person("Hans", "Muster")` is serialized:

<table>
    <tr>
        <th>With vanilla Gson</th>
        <th>With FxGson-configured Gson</th>
    </tr>
    <tr>
        <td>
        <pre>{
    "firstName": {
        "name": "",
        "value": "Hans",
        "valid": true,
        "helper": {
            "observable": {}
        }
    },
    "lastName": {
        "name": "",
        "value": "Muster",
        "valid": true,
        "helper": {
            "observable": {}
        }
    }
}</pre>
        </td>
        <td>
            <pre>{
    "firstName": "Hans",
    "lastName": "Muster"
}</pre>
        </td>
    </tr>
</table>

This produces a more human-readable output, and make manual modifications of the JSON easier.

## Usage

All you need to know is [in the wiki](https://github.com/joffrey-bion/fx-gson/wiki/Basic-FX-Gson-usage), but here is a 
quick overview.

You can use FX Gson in multiple ways depending on the degree of customization you need:
- directly [create a ready-to-go `Gson`](https://github.com/joffrey-bion/fx-gson/wiki/Basic-FX-Gson-usage#simple-ways-matter) able to serialize JavaFX properties

    ```java
    // to handle only Properties and Observable collections
    Gson fxGson = FxGson.create();
    
    // to also handle the Color & Font classes
    Gson fxGsonWithExtras = FxGson.createWithExtras();
    ```

- [create a pre-configured `GsonBuilder`](https://github.com/joffrey-bion/fx-gson/wiki/Basic-FX-Gson-usage#using-pre-configured-gsonbuilders) that you can further configure yourself

    ```java
    Gson fxGson = FxGson.coreBuilder()
                        .registerTypeAdapterFactory(new MyFactory())
                        .disableHtmlEscaping()
                        .create();
    
    Gson fxGsonWithExtras = FxGson.fullBuilder()
                                  .registerTypeAdapter(Pattern.class, new PatternSerializer())
                                  .setPrettyPrinting()
                                  .create();
    ```

- [add JavaFX configuration to an existing `GsonBuilder`](https://github.com/joffrey-bion/fx-gson/wiki/Basic-FX-Gson-usage#adding-javafx-support-to-an-existing-gsonbuilder)

    ```java
    GsonBuilder builder = MyLib.getBuilder();
    Gson gson = FxGson.addFxSupport(builder).create();
    ```

- [cherry-pick some pieces of FX Gson configuration](https://github.com/joffrey-bion/fx-gson/wiki/Customize-FX-Gson) and customize it to fit your needs

## Java version compatiblity

There are different JRE requirements depending on the version of FX Gson:

| FX Gson version | JRE requirement      |
|-----------------|----------------------|
| 3.x.x           | 8                    |
| 4.x.x           | 9+ (JPMS compatible) |
| 5.x.x           | 8+ (JPMS compatible) |

Starting from FX Gson 4.0.0, the library has a `module-info.java` for compatibility with Jigsaw (Java 9+).

Starting from FX Gson 5.0.0, the library still has `module-info.java` compiled with Java 9, but all other class files
have bytecode level 8, so it can still be used in Java 8. Thanks to @Glavo for the contribution!

## Setup - adding the dependency

### Manual download
 
You may manually download the JAR from 
[Maven Central](https://repo1.maven.org/maven2/org/hildan/fxgson/fx-gson/), although I recommend
using a build tool such as [Gradle](https://gradle.org/).
 
### Gradle

```kotlin
compile("org.hildan.fxgson:fx-gson:$VERSION")
```

### Maven

```xml
<dependency>
   <groupId>org.hildan.fxgson</groupId>
   <artifactId>fx-gson</artifactId>
   <version>$VERSION</version> <!-- replace with latest version -->
   <type>pom</type>
</dependency>
```    
## License

Code released under [the MIT license](https://github.com/joffrey-bion/io-utils/blob/master/LICENSE)
