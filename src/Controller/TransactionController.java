package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {
    @FXML
    private AnchorPane TransactionPane;
    @FXML
    private TableColumn<Transaction, Integer> Transaction_Id;
    @FXML
    private TableColumn<Transaction, Integer> Store_Id;
    @FXML
    private TableColumn<Transaction, Integer> Table_Id;
    @FXML
    private TableColumn<Transaction, Integer> Date_Time;
    @FXML
    private TableColumn<Transaction, Integer> Tax_Id;
    @FXML
    private TableColumn<Transaction, String> Service_Id;
    @FXML
    public Button Insert_Button;
    @FXML
    public Button Delete_Button;
    @FXML
    public Button Update_Button;
    @FXML
    public Button Menu;
    @FXML
    public Button refresh;
    @FXML
    public TextField trans_id;
    @FXML
    public TextField store_id;
    @FXML
    public TextField table_id;
    @FXML
    public TextField date_time;
    @FXML
    public TextField tax_id;
    @FXML
    public TextField service_id;
    @FXML
    private TableView<Transaction> TransactionTableDetailView;

    public PreparedStatement preparedStatement;
    public ResultSet rs;

    public Transaction trans;
    public GuestTableController gtc;
    public StoreController sc;
    public Store store;


    @FXML
    void BackToMenu(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/GUI/MainMenu.fxml"));
        TransactionPane.getChildren().setAll(pane);
    }


    private ObservableList<Transaction> data = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Transaction_Id.setCellValueFactory(new PropertyValueFactory<>("transaction_id"));
        Store_Id.setCellValueFactory(new PropertyValueFactory<>("store_id"));
        Table_Id.setCellValueFactory(new PropertyValueFactory<>("table_id"));
        Date_Time.setCellValueFactory(new PropertyValueFactory<>("date_time"));
        Tax_Id.setCellValueFactory(new PropertyValueFactory<>("tax_id"));
        Service_Id.setCellValueFactory(new PropertyValueFactory<>("service_id"));

        trans_id.setEditable(false);
        trans_id.setDisable(true);

        TransactionTableDetailView.setItems(data);
        getUpdated();
    }

    public void Refresh() {
        getUpdated();
    }


    @FXML
    public void openGuest() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/GuestTable.fxml"));
            Parent root = loader.load();
            GuestTableController gtc = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

