package lk.ijse.salon.to;

/*
    @author THINUX
    @created 21-Nov-22
*/

import java.sql.Time;
import java.util.Date;

public class Appointment {
    private String aid;
    private Date date;
    private Time time;
    private String cid;

    public Appointment() {
    }

    public Appointment(String aid, Date date, Time time, String cid) {
        this.aid = aid;
        this.date = date;
        this.time = time;
        this.cid = cid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "aid='" + aid + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", cid='" + cid + '\'' +
                '}';
    }
}
