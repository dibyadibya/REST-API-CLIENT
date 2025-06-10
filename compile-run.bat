@echo off
set JARPATH=C:\Users\dibya\Downloads\json-simple-1.1.1.jar

javac -cp ".;%JARPATH%" WeatherForecastApp.java
if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b
)

java -cp ".;%JARPATH%" WeatherForecastApp
pause
