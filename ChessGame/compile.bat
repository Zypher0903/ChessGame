@echo off
echo Compiling Chess Pro...
if not exist bin mkdir bin
if not exist data mkdir data

javac -d bin -sourcepath src src\Main.java src\chess\account\*.java src\chess\ai\*.java src\chess\game\*.java src\chess\pieces\*.java src\chess\menu\*.java src\chess\gui\*.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Compilation successful!
    echo To run the game: run.bat
) else (
    echo.
    echo Compilation failed!
)
pause
