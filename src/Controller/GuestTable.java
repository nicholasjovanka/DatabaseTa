package Controller;

import javafx.beans.property.SimpleIntegerProperty;

public class GuestTable {
    private SimpleIntegerProperty tableId;
    private SimpleIntegerProperty capacity;

    GuestTable(int id, int cap) {
        this.tableId = new SimpleIntegerProperty(id);
        this.capacity = new SimpleIntegerProperty(cap);
    }

    ;

    public int getTableId() {
        return tableId.get();
    }

    public void setTableId(int tableId) {
        this.tableId = new SimpleIntegerProperty(tableId);
    }

    public int getCapacity() {
        return capacity.get();
    }

    public void setCapacity(int capacity) {
        this.capacity = new SimpleIntegerProperty(capacity);
    }

}
