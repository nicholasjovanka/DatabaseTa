package Controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
    private final IntegerProperty item_price;
    private final StringProperty item_name;
    private final StringProperty item_type;
    private final IntegerProperty item_id;

    public Item(Integer item_price, String item_name, String item_type, Integer item_id) {
        this.item_price = new SimpleIntegerProperty(item_price);
        this.item_name = new SimpleStringProperty(item_name);
        this.item_type = new SimpleStringProperty(item_type);
        this.item_id = new SimpleIntegerProperty(item_id);
    }

    public int getItem_price() {
        return item_price.get();
    }

    public void setItem_price(Integer value) {
        item_price.set(value);
    }

    public String getItem_name() {
        return item_name.get();
    }

    public void setItem_name(String value) {
        item_name.set(value);
    }

    public String getItem_type() {
        return item_type.get();
    }

    public void setItem_type(String value) {
        item_type.set(value);
    }

    public int getItem_id() {
        return item_id.get();
    }

    public void setItem_id(Integer value) {
        item_id.set(value);
    }

}
