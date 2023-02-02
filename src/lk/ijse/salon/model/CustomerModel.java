package lk.ijse.salon.model;

/*
    @author THINUX
    @created 19-Nov-22
*/

import lk.ijse.salon.to.Customer;
import lk.ijse.salon.util.CrudUtil;
import lk.ijse.salon.view.tm.CustomerTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public static boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO customer VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, customer.getCid(), customer.getName(), customer.getAddress(), customer.getContact());
    }

    public static Customer search(String cID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM customer WHERE CID = ?";
        ResultSet result = CrudUtil.execute(sql, cID);

        if (result.next()) {
            return new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }

    public static ArrayList<String> loadCIDs() throws SQLException, ClassNotFoundException {
        String sql = "SELECT CID FROM customer";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }

    public static boolean delete(String selectedID) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM customer where CID = ?";
        return CrudUtil.execute(sql, selectedID);
    }

    public static boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE customer set name = ?, address = ?, contact = ? WHERE CID = ?";
        return CrudUtil.execute(sql, customer.getName(), customer.getAddress(), customer.getContact(), customer.getCid());

    }

    public static ArrayList getDetails() throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM customer;";
        ResultSet resultSet=CrudUtil.execute(sql);
        ArrayList<CustomerTm> arrayList=new ArrayList();
        while (resultSet.next()){
            arrayList.add(new CustomerTm(resultSet.getString(1),resultSet.getString(2),
                    resultSet.getString(3),resultSet.getString(4)));
        }
        return arrayList;
    }

    public static int customerCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) from customer";
        ResultSet result = CrudUtil.execute(sql);

        if (result.next())
            return result.getInt(1);
        return 0;
    }
}
