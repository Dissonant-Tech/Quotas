#!/bin/bash

./gradlew assembleDebug && sudo adb install -r app/build/outputs/apk/app-debug.apk
