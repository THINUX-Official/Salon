package lk.ijse.salon.model;

/*
    @author THINUX
    @created 22-Nov-22
*/

import lk.ijse.salon.to.Payment;
import lk.ijse.salon.util.CrudUtil;
import lk.ijse.salon.view.tm.PaymentTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {

    public static boolean save(Payment payment) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO payment VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, payment.getpID(), payment.getpDate(), payment.getDesc(), payment.getCost());
    }

    public static String generateNextPID() throws SQLException, ClassNotFoundException {
        String sql = "SELECT PID FROM payment ORDER BY PID DESC LIMIT 1";
        ResultSet result = CrudUtil.execute(sql);

        if (result.next()) {
            return generateNextPID(result.getString(1));
        }
        return generateNextPID(result.getString(null));
    }

    private static String generateNextPID(String currentPID) {
        if (currentPID != null) {
            String[] split = currentPID.split("P00");
            int pID = Integer.parseInt(split[1]);
            pID += 1;
            return "P00" + pID;
        }
        return "P001";
    }

    public static ArrayList getDetails() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM payment";
        ResultSet resultSet = CrudUtil.execute(sql);
        ArrayList<PaymentTm> arrayList = new ArrayList();
        while (resultSet.next()){
            arrayList.add(new PaymentTm(resultSet.getString(1),resultSet.getDate(2),
                    resultSet.getString(3),resultSet.getDouble(4)));
        }
        return arrayList;
    }

    public static boolean update(Payment payment) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE payment set pDate = ?, description = ?, cost = ? WHERE PID = ?";
        return CrudUtil.execute(sql, payment.getpDate(), payment.getDesc(), payment.getCost(), payment.getpID());
    }
}
