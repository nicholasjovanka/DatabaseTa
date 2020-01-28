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
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GuestTableController implements Initializable {
    boolean deleteison = false;
    @FXML
    private TableColumn<GuestTable, Integer> TableIdColumn;

    @FXML
    private TableView<GuestTable> GuestTableView;
    @FXML
    private TableColumn<GuestTable, String> CapacityColumn;
    @FXML
    private TextField CapacityInsertField;
    @FXML
    private TextField CapacityUpdateField;

    @FXML
    private TextField TableIdField;
    @FXML
    private Pane ConfirmationPane;
    @FXML
    private Pane InsertPane;
    @FXML
    private Pane UpdatePane;
    public GuestTable gt;
    public TransactionController tc;
    public static boolean select=false;


    ObservableList<GuestTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableIdColumn.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        CapacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        GuestTableView.setItems(oblist);
        GuestTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                gt = newSelection;
                GuestTableView.getScene().getWindow();
            }
        });

        refresh();
    }

    public int selectDataForTableId() {
        System.out.println(select);
        if(select){
            gt = GuestTableView.getSelectionModel().getSelectedItem();
            return gt.getTableId();

        }
        return 0;
    }

    public GuestTable getGuestTable() {
        return gt;
    }

    public void refresh() {
        GuestTableView.getItems().clear();
        oblist.clear();
        try {
            MainMenu.db.getGuestTable();
            while (MainMenu.db.rs.next()) {
                oblist.add(new GuestTable(MainMenu.db.rs.getInt("Table_Id"), MainMenu.db.rs.getInt("Capacity")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void Close(MouseEvent event) {
        if (InsertPane.isVisible()) {
            InsertPane.setVisible(false);
        } else if (UpdatePane.isVisible()) {
            UpdatePane.setVisible(false);
        }
        refresh();
    }

    @FXML
    void Insert(MouseEvent event) {
        ConfirmationPane.setVisible(true);
    }

    @FXML
    void InsertGuestTable(MouseEvent event) {
        InsertPane.setVisible(true);
    }

    @FXML
    void Update(MouseEvent event) {
        if (!GuestTableView.getSelectionModel().isEmpty()) {
            UpdatePane.setVisible(true);
        } else {
            alertMessage("You need to select an item to update");
        }
    }

    @FXML
    void UpdateClick(MouseEvent event) {
        ConfirmationPane.setVisible(true);
    }

    @FXML
    void Delete(MouseEvent event) {
        if (!GuestTableView.getSelectionModel().isEmpty()) {
            ConfirmationPane.setVisible(true);
            deleteison = true;
        } else {
            alertMessage("You need to select an item to delete");
        }
    }


    @FXML
    void No(MouseEvent event) {
        if (deleteison) {
            deleteison = false;
        }
        ConfirmationPane.setVisible(false);
    }

    @FXML
    void Yes(MouseEvent event) {
        if (InsertPane.isVisible()) {
            String tableid = TableIdField.getText();
            String capacity = CapacityInsertField.getText();
            TableIdField.clear();
            CapacityInsertField.clear();
            if (MainMenu.db.insertTableGuest(tableid, capacity)) {
                ConfirmationPane.setVisible(false);
                InsertPane.setVisible(false);
                refresh();
            } else {
                ConfirmationPane.setVisible(false);
            }
        } else if (deleteison) {
            MainMenu.db.deleteTableGuest(GuestTableView.getSelectionModel().getSelectedItem().getTableId());
            ConfirmationPane.setVisible(false);
            deleteison = false;
            refresh();
        } else if (UpdatePane.isVisible()) {
            String capacity = CapacityUpdateField.getText();
            CapacityInsertField.clear();
            if (MainMenu.db.updateGuestTable(GuestTableView.getSelectionModel().getSelectedItem(), capacity)) {
                ConfirmationPane.setVisible(false);
                UpdatePane.setVisible(false);
                refresh();
            } else {
                ConfirmationPane.setVisible(false);
            }
        }
    }

    @FXML
    void Refresh(MouseEvent event) {
        refresh();
    }

    public static void alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

