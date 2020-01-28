package Controller;

import Controller.GuestTable;
import Controller.GuestTableController;
import javafx.scene.control.Tab;

import javax.xml.ws.ServiceMode;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class DB {
    public static String user = "NAM8446";
    public String pass = "dnytaj2b";
    public String link = "dbta.1ez.xyz";
    public String url = "jdbc:mysql://" + link + "/11_mujigay";
    public Connection conn;
    public ResultSet rs;
    public String query;
    public PreparedStatement preparedStatement;

    public boolean connectDB(String user, String pass) {
        try {
            conn = DriverManager.getConnection(url, user, pass);
            return true;
        } catch (SQLException e) {
            LoginController.alertMessage(e.toString());
            return false;
        }
    }

    public String next() {
        try {
            rs.next();
            // set column index
            return rs.getString(1);
        } catch (Exception e) {
            return "NULL";
        }
    }


    //Queries for GuestTable
    public void getGuestTable() throws SQLException {
        this.query = "SELECT * from Guest_Table";
        PreparedStatement pst = conn.prepareStatement(query);
        rs = pst.executeQuery(query);
    }

    public boolean insertTableGuest(String table_Idst, String capacityst) {
        if (table_Idst.isEmpty()) {
            String insertStr = "INSERT INTO  Guest_Table(Capacity) VALUES(?)";
            try {
                int capacity = Integer.parseInt(capacityst);
                try (PreparedStatement pst = conn.prepareStatement(insertStr)) {
                    pst.setInt(1, capacity);
                    pst.executeUpdate();
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    GuestTableController.alertMessage(e.toString());
                    return false;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                GuestTableController.alertMessage(nfe.toString());
                return false;
            }


        } else {
            String insertStr = "INSERT INTO  Guest_Table(table_Id,Capacity) VALUES(?, ?)";
            try {
                int table_Id = Integer.parseInt(table_Idst);
                int capacity = Integer.parseInt(capacityst);
                try (PreparedStatement pst = conn.prepareStatement(insertStr)) {
                    pst.setInt(1, table_Id);
                    pst.setInt(2, capacity);
                    pst.executeUpdate();
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    GuestTableController.alertMessage(e.toString());
                    return false;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                GuestTableController.alertMessage(nfe.toString());
                return false;
            }
        }
    }

    public boolean deleteTableGuest(int table_Id) {
        String deleteStr = "DELETE FROM Guest_Table Where Table_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(deleteStr)) {
            pst.setInt(1, table_Id);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            GuestTableController.alertMessage(e.toString());
            return false;
        }
    }

    public boolean updateGuestTable(GuestTable a, String capacitystr) {
        int Capacity = a.getCapacity();
        if (!capacitystr.isEmpty()) {
            try {
                Capacity = Integer.parseInt(capacitystr);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                GuestTableController.alertMessage(nfe.toString());
                return false;
            }
        }
        String updatequery = "Update Guest_Table SET Capacity=? Where Table_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(updatequery);) {
            pst.setInt(1, Capacity);
            pst.setInt(2, a.getTableId());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            GuestTableController.alertMessage((e.toString()));
            e.printStackTrace();
            return false;
        }


    }

    //Queries for ItemTable
    public void selectTableItem() throws SQLException {
        this.query = "SELECT * from Item";
        PreparedStatement pst = conn.prepareStatement(query);
        rs = pst.executeQuery(query);
    }

    public void getSelectedItem(Item item) {
        this.query = "Select * from Item where item_id=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, item.getItem_id());
            rs = pst.executeQuery();
        } catch (SQLException e) {
            ItemController.alertMessage(e.toString());
        }
    }

    public boolean insertTableItem(String item_id, String item_name, String item_type, String item_price) {
        if (item_id.isEmpty()) {
            String query = "INSERT INTO Item (item_name, item_type, item_price) VALUES(?,?,?)";
            try {
                int price = Integer.parseInt(item_price);
                try {
                    PreparedStatement pst = conn.prepareStatement(query);
                    pst.setString(1, item_name);
                    pst.setString(2, item_type);
                    pst.setInt(3, price);
                    pst.executeUpdate();
                    return true;

                } catch (SQLException e) {
                    ItemController.alertMessage(e.toString());
                    return false;
                }

            } catch (NumberFormatException e) {
                ItemController.alertMessage(e.toString());
                return false;
            }

        } else {
            String query = "INSERT INTO Item (item_id,item_name, item_type, item_price) VALUES(?,?,?,?)";
            try {
                int id = Integer.parseInt(item_id);
                int price = Integer.parseInt(item_price);
                try {
                    PreparedStatement pst = conn.prepareStatement(query);
                    pst.setInt(1, id);
                    pst.setString(2, item_name);
                    pst.setString(3, item_type);
                    pst.setInt(4, price);
                    pst.executeUpdate();
                    return true;

                } catch (SQLException e) {
                    ItemController.alertMessage(e.toString());
                    return false;
                }

            } catch (NumberFormatException e) {
                ItemController.alertMessage(e.toString());
                return false;
            }
        }

    }

    public boolean deleteTableItem(int itemid) {
        String deleteStr = "DELETE FROM Item Where item_id=?";
        try (PreparedStatement pst = conn.prepareStatement(deleteStr)) {
            pst.setInt(1, itemid);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            ItemController.alertMessage(e.toString());
            return false;
        }
    }

    public boolean updateItem(Item item, String itemname, String itemtype, String itemprice) {
        String name = item.getItem_name();
        String type = item.getItem_type();
        int price = item.getItem_price();
        if (!itemname.isEmpty()) {
            name = itemname;
        }
        if (!itemtype.isEmpty()) {
            type = itemtype;
        }
        if (!itemprice.isEmpty()) {
            try {
                price = Integer.parseInt(itemprice);
            } catch (NumberFormatException e) {
                ItemController.alertMessage(e.toString());
                return false;
            }
        }
        String query = "Update Item SET item_name=?,item_type=?,item_price=? Where item_id=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, type);
            pst.setInt(3, price);
            pst.setInt(4, item.getItem_id());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            ItemController.alertMessage(e.toString());
            return false;
        }

    }

    //FOR CART
    public int getTax(String time) throws SQLException {
        this.query = "Select Tax_Id from Tax Where Valid_From<? and Valid_To IS NULL";
        int tax = 0;
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, time);
        rs = pst.executeQuery();
        while (rs.next()) {
            tax = rs.getInt("Tax_Id");
        }
        return tax;
    }

    public void selectStoreID() throws SQLException {
        this.query = "SELECT StoreID from Store";
        PreparedStatement pst = conn.prepareStatement(query);
        rs = pst.executeQuery(query);
    }

    public void selectTableID() throws SQLException {
        this.query = "SELECT Table_Id from Guest_Table";
        PreparedStatement pst = conn.prepareStatement(query);
        rs = pst.executeQuery(query);
    }

    public void selectServiceID() throws SQLException {
        this.query = "SELECT Service_Id from Services";
        PreparedStatement pst = conn.prepareStatement(query);
        rs = pst.executeQuery(query);
    }

    public boolean insertCart(int StoreId, int TableId, int ServiceId, Vector<CartItem> cart) {
        this.query = "Insert into Transaction(Store_Id,Table_ID,DATE_TIME,Tax_Id,Service_Id)VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:s");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date now = new java.util.Date();
            String strDate = sdf.format(now);
            String taxdate = sdf.format(now);
            pst.setInt(1, StoreId);
            pst.setInt(2, TableId);
            pst.setString(3, strDate);
            pst.setInt(4, getTax(taxdate));
            pst.setInt(5, ServiceId);
            pst.executeUpdate();
        } catch (SQLException e) {
            CartController.alertMessage(e.toString());
            return false;
        }
        this.query = "SELECT LAST_INSERT_ID()";
        int tid = 0;
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                tid = rs.getInt("LAST_INSERT_ID()");
            }
        } catch (SQLException e) {
            CartController.alertMessage(e.toString());
            return false;
        }
        this.query = "Insert into Transaction_Detail(transaction_id,item_id,quantity)VALUES(?,?,?)";
        for (CartItem i : cart) {
            try {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, tid);
                pst.setInt(2, i.getItemid());
                pst.setInt(3, i.getItemqty());
                pst.executeUpdate();
            } catch (SQLException e) {
                CartController.alertMessage(e.toString());
                return false;
            }
        }
        return true;


    }

    //Queries for StoreTable
    public void SelectTableStore() throws SQLException {
        this.query = "SELECT * from Store";
        PreparedStatement pst = conn.prepareStatement(query);
        rs = pst.executeQuery(query);
    }

    public void InsertTableStore(String store_name, String store_location) throws SQLException {
        String query = "INSERT INTO Store (Storename, Storelocation) VALUES(?, ?)";
        preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, store_name);
            preparedStatement.setString(2, store_location);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            preparedStatement.executeUpdate();
