package lk.ijse.salon.model;

/*
    @author THINUX
    @created 22-Nov-22
*/

import lk.ijse.salon.db.DBConnection;
import lk.ijse.salon.to.OrderDetail;
import lk.ijse.salon.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailModel {

    public static boolean saveOrderDetails(ArrayList<OrderDetail> orderDetails) throws SQLException, ClassNotFoundException {
        for (OrderDetail orderDetail : orderDetails) {
            if (!save(orderDetail)) {
                return false;
            }
        }
        return true;
    }

    public static int orderCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) from orders";
        ResultSet result = CrudUtil.execute(sql);

        if (result.next())
            return result.getInt(1);
        return 0;
    }

    private static boolean save(OrderDetail cartDetail) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO orderdetail VALUES(?,?,?,?)");

        statement.setObject(1, cartDetail.getoID());
        statement.setObject(2, cartDetail.getiID());
        statement.setObject(3, cartDetail.getUnitPrice());
        statement.setObject(4, cartDetail.getQty());

        return statement.executeUpdate() > 0;
    }
}
