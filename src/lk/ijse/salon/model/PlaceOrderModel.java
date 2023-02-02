package lk.ijse.salon.model;

/*
    @author THINUX
    @created 22-Nov-22
*/

import lk.ijse.salon.db.DBConnection;
import lk.ijse.salon.to.Order;

import java.sql.SQLException;

public class PlaceOrderModel {
    public static boolean placeOrder(Order order) throws SQLException, ClassNotFoundException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isOrderAdded = OrderModel.addOrder(order);
                if (isOrderAdded) {
                    System.out.println("orderAdded");
                    boolean isUpdated = ItemModel.updateQty(order.getOrderDetails());

                    if (isUpdated) {
                        System.out.println("Updated");
                        boolean isOrderDetailAdded = OrderDetailModel.saveOrderDetails(order.getOrderDetails());

                        if (isOrderDetailAdded) {
                            System.out.println("DetailsAdded");
                            DBConnection.getInstance().getConnection().commit();
                            return true;
                        }
                    }
                }
                DBConnection.getInstance().getConnection().rollback();
                return false;
        } finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }
}

