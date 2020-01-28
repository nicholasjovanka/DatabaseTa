package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private javafx.scene.control.PasswordField PasswordField;

    @FXML
    private Pane LoginPane;

    @FXML
    private TextField UserNameField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserNameField.setText("NAM8420");
        PasswordField.setText("dnytaj2b");
    }

    @FXML
    void Login(ActionEvent event) throws IOException {
        if (MainMenu.db.connectDB(UserNameField.getText(), PasswordField.getText())) {
            Stage currentstage = (Stage) LoginPane.getScene().getWindow();
            Parent newpane = FXMLLoader.load(getClass().getResource("/GUI/MainMenu.fxml"));
            currentstage.setScene(new Scene(newpane));
            Main.menustate = true;
        }
    }

    public static void alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
