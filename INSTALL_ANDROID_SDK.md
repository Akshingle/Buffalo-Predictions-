# 🚨 SDK Installation Guide

The automated download approach had connectivity issues. Let's do this manually (2 minutes):

---

## **Method 1: Android Studio (EASIEST)**

```powershell
# 1. Download Android Studio
# Go to: https://developer.android.com/studio
# Download: android-studio-2024.1.1.10-windows.exe

# 2. Run the installer when download completes
# - Accept all prompts
# - Select "Install Android SDK"
# - Choose standard setup
# - Let it complete (~5 minutes)

# 3. That's it! The SDK will be automatically in:
#    C:\Users\$env:USERNAME\AppData\Local\Android\sdk

# 4. Then build:
cd C:\Users\akshay.ingle\Desktop\APP
gradle clean build
```

---

## **Method 2: Manual SDK Setup (If Android Studio doesn't work)**

```powershell
# 1. Open this in a new tab:
https://developer.android.com/studio#command-tools

# 2. Download: "Command line tools only"
# File: commandlinetools-win-XXXX_latest.zip

# 3. Extract manually:
#    - Right-click ZIP → Extract All
#    - Destination: C:\Android\cmdline-tools

# 4. Create SDK folders:
mkdir C:\Android\sdk

# 5. Set environment variable:
[Environment]::SetEnvironmentVariable("ANDROID_HOME", "C:\Android\sdk", "User")

# 6. Close PowerShell and reopen it, then:
cd C:\Android\cmdline-tools\cmdline-tools\bin

# 7. Install components:
sdkmanager.bat "platforms;android-34" "platforms;android-24" "build-tools;34.0.0"

# 8. Go back and build:
cd C:\Users\akshay.ingle\Desktop\APP
gradle clean build
```

---

## **After Installation**

Once SDK is installed:

```bash
cd C:\Users\akshay.ingle\Desktop\APP
gradle clean build
```

Expected output:
```
BUILD SUCCESSFUL in 45s
Output: app/build/outputs/apk/debug/app-debug.apk
```

---

## **Next Steps After Build**

```bash
# Install on device/emulator
gradle installDebug

# Launch the app
adb shell am start -n com.buffalomilkpredictor/.MainActivity

# View app logs
adb logcat | grep buffalomilkpredictor
```

---

⏱️ **Total time**: 15-30 minutes (including downloads)

📌 **Recommended**: Use Method 1 (Android Studio) - it's the most reliable!
