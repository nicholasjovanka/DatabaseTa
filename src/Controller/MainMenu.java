package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    public static DB db = new DB();
    @FXML
    private Pane BackgroundPane;

    @FXML
    private Pane MenuPane;

    @FXML
    private Pane MenuDisplayPane;

    @FXML
    private Pane ContentPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parent newpane = null;
        try {
            newpane = FXMLLoader.load(getClass().getResource("/GUI/Cart.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContentPane.getChildren().setAll(newpane);
    }


    public void LogOut() throws IOException, SQLException {
        Stage currentstage = (Stage) BackgroundPane.getScene().getWindow();
        Parent newpane = FXMLLoader.load(getClass().getResource("/GUI/Login.fxml"));
        MainMenu.db.conn.close();
        currentstage.setScene(new Scene(newpane));
        Main.menustate = false;
    }

    public void GuestTableScene(ActionEvent actionEvent) throws IOException {
        Parent newpane = FXMLLoader.load(getClass().getResource("/GUI/GuestTable.fxml"));
        ContentPane.getChildren().setAll(newpane);
        MenuDisplayPane.setVisible(false);
    }

    public void ItemScene(ActionEvent actionEvent) throws IOException {
        Parent newpane = FXMLLoader.load(getClass().getResource("/GUI/Item.fxml"));
        ContentPane.getChildren().setAll(newpane);
        MenuDisplayPane.setVisible(false);
    }

    public void CartScene(ActionEvent actionEvent) throws IOException {
        Parent newpane = FXMLLoader.load(getClass().getResource("/GUI/Cart.fxml"));
        ContentPane.getChildren().setAll(newpane);
        MenuDisplayPane.setVisible(false);
    }

    public void TransactionDetailScene(ActionEvent actionEvent) throws IOException {
        Parent newpane = FXMLLoader.load(getClass().getResource("/GUI/TransactionDetail.fxml"));
        ContentPane.getChildren().setAll(newpane);
        MenuDisplayPane.setVisible(false);
    }

    public void TransactionScene(ActionEvent actionEvent) throws IOException {
        Parent newpane = FXMLLoader.load(getClass().getResource("/GUI/Transaction.fxml"));
        ContentPane.getChildren().setAll(newpane);
        MenuDisplayPane.setVisible(false);
    }

    public void StoreScene(ActionEvent actionEvent) throws IOException {
        Parent newpane = FXMLLoader.load(getClass().getResource("/GUI/Store.fxml"));
        ContentPane.getChildren().setAll(newpane);
        MenuDisplayPane.setVisible(false);
    }

    public void TaxScene(ActionEvent actionEvent) throws IOException {
        Parent newpane = FXMLLoader.load(getClass().getResource("/GUI/Tax.fxml"));
        ContentPane.getChildren().setAll(newpane);
        MenuDisplayPane.setVisible(false);
    }

    public void ServiceScene(ActionEvent actionEvent) throws IOException {
        Parent newpane = FXMLLoader.load(getClass().getResource("/GUI/Service.fxml"));
        ContentPane.getChildren().setAll(newpane);
        MenuDisplayPane.setVisible(false);
    }

    public void MenuButton() {
        if (MenuDisplayPane.isVisible()) {
            MenuDisplayPane.setVisible(false);
        } else {
            MenuDisplayPane.setVisible(true);
        }
    }


}
