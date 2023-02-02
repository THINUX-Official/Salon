package lk.ijse.salon.view.tm;

/*
    @author THINUX
    @created 22-Nov-22
*/

public class ItemTm {
    private String id;
    private String desc;
    private String unitPrice;
    private String qtyOnHand;

    public ItemTm() {
    }

    public ItemTm(String id, String desc, String unitPrice, String qtyOnHand) {
        this.id = id;
        this.desc = desc;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(String qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "ItemTm{" +
                "id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", qtyOnHand='" + qtyOnHand + '\'' +
                '}';
    }
}
