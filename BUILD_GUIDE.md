# Buffalo Milk Predictor - Build & Setup Guide

## System Requirements

### Minimum Requirements
- **OS**: Windows, macOS, or Linux
- **RAM**: 8 GB (16 GB recommended)
- **Disk Space**: 10 GB free (for Android SDK + project)
- **Java**: JDK 17 or higher
- **Gradle**: 8.2 or higher (included with Android Studio)

### Android SDK Requirements
- **Min API Level**: 24 (Android 7.0)
- **Target API Level**: 34 (Android 14)
- **Compile API Level**: 34

## Installation Steps

### 1. Install Prerequisites

#### Windows
```bash
# Install Java JDK 17+ (from oracle.com or openjdk.org)
# Add JAVA_HOME to environment variables

# Install Android Studio from developer.android.com
# During installation, select:
# - Android SDK
# - Android SDK Platform-Tools
# - Android Virtual Device (AVD)
```

#### macOS
```bash
# Install Java
brew install openjdk@17
echo 'export PATH="/usr/local/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc

# Install Android Studio
brew install android-studio
```

#### Linux
```bash
# Install Java
sudo apt-get install openjdk-17-jdk

# Install Android Studio
# Download from developer.android.com and extract
```

### 2. Set Up Android Studio

1. Open Android Studio
2. Go to `File > Settings > Appearance & Behavior > System Settings > Android SDK`
3. Install the following:
   - API 34 (Android 14)
   - API 28 (Android 9) - for testing
   - Build Tools 34.0.0
   - Android SDK Command-line Tools
   - Android Emulator
   - Android SDK Platform-Tools

### 3. Configure Local Environment

Create `local.properties` in project root:
```properties
sdk.dir=/path/to/your/Android/sdk
ndk.dir=/path/to/your/Android/ndk
```

**Find Android SDK path:**
- **Windows**: `C:\Users\[YourUsername]\AppData\Local\Android\Sdk`
- **macOS**: `/Users/[YourUsername]/Library/Android/sdk`
- **Linux**: `~/Android/Sdk`

### 4. Clone & Set Up Project

```bash
# Clone the repository (if applicable)
git clone <repository-url>
cd APP

# Verify Gradle wrapper
./gradlew --version

# Sync dependencies
./gradlew build
```

## Building the Application

### Debug Build

```bash
# Build debug APK
./gradlew assembleDebug

# Output: app/build/outputs/apk/debug/app-debug.apk
```

### Release Build

```bash
# First, generate signing key (one-time)
keytool -genkey -v -keystore release.keystore -keyalg RSA -keysize 2048 -validity 10000 -alias buffalo

# Add to local.properties
echo "RELEASE_STORE_FILE=release.keystore" >> local.properties
echo "RELEASE_STORE_PASSWORD=<password>" >> local.properties
echo "RELEASE_KEY_ALIAS=buffalo" >> local.properties
echo "RELEASE_KEY_PASSWORD=<password>" >> local.properties

# Build release APK
./gradlew assembleRelease

# Output: app/build/outputs/apk/release/app-release.apk
```

## Installing & Running

### Via Android Studio

1. Open project in Android Studio
2. Connect device or start emulator
3. Click `Run > Run 'app'`
4. Select target device
5. App launches automatically

### Via Command Line

```bash
# Install debug build
./gradlew installDebug

# Install and run
./gradlew installDebug
adb shell am start -n com.buffalomilkpredictor/.MainActivity

# Install release build
./gradlew installRelease
```

### Via ADB Directly

```bash
# Connect device
adb devices

# Install APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Launch app
adb shell am start -n com.buffalomilkpredictor/.MainActivity

# View logs
adb logcat
```

## Emulator Setup

### Create AVD

```bash
# Using Android Studio GUI:
# 1. Tools > AVD Manager
# 2. Create Virtual Device
# 3. Select Pixel 4 or similar
# 4. Select API 28 or higher
# 5. Configure hardware
# 6. Start AVD

# Via Command Line:
sdkmanager "system-images;android-34;google_apis;x86_64"
avdmanager create avd -n buffalo_emulator -k "system-images;android-34;google_apis;x86_64" -d "pixel_4"
```

### Start Emulator

```bash
# Via Android Studio GUI: Click play button in AVD Manager

# Via Command Line:
emulator -avd buffalo_emulator

# With additional options:
emulator -avd buffalo_emulator -gpu on -cores 4 -memory 2048
```

## Gradle Tasks