//            gtc.select = true;
//            int table_id_int = gtc.selectDataForTableId();
//            String table_id_string = String.valueOf(table_id_int);
//            table_id.setText(table_id_string);

            GuestTable gt = gtc.getGuestTable();

            String table_id_string = String.valueOf(gt.getTableId());

            table_id.setText(table_id_string);


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void OpenService() {

    }

    @FXML
    public void openStore() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Store.fxml"));
            Parent root = loader.load();
            sc = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            store = sc.getStoreTable();

            String store_id_int = String.valueOf(store.getStore_id());

            store_id.setText(store_id_int);


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void getUpdated() {
        TransactionTableDetailView.getItems().clear();
        data.clear();
        try {
            MainMenu.db.SelectTableTransaction();
            while (MainMenu.db.rs.next()) {
                data.add(new Transaction(MainMenu.db.rs.getInt("Transaction_Id"), MainMenu.db.rs.getInt("Store_ID"), MainMenu.db.rs.getInt("Table_ID"),
                        MainMenu.db.rs.getString("DATE_TIME"), MainMenu.db.rs.getInt("Tax_Id"), MainMenu.db.rs.getString("Service_Percent")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void InsertClick() throws SQLException {
        String str_id = store_id.getText();
        String tbl_id = table_id.getText();
        String date = date_time.getText();
        String tx_id = tax_id.getText();
        String srvc_id = service_id.getText();


        MainMenu.db.InsertTableTransaction(str_id, tbl_id, date, tx_id, srvc_id);
        getUpdated();
    }

    public void DeleteClick() throws SQLException {
        String transaction_id = trans_id.getText();

        if (!TransactionTableDetailView.getSelectionModel().isEmpty()) {
            try {
                int transaction_id_int = Integer.parseInt(transaction_id);
                MainMenu.db.DeleteTableTransaction(transaction_id_int);
                getUpdated();
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                GuestTableController.alertMessage(nfe.toString());
            }
        } else {
            alertMessage("Please select Id field");
        }
    }

    public void UpdateClick() throws SQLException {
        if (!TransactionTableDetailView.getSelectionModel().isEmpty()) {
            Transaction table = TransactionTableDetailView.getSelectionModel().getSelectedItem();
            String transaction_id = trans_id.getText();
            String str_id = store_id.getText();
            String tbl_id = table_id.getText();
            String date = date_time.getText();
            String tx_id = tax_id.getText();
            String srvc_id = service_id.getText();

            MainMenu.db.UpdateTableTransaction(table, transaction_id, str_id, tbl_id, date, tx_id, srvc_id);
            getUpdated();
        } else {
            alertMessage("Please select item from table");
        }
    }

    public static void alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void selectId(MouseEvent mouseEvent) {
        trans = TransactionTableDetailView.getSelectionModel().getSelectedItem();
        String transaction_id_string = String.valueOf(trans.getTransaction_id());
        trans_id.setText(transaction_id_string);
    }

    public void PrintBill(MouseEvent mouseEvent) {
        try {
            String destination = "C:/receipt.pdf";
            Font tableHeaderFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);
            File file = new File(destination);
            file.getParentFile().mkdirs();

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(destination));
            document.open();

            trans = TransactionTableDetailView.getSelectionModel().getSelectedItem();
            String date = trans.getDate_time();
            int transaction_id_int = trans.getTransaction_id();
            String service_percent = trans.getService_id();
            int tax_id_int = trans.getTax_id();
            int store_id_int = trans.getStore_id();
            int table_id_int = trans.getTable_id();

            String store_name = MainMenu.db.returnStoreName(store_id_int);

            document.add(new Paragraph(String.format("%" + 42 + "s", "Mujigae Resto"), tableHeaderFont));
            document.add(new Paragraph(String.format("%" + 50 + "s", store_name)));
            document.add(new Paragraph("Transaction Id: " + transaction_id_int));
            document.add(new Paragraph("Table No: " + table_id_int));
            document.add(new Paragraph("Date: " + date));
            document.add(new Paragraph("----------------------------------------------------------------------------------------"));

            PdfPTable table = new PdfPTable(4);
            table.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);

            String query = "SELECT item_name, item_price, quantity , (item_price*quantity) as price FROM Item INNER JOIN Transaction_Detail ON Transaction_Detail.item_id = Item.item_id WHERE transaction_id=" + transaction_id_int;
            PreparedStatement pst = MainMenu.db.conn.prepareStatement(query);
            rs = pst.executeQuery(query);

            while (rs.next()) {
                cell.setPhrase(new Phrase(rs.getString("item_name")));
                table.addCell(cell);

                cell.setPhrase(new Phrase(rs.getString("quantity")));
                table.addCell(cell);

                cell.setPhrase(new Phrase(rs.getString("item_price")));
                table.addCell(cell);

                cell.setPhrase(new Phrase(rs.getString("price")));
                table.addCell(cell);
            }

            document.add(table);

            document.add(new Paragraph("---------------------------------------------------------------------------------------------"));
            String query_2 = "SELECT SUM(item_price*quantity) FROM Item INNER JOIN Transaction_Detail ON Transaction_Detail.item_id = Item.item_id WHERE transaction_id=" + transaction_id_int;

            pst = MainMenu.db.conn.prepareStatement(query_2);
            rs = pst.executeQuery(query_2);
            rs.next();
            document.add(new Paragraph(String.format("%" + 95 + "s", "Subtotal: " + rs.getString(1))));

            int subtotal = rs.getInt(1);
            BigDecimal sp = new BigDecimal(service_percent);
            BigDecimal service_cost = new BigDecimal(BigInteger.ZERO);
            BigDecimal service_cost_times_subtotal = sp.multiply(new BigDecimal(subtotal));
            service_cost = service_cost.add(service_cost_times_subtotal.setScale(0, RoundingMode.HALF_UP).stripTrailingZeros());

            BigDecimal tax = MainMenu.db.returnTax(tax_id_int);
            BigDecimal tax_cost = new BigDecimal(BigInteger.ZERO);
            BigDecimal tax_times_subtotal = tax.multiply(new BigDecimal(subtotal));
            tax_cost = tax_cost.add(tax_times_subtotal.setScale(0, RoundingMode.HALF_UP).stripTrailingZeros());

            BigDecimal total_cost = new BigDecimal(BigInteger.ZERO);
            total_cost = total_cost.add(tax_cost);
            total_cost = total_cost.add(service_cost);
            total_cost = total_cost.add(new BigDecimal(subtotal));

            document.add(new Paragraph(String.format("%" + 92 + "s", "Service Cost: " + service_cost)));
            document.add(new Paragraph(String.format("%" + 93 + "s", "Tax Cost: " + tax_cost)));
            document.add(new Paragraph(String.format("%" + 97 + "s", "Total: " + total_cost)));

            document.add(new Paragraph("---------------------------------------------------------------------------------------------"));
            document.add(new Paragraph(String.format("%" + 42 + "s", "Terima Kasih"), tableHeaderFont));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Receipt Printed Successfully");
            alert.showAndWait();
            document.close();
        } catch (FileNotFoundException | DocumentException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("There is something wrong");
            alert.showAndWait();
            e.printStackTrace();
        }

    }
}
