$iconDir = 'C:\Users\akshay.ingle\Desktop\APP\app\src\main\res\mipmap-mdpi'
$pngBase64 = 'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNkYPhfDwAChwGA60e6kgAAAABJRU5ErkJggg=='
$pngBytes = [Convert]::FromBase64String($pngBase64)
[System.IO.File]::WriteAllBytes("$iconDir\ic_launcher.png", $pngBytes)

$iconDir = 'C:\Users\akshay.ingle\Desktop\APP\app\src\main\res\mipmap-hdpi'
[System.IO.File]::WriteAllBytes("$iconDir\ic_launcher.png", $pngBytes)

$iconDir = 'C:\Users\akshay.ingle\Desktop\APP\app\src\main\res\mipmap-xhdpi'
[System.IO.File]::WriteAllBytes("$iconDir\ic_launcher.png", $pngBytes)

$iconDir = 'C:\Users\akshay.ingle\Desktop\APP\app\src\main\res\mipmap-xxhdpi'
[System.IO.File]::WriteAllBytes("$iconDir\ic_launcher.png", $pngBytes)

$iconDir = 'C:\Users\akshay.ingle\Desktop\APP\app\src\main\res\mipmap-xxxhdpi'
[System.IO.File]::WriteAllBytes("$iconDir\ic_launcher.png", $pngBytes)

Write-Host "All launcher icons created successfully!"