//            preparedStatement.close();
        }
    }

    public void DeleteTableStore(int storeId) throws SQLException {
        String query = "DELETE FROM Store WHERE StoreId=?";
        preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, storeId);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            preparedStatement.executeUpdate();
//            preparedStatement.close();
        }
    }

    public void UpdateTableStore(Store a, String id, String name, String location) throws SQLException {
        int Id = a.getStore_id();
        String Name = a.getStore_name();
        String Location = a.getStore_location();

        if (!id.isEmpty()) {
            try {
                Id = Integer.parseInt(id);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                ItemController.alertMessage(nfe.toString());
            }
        }
        if (!name.isEmpty()) {
            Name = name;
        }
        if (!location.isEmpty()) {
            Location = location;
        }

        String updatequery = "Update Store SET Storename=?, Storelocation=? Where StoreId=?";
        try (PreparedStatement pst = conn.prepareStatement(updatequery);) {
            pst.setString(1, Name);
            pst.setString(2, Location);
            pst.setInt(3, Id);
            pst.executeUpdate();
        } catch (SQLException e) {
            GuestTableController.alertMessage((e.toString()));
            e.printStackTrace();
        }

    }

    // queries for Transaction
    public void SelectTableTransaction() throws SQLException {
        this.query = "SELECT Transaction_Id, Store_Id, Table_Id, DATE_TIME, Tax_Id, Service_Percent from Services INNER JOIN Transaction ON Services.Service_Id = Transaction.Service_Id";
        PreparedStatement pst = conn.prepareStatement(query);
        rs = pst.executeQuery(query);
    }


    public String returnStoreName(int store_id_int) {
        String query = "SELECT Storelocation FROM Store WHERE StoreId=?";

        preparedStatement = null;

        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, store_id_int);
            rs = preparedStatement.executeQuery();
            rs.next();
            String store_name = rs.getString("Storelocation");
            rs.close();

            return store_name;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "none";
    }

    public BigDecimal returnTax(int tax_id_int) {
        String query = "SELECT Tax_Percentage FROM Tax WHERE Tax_Id=?";
        preparedStatement = null;
        BigDecimal tax_id = new BigDecimal(BigInteger.ZERO);
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, tax_id_int);
            rs = preparedStatement.executeQuery();
            rs.next();
            tax_id = rs.getBigDecimal("Tax_Percentage");
            rs.close();

            return tax_id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tax_id;
    }


    public void InsertTableTransaction(String store_id, String table_id, String date_time, String tax_id, String service_id) throws SQLException {
        String query = "INSERT INTO Transaction (Store_ID, Table_ID, DATE_TIME, Tax_Id, Service_Id) VALUES(?, ?, ?, ?, ?)";
        preparedStatement = null;


        try {
            int store_id_int = Integer.parseInt(store_id);
            int table_id_int = Integer.parseInt(table_id);
            int tax_id_int = Integer.parseInt(tax_id);
            BigDecimal service_id_int = new BigDecimal(service_id);

            try {
                preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, store_id_int);
                preparedStatement.setInt(2, table_id_int);
                preparedStatement.setString(3, date_time);
                preparedStatement.setInt(4, tax_id_int);
                preparedStatement.setBigDecimal(5, service_id_int);
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                preparedStatement.executeUpdate();
//                preparedStatement.close();
            }
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
            GuestTableController.alertMessage(nfe.toString());
        }
    }

    public void DeleteTableTransaction(int transactionId) throws SQLException {
        String query = "DELETE FROM Transaction WHERE Transaction_Id=?";
        preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, transactionId);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            preparedStatement.executeUpdate();
        }
    }

    public void UpdateTableTransaction(Transaction a, String transaction_id, String store_id, String table_id, String date_time, String tax_id, String service_id) throws SQLException {
        int transaction_id_int = a.getTransaction_id();
        int store_id_int = a.getStore_id();
        int table_id_int = a.getTable_id();
        String date_time_string = a.getDate_time();
        int tax_id_int = a.getTax_id();
        String service_id_string = a.getService_id();
        BigDecimal service_id_bd = new BigDecimal(service_id_string);

        if (!transaction_id.isEmpty()) {
            try {
                transaction_id_int = Integer.parseInt(transaction_id);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                ItemController.alertMessage(nfe.toString());
            }
        }
        if (!store_id.isEmpty()) {
            try {
                store_id_int = Integer.parseInt(store_id);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                ItemController.alertMessage(nfe.toString());
            }
        }
        if (!table_id.isEmpty()) {
            try {
                table_id_int = Integer.parseInt(table_id);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                ItemController.alertMessage(nfe.toString());
            }
        }
        if (!date_time.isEmpty()) {
            date_time_string = date_time;
        }
        if (!tax_id.isEmpty()) {
            try {
                tax_id_int = Integer.parseInt(tax_id);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                ItemController.alertMessage(nfe.toString());
            }
        }
        if (!service_id.isEmpty()) {
            try {
                service_id_bd = new BigDecimal(service_id);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                ItemController.alertMessage(nfe.toString());
            }
        }

        String updatequery = "Update Transaction SET Store_ID=?, Table_ID=?, DATE_TIME=?, Tax_Id=?, Service_Id=? Where Transaction_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(updatequery);) {
            pst.setInt(1, store_id_int);
            pst.setInt(2, table_id_int);
            pst.setString(3, date_time_string);
            pst.setInt(4, tax_id_int);
            pst.setBigDecimal(5, service_id_bd);
            pst.setInt(6, transaction_id_int);
            pst.executeUpdate();
        } catch (SQLException e) {
            GuestTableController.alertMessage((e.toString()));
            e.printStackTrace();
        }

    }

    // queries for TransactionDetail
    public void SelectTableTransactionDetail() throws SQLException {
        this.query = "SELECT Transaction_Detail_Id, transaction_id, item_name, quantity from Item INNER JOIN Transaction_Detail ON Item.item_id = Transaction_Detail.item_id";
        PreparedStatement pst = conn.prepareStatement(query);
        rs = pst.executeQuery(query);
    }

    public void DeleteTableTransactionDetail(String transaction_detail_id_string) throws SQLException {
        int transaction_detail_id_int = Integer.parseInt(transaction_detail_id_string);
        String query = "DELETE FROM Transaction_Detail WHERE Transaction_Detail_Id=?";
        preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, transaction_detail_id_int);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            preparedStatement.executeUpdate();
        }

    }

    public int retrieve_Itemid(String item_name) throws SQLException {
        this.query = "SELECT item_id FROM Item WHERE item_name=?";
        preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, item_name);
        rs = preparedStatement.executeQuery();

        rs.next();
        int item_id_int = rs.getInt("item_id");

        return item_id_int;
    }

    public void UpdateTableTransactionDetail(TransactionDetail a, String transaction_detail_id, String transaction_id, String item_name, String quantity) {
        int transaction_detail_id_int = Integer.parseInt(transaction_detail_id);
        int transaction_id_int = a.getTransaction_id();
        int item_id_int = 0;
        try {
            item_id_int = retrieve_Itemid(a.getItem_name());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int quantity_int = a.getQuantity();


        if (!transaction_id.isEmpty()) {
            try {
                transaction_id_int = Integer.parseInt(transaction_id);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                ItemController.alertMessage(nfe.toString());
            }
        }

        if (!quantity.isEmpty()) {
            try {
                quantity_int = Integer.parseInt(quantity);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                ItemController.alertMessage(nfe.toString());
            }
        }

        if (!item_name.isEmpty()) {
            try {
                item_id_int = retrieve_Itemid(item_name);
            } catch (NumberFormatException | SQLException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                ItemController.alertMessage(nfe.toString());
            }
        }

        String updatequery = "Update Transaction_Detail SET transaction_id=?, item_id=?, quantity=? Where Transaction_Detail_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(updatequery);) {
            pst.setInt(1, transaction_id_int);
            pst.setInt(2, item_id_int);
            pst.setInt(3, quantity_int);
            pst.setInt(4, transaction_detail_id_int);
            pst.executeUpdate();
        } catch (SQLException e) {
            GuestTableController.alertMessage((e.toString()));
            e.printStackTrace();
        }


    }

    //Queries for Tax
    public void SelectTax() throws SQLException {
        this.query = "SELECT * from Tax";
        PreparedStatement pst = conn.prepareStatement(query);
        rs = pst.executeQuery(query);
    }

    public void insertTax(String taxIdStr, String taxPercentageStr, String startDate, String endDate){
        if(taxIdStr.isEmpty()){
            String query = "INSERT INTO Tax(Tax_Percentage, Valid_From, Valid_To) VALUES (?,?,?)";
            try{
                float taxPercentage = Float.parseFloat(taxPercentageStr);
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setFloat(1, taxPercentage);
                    pst.setString(2, startDate);
                    pst.setString(3, endDate);
                    pst.executeUpdate();
                } catch (SQLException e) {
                    TaxController.alertMessage((e.toString()));
                    e.printStackTrace();
                }
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                TaxController.alertMessage(nfe.toString());
            }
        }

        else {
            String query = "INSERT INTO Tax(Tax_Id, Tax_Percentage, Valid_From, Valid_To) VALUES (?,?,?,?)";
            try{
                int taxId = Integer.parseInt(taxIdStr);
                float taxPercentage = Float.parseFloat(taxPercentageStr);
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setInt(1, taxId);
                    pst.setFloat(2, taxPercentage);
                    pst.setString(3, startDate);
                    pst.setString(4, endDate);
                    pst.executeUpdate();
                } catch (SQLException e) {
                    TaxController.alertMessage((e.toString()));
                    e.printStackTrace();
                }
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                TaxController.alertMessage(nfe.toString());
            }
        }
    }

    public void updateTax(TaxModelTable taxObject, String taxPercentage, String startDate, String endDate){
        int id = taxObject.getId();
        float percentage = taxObject.getPercentage();
        String start = taxObject.getStart();
        String end = taxObject.getEnd();
        if(!taxPercentage.isEmpty()){
            try {
                percentage = Float.parseFloat(taxPercentage);
            }catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                TaxController.alertMessage(nfe.toString());
            }
        }

        if(startDate != null){
            start = startDate;
        }

        if(endDate != null){
            end = endDate;
        }

        String updatequery="UPDATE Tax SET Tax_Percentage=?, Valid_From=?, Valid_To=? Where Tax_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(updatequery);) {
            pst.setFloat(1, percentage);
            pst.setString(2, start);
            pst.setString(3, end);
            pst.setInt(4, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            TaxController.alertMessage((e.toString()));
            e.printStackTrace();
        }
    }

    public void deleteTax(int taxId){
        String deleteStr="DELETE FROM Tax Where Tax_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(deleteStr)) {
            pst.setInt(1, taxId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            TaxController.alertMessage(e.toString());
        }
    }

    //Queries for Service
    public void SelectService() throws SQLException {
        this.query = "SELECT * from Services";
        PreparedStatement pst = conn.prepareStatement(query);
        rs = pst.executeQuery(query);
    }

    public void insertService(String serviceIdStr, String servicePercentageStr, String startDate, String endDate){
        if(serviceIdStr.isEmpty()){
            String query = "INSERT INTO Services(Service_Percent, Valid_from, Valid_to) VALUES (?,?,?)";
            try{
                float servicePercentage = Float.parseFloat(servicePercentageStr);
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setFloat(1, servicePercentage);
                    pst.setString(2, startDate);
                    pst.setString(3, endDate);
                    pst.executeUpdate();
                } catch (SQLException e) {
                    TaxController.alertMessage((e.toString()));
                    e.printStackTrace();
                }
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                TaxController.alertMessage(nfe.toString());
            }
        }

        else {
            String query = "INSERT INTO Services(Service_Id, Service_Percent, Valid_from, Valid_to) VALUES (?,?,?,?)";
            try{
                int serviceId = Integer.parseInt(serviceIdStr);
                float servicePercentage = Float.parseFloat(servicePercentageStr);
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setInt(1, serviceId);
                    pst.setFloat(2, servicePercentage);
                    pst.setString(3, startDate);
                    pst.setString(4, endDate);
                    pst.executeUpdate();
                } catch (SQLException e) {
                    TaxController.alertMessage((e.toString()));
                    e.printStackTrace();
                }
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                ServiceController.alertMessage(nfe.toString());
            }
        }
    }

    public void updateService(ServiceModelTable serviceObject, String servicePercentage, String startDate, String endDate){
        int id = serviceObject.getId();
        float percentage = serviceObject.getPercentage();
        String start = serviceObject.getStart();
        String end = serviceObject.getEnd();
        if(!servicePercentage.isEmpty()){
            try {
                percentage = Float.parseFloat(servicePercentage);
            }catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                TaxController.alertMessage(nfe.toString());
            }
        }

        if(startDate != null){
            start = startDate;
        }

        if(endDate != null){
            end = endDate;
        }

        String updatequery="UPDATE Services SET Service_Percent=?, Valid_from=?, Valid_to=? Where Service_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(updatequery);) {
            pst.setFloat(1, percentage);
            pst.setString(2, start);
            pst.setString(3, end);
            pst.setInt(4, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            ServiceController.alertMessage((e.toString()));
            e.printStackTrace();
        }
    }

    public void deleteService(int serviceId){
        String deleteStr="DELETE FROM Services Where Service_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(deleteStr)) {
            pst.setInt(1, serviceId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            ServiceController.alertMessage(e.toString());
        }
    }
}



