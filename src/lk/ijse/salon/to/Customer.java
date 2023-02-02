package lk.ijse.salon.to;

/*
    @author THINUX
    @created 19-Nov-22
*/

public class Customer {
    private String cID;
    private String name;
    private String address;
    private String contact;

    public Customer() {
    }

    public Customer(String cid, String name, String address, String contact) {
        this.cID = cid;
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public String getCid() {
        return cID;
    }

    public void setCid(String cid) {
        this.cID = cid;
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
        return "Customer{" +
                "cid='" + cID + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
