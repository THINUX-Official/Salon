package lk.ijse.salon.model;

/*
    @author THINUX
    @created 19-Nov-22
*/

import lk.ijse.salon.db.DBConnection;
import lk.ijse.salon.to.Item;
import lk.ijse.salon.to.OrderDetail;
import lk.ijse.salon.to.SupOrderDetail;
import lk.ijse.salon.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {

    public static boolean updateQty(ArrayList<OrderDetail> orderDetails) throws SQLException, ClassNotFoundException {
        for (OrderDetail cartDetail : orderDetails) {
            if (!updateQty(cartDetail)){
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE item SET qtyOnHand = item.qtyOnHand -? WHERE IID =?");
        stm.setObject(1, orderDetail.getQty());
        stm.setObject(2, orderDetail.getiID());

        return stm.executeUpdate() > 0;
    }

    public static boolean updateQtySup(ArrayList<SupOrderDetail> supOrderDetails) throws SQLException, ClassNotFoundException {
        for (SupOrderDetail cartDetail : supOrderDetails) {
            if (!updateQtySup(cartDetail)){
                return false;
            }
        }
        return true;
    }

    private static boolean updateQtySup(SupOrderDetail supOrderDetail) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE item SET qtyOnHand = item.qtyOnHand +? WHERE IID =?");
        stm.setObject(1, supOrderDetail.getQty());
        stm.setObject(2, supOrderDetail.getiID());

        return stm.executeUpdate() > 0;
    }



    public static boolean save(Item item) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO item VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, item.getIid(), item.getDesc(), item.getUnitPrice(), item.getQtyOnHand());
    }

    public static Item search(String iid) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM item WHERE IID = ?";
        ResultSet result = CrudUtil.execute(sql, iid);

        if (result.next()) {
            return new Item(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getInt(4)
            );
        }
        return null;
    }

    public static ArrayList<String> loadIIDs() throws SQLException, ClassNotFoundException {
        String sql = "SELECT IID FROM item";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> iIDList = new ArrayList<>();

        while (result.next()) {
            iIDList.add(result.getString(1));
        }
        return iIDList;
    }

    public static int productCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) from item;";
        ResultSet result = CrudUtil.execute(sql);

        if (result.next())
            return result.getInt(1);
        return 0;
    }
}
