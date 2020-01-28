package Controller;

import com.sun.org.apache.xml.internal.security.algorithms.implementations.IntegrityHmac;
import javafx.beans.property.*;

import java.math.BigDecimal;

public class Transaction {
    private final IntegerProperty transaction_id;
    private final IntegerProperty store_id;
    private final IntegerProperty table_id;
    private final StringProperty date_time;
    private final IntegerProperty tax_id;
    private final StringProperty service_id;


    public Transaction(Integer transaction_id, Integer store_id, Integer table_id, String date_time, Integer tax_id, String service_id) {
        this.transaction_id = new SimpleIntegerProperty(transaction_id);
        this.store_id = new SimpleIntegerProperty(store_id);
        this.table_id = new SimpleIntegerProperty(table_id);
        this.date_time = new SimpleStringProperty(date_time);
        this.tax_id = new SimpleIntegerProperty(tax_id);
        this.service_id = new SimpleStringProperty(service_id);
    }

    public Integer getTransaction_id() {
        return transaction_id.get();
    }

    public void setTransaction_id(Integer value) {
        transaction_id.set(value);
    }

    public Integer getStore_id() {
        return store_id.get();
    }

    public void setStore_id(Integer value) {
        store_id.set(value);
    }

    public Integer getTax_id() {
        return tax_id.get();
    }

    public void setTax_id(Integer value) {
        tax_id.set(value);
    }

    public String getService_id() {
        return service_id.get();
    }

    public void setService_id(String value) {
        service_id.set(value);
    }

    public Integer getTable_id() {
        return table_id.get();
    }

    public void setTable_id(Integer value) {
        table_id.set(value);
    }

    public String getDate_time() {
        return date_time.get();
    }

    public void setDate_time(String value) {
        date_time.set(value);
    }
}

