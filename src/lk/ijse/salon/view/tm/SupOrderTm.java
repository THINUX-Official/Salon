package lk.ijse.salon.view.tm;

/*
    @author THINUX
    @created 22-Nov-22
*/

public class SupOrderTm {
    private String iid;
    private String sid;
    private String desc;
    private double unitPrice;
    private int qty;
    private double total;

    public SupOrderTm() {
    }

    public SupOrderTm(String iid, String sid, String desc, double unitPrice, int qty, double total) {
        this.iid = iid;
        this.sid = sid;
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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
        return "SupOrderTm{" +
                "iid='" + iid + '\'' +
                ", sid='" + sid + '\'' +
                ", desc='" + desc + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", total=" + total +
                '}';
    }
}
