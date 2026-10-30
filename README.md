# Rode Kruis APP: vehicle check forms for the Dutch Red Cross

An Android app (Kotlin) for the vehicle logistics of the Rode Kruis (Dutch Red Cross).
A volunteer picks a vehicle, fills in a check form when the vehicle is handed out or taken back, and sends the form by email.

- Team: Gurpreet Singh, Miguel Angel Dominguez Romero
- Client: Rode Kruis (Dutch Red Cross), vehicle logistics
- Period: October 2022 to January 2023

## What it does

- The start screen has six buttons: Auto, Ambulance, Gator, Quad, Fiets (bike) and Aanhanger (trailer).
- Each button opens a check form for that vehicle. You choose "Inname" (taken back) or "Uitgifte" (handed out).
- The form asks for the user's name, the inspector's name and the licence plate. For bikes and trailers you pick the number from a list. The "Vandaag" button fills in the current date and time.
- A checklist covers the accessories of that vehicle, such as a charging cable, papers or a first aid bag. Bikes and quads have a counter for the number of helmets.
- You tick "Het voertuig is schadevrij" when the vehicle has no damage. The bike form also asks you to confirm that you checked the bike trailer coupling.
- Empty required fields get the message "Verplicht" (required). The GDPR (AVG) consent box is required too.
- "Versturen" shows a GDPR consent dialog. After "Akkoord" the phone's email app opens with the form as text. You can add an extra CC address.

The app has no backend or database. The texts in the app are in Dutch.

> **Warning:** the email goes to `Mdlogistiek@redcross.nl`, a real Red Cross address. It is set in every `*Activity.kt` file. Do not press send in the email app when you test. Close the draft or change the address in the code first.

## What is where

```text
app/
  build.gradle                 app module: SDK versions and dependencies
  src/main/AndroidManifest.xml the screens (activities) of the app
  src/main/java/com/example/rodekruisapp/
    MainActivity.kt            start screen with the six vehicle buttons
    AutoActivity.kt ...        one form screen per vehicle
  src/main/res/layout/         the layouts of the screens
  src/main/res/values/         texts (strings.xml), colours and themes
  src/test/                    unit test (template)
  src/androidTest/             instrumented test (template, needs a device)
build.gradle, settings.gradle  Gradle project files
gradlew, gradlew.bat           Gradle wrapper (downloads Gradle 7.3.3)
```

## What you need

- Android Studio, or on Linux the Android command line tools (tested with version 11076708)
- JDK 17 (tested with Eclipse Temurin 17.0.21). JDK 11 works too. JDK 21 does not work, because Gradle 7.3.3 does not run on it.
- Android SDK Platform 32 (Android 12L) and Build-Tools 30.0.3
- An Android emulator or phone with Android 5.0 (API 21) or newer, and an email app on it (tested on an emulator with Android 12L and Google APIs)

Gradle 7.3.3 and all libraries are downloaded on the first build. That needs an internet connection.

## Getting started

### 1. Clone the repository

```bash
git clone https://github.com/guruITA/Rode_kruis-_APP.git
cd Rode_kruis-_APP
```

### 2. Open the project in Android Studio

1. Start Android Studio and choose **Open**. Select the `Rode_kruis-_APP` folder.
2. Set the Gradle JDK to 17. Go to **Settings > Build, Execution, Deployment > Build Tools > Gradle**. Under **Gradle JDK**, pick a JDK 17. If you have none, choose **Download JDK**, version 17, vendor Eclipse Temurin.
3. Click **Sync Project with Gradle Files** (the elephant icon). If Android Studio asks to install a missing SDK package such as Android 12L (API 32), accept it.
4. Android Studio may offer to upgrade the Android Gradle Plugin. Close that message. The project builds as it is.

Android Studio writes your SDK path to `local.properties`. That file is ignored by git.

### 3. Run the app

1. Open **Device Manager** and create a virtual device, for example a Pixel with Android 12 or newer. A system image with Google APIs or Google Play includes Gmail.
2. Pick the device in the toolbar and click **Run 'app'**.
3. The start screen shows the six vehicle buttons.

