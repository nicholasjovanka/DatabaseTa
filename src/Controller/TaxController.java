package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TaxController implements Initializable{

    @FXML
    private AnchorPane pane_tax;

    @FXML
    private Pane pane_insert;

    @FXML
    private Pane pane_update;

    @FXML
    private DatePicker datepicker_end;

    @FXML
    private DatePicker datepicker_start;

    @FXML
    private TableView<TaxModelTable> table;

    @FXML
    private TableColumn<TaxModelTable, Integer> col_taxId;

    @FXML
    private TableColumn<TaxModelTable, Integer> col_taxPercentage;

    @FXML
    private TableColumn<TaxModelTable, String> col_taxStart;

    @FXML
    private TableColumn<TaxModelTable, String> col_taxEnd;

    @FXML
    private Button Menu;

    @FXML
    private Button Refresh;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private Button InsertButton;

    @FXML
    private TextField text_id;

    @FXML
    private TextField text_percentage;

    @FXML
    private TextField text_percentage2;

    @FXML
    private DatePicker datepicker_start2;

    @FXML
    private DatePicker datepicker_end2;

    @FXML
    void BackToMenu(MouseEvent event) {

    }

    @FXML
    void DeleteSelected(MouseEvent event) {

    }

    @FXML
    void InsertClick(MouseEvent event) {

    }

    @FXML
    void Refresh(MouseEvent event)  {

    }

    @FXML
    void Update(MouseEvent event) {

    }

    ObservableList<TaxModelTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        col_taxId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_taxPercentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        col_taxStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        col_taxEnd.setCellValueFactory(new PropertyValueFactory<>("end"));

        getUpdated();
        table.setItems(oblist);
    }

    public void getUpdated(){
        table.getItems().clear();
        oblist.clear();
        try {
            MainMenu.db.SelectTax();
            while(MainMenu.db.rs.next()){
                oblist.add(new TaxModelTable(MainMenu.db.rs.getInt("Tax_Id"), MainMenu.db.rs.getFloat("Tax_Percentage"), MainMenu.db.rs.getString("Valid_From"), MainMenu.db.rs.getString("Valid_To")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void close(MouseEvent event) {
        if (pane_insert.isVisible()) {
            pane_insert.setVisible(false);
        }
        else if(pane_update.isVisible()){
            pane_update.setVisible(false);
        }
        refresh();
    }

    public void insertButton(MouseEvent event) {
        pane_insert.setVisible(true);
    }


    public void insertTax(){
        String taxid = text_id.getText();
        String percentage = text_percentage.getText();
        String validFrom = null;
        String validTo = null;

        if (datepicker_start.getValue() != null){
            validFrom = datepicker_start.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        if (datepicker_end.getValue() != null){
            validTo = datepicker_end.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        text_id.clear();
        text_percentage.clear();
        MainMenu.db.insertTax(taxid, percentage, validFrom, validTo);
        refresh();
        pane_insert.setVisible(false);
    }

    public void updateButton(){
        pane_update.setVisible(true);
    }

    public void updateTax(){
        TaxModelTable object = table.getSelectionModel().getSelectedItem();
        String percentage = text_percentage2.getText();
        String validFrom = null;
        String validTo = null;

        if (datepicker_start2.getValue() != null){
            validFrom = datepicker_start2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        if (datepicker_end2.getValue() != null){
            validTo = datepicker_end2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        text_percentage2.clear();
        MainMenu.db.updateTax(object, percentage, validFrom, validTo);
        refresh();
        pane_update.setVisible(false);
    }

    public void delete(){
        MainMenu.db.deleteTax(table.getSelectionModel().getSelectedItem().getId());
        refresh();
    }

    public void refresh(){
        getUpdated();
    }

    public static void alertMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
