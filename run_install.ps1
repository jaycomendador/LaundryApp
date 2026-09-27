Set-Location 'C:\Users\Jay Comendador\Desktop\laundryshop'
$env:ANDROID_USER_HOME = 'C:\Users\Jay Comendador\.android'
$env:ANDROID_HOME = 'C:\Users\Jay Comendador\AppData\Local\Android\Sdk'
$env:JAVA_HOME = 'C:\Users\Jay Comendador\Desktop\laundryshop\jdk21'
Remove-Item Env:\ANDROID_PREFS_ROOT -ErrorAction SilentlyContinue
& 'C:\Users\Jay Comendador\Desktop\laundryshop\jdk21\bin\java.exe' -Xmx1024m -classpath 'gradle/wrapper/gradle-wrapper.jar' org.gradle.wrapper.GradleWrapperMain installDebug
