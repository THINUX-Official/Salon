package lk.ijse.salon.to;

/*
    @author THINUX
    @created 22-Nov-22
*/

public class Payment {
    private String pID;
    private String pDate;
    private String desc;
    private double cost;

    public Payment() {
    }

    public Payment(String pID, String pDate, String desc, double cost) {
        this.pID = pID;
        this.pDate = pDate;
        this.desc = desc;
        this.cost = cost;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getpDate() {
        return pDate;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "pID='" + pID + '\'' +
                ", pDate='" + pDate + '\'' +
                ", desc='" + desc + '\'' +
                ", cost=" + cost +
                '}';
    }
}
