package lk.ijse.salon.controller;

/*
    @author THINUX
    @created 15-Nov-22
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.salon.model.CustomerModel;
import lk.ijse.salon.model.ItemModel;
import lk.ijse.salon.model.OrderDetailModel;
import lk.ijse.salon.model.ServiceModel;
import lk.ijse.salon.util.Navigation;
import lk.ijse.salon.util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class MenuAdminFormController {
    public AnchorPane pane;
    public AnchorPane ancPane;
    public AnchorPane loginPane;
    public Label lblProductCount;
    public Label lblOrderCount;
    public AnchorPane pane1;
    public Label lblDate;
    public Label lblTime;
    public Label lblServiceCount;
    public Label lblCustomerCount;

    public void initialize() throws IOException, SQLException, ClassNotFoundException {
        customerCount();
        orderCount();
        serviceCount();
        productCount();

        loadOrderDate();
        loadOrderTime();
    }

    private void loadOrderDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    private void loadOrderTime() {
        lblTime.setText(String.valueOf(LocalTime.now()));
    }

    private void customerCount() throws SQLException, ClassNotFoundException {
        lblCustomerCount.setText(String.valueOf(CustomerModel.customerCount()));
    }

    private void orderCount() throws SQLException, ClassNotFoundException {
        lblOrderCount.setText(String.valueOf(OrderDetailModel.orderCount()));
    }

    private void serviceCount() throws SQLException, ClassNotFoundException {
        lblServiceCount.setText(String.valueOf(ServiceModel.serviceCount()));
    }

    private void productCount() throws SQLException, ClassNotFoundException {
        lblProductCount.setText(String.valueOf(ItemModel.productCount()));
    }

    public void manageCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.MANAGE_CUSTOMER, pane);
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/LoginForm.fxml")))));

//        Navigation.navigate(Routes.LOGIN, pane);
    }

    public void manageAppointmentOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.MANAGE_APPOINTMENT, pane);
    }

    public void manageEmployeesOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.MANAGE_EMPLOYEES, pane);
    }

    public void manageSuppliersOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.MANAGE_SUPPLIERS, pane);
    }

    public void manageServicesOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.MANAGE_SERVICES, pane);
    }

    public void manageCustomerOrderOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.MANAGE_CUSTOMER_ORDERS, pane);
    }

    public void manageProductOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.MANAGE_PRODUCT, pane);
    }

    public void manageReportOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.REPORT, pane);
    }

    public void managePaymentOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.MANAGE_PAYMENT, pane);
    }

    public void dashboardOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/MenuAdminForm.fxml")))));

//        Navigation.navigate(Routes.ADMIN_MENU, pane);
    }

    public void manageServiceOrderOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.GIVEN_SERVICE, pane);
    }
}
