# NYT Tiles Game
by Cedar Hudgens (gphudgens@cpp.edu)

## How to Run
1. Clone repository `git clone https://github.com/Grace0Hud/NYT_Tiles`
2. Open in IntelliJ
3. Run SwingUI.java

## Features Implemented
- **Grid Layout**: 4x4 grid of tiles with hidden categories
- **Selection Mechanism**: Click to select tiles. 2 can be matched at a time.
There is no manually selecting tiles, but this is done by the controller. 
- **Category Validation**: Check if selected tiles belong to same category (have one of the same ids)
- **Scoring System**: Points for correct matches, penalties for mistakes
- **Difficulty Levels**: Easy (obvious), Medium (tricky), Hard (wordplay/abstract)
- **Clear Board**: Remove correctly matched groups
- **End Condition**: Win when all groups found, lose after 3 wrong attempts

## Controls

## Known Issues

## External Libraries
- JUnit 5.8.1 (testing)
- jackson 2.16.1 (JSON Parsing)