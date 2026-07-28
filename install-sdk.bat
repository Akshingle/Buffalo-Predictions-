@echo off
REM Android SDK Installer for Buffalo Milk Predictor
REM This script downloads and installs Android SDK components

echo.
echo ================================================
echo Buffalo Milk Predictor - Android SDK Installer
echo ================================================
echo.

REM Create SDK directory
echo Creating SDK directories...
if not exist C:\Android mkdir C:\Android
if not exist C:\Android\sdk mkdir C:\Android\sdk

echo [OK] Directories created

REM Download SDK Command-Line Tools
echo.
echo Downloading Android SDK Command-Line Tools...
echo This will take a few minutes (~700 MB)...
echo.

cd C:\Android

REM Using PowerShell to download (more reliable)
powershell -Command "$ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri 'https://dl.google.com/android/repository/commandlinetools-win-11076708_latest.zip' -OutFile 'cmdline-tools.zip' -TimeoutSec 600"

if exist cmdline-tools.zip (
    echo [OK] Download complete
    
    REM Extract the archive
    echo Extracting SDK tools...
    powershell -Command "Expand-Archive -Path 'cmdline-tools.zip' -DestinationPath '.' -Force"
    
    if exist cmdline-tools (
        echo [OK] Extraction complete
        
        REM Create tools directory structure
        if not exist cmdline-tools\tools mkdir cmdline-tools\tools
        
        REM Move latest content to proper location
        powershell -Command "Get-ChildItem -Path 'cmdline-tools' -Exclude 'tools' -Force | Move-Item -Destination 'cmdline-tools\tools\' -Force"
        
        echo.
        echo Installing Android platforms and build-tools...
        echo This may take several minutes...
        echo.
        
        REM Set environment for SDK manager
        setx ANDROID_SDK_ROOT C:\Android\sdk
        set ANDROID_SDK_ROOT=C:\Android\sdk
        
        REM Accept licenses
        echo y | C:\Android\cmdline-tools\tools\bin\sdkmanager.bat "licenses"
        
        REM Install required components
        C:\Android\cmdline-tools\tools\bin\sdkmanager.bat "platforms;android-34" "platforms;android-24" "build-tools;34.0.0"
        
        echo.
        echo [OK] Android SDK installed successfully
        
        REM Set permanent environment variable
        setx ANDROID_HOME C:\Android\sdk
        
        REM Cleanup download
        del cmdline-tools.zip
        
        echo.
        echo ================================================
        echo Installation Complete!
        echo ================================================
        echo.
        echo ANDROID_HOME set to: C:\Android\sdk
        echo.
        echo You can now build the app by running:
        echo   gradle clean build
        echo.
    ) else (
        echo [ERROR] Extraction failed
        goto error
    )
) else (
    echo [ERROR] Download failed
    goto error
)

goto success

:error
echo.
echo [ERROR] Installation encountered an issue
echo.
echo Please try manual installation:
echo 1. Download from: https://developer.android.com/studio
echo 2. Install Android Studio
echo 3. Let it download SDK automatically
echo.
pause
exit /b 1

:success
echo Press any key to continue...
pause
