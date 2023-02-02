package lk.ijse.salon.view.tm;

/*
    @author THINUX
    @created 28-Nov-22
*/

import java.sql.Date;
import java.sql.Time;

public class AppointmentTm {
    private String aid;
    private String cid;
    private String name;
    private String address;
    private int contact;
    private Date date;
    private Time time;

    public AppointmentTm() {
    }

    public AppointmentTm(String aid, String cid, String name, String address, int contact, Date date, Time time) {
        this.aid = aid;
        this.cid = cid;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.date = date;
        this.time = time;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "AppointmentTm{" +
                "aid='" + aid + '\'' +
                ", cid='" + cid + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact=" + contact +
                ", aDate=" + date +
                ", aTime=" + time +
                '}';
    }
}
