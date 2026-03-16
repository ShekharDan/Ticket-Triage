# Use JDK 25 for this project (run once per terminal: .\use-jdk25.ps1)
$jdk = "C:\Program Files\Java\jdk-25"
$env:JAVA_HOME = $jdk
$env:Path = "$jdk\bin;" + $env:Path
Write-Host "JAVA_HOME set to $jdk"
java -version
