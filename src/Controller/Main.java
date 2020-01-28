package Controller;


import Controller.MainMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {
    static boolean menustate = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Login.fxml"));
        primaryStage.setTitle("Database Program");
        primaryStage.setScene(new Scene(root));
        primaryStage.resizableProperty().setValue(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            if (menustate) {
                try {
                    MainMenu.db.conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            Platform.exit();
            System.exit(0);

        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
