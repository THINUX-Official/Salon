package lk.ijse.salon.view.tm;

/*
    @author THINUX
    @created 30-Nov-22
*/

public class GivenServiceTm {
    String serID;
    String cid;
    String desc;
    Double price;


    public GivenServiceTm() {
    }

    public GivenServiceTm(String serID, String cid, String desc, Double price) {
        this.serID = serID;
        this.cid = cid;
        this.desc = desc;
        this.price = price;
    }

    public String getSerID() {
        return serID;
    }

    public void setSerID(String serID) {
        this.serID = serID;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "GivenServiceTm{" +
                "serID='" + serID + '\'' +
                ", cid='" + cid + '\'' +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                '}';
    }
}