### Building
```bash
./gradlew build                    # Build all variants
./gradlew assembleDebug            # Build debug APK
./gradlew assembleRelease          # Build release APK
./gradlew bundleRelease            # Build AAB for Play Store
```

### Testing
```bash
./gradlew test                     # Run unit tests
./gradlew connectedAndroidTest     # Run instrumented tests
./gradlew connectedCheck           # Full device testing
```

### Code Quality
```bash
./gradlew lint                     # Run Android Lint
./gradlew detekt                   # Kotlin static analysis (if configured)
./gradlew spotbugsRelease          # Bug detection (if configured)
```

### Cleaning
```bash
./gradlew clean                    # Clean build artifacts
./gradlew cleanBuildCache          # Clear build cache
./gradlew --stop                   # Stop Gradle daemon
```

## Troubleshooting

### Build Failures

**Error: "Failed to find Build Tools"**
```bash
sdkmanager "build-tools;34.0.0"
```

**Error: "Could not find com.android.tools.build:gradle"**
```bash
./gradlew --refresh-dependencies build
```

**Error: "No target device"**
```bash
adb devices                         # Check connected devices
./gradlew installDebug -P android.useLegacyToolchain=true  # For older devices
```

### Runtime Issues

**Camera not working**
- Ensure camera permission in manifest
- Check device has camera hardware
- Restart emulator
- Use `adb shell pm grant com.buffalomilkpredictor android.permission.CAMERA`

**Database errors**
- Clear app data: `adb shell pm clear com.buffalomilkpredictor`
- Delete database: `adb shell rm /data/data/com.buffalomilkpredictor/databases/*`

**Memory issues**
- Increase Gradle heap: Add to `gradle.properties`:
  ```
  org.gradle.jvmargs=-Xmx2048m
  ```

### Compilation Warnings

**Suppress specific warnings**:
```kotlin
@Suppress("DEPRECATION")
fun legacyFunction() { }
```

**Update deprecated dependencies**:
```bash
./gradlew dependencyUpdates
```

## Development Workflow

### 1. Feature Development
```bash
# Create feature branch
git checkout -b feature/new-feature

# Make changes
# Build frequently
./gradlew build

# Test on device
./gradlew installDebug
```

### 2. Code Quality Checks
```bash
# Before commit
./gradlew build test lint

# Fix issues if any
```

### 3. Commit & Push
```bash
git add .
git commit -m "Add new feature"
git push origin feature/new-feature
```

## Performance Optimization

### Build Optimization
```gradle
// In build.gradle.kts
android {
    buildFeatures {
        compose = true
        buildConfig = false  // If not needed
        aidl = false        // If not using AIDL
    }
    
    packagingOptions {
        resources.excludes += setOf(
            "META-INF/proguard/androidx-*.pro",
            "META-INF/kotlin-stdlib*.version.txt"
        )
    }
}
```

### Gradle Optimization
```properties
# In gradle.properties
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.workers.max=8
android.useAndroidX=true
android.enableJetifier=false
```

## CI/CD Setup (Optional)

### GitHub Actions
Create `.github/workflows/build.yml`:
```yaml
name: Build
on: [push, pull_request]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'openjdk'
      - run: ./gradlew build
      - run: ./gradlew test
```

## Release Preparation

### Pre-Release Checklist
- [ ] Update version code in build.gradle.kts
- [ ] Update version name
- [ ] Run full test suite
- [ ] Update CHANGELOG.md
- [ ] Run ProGuard/R8 minification
- [ ] Test release APK on devices
- [ ] Verify all features work

### Build Release
```bash
./gradlew assembleRelease
./gradlew bundleRelease  # For Play Store
```

### Sign APK
```bash
jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
  -keystore release.keystore \
  app/build/outputs/apk/release/app-release.apk \
  buffalo

zipalign -v 4 app/build/outputs/apk/release/app-release.apk \
  app-release-aligned.apk
```

## Documentation

- **API Documentation**: Run `./gradlew dokka` (if configured)
- **README**: See README.md
- **Testing Guide**: See TESTING.md
- **Architecture**: See docs/ folder

## Support & Resources

- **Android Developer Docs**: https://developer.android.com/docs
- **Gradle Documentation**: https://gradle.org/documentation
- **Jetpack Compose**: https://developer.android.com/jetpack/compose
- **Room Database**: https://developer.android.com/training/data-storage/room
- **CameraX**: https://developer.android.com/training/camerax

---

**Last Updated**: 2024
**Status**: Complete and Tested
