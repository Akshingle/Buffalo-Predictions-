# Simple Android SDK Installer
Write-Host "Installing Android SDK..."

$sdkDir = "C:\Android\sdk"
New-Item -ItemType Directory -Path C:\Android -Force | Out-Null

Write-Host "Downloading SDK tools..."
$url = "https://dl.google.com/android/repository/commandlinetools-win-11076708_latest.zip"
$zip = "C:\Android\cmdline-tools.zip"

try {
    $ProgressPreference = 'SilentlyContinue'
    Invoke-WebRequest -Uri $url -OutFile $zip -TimeoutSec 600
    Write-Host "Downloaded successfully"
} catch {
    Write-Host "Download failed. Please install Android Studio instead."
    Write-Host "From: https://developer.android.com/studio"
    exit
}

Write-Host "Extracting files..."
Expand-Archive -Path $zip -DestinationPath C:\Android -Force
Remove-Item $zip -Force

Write-Host "Setting environment..."
[Environment]::SetEnvironmentVariable("ANDROID_HOME", $sdkDir, "User")
$env:ANDROID_HOME = $sdkDir

Write-Host "Installing components..."
C:\Android\cmdline-tools\bin\sdkmanager.bat "platforms;android-34" "platforms;android-24" "build-tools;34.0.0" --verbose

Write-Host "Done! Android SDK is ready."
Write-Host "Run: gradle clean build"
