package lk.ijse.salon.view.tm;

/*
    @author THINUX
    @created 22-Nov-22
*/

import java.util.Date;

public class PaymentTm {
    private String pid;
    private Date date;
    private String desc;
    private double cost;

    public PaymentTm() {
    }

    public PaymentTm(String pid, Date date, String desc, double cost) {
        this.pid = pid;
        this.date = date;
        this.desc = desc;
        this.cost = cost;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        return "PaymentTm{" +
                "pid='" + pid + '\'' +
                ", date=" + date +
                ", desc='" + desc + '\'' +
                ", cost=" + cost +
                '}';
    }
}
