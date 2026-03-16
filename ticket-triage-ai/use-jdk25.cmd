@echo off
REM Use JDK 25 for this project (run once per terminal: use-jdk25.cmd)
set "JAVA_HOME=C:\Program Files\Java\jdk-25"
set "PATH=%JAVA_HOME%\bin;%PATH%"
echo JAVA_HOME set to %JAVA_HOME%
java -version
