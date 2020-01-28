package Controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class TransactionDetail {
    private final IntegerProperty transaction_detail_id;
    private final IntegerProperty transaction_id;
    private final StringProperty item_name;
    private final IntegerProperty quantity;


    public TransactionDetail(Integer transaction_detail_id, Integer transaction_id, String item_name, Integer quantity) {
        this.transaction_detail_id = new SimpleIntegerProperty(transaction_detail_id);
        this.transaction_id = new SimpleIntegerProperty(transaction_id);
        this.item_name = new SimpleStringProperty(item_name);
        this.quantity = new SimpleIntegerProperty(quantity);
    }


    public Integer getTransaction_detail_id() {
        return transaction_detail_id.get();
    }

    public void setTransaction_detail_id(Integer value) {
        transaction_detail_id.set(value);
    }

    public Integer getTransaction_id() {
        return transaction_id.get();
    }

    public void setTransaction_id(Integer value) {
        transaction_id.set(value);
    }

    public String getItem_name() {
        return item_name.get();
    }

    public void setItem_name(String value) {
        item_name.set(value);
    }

    public Integer getQuantity() {
        return quantity.get();
    }

    public void setQuantity(Integer value) {
        quantity.set(value);
    }

}
