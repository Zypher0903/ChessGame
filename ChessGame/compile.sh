#!/bin/bash

echo "Compiling Chess Pro..."
mkdir -p bin
mkdir -p data

javac -d bin -sourcepath src src/Main.java src/chess/account/*.java src/chess/ai/*.java src/chess/game/*.java src/chess/pieces/*.java src/chess/menu/*.java src/chess/gui/*.java

if [ $? -eq 0 ]; then
    echo ""
    echo "Compilation successful!"
    echo "To run the game: ./run.sh"
else
    echo ""
    echo "Compilation failed!"
fi
