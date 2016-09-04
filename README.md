# Cambuzz-android-client
This repository contains the android client of [Cambuzz Vitcc](http://www.cambuzz.co.in/). This app connects the Cambuzz user directly to the Buzz feed of Cambuzz - Ongoing events, upcoming competitions, fresh news, roumours and a lot more. It also provides with the module of 'Track Your Teacher', where student's can check whether a particular faculty is free or not at the moment or what are their free slots. Not only that, they get the cabin location details of that faculty

# Development Setup
1. Go to the project repo and click the `Fork` button
2. Clone your forked repository : `git clone https://github.com/Cambuzz/cambuzz-android-client.git`
3. Move to android project folder `cd source-code`
4. Open the project with Android Studio

# How to build

All dependencies are defined in ```source-code/app/build.gradle```. Import the project in Android Studio or use Gradle in command line:

```
./gradlew assembleRelease
```

The result apk file will be placed in ```source-code/app/build/outputs/apk/```.
