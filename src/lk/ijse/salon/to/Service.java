package lk.ijse.salon.to;

/*
    @author THINUX
    @created 19-Nov-22
*/

public class Service {
    String serID;
    String name;
    String type;
    double price;


    public Service() {
    }

    public Service(String serID, String name, String type, double price) {
        this.serID = serID;
        this.name = name;
        this.type = type;
        this.price = price;

    }

    public String getSerID() {
        return serID;
    }

    public void setSerID(String serID) {
        this.serID = serID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serID='" + serID + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}
