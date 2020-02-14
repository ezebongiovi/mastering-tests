[![CircleCI](https://circleci.com/gh/ezebongiovi/mastering-tests.svg?style=svg)](https://circleci.com/gh/ezebongiovi/mastering-tests)

## Description

This is a personal project built for practicing TDD skills in real world scenarios.

## Used techs
* Room
    * For offline support
* Kotlin
    * Because it's cute
* Espresso
    * For UI testing
* JUnit
    * For instrumented tests
* Mockito
    * For mocking during instrumented tests
* Dagger
    * For dependency injection
* DataBinding
    * For driving the UI from a model


## The tests

I'm a little bit disappointed on this, I faced some unexpected scenarios.

* Room tests need to run on Android devices, it means, all database tests depend on an Android device. Because of that the whole database has been tested using instrumented tests.

* Tried to add coverage reports for instrumented and UI tests, in the process I realized Circle CI doesn't support virtualization. And those tools which provide us with  android emulators during continuous integration are expensive. So this project has no coverage report because of that. 

<a href="https://support.circleci.com/hc/en-us/articles/360000028928-Testing-with-Android-emulator-on-CircleCI-2-0">Discussion</a>
> Running the Android emulator is not currently supported on CircleCI 2.0, since it's not supported by the type of virtualization CircleCI uses on Linux.

## The app

### Download images by URL
<img width=300 height=550 src="https://drive.google.com/uc?export=download&id=1FudSjH00EaXafmJoPsDD3wQneNtukiUN"/>

### Share and Delete downloaded images
<img width=300 height=550 src="https://drive.google.com/uc?export=download&id=1ygRYcWOsnuaSNUTxlsm5EpSfz3nEAiqu"/>

### Filter your downloaded images
<img width=300 height=550 src="https://drive.google.com/uc?export=download&id=1SnvHC0I6tDlrc3LxeTMBKTRPxwyFDU2H"/>


### TODO
Research about alternative CI tools like Jenkins for adding coverage reports on instrumented tests

### Update
On <a href="https://github.com/ezebongiovi/mastering-instrumented-tests">this repository</a> I've solved the coverage report issue by using <a href="https://app.bitrise.io/referral/991edfc02003fd69
">Bitrise</a>
