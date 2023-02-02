package lk.ijse.salon.util;

/*
    @author THINUX
    @created 15-Nov-22
*/

import animatefx.animation.FadeIn;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static AnchorPane pane;
    public static void navigate(Routes route, AnchorPane pane) throws IOException {

        Navigation.pane = pane;
        //animation
        new FadeIn(pane).setSpeed(2).play();

        Navigation.pane.getChildren().clear();
        Stage window = (Stage) Navigation.pane.getScene().getWindow();

        switch (route) {
            case LOGIN:
                window.setTitle("Login Form");
                initUI("LoginForm.fxml");
                break;
            case ADMIN_MENU:
                window.setTitle("Admin Menu Form");
                initUI("MenuAdminForm.fxml");
                break;
            case RECEPTION_MENU:
                window.setTitle("Reception Menu Form");
                initUI("MenuReceptionForm.fxml");
                break;
                case MANAGE_CUSTOMER:
                window.setTitle("Manage Customer Form");
                initUI("CustomerForm.fxml");
                break;
                case MANAGE_APPOINTMENT:
                window.setTitle("Manage Appointment Form");
                initUI("AppointmentForm.fxml");
                break;
                case MANAGE_EMPLOYEES:
                window.setTitle("Manage Employees Form");
                initUI("EmployeesForm.fxml");
                break;
                case MANAGE_SUPPLIERS:
                window.setTitle("Manage Suppliers Form");
                initUI("SuppliersForm.fxml");
                break;
                case MANAGE_SERVICES:
                window.setTitle("Manage Services Form");
                initUI("ServicesForm.fxml");
                break;
                case MANAGE_CUSTOMER_ORDERS:
                window.setTitle("Manage Customer Order Form");
                initUI("CustomerOrderForm.fxml");
                break;
                case MANAGE_PRODUCT:
                window.setTitle("Manage Product Form");
                initUI("ProductForm.fxml");
                break;
                case REPORT:
                window.setTitle("Report Form");
                initUI("ReportForm.fxml");
                break;
                case DAILY_REPORT:
                window.setTitle("Daily Report Form");
                initUI("DailyReportForm.fxml");
                break;
                case MONTHLY_REPORT:
                window.setTitle("Monthly Report Form");
                initUI("MonthlyReportForm.fxml");
                break;
                case ANNUAL_REPORT:
                window.setTitle("Annual Report Form");
                initUI("AnnualReportForm.fxml");
                break;
                case MANAGE_SALARY:
                window.setTitle("Manage Salary Form");
                initUI("SalaryForm.fxml");
                break;
                case MANAGE_PAYMENT:
                window.setTitle("Manage Payment Form");
                initUI("PaymentForm.fxml");
                break;
                case MANAGE_SUPPLIERS_ORDERS:
                window.setTitle("Manage Suppliers Orders Form");
                initUI("SupplierOrderForm.fxml");
                break;
                case DASHBOARD:
                window.setTitle("Dashboard Form");
                initUI("DashBoard.fxml");
                break;
                case GIVEN_SERVICE:
                    window.setTitle("Given Service Form");
                    initUI("GivenServiceForm.fxml");
                break;
            default:
                new Alert(Alert.AlertType.ERROR, "Not suitable UI found!").show();
        }
    }
    private static void initUI(String location) throws IOException {
        Navigation.pane.getChildren().add(FXMLLoader.load(Navigation.class
                .getResource("/lk/ijse/salon/view/" + location)));
    }
}
