@echo off

set "sourceDir=src"
set "outputDir=lib\classes"
set "mainClass=Expense_Tracker"
setlocal enabledelayedexpansion

:: Check number of arguments
if "%~1"=="" (
    echo Error: No arguments provided.
    exit /b 1
)

:: Handle arguments
if "%~1"=="-r" (
    if "%~2"=="" (
        echo Error: No argument provided after -r.
        exit /b 1
    )
    set "additionalArgs=%~2"
    set res=true
) else (
    set "additionalArgs=%*"
)

:: Create the output directory if it doesn't exist
if not exist "%outputDir%" mkdir "%outputDir%"

:: Compile if needed (check for missing directory or specific class files)
if not exist "%outputDir%" set res=true
if not exist "%outputDir%\%mainClass%.class" set res=true

if defined res (
    echo Initializing...
    javac -cp "lib\jackson-core.jar;lib\jackson.jar" -d lib\classes src\*.java
    if !errorlevel! neq 0 (
        echo Error: Compilation failed.
        exit /b 1
    )
)

:: Run the Java main class with additional arguments
java -cp "lib\classes;lib\jackson-core.jar;lib\jackson.jar;lib\jackson-annotations.jar" Expense_Tracker %additionalArgs%
if !errorlevel! neq 0 (
    echo Error: Application execution failed.
    exit /b 1
)

endlocal
