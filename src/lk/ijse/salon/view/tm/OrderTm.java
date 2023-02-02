package lk.ijse.salon.view.tm;

/*
    @author THINUX
    @created 22-Nov-22
*/

public class OrderTm {
    private String iid;
    private String cid;
    private String desc;
    private double unitPrice;
    private int qty;
    private double total;

    public OrderTm(Object value, String text, double unitPrice, int qty, double total) {
    }

    public OrderTm(String iid, String cid, String desc, double unitPrice, int qty, double total) {
        this.iid = iid;
        this.cid = cid;
        this.desc = desc;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.total = total;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderTm{" +
                "iid='" + iid + '\'' +
                ", cid='" + cid + '\'' +
                ", desc='" + desc + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", total=" + total +
                '}';
    }
}