### 4. Try a form

1. Tap **Auto**.
2. Fill in "Naam gebruiker", "Naam RKLC" and "Kenteken", and tap **Vandaag**.
3. Tick some accessories, the damage box and the AVG box.
4. Tap **Versturen** and then **Akkoord**.
5. Android asks which email app to use. Pick one and look at the draft. Do not send it (see the warning above).

On an emulator without an email app you see "No apps can perform this action". Sign in to Gmail on the emulator, or use a phone.

### 5. Build from the command line (optional)

You can also build the APK without Android Studio.

On Linux (x64), install JDK 17 and the Android SDK in your home folder:

```bash
export ANDROID_TOOLS="$HOME/android-tools"
mkdir -p "$ANDROID_TOOLS/jdk-17" "$ANDROID_TOOLS/android-sdk/cmdline-tools"
curl -L -o "$ANDROID_TOOLS/jdk17.tar.gz" "https://api.adoptium.net/v3/binary/latest/17/ga/linux/x64/jdk/hotspot/normal/eclipse"
tar -xzf "$ANDROID_TOOLS/jdk17.tar.gz" -C "$ANDROID_TOOLS/jdk-17" --strip-components=1
curl -L -o "$ANDROID_TOOLS/cmdline-tools.zip" https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip
unzip -q "$ANDROID_TOOLS/cmdline-tools.zip" -d "$ANDROID_TOOLS/android-sdk/cmdline-tools"
mv "$ANDROID_TOOLS/android-sdk/cmdline-tools/cmdline-tools" "$ANDROID_TOOLS/android-sdk/cmdline-tools/latest"
```

Set the paths and install the SDK packages. Do this in every new terminal before you run `./gradlew`:

```bash
export ANDROID_TOOLS="$HOME/android-tools"
export JAVA_HOME="$ANDROID_TOOLS/jdk-17"
export ANDROID_HOME="$ANDROID_TOOLS/android-sdk"
export PATH="$JAVA_HOME/bin:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH"
yes | sdkmanager --licenses
sdkmanager "platforms;android-32" "build-tools;30.0.3" "platform-tools"
```

Build the debug APK from the project folder:

```bash
./gradlew assembleDebug
```

The APK is `app/build/outputs/apk/debug/app-debug.apk`. To put it on a running emulator or a phone with USB debugging on:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

On Windows, install Android Studio first (steps 2 and 3). It installs the SDK in `%LOCALAPPDATA%\Android\Sdk`. Install JDK 17 and build in PowerShell:

```powershell
winget install --id EclipseAdoptium.Temurin.17.JDK -e
```

Open a new PowerShell window in the project folder:

```powershell
$env:JAVA_HOME = (Get-ChildItem "C:\Program Files\Eclipse Adoptium" -Directory -Filter "jdk-17*" | Select-Object -First 1).FullName
$env:ANDROID_HOME = "$env:LOCALAPPDATA\Android\Sdk"
.\gradlew.bat assembleDebug
```

To install the APK on a running emulator:

```powershell
& "$env:ANDROID_HOME\platform-tools\adb.exe" install -r app\build\outputs\apk\debug\app-debug.apk
```

## Tests

The project only has the two template tests from Android Studio.

Run the unit test (no device needed). Use the same terminal setup as in step 5:

```bash
./gradlew test
```

The instrumented test needs a running emulator or a connected phone:

```bash
./gradlew connectedAndroidTest
```

On Windows, use `.\gradlew.bat test` and `.\gradlew.bat connectedAndroidTest`. In Android Studio, right-click the `app/src/test` folder and choose **Run 'Tests in ...'**.

## Who did what

- **Gurpreet Singh** built almost the whole app: the start screen, the six vehicle forms with their accessory checklists and counters, the bike and trailer number lists, the date button, the required field checks, the AVG consent and the email text.
- **Miguel Angel Dominguez Romero** made one small change to an early form screen (`SecondActivity`) that was later replaced by the vehicle forms.
