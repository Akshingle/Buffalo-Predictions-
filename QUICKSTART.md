# Buffalo Milk Predictor - Quick Start Guide

## 5-Minute Setup

### Step 1: Install Prerequisites
```bash
# Install Android Studio from developer.android.com
# Install Java JDK 17+
# Install/Update Android SDK
```

### Step 2: Clone and Setup
```bash
cd APP
./gradlew build
```

### Step 3: Run on Device/Emulator
```bash
# Create Android emulator with API 28+
# Or connect physical device

./gradlew installDebug
adb shell am start -n com.buffalomilkpredictor/.MainActivity
```

Done! The app is now running.

## Project Structure (Quick Overview)

```
APP/
├── app/src/main/
│   ├── kotlin/com/buffalomilkpredictor/  ← All source code
│   ├── res/                               ← Resources (strings, colors, etc.)
│   └── AndroidManifest.xml                ← App configuration
├── build.gradle.kts                       ← App build config
├── README.md                              ← Full documentation
├── BUILD_GUIDE.md                         ← Detailed build instructions
└── TESTING.md                             ← Testing guide
```

## Key Features to Explore

### 1. HomeScreen
- Entry point of the app
- Navigation to all features
- Located in: `ui/screens/home/HomeScreen.kt`

### 2. Camera Functionality
- CameraX integration
- Photo capture
- Located in: `utils/camera/CameraManager.kt`

### 3. ML Analysis Engine
- Scoring algorithm
- Breed identification
- Located in: `ml/analysis/BuffaloAnalysisEngine.kt`

### 4. Database Operations
- Buffalo analysis storage
- History management
- Located in: `data/database/`

### 5. PDF Generation
- Report creation
- Located in: `utils/pdf/PDFReportGenerator.kt`

## Common Development Tasks

### Add a New Screen
1. Create file: `ui/screens/newscreen/NewScreen.kt`
2. Add Composable function
3. Add route to `ui/navigation/Navigation.kt`

### Add a String Resource
1. Edit: `res/values/strings.xml` (English)
2. Edit: `res/values-hi/strings.xml` (Hindi)
3. Edit: `res/values-mr/strings.xml` (Marathi)

### Add a Database Query
1. Add method to `data/database/BuffaloAnalysisDao.kt`
2. Use in repository: `data/repository/BuffaloAnalysisRepository.kt`

### Create a ViewModel
1. Create file: `ui/viewmodel/YourViewModel.kt`
2. Extend: `ViewModel`
3. Use in screen with: `viewModel<YourViewModel>()`

## Build Commands (Most Used)

```bash
# Build and run
./gradlew installDebug && adb shell am start -n com.buffalomilkpredictor/.MainActivity

# Just build
./gradlew build

# Run tests
./gradlew test

# Check for errors
./gradlew lint

# Clean build
./gradlew clean build
```

## Debugging Tips

### View Logs
```bash
adb logcat | grep buffalomilkpredictor
```

### Check Database
```bash
adb shell sqlite3 /data/data/com.buffalomilkpredictor/databases/buffalo_database
```

### Inspect Files
```bash
adb shell ls -la /sdcard/Android/data/com.buffalomilkpredictor/
```

## Testing the App

### Manual Testing
1. Launch app
2. Grant camera permission
3. Take a photo
4. Start analysis
5. View results
6. Check history
7. Generate PDF

### Automated Testing
```bash
./gradlew test connectedAndroidTest
```

## Important Files to Know

| File | Purpose |
|------|---------|
| `MainActivity.kt` | App entry point |
| `BuffaloAnalysisEngine.kt` | Core analysis logic |
| `Theme.kt` | UI theme definition |
| `Navigation.kt` | Screen navigation |
| `BuffaloDatabase.kt` | Database setup |
| `build.gradle.kts` | Dependencies |

## Useful Resources

- **Android Docs**: https://developer.android.com/docs
- **Kotlin Docs**: https://kotlinlang.org/docs
- **Jetpack Compose**: https://developer.android.com/jetpack/compose
- **Room Database**: https://developer.android.com/training/data-storage/room

## FAQ

**Q: App crashes on startup?**
A: Check permissions, verify device Android version ≥ 7.0 (API 24)

**Q: Camera not working?**
A: Grant permission at runtime, check device has camera

**Q: Can't build?**
A: Run `./gradlew clean build`, check Java version (17+)

**Q: How do I change the language?**
A: Run app, go to Settings, select language

## Next Steps

1. **Explore Code**: Read through the existing implementations
2. **Run Tests**: Execute `./gradlew test` to verify setup
3. **Try Features**: Launch app and test each feature
4. **Read Docs**: Check README.md, BUILD_GUIDE.md, TESTING.md
5. **Start Developing**: Make changes and rebuild with `./gradlew installDebug`

## Getting Help

- **Documentation**: See README.md, BUILD_GUIDE.md
- **Code Comments**: Check inline comments in source files
- **Testing Guide**: See TESTING.md for detailed test cases
- **Error Messages**: Read logcat output carefully

---

**Status**: ✅ Ready to develop and test
**Estimated Setup Time**: 15-30 minutes (including SDK installation)
**Last Updated**: 2024
