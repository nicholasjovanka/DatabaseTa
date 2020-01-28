package Controller;

public class CartItem {
    private Integer itemid;
    private String itemname;
    private Integer itemqty;

    public CartItem(Integer id, String name, Integer qty) {
        this.itemid = id;
        this.itemname = name;
        this.itemqty = qty;
    }

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public Integer getItemqty() {
        return itemqty;
    }

    public void setItemqty(Integer itemqty) {
        this.itemqty = itemqty;
    }
}
