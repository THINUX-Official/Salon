package lk.ijse.salon.model;

/*
    @author THINUX
    @created 01-Dec-22
*/

import lk.ijse.salon.db.DBConnection;
import lk.ijse.salon.to.SupOrder;

import java.sql.SQLException;

public class SupplierPlaceOrderModel {

    public static boolean placeOrder(SupOrder supOrder) throws SQLException, ClassNotFoundException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isOrderAdded = SupOrderModel.addOrder(supOrder);
            if (isOrderAdded) {
                System.out.println("orderAdded");
                boolean isUpdated = ItemModel.updateQtySup(supOrder.getSupOrderDetails());

                if (isUpdated) {
                    System.out.println("Updated");
                    boolean isOrderDetailAdded = SupOrderDetailModel.saveOrderDetails(supOrder.getSupOrderDetails());

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
