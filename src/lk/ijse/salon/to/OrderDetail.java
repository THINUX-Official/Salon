package lk.ijse.salon.to;

/*
    @author THINUX
    @created 22-Nov-22
*/

public class OrderDetail {
    private String oID;
    private String iID;
    private double unitPrice;
    private int qty;

    public OrderDetail() {
    }

    public OrderDetail(String oID, String iID, double unitPrice, int qty) {
        this.oID = oID;
        this.iID = iID;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public String getoID() {
        return oID;
    }

    public void setoID(String oID) {
        this.oID = oID;
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


}
