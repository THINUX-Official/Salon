package lk.ijse.salon.to;

/*
    @author THINUX
    @created 01-Dec-22
*/

public class SupOrderDetail {
    private String sOID;
    private String iID;
    private double unitPrice;
    private int qty;

    public SupOrderDetail() {
    }

    public SupOrderDetail(String sOID, String iID, double unitPrice, int qty) {
        this.sOID = sOID;
        this.iID = iID;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public String getsOID() {
        return sOID;
    }

    public void setsOID(String sOID) {
        this.sOID = sOID;
    }

    public String getiID() {
        return iID;
    }

    public void setiID(String iID) {
        this.iID = iID;
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

    @Override
    public String toString() {
        return "SupOrderDetail{" +
                "sOID='" + sOID + '\'' +
                ", iID='" + iID + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                '}';
    }
}
