package lk.ijse.salon.model;

/*
    @author THINUX
    @created 19-Nov-22
*/

import lk.ijse.salon.to.Supplier;
import lk.ijse.salon.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public static boolean save(Supplier supplier) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO supplier VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, supplier.getSid(), supplier.getName(), supplier.getAddress(), supplier.getContact());
    }

    public static Supplier search(String sid) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM supplier WHERE SID = ?";
        ResultSet result = CrudUtil.execute(sql, sid);

        if (result.next()) {
            return new Supplier(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }

    public static ArrayList<String> loadSIDs() throws SQLException, ClassNotFoundException {
        String sql = "SELECT SID FROM supplier";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }
}
