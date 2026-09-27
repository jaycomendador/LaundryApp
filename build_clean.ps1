Remove-Item env:ANDROID_PREFS_ROOT -ErrorAction SilentlyContinue
[Environment]::SetEnvironmentVariable('ANDROID_PREFS_ROOT', $null, 'Process')
$env:ANDROID_USER_HOME = 'C:\Users\Jay Comendador\.android'
$env:JAVA_HOME = 'C:\Users\Jay Comendador\Desktop\laundryshop\jdk21'
Set-Location 'C:\Users\Jay Comendador\Desktop\laundryshop'
.\gradlew.bat assembleDebug --no-daemon
