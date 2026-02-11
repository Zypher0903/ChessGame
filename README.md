# CHESS - Advanced Chess Game

A professional chess game with account system, AI opponent, trophy rankings, and modern UI.

## FEATURES

### Account System
- Create account with username and password
- Secure login system
- Profile statistics tracking
- Trophy ranking system

### Trophy & Ranking System
- Earn 8 trophies per win
- Trophy loss varies by rank:
  - 0-100 trophies: -2 per loss
  - 100-500 trophies: -3 per loss
  - 500-1000 trophies: -3 per loss
  - 1000-3000 trophies: -5 per loss
  - 3000-10000 trophies: -6 per loss
  - 10000-20000 trophies: -7 per loss
  - 20000-50000 trophies: -8 per loss
  - 50000+ trophies: -10 per loss

### Rank Tiers
- Bronze (0-99 trophies)
- Silver (100-499 trophies)
- Gold (500-999 trophies)
- Platinum (1000-2999 trophies)
- Diamond (3000-9999 trophies)
- Master (10000-19999 trophies)
- Grandmaster (20000-49999 trophies)
- Legend (50000+ trophies)

### Game Modes
- **VS AI** - Play against computer (Easy, Medium, Hard)
- **VS Online** - Coming soon
- **Party Mode** - Coming soon

### Game Features
- Full chess rules implementation
- Move validation
- Check and checkmate detection
- Stalemate detection
- Pawn promotion (auto-promotes to Queen)
- Modern, clean UI
- Trophy tracking during game
- Win/Loss/Draw statistics

## HOW TO RUN

### Windows (PowerShell):
```powershell
cd path\to\ChessGamePro
.\compile.bat
.\run.bat
```

### Windows (Command Prompt):
```cmd
cd path\to\ChessGamePro
compile.bat
run.bat
```

### Linux/Mac:
```bash
cd path/to/ChessGamePro
./compile.sh
./run.sh
```

### IntelliJ IDEA:
1. Open IntelliJ IDEA
2. File → Open → Select ChessGamePro folder
3. Wait for indexing
4. Run → Edit Configurations
5. Set Working Directory to ChessGamePro folder (not src!)
6. Right-click Main.java → Run

## PROJECT STRUCTURE

```
ChessGamePro/
├── src/
│   ├── Main.java                    # Entry point
│   └── chess/
│       ├── account/
│       │   ├── Account.java         # User account model
│       │   └── AccountManager.java  # Account persistence
│       ├── ai/
│       │   └── ChessAI.java         # AI opponent
│       ├── game/
│       │   ├── Position.java        # Board position
│       │   ├── Move.java            # Move representation
│       │   ├── Board.java           # Chess board
│       │   └── ChessGame.java       # Game logic
│       ├── pieces/
│       │   ├── Piece.java           # Abstract piece
│       │   ├── King.java
│       │   ├── Queen.java
│       │   ├── Rook.java
│       │   ├── Bishop.java
│       │   ├── Knight.java
│       │   └── Pawn.java
│       ├── menu/
│       │   ├── LoginScreen.java     # Login UI
│       │   ├── MainMenu.java        # Main menu UI
│       │   └── ProfileWindow.java   # Profile stats UI
│       └── gui/
│           └── GameWindow.java      # Game UI
├── assets/
│   └── pieces.png                   # Chess piece sprites
├── data/
│   └── accounts.dat                 # Saved accounts (created on first run)
├── bin/                             # Compiled classes
├── compile.bat / compile.sh         # Compilation scripts
└── run.bat / run.sh                 # Run scripts
```

## FIRST TIME SETUP

1. **Extract the ZIP file**
2. **Ensure you have JDK 8 or higher** installed
3. **Verify file structure**: Make sure `assets/pieces.png` exists
4. **Compile the project** using the compile script
5. **Run the game** using the run script

## GAMEPLAY

### Creating Account
1. Launch the game
2. Enter desired username (minimum 3 characters)
3. Enter password (minimum 4 characters)
4. Click "CREATE NEW ACCOUNT"
5. Login with your credentials

### Playing VS AI
1. Login to your account
2. Click "VS AI" card
3. Click "PLAY" button
4. Select difficulty (Easy, Medium, Hard)
5. Play as White against AI (Black)

### How to Play
- Click on your piece to select it (highlighted in green)
- Valid moves show in yellow
- Click a yellow square to move
- Click another piece to change selection
- White moves first
- Game ends on checkmate or stalemate

### Trophy System
- Win: +8 trophies
- Loss: Variable (see ranking system)
- Draw: 0 trophies
- Trophy changes displayed after game ends

## STATISTICS TRACKED

- Total Trophies
- Wins
- Losses  
- Draws
- Win Rate (%)
- Current Rank
- Trophy loss amount per game

## REQUIREMENTS

- Java JDK 8 or higher
- Windows, Linux, or Mac OS
- 50 MB free space
- Display resolution: 800x600 minimum

## TROUBLESHOOTING

**Images not loading:**
- Ensure `assets/pieces.png` is in the correct location
- Working directory must be ChessGamePro folder (not src or bin)

**"javac not found":**
- Install JDK (not just JRE)
- Add Java to PATH

**Account data not saving:**
- Check write permissions in the data folder
- Folder will be created automatically on first run

**IntelliJ issues:**
- File → Invalidate Caches → Restart
- Verify Working Directory in Run Configuration

## PLANNED FEATURES

- Online multiplayer
- Party/Room system
- Friend list
- Match history
- Replay system
- Tournament mode
- Custom time controls
- En passant and castling
- More AI difficulty levels
- Profile customization

## TECHNICAL DETAILS

- Language: Java
- GUI: Swing
- Data Persistence: Java Serialization
- AI: Evaluation-based decision making
- Design Pattern: MVC (Model-View-Controller)

## TIPS

- Start with Easy AI to learn
- Study your win/loss patterns in Profile
- Higher ranks mean more at stake per game
- Trophy loss increases with rank to maintain balance
- Practice against AI before playing online (when available)

## LICENSE

Free to use and modify for personal and educational purposes.

---

**Enjoy the game and climb the ranks!**
