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

public class ServiceController implements Initializable{

    @FXML
    private TableView<ServiceModelTable> table;

    @FXML
    private TableColumn<ServiceModelTable, Integer> col_serviceId;

    @FXML
    private TableColumn<ServiceModelTable, Integer> col_servicePercentage;

    @FXML
    private TableColumn<ServiceModelTable, String> col_serviceStart;

    @FXML
    private TableColumn<ServiceModelTable, String> col_serviceEnd;

    @FXML
    private Button Refresh;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private Button InsertButton;

    @FXML
    private Pane pane_insert;

    @FXML
    private TextField service_id;

    @FXML
    private TextField service_percentage;

    @FXML
    private DatePicker datepicker_start;

    @FXML
    private DatePicker datepicker_end;

    @FXML
    private Pane pane_update;

    @FXML
    private TextField service_percentage2;

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

    ObservableList<ServiceModelTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        col_serviceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_servicePercentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        col_serviceStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        col_serviceEnd.setCellValueFactory(new PropertyValueFactory<>("end"));

        getUpdated();
        table.setItems(oblist);
    }

    public void getUpdated(){
        table.getItems().clear();
        oblist.clear();
        try {
            MainMenu.db.SelectService();
            while(MainMenu.db.rs.next()){
                oblist.add(new ServiceModelTable(MainMenu.db.rs.getInt("Service_Id"), MainMenu.db.rs.getFloat("Service_Percent"), MainMenu.db.rs.getString("Valid_from"), MainMenu.db.rs.getString("Valid_to")));
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


    public void insertService(){
        String serviceid = service_id.getText();
        String percentage = service_percentage.getText();
        String validFrom = null;
        String validTo = null;

        if (datepicker_start.getValue() != null){
            validFrom = datepicker_start.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        if (datepicker_end.getValue() != null){
            validTo = datepicker_end.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        service_id.clear();
        service_percentage.clear();
        MainMenu.db.insertService(serviceid, percentage, validFrom, validTo);
        refresh();
        pane_insert.setVisible(false);
    }

    public void updateButton(){
        pane_update.setVisible(true);
    }

    public void updateService(){
        ServiceModelTable object = table.getSelectionModel().getSelectedItem();
        String percentage = service_percentage2.getText();
        String validFrom = null;
        String validTo = null;

        if (datepicker_start2.getValue() != null){
            validFrom = datepicker_start2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        if (datepicker_end2.getValue() != null){
            validTo = datepicker_end2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        service_percentage2.clear();
        MainMenu.db.updateService(object, percentage, validFrom, validTo);
        refresh();
        pane_update.setVisible(false);
    }

    public void delete(){
        MainMenu.db.deleteService(table.getSelectionModel().getSelectedItem().getId());
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
