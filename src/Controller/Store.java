package Controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Store {
    private final IntegerProperty store_id;
    private final StringProperty store_name;
    private final StringProperty store_location;

    public Store(Integer store_id, String store_name, String store_location) {
        this.store_id = new SimpleIntegerProperty(store_id);
        this.store_name = new SimpleStringProperty(store_name);
        this.store_location = new SimpleStringProperty(store_location);
    }

    public Integer getStore_id() {
        return store_id.get();
    }

    public void setStore_id(Integer value) {
        store_id.set(value);
    }

    public String getStore_name() {
        return store_name.get();
    }

    public void setStore_name(String value) {
        store_name.set(value);
    }

    public String getStore_location() {
        return store_location.get();
    }

    public void setStore_location(String value) {
        store_location.set(value);
    }
}
