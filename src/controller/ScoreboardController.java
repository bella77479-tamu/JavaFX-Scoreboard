package controller;
 
import javafx.fxml.FXML;
import javafx.scene.control.Label;
 
import model.Game;
 
public class ScoreboardController {
 
    @FXML private Label homename;
    @FXML private Label awayname;
    @FXML private Label homescore;
    @FXML private Label awayscore;
    @FXML private Label lastplaylabel;
 
    private Game game;
 
    public void setGame(Game game) {
        this.game = game;
    }

    public void refreshDisplay() {
        homename.setText(game.getHomeName());
        awayname.setText(game.getAwayName());
        homescore.setText(String.valueOf(game.getHomeScore()));
        awayscore.setText(String.valueOf(game.getAwayScore()));
        lastplaylabel.setText(game.getLastPlayDescription());
    }
}