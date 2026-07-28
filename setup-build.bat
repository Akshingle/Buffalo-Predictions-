@echo off
REM Buffalo Milk Predictor - Android SDK Setup Script
REM This script helps configure Android SDK for local development

echo.
echo ========================================
echo Buffalo Milk Predictor - Build Setup
echo ========================================
echo.

REM Check if Android SDK is already configured
if exist "%ANDROID_HOME%\platforms\android-34" (
    echo [OK] Android SDK found at: %ANDROID_HOME%
    goto check_gradle
)

REM Check common SDK locations
echo Checking for Android SDK...

if exist "C:\Users\%USERNAME%\AppData\Local\Android\sdk" (
    echo [FOUND] Android SDK at default location
    set ANDROID_HOME=C:\Users\%USERNAME%\AppData\Local\Android\sdk
    goto verify_sdk
)

if exist "C:\Android\sdk" (
    echo [FOUND] Android SDK at C:\Android\sdk
    set ANDROID_HOME=C:\Android\sdk
    goto verify_sdk
)

echo [WARNING] Android SDK not found in standard locations
echo.
echo To complete the build, you have three options:
echo.
echo Option 1: Install Android Studio (Recommended)
echo   Download from: https://developer.android.com/studio
echo   It will automatically install the Android SDK
echo.
echo Option 2: Install Command-Line Tools
echo   1. Download from: https://developer.android.com/studio#command-tools
echo   2. Extract to: C:\Android\cmdline-tools
echo   3. Run: C:\Android\cmdline-tools\bin\sdkmanager.bat --list
echo   4. Install required platforms and build-tools
echo.
echo Option 3: Manual Configuration
echo   1. Download Android SDK manually
echo   2. Set ANDROID_HOME environment variable
echo   3. Ensure these are installed:
echo      - platforms/android-34
echo      - platforms/android-24
echo      - build-tools/34.0.0
echo.
pause
goto end

:verify_sdk
echo [CHECKING] Verifying SDK components...

if not exist "%ANDROID_HOME%\platforms\android-34" (
    echo [MISSING] Android 14 (API 34) platform
    echo   Install with: sdkmanager "platforms;android-34"
)

if not exist "%ANDROID_HOME%\platforms\android-24" (
    echo [MISSING] Android 7.0 (API 24) platform
    echo   Install with: sdkmanager "platforms;android-24"
)

if not exist "%ANDROID_HOME%\build-tools\34.0.0" (
    echo [MISSING] Build tools 34.0.0
    echo   Install with: sdkmanager "build-tools;34.0.0"
)

:check_gradle
echo.
echo Checking Gradle...

if exist "gradle\wrapper\gradle-8.2\bin\gradle.bat" (
    echo [OK] Gradle 8.2 found
) else (
    echo [WARNING] Gradle not found in expected location
)

:check_java
echo.
echo Checking Java...

java -version >nul 2>&1
if errorlevel 0 (
    echo [OK] Java is installed
    java -version
) else (
    echo [ERROR] Java is not installed or not in PATH
    echo Download from: https://www.oracle.com/java/technologies/downloads/
)

:create_local_properties
echo.
echo Creating local.properties...

if "%ANDROID_HOME%"=="" (
    echo [ERROR] ANDROID_HOME not set. Cannot create local.properties
    pause
    goto end
)

(
    echo # Android SDK configuration
    echo sdk.dir=%ANDROID_HOME:\=/%
) > local.properties

echo [OK] local.properties created with SDK path: %ANDROID_HOME%

:build_ready
echo.
echo ========================================
echo Setup Complete - Ready to Build
echo ========================================
echo.
echo You can now run:
echo   gradle clean build
echo.
echo Or using explicit gradle path:
echo   gradle\wrapper\gradle-8.2\bin\gradle.bat clean build
echo.

:end
pause
