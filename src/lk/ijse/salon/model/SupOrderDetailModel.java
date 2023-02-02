package lk.ijse.salon.model;

/*
    @author THINUX
    @created 01-Dec-22
*/

import lk.ijse.salon.db.DBConnection;
import lk.ijse.salon.to.SupOrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupOrderDetailModel {
    public static boolean saveOrderDetails(ArrayList<SupOrderDetail> supOrderDetails) throws SQLException, ClassNotFoundException {
        for (SupOrderDetail supOrderDetail : supOrderDetails) {
            if (!save(supOrderDetail)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(SupOrderDetail cartDetail) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO suporderdetail VALUES(?,?,?,?)");

        statement.setObject(1, cartDetail.getsOID());
        statement.setObject(2, cartDetail.getiID());
        statement.setObject(3, cartDetail.getUnitPrice());
        statement.setObject(4, cartDetail.getQty());

        return statement.executeUpdate() > 0;
    }
}
