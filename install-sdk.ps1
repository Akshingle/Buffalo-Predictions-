# Android SDK Installation Script for Buffalo Milk Predictor
# This script automates Android SDK download and setup

Write-Host "================================================" -ForegroundColor Cyan
Write-Host "Buffalo Milk Predictor - Android SDK Setup" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan
Write-Host ""

# Check if running as admin
$isAdmin = ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator")
if (-not $isAdmin) {
    Write-Host "Note: Running without admin. Some operations may require elevation." -ForegroundColor Yellow
}

# Define paths
$sdkRoot = "C:\Android"
$sdkDir = "$sdkRoot\sdk"
$cmdlineToolsDir = "$sdkRoot\cmdline-tools"
$toolsDir = "$cmdlineToolsDir\tools"

# Step 1: Create directories
Write-Host ""
Write-Host "[1/5] Creating directories..." -ForegroundColor Cyan
if (-not (Test-Path $sdkRoot)) { New-Item -ItemType Directory -Path $sdkRoot -Force | Out-Null }
if (-not (Test-Path $sdkDir)) { New-Item -ItemType Directory -Path $sdkDir -Force | Out-Null }
Write-Host "  ✓ Directories ready" -ForegroundColor Green

# Step 2: Download SDK Command-Line Tools
Write-Host ""
Write-Host "[2/5] Downloading Android SDK Command-Line Tools..." -ForegroundColor Cyan
Write-Host "  This will take 2-5 minutes - 700 MB download..." -ForegroundColor Yellow
Write-Host ""

$downloadUrl = "https://dl.google.com/android/repository/commandlinetools-win-11076708_latest.zip"
$zipFile = "$sdkRoot\cmdline-tools.zip"

try {
    $ProgressPreference = 'SilentlyContinue'
    Invoke-WebRequest -Uri $downloadUrl -OutFile $zipFile -TimeoutSec 600 -ErrorAction Stop
    Write-Host "  ✓ Download complete" -ForegroundColor Green
} catch {
    Write-Host "  ✗ Download failed: $_" -ForegroundColor Red
    Write-Host ""
    Write-Host "Try alternative: Download Android Studio from https://developer.android.com/studio" -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
}

# Step 3: Extract and setup
Write-Host ""
Write-Host "[3/5] Extracting and organizing files..." -ForegroundColor Cyan

try {
    # Extract zip
    Expand-Archive -Path $zipFile -DestinationPath $sdkRoot -Force -ErrorAction Stop
    
    # Move to proper location
    if (Test-Path "$sdkRoot\cmdline-tools" -and -not (Test-Path "$cmdlineToolsDir\tools")) {
        Move-Item -Path "$sdkRoot\cmdline-tools" -Destination "$cmdlineToolsDir\tools" -Force -ErrorAction Stop
    }
    
    Remove-Item $zipFile -Force -ErrorAction SilentlyContinue
    Write-Host "  ✓ Extraction complete" -ForegroundColor Green
} catch {
    Write-Host "  ✗ Extraction failed: $_" -ForegroundColor Red
    exit 1
}

# Step 4: Set environment variables
Write-Host ""
Write-Host "[4/5] Configuring environment..." -ForegroundColor Cyan

$env:ANDROID_SDK_ROOT = $sdkDir
$env:ANDROID_HOME = $sdkDir

# Set permanently
[Environment]::SetEnvironmentVariable("ANDROID_SDK_ROOT", $sdkDir, "User")
[Environment]::SetEnvironmentVariable("ANDROID_HOME", $sdkDir, "User")

Write-Host "  ✓ Environment variables set" -ForegroundColor Green
Write-Host "    ANDROID_HOME=$sdkDir" -ForegroundColor Gray

# Step 5: Install SDK components
Write-Host ""
Write-Host "[5/5] Installing SDK platforms and build-tools..." -ForegroundColor Cyan
Write-Host "  This may take 5-10 minutes..." -ForegroundColor Yellow
Write-Host ""

$sdkManagerPath = "$toolsDir\bin\sdkmanager.bat"

if (Test-Path $sdkManagerPath) {
    try {
        # Accept licenses silently
        & cmd /c "echo y | $sdkManagerPath licenses" 2>&1 | Out-Null
        
        # Install components
        & cmd /c "$sdkManagerPath `"platforms;android-34`" `"platforms;android-24`" `"build-tools;34.0.0`"" 2>&1 | Select-Object -First 50
        
        Write-Host ""
        Write-Host "  ✓ Installation complete" -ForegroundColor Green
    } catch {
        Write-Host "  ✗ Installation failed: $_" -ForegroundColor Red
    }
} else {
    Write-Host "  ✗ SDK manager not found at: $sdkManagerPath" -ForegroundColor Red
}

# Verification
Write-Host ""
Write-Host "================================================" -ForegroundColor Cyan
Write-Host "Setup Complete!" -ForegroundColor Green
Write-Host "================================================" -ForegroundColor Cyan
Write-Host ""

# Verify installation
$platformsCheck = Test-Path "$sdkDir\platforms\android-34"
$buildToolsCheck = Test-Path "$sdkDir\build-tools\34.0.0"

Write-Host "Installation Status:"
Write-Host "  Android 14 (API 34): $(if ($platformsCheck) { '✓ Installed' } else { '✗ Not found' })" -ForegroundColor $(if ($platformsCheck) { 'Green' } else { 'Red' })
Write-Host "  Build Tools 34.0.0:  $(if ($buildToolsCheck) { '✓ Installed' } else { '✗ Not found' })" -ForegroundColor $(if ($buildToolsCheck) { 'Green' } else { 'Red' })
Write-Host ""

if ($platformsCheck -and $buildToolsCheck) {
    Write-Host "Ready to build! Run: gradle clean build" -ForegroundColor Green
} else {
    Write-Host "Some components may not have installed correctly." -ForegroundColor Yellow
    Write-Host "Try running: gradle clean build" -ForegroundColor Yellow
}

Write-Host ""
Read-Host "Press Enter to exit"
