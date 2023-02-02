package lk.ijse.salon.model;

/*
    @author THINUX
    @created 28-Nov-22
*/

import lk.ijse.salon.db.DBConnection;
import lk.ijse.salon.to.SupOrder;
import lk.ijse.salon.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupOrderModel {
    public static boolean save(SupOrder supOrder) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO supOrder VALUES(?, ?, ?)";
        return CrudUtil.execute(sql, supOrder.getsOID(), supOrder.getSoDate(), supOrder.getSID());
    }

    public static String generateNextSOID() throws SQLException, ClassNotFoundException {
        String sql = "SELECT SOID FROM supOrder ORDER BY SOID DESC LIMIT 1";
        ResultSet result = CrudUtil.execute(sql);

        if (result.next()) {
            return generateNextSOID(result.getString(1));
        }
        return generateNextSOID(result.getString(null));
    }

    private static String generateNextSOID(String currentSOID) {
        if (currentSOID != null) {
            String[] split = currentSOID.split("SO0");
            int sOID = Integer.parseInt(split[1]);
            sOID += 1;
            return "SO0" + sOID;
        }
        return "SO01";
    }

    public static boolean addOrder(SupOrder supOrder) throws SQLException, ClassNotFoundException {
        PreparedStatement statement= DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO supOrder VALUES(?,now(),?) ");
        statement.setObject(1,supOrder.getsOID());
        statement.setObject(2,supOrder.getSID());

        boolean isOrderAdded =statement.executeUpdate()>0;
        return isOrderAdded;
    }
}
