package Controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;

public class CartController implements Initializable {
    boolean deleteison = false;
    boolean emptycart = false;
    ObservableList<CartItem> oblist = FXCollections.observableArrayList();
    ObservableList<Integer> StoreId = FXCollections.observableArrayList();
    ObservableList<Integer> TableId = FXCollections.observableArrayList();

    ObservableList<Integer> ServiceId = FXCollections.observableArrayList();
    @FXML
    private TableView<CartItem> CartTableView;

    @FXML
    private TableColumn<CartItem, Integer> IdColumn;

    @FXML
    private TableColumn<CartItem, String> ItemNameColumn;

    @FXML
    private TableColumn<CartItem, Integer> QuantityColumn;

    @FXML
    private Pane InsertPane;

    @FXML
    private ComboBox<Integer> StoreIdBox;

    @FXML
    private ComboBox<Integer> TableIdBox;


    @FXML
    private ComboBox<Integer> ServiceIdBox;

    @FXML
    private Pane ConfirmationPane;

    @FXML
    private Button YesButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        ItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        QuantityColumn.setCellValueFactory(new PropertyValueFactory<>("itemqty"));
        CartTableView.setItems(oblist);
        StoreIdBox.setItems(StoreId);
        TableIdBox.setItems(TableId);
        ServiceIdBox.setItems(ServiceId);
        refresh();
        refreshcombo();
    }

    public void refreshcombo() {
        StoreIdBox.getItems().clear();
        TableIdBox.getItems().clear();
        ServiceIdBox.getItems().clear();
        StoreId.clear();
        TableId.clear();
        ServiceId.clear();
        try {
            MainMenu.db.selectStoreID();
            while (MainMenu.db.rs.next()) {
                StoreId.add(MainMenu.db.rs.getInt("StoreId"));
            }
        } catch (SQLException e) {
            alertMessage(e.toString());
        }
        try {
            MainMenu.db.selectTableID();
            while (MainMenu.db.rs.next()) {
                TableId.add(MainMenu.db.rs.getInt("Table_Id"));
            }
        } catch (SQLException e) {
            alertMessage(e.toString());
        }
        try {
            MainMenu.db.selectServiceID();
            while (MainMenu.db.rs.next()) {
                ServiceId.add(MainMenu.db.rs.getInt("Service_Id"));
            }
        } catch (SQLException e) {
            alertMessage(e.toString());
        }

    }


    @FXML
    void Delete(MouseEvent event) {
        if (!CartTableView.getSelectionModel().isEmpty()) {
            ConfirmationPane.setVisible(true);
            deleteison = true;
        } else {
            alertMessage("Select an item to delete");
        }
    }

    @FXML
    void EmptyCart(MouseEvent event) {
        ConfirmationPane.setVisible(true);
        emptycart = true;
    }


    @FXML
    void InsertCart(ActionEvent event) {
        if (!(StoreIdBox.getSelectionModel().isEmpty() || ServiceIdBox.getSelectionModel().isEmpty() || TableIdBox.getSelectionModel().isEmpty())) {
            ConfirmationPane.setVisible(true);
        } else {
            alertMessage("StoreId/ServiceId/TableId cannot be empty");
        }
    }

    @FXML
    void Insert(MouseEvent event) {
        if (!ItemController.cart.isEmpty()) {
            InsertPane.setVisible(true);
            refreshcombo();
        } else {
            alertMessage("Need item in cart to insert a new transaction");
        }
    }

    @FXML
    void No(MouseEvent event) {
        ConfirmationPane.setVisible(false);
        if (deleteison) {
            deleteison = false;
        } else if (emptycart) {
            emptycart = false;
        }
    }

    @FXML
    void Refresh(MouseEvent event) {

    }

    @FXML
    void Yes(MouseEvent event) {
        if (InsertPane.isVisible()) {
            int storeid = StoreIdBox.getSelectionModel().getSelectedItem();
            int tableid = TableIdBox.getSelectionModel().getSelectedItem();
            int serviceid = ServiceIdBox.getSelectionModel().getSelectedItem();

            if (MainMenu.db.insertCart(storeid, tableid, serviceid, ItemController.cart)) {
                ConfirmationPane.setVisible(false);
                InsertPane.setVisible(false);
                ItemController.cart.clear();
                StoreIdBox.getSelectionModel().clearSelection();
                TableIdBox.getSelectionModel().clearSelection();
                ServiceIdBox.getSelectionModel().clearSelection();
            } else {
                ConfirmationPane.setVisible(false);
            }
        } else if (deleteison) {
            CartItem deletedobject = CartTableView.getSelectionModel().getSelectedItem();
            ItemController.cart.remove(deletedobject);
            refresh();
            ConfirmationPane.setVisible(false);
            deleteison = false;
        } else if (emptycart) {
            ItemController.cart.clear();
            refresh();
            ConfirmationPane.setVisible(false);
            deleteison = false;
        }
    }

    public static void alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void refresh() {
        CartTableView.getItems().clear();
        oblist.clear();
        for (CartItem i : ItemController.cart) {
            oblist.add(i);
        }
    }

    @FXML
    void Close(MouseEvent event) {
        if (InsertPane.isVisible()) {
            InsertPane.setVisible(false);
        }
        refresh();
    }

}
