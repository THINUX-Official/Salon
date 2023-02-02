package lk.ijse.salon.model;

/*
    @author THINUX
    @created 22-Nov-22
*/

import lk.ijse.salon.db.DBConnection;
import lk.ijse.salon.to.Order;
import lk.ijse.salon.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderModel {

    public static boolean addOrder(Order order) throws SQLException, ClassNotFoundException {
        PreparedStatement statement= DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO orders VALUES(?,now(),?) ");
        statement.setObject(1,order.getoID());

        statement.setObject(2,order.getCID());

        boolean isOrderAdded =statement.executeUpdate()>0;
        return isOrderAdded;
    }

    public static boolean save(Order order) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orders VALUES(?, ?, ?)";
        return CrudUtil.execute(sql, order.getoID(), order.getoDate(), order.getCID());
    }

    public static String generateNextOID() throws SQLException, ClassNotFoundException {
        String sql = "SELECT OID FROM orders ORDER BY OID DESC LIMIT 1";
        ResultSet result = CrudUtil.execute(sql);

        if (result.next()) {
            return generateNextOID(result.getString(1));
        }
        return generateNextOID(result.getString(null));
    }

    private static String generateNextOID(String currentOID) {
        if (currentOID != null) {
            String[] split = currentOID.split("O00");
            int oID = Integer.parseInt(split[1]);
            oID += 1;
            return "O00" + oID;
        }
        return "O001";
    }
}
