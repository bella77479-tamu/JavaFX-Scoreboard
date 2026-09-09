package controller;
 
import java.io.IOException;
 
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
 
import model.Game;
 
public class SetupController {
 
    @FXML private TextField homeInput;
    @FXML private TextField visitorInput;
    @FXML private Button    enterTeamButton;
 
    @FXML
    private void enterButton() throws IOException {
        String homeName = homeInput.getText();
        String awayName = visitorInput.getText();
 
        if (homeName == null || homeName.isBlank() ||
            awayName == null || awayName.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Team Names");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a name for both teams before continuing.");
            alert.showAndWait();
            return;
        }
 
        Game game = new Game();
        game.setTeamNames("home", homeName);
        game.setTeamNames("away", awayName);
 
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/view/Scoreboard.fxml"));
        Parent scoreboard = loader1.load();
        ScoreboardController scoreboardController = loader1.getController();
 
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/Controller.fxml"));
        Parent remote = loader2.load();
        Controller controller = loader2.getController();
 
        scoreboardController.setGame(game);
        controller.setGame(game);
        controller.setScoreboardController(scoreboardController);
 
        Stage scoreboardStage = new Stage();
        scoreboardStage.setScene(new Scene(scoreboard));
        scoreboardStage.setTitle("Scoreboard");
        scoreboardStage.show();
 
        Stage controllerStage = new Stage();
        controllerStage.setScene(new Scene(remote));
        controllerStage.setTitle("Controller");
        controllerStage.show();
 
        scoreboardController.refreshDisplay();
 
        ((Stage) enterTeamButton.getScene().getWindow()).close();
    }
}