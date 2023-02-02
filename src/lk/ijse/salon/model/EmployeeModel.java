package lk.ijse.salon.model;

/*
    @author THINUX
    @created 20-Nov-22
*/

import lk.ijse.salon.to.Employee;
import lk.ijse.salon.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    public static boolean save(Employee employee) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO employee VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, employee.getEID(), employee.getName(), employee.getAddress(), employee.getContact());
    }

    public static Employee search(String eID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM employee WHERE EID = ?";
        ResultSet result = CrudUtil.execute(sql, eID);

        if (result.next()) {
            return new Employee(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }

    public static ArrayList<String> loadCIDs() throws SQLException, ClassNotFoundException {
        String sql = "SELECT EID FROM employee";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }
}
