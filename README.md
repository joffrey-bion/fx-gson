# FX Gson

[![Bintray](https://img.shields.io/bintray/v/joffrey-bion/maven/fx-gson.svg)](https://bintray.com/joffrey-bion/maven/fx-gson/_latestVersion)
[![Travis Build](https://img.shields.io/travis/joffrey-bion/fx-gson/master.svg)](https://travis-ci.org/joffrey-bion/fx-gson)
[![Dependency Status](https://www.versioneye.com/user/projects/57327660a0ca35004baf8bfb/badge.svg)](https://www.versioneye.com/user/projects/57327660a0ca35004baf8bfb)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/joffrey-bion/fx-gson/blob/master/LICENSE)

Gson extension to serialize JavaFX properties as their values, and deserialize values into properties.

In JavaFX, POJOs usually contain `Property` objects instead of primitives. When serialized with Gson, we don't want to
see the internals of such `Property` objects in the produced JSON, but rather the actual value held by the property.

For instance, if I have a class like this:

    public class Person {
        private final StringProperty firstName;
        private final StringProperty lastName;

        public Person(String firstName, String lastName) {
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
        }
    }

Normal Gson usage would produce:

    {
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
    }

With FxGson, we get instead:

    {
        "firstName": "Hans",
        "lastName": "Muster"
    }

Isn't that beautiful?

## Usage

### Add the dependency in Gradle

    repositories {
        jcenter()
    }

    dependencies {
        compile 'org.hildan.fxgson:fx-gson:1.2'
    }

### Use FxGson

#### Simple usage with the FxGson class

You can use the built-in `GsonBuilder`s directly:

    Gson fxGson = FxGson.coreBuilder().create();

    Gson fxGsonWithExtras = FxGson.fullBuilder().create();

Because `FxGson` returns a builder, you can add your own configuration to it:

    Gson gson = FxGson.coreBuilder()
                      .registerTypeAdapterFactory(new MyFactory())
                      .setPrettyPrinting()
                      .create();

Sometimes you don't control the creation of the `GsonBuilder` you are using, because you take it from some library or
 some other piece of code.
In this case, use the provided helper methods to add `FxGson` configuration to an existing `GsonBuilder`:

    GsonBuilder builder = MyLib.getBuilder();
    Gson gson = FxGson.addCoreSerializers(builder).create();

#### Using TypeAdapter factories

FxGson is simply a helper class registering a particular `TypeAdapterFactory` on a `GsonBuilder`. If you want more
control, you can use directly or even extend the `JavaFxPropertyTypeAdapterFactory` or `JavaFxExtraTypeAdapterFactory`
yourself.

When doing so, be aware that observable collections like `ObservableList` require a specific Gson `InstanceCreator`,
because otherwise Gson does not know how to serialize them. You can find predefined instance creators for JavaFX
observable collections in the package `org.hildan.fxgson.creators`.

## License

Code released under [the MIT license](https://github.com/joffrey-bion/io-utils/blob/master/LICENSE)
