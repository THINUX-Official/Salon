package lk.ijse.salon.controller;

/*
    @author THINUX
    @created 16-Nov-22
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lk.ijse.salon.db.DBConnection;
import lk.ijse.salon.util.Navigation;
import lk.ijse.salon.util.Routes;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

public class ReportFormController {
    @FXML
    private AnchorPane pane;

    @FXML
    void menuOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.ADMIN_MENU, pane);
    }

    @FXML
    void dailyReportOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.DAILY_REPORT, pane);
    }

    @FXML
    void monthlyReportOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.MONTHLY_REPORT, pane);
    }

    public void annualReportOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ANNUAL_REPORT, pane);
    }

    public void customerDetailsOnAction(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {
        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/salon/report/Customer_Report.jrxml");
        HashMap<String,Object> hm = new HashMap<>(0);

        try{
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void productDetailsOnAction(ActionEvent actionEvent) {
        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/salon/report/Product _Details.jrxml");
        HashMap<String,Object> hm = new HashMap<>(0);

        try{
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void supplierDetailsOnAction(ActionEvent actionEvent) {
        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/salon/report/Supplier_Details.jrxml");
        HashMap<String,Object> hm = new HashMap<>(0);

        try{
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

