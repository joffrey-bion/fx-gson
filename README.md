# FX Gson

[![Bintray](https://img.shields.io/bintray/v/joffrey-bion/maven/fx-gson.svg)](https://bintray.com/joffrey-bion/maven/fx-gson/_latestVersion)
[![Travis Build](https://img.shields.io/travis/joffrey-bion/fx-gson/master.svg)](https://travis-ci.org/joffrey-bion/fx-gson)
[![Dependency Status](https://www.versioneye.com/user/projects/57327660a0ca35004baf8bfb/badge.svg)](https://www.versioneye.com/user/projects/57327660a0ca35004baf8bfb)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/joffrey-bion/fx-gson/blob/master/LICENSE)

A set of type adapters for [Google Gson](https://github.com/google/gson) to serialize JavaFX properties as their values,
and deserialize values into properties.

## Why FX Gson?

In JavaFX, POJOs usually contain `Property` objects instead of primitives. When serialized with Gson, we don't want to
see the internals of such `Property` objects in the produced JSON, but rather the actual value held by the property.

For instance, suppose the `Person` class is defined like this:

    public class Person {
        private final StringProperty firstName;
        private final StringProperty lastName;

        public Person(String firstName, String lastName) {
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
        }
        
        // getters / setters / prop getters
    }
    
Here is how it is serialized:

<table>
    <tr>
        <th>With Gson</th>
        <th>With FxGson</th>
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


Convincing, eh?

## Usage

#### The simple way

You can use the built-in factory methods directly:

    // to handle only Properties and Observable collections
    Gson fxGson = FxGson.create();

    // to also handle the Color & Font classes
    Gson fxGsonWithExtras = FxGson.createWithExtras();

`FxGson` can also return a builder so that you can add your own configuration to it:

    Gson fxGson = FxGson.coreBuilder()
                        .registerTypeAdapterFactory(new MyFactory())
                        .disableHtmlEscaping()
                        .create();

    Gson fxGsonWithExtras = FxGson.fullBuilder()
                                  .registerTypeAdapter(Pattern.class, new PatternSerializer())
                                  .setPrettyPrinting()
                                  .create();
                      
You can find more info on the built-in "core" and "full" builder [in the Wiki](https://github.com/joffrey-bion/fx-gson/wiki/Built-in-GsonBuilders).

#### Configuring an existing builder to handle JavaFX properties

Sometimes you don't control the creation of the `GsonBuilder` you are using, because you take it from some library or
 some other piece of code.
In this case, use the provided helper methods to add `FxGson` configuration to an existing `GsonBuilder`:

    GsonBuilder builder = MyLib.getBuilder();
    Gson gson = FxGson.addFxSupport(builder).create();

#### Going full control

You will find more customization info in [this dedicated wiki page](https://github.com/joffrey-bion/fx-gson/wiki/Customize-FX-Gson).

### Add the dependency
 
#### In Gradle

    repositories {
        jcenter()
    }

    dependencies {
        compile 'org.hildan.fxgson:fx-gson:2.0.0'
    }

#### In Maven

    <dependency>
      <groupId>org.hildan.fxgson</groupId>
      <artifactId>fx-gson</artifactId>
      <version>2.0.0</version>
      <type>pom</type>
    </dependency>
    
Note: the artifact is on Bintray JCenter, not in Maven Central, so make sure you point your maven to JCenter by adding 
the repo to your `settings.xml` using the URL `http://jcenter.bintray.com`.

You can find a complete XML example in the "Set me up!" blue rectangle on the 
[JCenter home page](https://bintray.com/bintray/jcenter).

## License

Code released under [the MIT license](https://github.com/joffrey-bion/io-utils/blob/master/LICENSE)
