package lk.ijse.salon.to;

/*
    @author THINUX
    @created 22-Nov-22
*/

import java.sql.Date;
import java.util.ArrayList;

public class Order {
    private String oID;
    private java.sql.Date oDate;
    private String cID;

    private ArrayList<OrderDetail> orderDetails;

    public Order() {
    }

    public Order(String oID, Date oDate, String CID, ArrayList<OrderDetail> orderDetails) {
        this.oID = oID;
        this.oDate = oDate;
        this.cID = CID;
        this.orderDetails = orderDetails;
    }

    public String getoID() {
        return oID;
    }

    public void setoID(String oID) {
        this.oID = oID;
    }

    public Date getoDate() {
        return oDate;
    }

    public void setoDate(Date oDate) {
        this.oDate = oDate;
    }

    public String getCID() {
        return cID;
    }

    public void setCID(String CID) {
        this.cID = CID;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oID='" + oID + '\'' +
                ", oDate=" + oDate +
                ", CID='" + cID + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
