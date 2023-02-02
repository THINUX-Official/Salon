package lk.ijse.salon.model;

/*
    @author THINUX
    @created 19-Nov-22
*/

import lk.ijse.salon.to.Service;
import lk.ijse.salon.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceModel {
    public static boolean save(Service service) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO service VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, service.getSerID(), service.getName(), service.getType(), service.getPrice());
    }

    public static Service search(String serID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM service WHERE serID = ?";
        ResultSet result = CrudUtil.execute(sql, serID);

        if (result.next()) {
            return new Service(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4)
            );
        }
        return null;
    }

    public static ArrayList<String> loadIIDs() throws SQLException, ClassNotFoundException {
        String sql = "SELECT serID FROM service";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> serIDList = new ArrayList<>();

        while (result.next()) {
            serIDList.add(result.getString(1));
        }
        return serIDList;
    }

    public static int serviceCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) from serviceOrder;";
        ResultSet result = CrudUtil.execute(sql);

        if (result.next())
            return result.getInt(1);
        return 0;
    }
}
