package lk.ijse.salon.to;

/*
    @author THINUX
    @created 28-Nov-22
*/

import java.util.ArrayList;
import java.util.Date;

public class SupOrder {
    private String sOID;
    private Date soDate;
    private String SID;

    private ArrayList<SupOrderDetail> supOrderDetails;

    public SupOrder() {
    }

    public SupOrder(String sOID, Date soDate, String SID, ArrayList<SupOrderDetail> supOrderDetails) {
        this.sOID = sOID;
        this.soDate = soDate;
        this.SID = SID;
        this.supOrderDetails = supOrderDetails;
    }

    public String getsOID() {
        return sOID;
    }

    public void setsOID(String sOID) {
        this.sOID = sOID;
    }

    public Date getSoDate() {
        return soDate;
    }

    public void setSoDate(Date soDate) {
        this.soDate = soDate;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public ArrayList<SupOrderDetail> getSupOrderDetails() {
        return supOrderDetails;
    }

    public void setSupOrderDetails(ArrayList<SupOrderDetail> supOrderDetails) {
        this.supOrderDetails = supOrderDetails;
    }

    @Override
    public String toString() {
        return "SupOrder{" +
                "sOID='" + sOID + '\'' +
                ", soDate=" + soDate +
                ", SID='" + SID + '\'' +
                ", supOrderDetails=" + supOrderDetails +
                '}';
    }
}
