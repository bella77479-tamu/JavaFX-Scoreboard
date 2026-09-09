package controller;
 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
 
import model.Game;
import model.ScoringPlay;
 
public class Controller {
 
    @FXML private RadioButton homeRadio;
    @FXML private RadioButton awayRadio;
    @FXML private Label       gameUpdates;
    @FXML private Button      extraPoint;
    @FXML private Button      safetyButton;
    @FXML private Button      fieldGoal;
    @FXML private Button      touchdown;
    @FXML private TextField   updateTeam;

    private Game                game;
    private ScoreboardController scoreboardController;
 
    public void setGame(Game game) {
        this.game = game;
    }
 
    public void setScoreboardController(ScoreboardController sc) {
        this.scoreboardController = sc;
    }

    @FXML
    private void addPoints(ActionEvent event) {
        if (!homeRadio.isSelected() && !awayRadio.isSelected()) {
            showAlert("No Team Selected", "Please select Home or Away before scoring.");
            return;
        }
 
        Button clicked = (Button) event.getSource();
        int points;
        if      (clicked == extraPoint)   points = 1;
        else if (clicked == safetyButton) points = 2;
        else if (clicked == fieldGoal)    points = 3;
        else if (clicked == touchdown)    points = 6;
        else return;

        try {
            if (homeRadio.isSelected()) {
                game.scoreHome(points);
            } else {
                game.scoreAway(points);
            }
        } catch (IllegalStateException e) {
            showAlert("Cannot Score", e.getMessage());
            return;
        }
 
        scoreboardController.refreshDisplay();
        refreshGameLog();
    }
 
    @FXML
    private void undoLast() {
        try {
            game.undoLast();
        } catch (IllegalStateException e) {
            showAlert("Nothing to Undo", "There are no scoring plays to undo.");
            return;
        }
        scoreboardController.refreshDisplay();
        refreshGameLog();
    }
 
    @FXML
    private void clearScore() {
        game.clearGame();
        scoreboardController.refreshDisplay();
        refreshGameLog();
    }
 
    private void refreshGameLog() {
        StringBuilder sb = new StringBuilder("Game Updates:\n");
        for (String line : game.getPlayLog()) {
            sb.append(line).append("\n");
        }
        gameUpdates.setText(sb.toString());
    }

    @FXML
    private void updateName(){
        if(updateTeam.getText() == null || updateTeam.getText().isBlank()){
            showAlert("Team name is null", "Please enter a name in order to update.");
            return;
        }
        if(!homeRadio.isSelected() && !awayRadio.isSelected()){
            showAlert("No Team Selected", "Please select Home or Away before changing a name.");
            return;
        }
        
        if(homeRadio.isSelected()){
            game.setTeamNames("home", updateTeam.getText());
        } else if(awayRadio.isSelected()){
            game.setTeamNames("away", updateTeam.getText());
        }
        scoreboardController.refreshDisplay();
    }
 
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}