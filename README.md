# Decompose Multiplatform Template

This is a Kotlin Multiplatform project template with [Decompose](https://github.com/arkivanov/decompose) configured.

> **Note**
> Due to the  [current state](https://github.com/arkivanov/Decompose/issues/74) of iOS support this template uses experimental versions of Decompose.
> If you have any issues, please report them on GitHub.

## Project Structure

The project consists of multiple modules:

### `shared`

This is a Kotlin module that contains the shared logic of all platforms. This also includes the
Decompose implementation. 

### `compose-ui`

This is a Kotlin module that contains the UI written with Compose Multiplatform that is shared
across all platforms.

It depends on the `shared` module as it uses the component interfaces from Decompose.

### `app-desktop`

This is a Kotlin module that contains and builds the desktop (JVM) application.

It makes use of the shared code from the modules `shared` and `compose-ui`.

### `app-android`

This is a Kotlin module that contains and builds the Android mobile application.

It makes use of the shared code from the modules `shared` and `compose-ui`.

### `app-ios-compose`

This is an Xcode project that builds an iOS mobile application with Compose UI.

It makes use of the shared code from the modules `shared` and `compose-ui`.

## Running the project

Depending on the platform you want to build for and run the project on, different gradle tasks may
be used.

### For Android

When using Android Studio you can simply select `app-android` from the run configurations and run
the app.

### For Desktop

```bash
./gradlew :app-desktop:run
```

### For iOS (Compose)

If you have installed the Kotlin Multiplatform Mobile plugin you can simply select `app-ios-compose`
from the run configurations and run the app.

Alternatively you can open `app-ios-compose/app-ios-compose.xcodeproj` in XCode and launch the project
from there.

## Further Reading

We encourage you to explore Decompose's features further and try adding them into your project:

* [Add basic navigation with child stack(s) to your project](https://arkivanov.github.io/Decompose/navigation/stack/overview/)
* [Add child slots for loading one child at a time, or none](https://arkivanov.github.io/Decompose/navigation/slot/overview/)
* [Add back button handlers for intercepting back button presses](https://arkivanov.github.io/Decompose/component/back-button/)

You can also have a look at various integrations, including:
* [State preservation with Essenty and Parcelize]() _(not available yet)_
* [Integration of MVIKotlin for sharing code using MVI pattern]() _(not available yet)_
