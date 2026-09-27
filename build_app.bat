@echo off
set "JAVA_HOME=C:\Users\Jay Comendador\Downloads\prsm\PrismLauncher-Windows-MSVC-Portable-10.0.5 (2)\PrismLauncher-Windows-MSVC-Portable-10.0.5\java\java-runtime-delta"
set "ANDROID_HOME=C:\Users\Jay Comendador\AppData\Local\Android\Sdk"
set "ANDROID_SDK_ROOT=C:\Users\Jay Comendador\AppData\Local\Android\Sdk"
cd /d "%~dp0"
call "%~dp0gradlew.bat" %*
