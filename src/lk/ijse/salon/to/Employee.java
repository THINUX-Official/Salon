package lk.ijse.salon.to;

/*
    @author THINUX
    @created 20-Nov-22
*/

public class Employee {
    String EID;
    String name;
    String address;
    String contact;

    public Employee() {
    }

    public Employee(String EID, String name, String address, String contact) {
        this.EID = EID;
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public String getEID() {
        return EID;
    }

    public void setEID(String EID) {
        this.EID = EID;
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
        return "Employee{" +
                "EID='" + EID + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
