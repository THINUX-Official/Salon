package lk.ijse.salon.to;

/*
    @author THINUX
    @created 19-Nov-22
*/

public class Supplier {
    String sid;
    String name;
    String address;
    String contact;

    public Supplier() {
    }

    public Supplier(String sid, String name, String address, String contact) {
        this.sid = sid;
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
