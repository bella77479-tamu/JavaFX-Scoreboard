Football Scoreboard — CSCE 314 Final Project

App Purpose:
This is a two-team American football scoreboard application built with JavaFX. 
Users enter home and away team names on a setup screen, then use a separate 
controller panel to record scoring events — touchdown (+6), field goal (+3), 
safety/2-pt conversion (+2), and extra point (+1). The scoreboard window updates
in real time, shows the last scoring play, and keeps a running game log. An undo
button reverts the most recent action, a clear button resets both scores 
while keeping team names, and the update name/ name input allows you to change the team names.


MVC Design:
The application is organized into three strict layers with no cross-layer logic leaking between 
them. 

Model:
The model is split across three classes. Team.java holds a single team's name and score, 
with methods to add points, reset the score, and rename the team. ScoringPlay.java represents 
one scoring event — it stores which side scored, how many points, and generates a human-readable
description (e.g. "Aggies scored a touchdown!"). Game.java is the central model: it owns two 
Team objects and an ArrayDeque<ScoringPlay> as an undo stack. All scoring logic, validation, \
and history management live here. The model throws descriptive exceptions for invalid states 
so controllers can catch them and show friendly alerts.

View:
All three screens (Setup, Scoreboard, Controller) are authored in SceneBuilder and saved as FXML 
files. style.css handles all visual styling including colors, fonts, and spacing. The view has 
zero logic, it only declares layout, binds fx:id attributes, and references event handler method 
names in the controller.

Controller:
SetupController.java handles the setup screen. When the user clicks Enter, it validates the 
names, creates a Game instance, calls game.setTeamNames(), and passes the same Game object
to both other controllers before opening their windows. Controller.java handles the scoring 
panel, it reads which radio button is selected, calls game.scoreHome() or game.scoreAway(), 
then tells ScoreboardController to refresh its display. ScoreboardController.java owns the 
scoreboard labels and has a single refreshDisplay() method that reads all values from the 
model and updates the UI. No scoring logic or history tracking lives in any controller.


How to Build and Run:
Compile:
Run from the src/ directory. Replace the module path with your actual JavaFX lib folder location.
javac --module-path "C:\Users\inane\OneDrive\Desktop\CSCE314\java\javaDownload\javafx-sdk-26\lib" --add-modules javafx.controls,javafx.fxml model/Team.java model/ScoringPlay.java model/Game.java model/GameTests.java controller/Controller.java controller/ScoreboardController.java controller/SetupController.java app/App.java

Run:
Run from the src/ directory. Replace the module path with your actual JavaFX lib folder location.
java --module-path "C:\Users\inane\OneDrive\Desktop\CSCE314\java\javaDownload\javafx-sdk-26\lib" --add-modules javafx.controls,javafx.fxml -cp . app.App


Model API Summary:

Team:
Team(String name) - Constructor. Throws IllegalArgumentException if name is null or blank.
getName() - Returns the team's name.
getScore() - Returns the team's current score.
setName(String name) - Updates the team name. Throws IllegalArgumentException if blank.
addScore(int points) - Adds points to the score (pass negative to subtract, used internally by undo).
resetScore() - Resets score to 0.

ScoringPlay:
ScoringPlay(Side side, int points, String teamName) - Constructor. Side is HOME or AWAY. Throws IllegalArgumentException if points ≤ 0.
getSide() - Returns ScoringPlay.Side.HOME or AWAY.
getPoints() - Returns the point value of this play.
getDescription() - Returns a display string, e.g. "Aggies scored a touchdown!"

Game:
setTeamNames(String side, String name) - Sets or updates one team's name. side must be "home" or "away". Throws IllegalArgumentException if name is null/blank or side is invalid.
namesAreSet() - Returns true if both home and away teams have been created.
getHomeName() / getAwayName() - Returns the respective team's name, or empty string if not yet set.
getHomeScore() / getAwayScore() - Returns the respective team's current score.
scoreHome(int points) - Records a scoring play for the home team. Throws IllegalStateException if both names aren't set; IllegalArgumentException if points ≤ 0.
scoreAway(int points) - Same as above for the away team.undoLast()Reverts the most recent scoring play. Throws IllegalStateException if history is empty.
canUndo() - Returns true if there is at least one play to undo.getLastPlayDescription()Returns the description of the most recent play, or empty string if none.
getPlayLog() - Returns a List<String> of all play descriptions, oldest first.clearGame()Resets both scores to 0 and clears history. Team names are kept.


How to Run the Model Tests:
The tests are in model/GameTests.java

Compile is same as earlier!
Compile (from src/ directory): 
javac --module-path "C:\Users\inane\OneDrive\Desktop\CSCE314\java\javaDownload\javafx-sdk-26\lib" --add-modules javafx.controls,javafx.fxml model/Team.java model/ScoringPlay.java model/Game.java model/GameTests.java controller/Controller.java controller/ScoreboardController.java controller/SetupController.java app/App.java

Run:
java -cp . model.GameTests

The output lists each test as PASS or FAIL with a description, grouped into Team, 
ScoringPlay, and Game sections, with a final summary line. All 36 tests should show
PASS on a clean build.


Known Limitations:
No persistent storage: game state is lost when the app is closed. There is no save or load feature.
No game clock or period tracking: the app records scores only; there is no timer, quarter, or 
overtime support.
Multi-window layout: the scoreboard and controller panel are separate windows. Closing one does
not automatically close the other.
No input length limit: team names accept any non-blank string; very long names may overflow 
the scoreboard display labels.
No score floor: scores can go negative if undo is somehow called more times than scores were 
added, though the UI prevents this in normal use since the undo button relies on canUndo().