package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TransactionDetailController implements Initializable {
    @FXML
    private AnchorPane TransactionDetailPane;
    @FXML
    private TableColumn<TransactionDetail, Integer> Transaction_Detail_Id;
    @FXML
    private TableColumn<Transaction, Integer> Transaction_Id;
    @FXML
    private TableColumn<Transaction, Integer> Item_Name;
    @FXML
    private TableColumn<Transaction, Integer> Quantity;
    @FXML
    public Button Menu;
    @FXML
    public Button refresh;
    @FXML
    public TextField trans_detail_id;
    @FXML
    public TextField trans_id;
    @FXML
    public TextField item_name;
    @FXML
    public TextField quantity;
    @FXML
    private TableView<TransactionDetail> TransactionDetailTableView;

    public TransactionDetail td;
    public Item it;
    public ItemController ic;

    private ObservableList<TransactionDetail> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Transaction_Detail_Id.setCellValueFactory(new PropertyValueFactory<>("transaction_detail_id"));
        Transaction_Id.setCellValueFactory(new PropertyValueFactory<>("transaction_id"));
        Item_Name.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        trans_detail_id.setEditable(false);
        trans_detail_id.setDisable(true);

        TransactionDetailTableView.setItems(data);
        getUpdated();

    }

    public void getUpdated() {
        TransactionDetailTableView.getItems().clear();
        data.clear();
        try {
            MainMenu.db.SelectTableTransactionDetail();
            while (MainMenu.db.rs.next()) {
                data.add(new TransactionDetail(MainMenu.db.rs.getInt("Transaction_Detail_Id"), MainMenu.db.rs.getInt("transaction_id"), MainMenu.db.rs.getString("item_name"),
                        MainMenu.db.rs.getInt("quantity")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void DeleteClick(MouseEvent mouseEvent) throws SQLException {
        if (!TransactionDetailTableView.getSelectionModel().isEmpty()) {
            String transaction_detail_id_string = trans_detail_id.getText();
            MainMenu.db.DeleteTableTransactionDetail(transaction_detail_id_string);
            getUpdated();
        } else {
            alertMessage("Please select Id field");
        }

    }

    public void UpdateClick(MouseEvent mouseEvent) {
        if (!TransactionDetailTableView.getSelectionModel().isEmpty()) {
            String transaction_details_id_string = trans_detail_id.getText();
            String transaction_string = trans_id.getText();
            String item_name_string = item_name.getText();
            String quantity_string = quantity.getText();

            MainMenu.db.UpdateTableTransactionDetail(td, transaction_details_id_string, transaction_string, item_name_string, quantity_string);
            getUpdated();
        } else {
            alertMessage("Please select item from table");
        }
    }

    public void Refresh(MouseEvent mouseEvent) {
        getUpdated();
    }

    public void selectId(MouseEvent mouseEvent) {
        td = TransactionDetailTableView.getSelectionModel().getSelectedItem();
        String transaction_detail_id_string = String.valueOf(td.getTransaction_detail_id());
        trans_detail_id.setText(transaction_detail_id_string);
    }

    public void openItem(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Item.fxml"));
            Parent root = loader.load();
            ic = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            it = ic.getItemTable();

            item_name.setText(it.getItem_name());


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
