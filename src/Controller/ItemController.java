package Controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.ConcurrentModificationException;
import java.util.ResourceBundle;
import java.util.Vector;

public class ItemController implements Initializable {
    boolean deleteison = false;
    public static Vector<CartItem> cart = new Vector<>();
    @FXML
    private TableView<Item> ItemTableView;

    @FXML
    private TableColumn<Item, Integer> ItemIdColumn;

    @FXML
    private TableColumn<Item, String> ItemNameColumn;

    @FXML
    private Label ItemName;

    @FXML
    private Label ItemType;

    @FXML
    private Label ItemPrice;

    @FXML
    private TextField QuantityField;

    @FXML
    private Pane InsertPane;

    @FXML
    private Pane UpdatePane;

    @FXML
    private TextField ItemNameField;

    @FXML
    private TextField ItemTypeField;

    @FXML
    private TextField ItemPriceField;

    @FXML
    private TextField ItemNameFieldUpdate;

    @FXML
    private TextField ItemTypeFieldUpdate;

    @FXML
    private TextField ItemPriceFieldUpdate;

    @FXML
    private TextField ItemIdField;


    @FXML
    private Label DeleteText;

    @FXML
    private Label UpdateText;

    @FXML
    private Pane ConfirmationPane;

    public Item it;

    ObservableList<Item> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        ItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        ItemTableView.setItems(oblist);
        QuantityField.setText("0");
        ItemTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                it = newSelection;
                ItemTableView.getScene().getWindow();
            }
        });

        refresh();
    }

    public Item getItemTable() {
        return it;
    }

    public void refresh() {
        ItemTableView.getItems().clear();
        oblist.clear();
        try {
            MainMenu.db.selectTableItem();
            while (MainMenu.db.rs.next()) {
                oblist.add(new Item(MainMenu.db.rs.getInt("item_price"), MainMenu.db.rs.getString("item_name"), MainMenu.db.rs.getString("item_type"), MainMenu.db.rs.getInt("item_id")));
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
    void DescribeItem(MouseEvent event) {
        if (!ItemTableView.getSelectionModel().isEmpty()) {
            QuantityField.setText("0");
            try {
                MainMenu.db.getSelectedItem(ItemTableView.getSelectionModel().getSelectedItem());
                while (MainMenu.db.rs.next()) {
                    ItemName.setText(MainMenu.db.rs.getString("item_name"));
                    ItemType.setText(MainMenu.db.rs.getString("item_type"));
                    ItemPrice.setText(String.valueOf(MainMenu.db.rs.getInt("item_price")));
                }
            } catch (SQLException e) {
                alertMessage(e.toString());
            }
        }
    }

    @FXML
    boolean AddToCart(MouseEvent event) throws ConcurrentModificationException {
        if (!ItemTableView.getSelectionModel().isEmpty()) {
            try {
                Item selecteditem = ItemTableView.getSelectionModel().getSelectedItem();
                int quantity = Integer.valueOf(QuantityField.getText());
                CartItem newitem = new CartItem(selecteditem.getItem_id(), selecteditem.getItem_name(), quantity);
                if (quantity > 0) {
                    cart.add(newitem);
                } else {
                    alertMessage("Quantity must be above 0");
                    return false;
                }
            } catch (NumberFormatException e) {
                alertMessage(e.toString());
                return false;
            }
            return true;
        } else {
            alertMessage("SELECT AN ITEM");
            return false;
        }
    }

    @FXML
    void ClearCart(MouseEvent event) {
        if (!cart.isEmpty()) {
            cart.clear();
        }
    }

    @FXML
    void minus(MouseEvent event) {
        if (!QuantityField.getText().equals("")) {
            String curvalue = QuantityField.getText();
            try {
                Integer newvalue = Integer.valueOf(curvalue);
                if (newvalue > 0) {
                    QuantityField.setText(String.valueOf(newvalue - 1));
                }
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                alertMessage(nfe.toString());
            }
        }
    }

    @FXML
    void plus(MouseEvent event) {
        if (!QuantityField.getText().equals("")) {
            String curvalue = QuantityField.getText();
            try {
                Integer newvalue = Integer.valueOf(curvalue) + 1;
                QuantityField.setText(String.valueOf(newvalue));
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                alertMessage(nfe.toString());
            }
        }
    }

    @FXML
    void Delete(MouseEvent event) {
        if (cart.isEmpty()) {
            if (!ItemTableView.getSelectionModel().isEmpty()) {
                ConfirmationPane.setVisible(true);
                deleteison = true;
            } else {
                alertMessage("You need to select an item to delete");
            }
        } else {
            alertMessage("REMOVE ITEM FROM CART FIRST");
        }
    }

    @FXML
    void Insert(MouseEvent event) {
        InsertPane.setVisible(true);
    }

    @FXML
    void InsertClick(ActionEvent event) {
        if (!(ItemNameField.getText().isEmpty() || ItemTypeField.getText().isEmpty() || ItemPriceField.getText().isEmpty())) {
            ConfirmationPane.setVisible(true);
        } else {
            alertMessage("You must fill in ItemName,ItemType, and Itemprice");
        }
    }

    @FXML
    void Update(MouseEvent event) {
        if (cart.isEmpty()) {
            if (!ItemTableView.getSelectionModel().isEmpty()) {
                UpdatePane.setVisible(true);
            } else {
                alertMessage("You need to select an item to update");
            }
        } else {
            alertMessage("REMOVE ITEM FROM CART FIRST");
        }
    }

    @FXML
    void UpdateClick(ActionEvent event) {
        ConfirmationPane.setVisible(true);
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
            String itemid = ItemIdField.getText();
            String itemname = ItemNameField.getText();
            String itemtype = ItemTypeField.getText();
            String itemprice = ItemPriceField.getText();
            if (MainMenu.db.insertTableItem(itemid, itemname, itemtype, itemprice)) {
                ConfirmationPane.setVisible(false);
                InsertPane.setVisible(false);
                refresh();
            } else {
                ConfirmationPane.setVisible(false);
            }
        } else if (deleteison) {
            MainMenu.db.deleteTableItem(ItemTableView.getSelectionModel().getSelectedItem().getItem_id());
            ConfirmationPane.setVisible(false);
            deleteison = false;
            refresh();
        } else if (UpdatePane.isVisible()) {
            String itemname = ItemNameFieldUpdate.getText();
            String itemtype = ItemTypeFieldUpdate.getText();
            String itemprice = ItemPriceFieldUpdate.getText();
            if (MainMenu.db.updateItem(ItemTableView.getSelectionModel().getSelectedItem(), itemname, itemtype, itemprice)) {
                UpdatePane.setVisible(false);
                ConfirmationPane.setVisible(false);
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
