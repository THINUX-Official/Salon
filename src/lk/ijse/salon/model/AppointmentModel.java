package lk.ijse.salon.model;

/*
    @author THINUX
    @created 21-Nov-22
*/

import lk.ijse.salon.to.Appointment;
import lk.ijse.salon.util.CrudUtil;
import lk.ijse.salon.view.tm.AppointmentTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppointmentModel {

    public static boolean save(Appointment appointment) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO appointment VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, appointment.getAid(), appointment.getDate(), appointment.getTime(), appointment.getCid());
    }

    public static Appointment search(String aID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM appointment WHERE AID = ?";
        ResultSet result = CrudUtil.execute(sql, aID);

        if (result.next()) {
            return new Appointment(
                    result.getString(1),
                    result.getDate(2),
                    result.getTime(3),
                    result.getString(4)
            );
        }
        return null;
    }

    public static String generateCurrentAID() throws SQLException, ClassNotFoundException {
        String sql = "SELECT AID FROM appointment ORDER BY AID DESC LIMIT 1";
        ResultSet result = CrudUtil.execute(sql);

        if (result.next()) {
            return result.getString(1);
        }
        return null;
    }

    public static ArrayList getDetails() throws SQLException, ClassNotFoundException {
        String sql="select salon.appointment.AID,salon.appointment.CID,salon.customer.name,salon.customer.address,\n" +
                "       salon.customer.contact, salon.appointment.aDate,\n" +
                "       salon.appointment.aTime FROM appointment INNER JOIN customer  on appointment.CID = customer.CID";
        ResultSet resultSet=CrudUtil.execute(sql);
        ArrayList<AppointmentTm> arrayList=new ArrayList();
        while (resultSet.next()){
            arrayList.add(new AppointmentTm(resultSet.getString(1),resultSet.getString(2),
                    resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5),
                    resultSet.getDate(6),resultSet.getTime(7)
                    ));
        }
        return arrayList;
    }
}
