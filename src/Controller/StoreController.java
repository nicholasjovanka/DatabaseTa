package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StoreController implements Initializable {
    @FXML
    private AnchorPane StorePane;
    @FXML
    private TableColumn<Item, Integer> Store_Id;
    @FXML
    private TableColumn<Item, String> Store_Name;
    @FXML
    private TableColumn<Item, String> Store_Location;
    @FXML
    public Button Insert_Button;
    @FXML
    public Button Delete_Button;
    @FXML
    public Button Menu;
    @FXML
    public Button refresh;
    @FXML
    public TextField id;
    @FXML
    public TextField name;
    @FXML
    public TextField location;
    @FXML
    public Button Update_Button;
    @FXML
    public ChoiceBox Choice;
    @FXML
    private TableView<Store> StoreTableView;

    public Store store;

    @FXML
    void BackToMenu(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/GUI/MainMenu.fxml"));
        StorePane.getChildren().setAll(pane);
    }

    private ObservableList<Store> data = FXCollections.observableArrayList();
    ;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Store_Id.setCellValueFactory(new PropertyValueFactory<>("store_id"));
        Store_Name.setCellValueFactory(new PropertyValueFactory<>("store_name"));
        Store_Location.setCellValueFactory(new PropertyValueFactory<>("store_location"));

        StoreTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                store = newSelection;
                StoreTableView.getScene().getWindow();
            }
        });

        id.setEditable(false);
        id.setDisable(true);

        StoreTableView.setItems(data);
        getUpdated();
    }

    public void Refresh() {
        getUpdated();
    }

    public Store getStoreTable() {
        return store;
    }


    public void getUpdated() {
        StoreTableView.getItems().clear();
        data.clear();
        try {
            MainMenu.db.SelectTableStore();
            while (MainMenu.db.rs.next()) {
                data.add(new Store(MainMenu.db.rs.getInt("StoreId"), MainMenu.db.rs.getString("Storename"), MainMenu.db.rs.getString("Storelocation")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean validation(String str_name, String store_location) {
        if (str_name.equals("") && store_location.equals("")) {
            return false;
        }
        return true;
    }

    public boolean deleteValidation() {
        if (id.getText().equals("")) {
            return false;
        }
        return true;
    }


    public void InsertClick() throws SQLException {
        String store_name = name.getText();
        String store_location = location.getText();

        if (validation(store_name, store_location)) {

            MainMenu.db.InsertTableStore(store_name, store_location);
            getUpdated();
        } else {
            alertMessage("Please input the fields");
        }
    }


    public void DeleteClick() throws SQLException {
        if (!StoreTableView.getSelectionModel().isEmpty()) {
            Store st = StoreTableView.getSelectionModel().getSelectedItem();
            int store_id_int = st.getStore_id();
            MainMenu.db.DeleteTableStore(store_id_int);
            getUpdated();
        } else {
            alertMessage("Please select Id field");
        }
    }


    public void UpdateClick() throws SQLException {
        if (!StoreTableView.getSelectionModel().isEmpty()) {
            Store table = StoreTableView.getSelectionModel().getSelectedItem();
            String store_id = id.getText();
            String store_name = name.getText();
            String store_location = location.getText();

            id.clear();
            name.clear();
            location.clear();

            MainMenu.db.UpdateTableStore(table, store_id, store_name, store_location);
            getUpdated();
        } else {
            alertMessage("Please select item from the table");
        }
    }

    public static void alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
