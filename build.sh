#!/bin/bash

./gradlew assembleDebug && sudo adb install -r build/outputs/apk/app-debug.apk
