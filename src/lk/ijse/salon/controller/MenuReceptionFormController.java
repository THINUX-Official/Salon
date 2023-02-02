package lk.ijse.salon.controller;

/*
    @author THINUX
    @created 15-Nov-22
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.salon.util.Navigation;
import lk.ijse.salon.util.Routes;

import java.io.IOException;
import java.util.Objects;

public class MenuReceptionFormController {

    public AnchorPane ancPane;
    public AnchorPane pane1;
    public Label lblDate;
    public Label lblTime;

    @FXML
    private AnchorPane pane;

    @FXML
    void logoutOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/LoginForm.fxml")))));
    }

    @FXML
    void manageAppointmentOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.MANAGE_APPOINTMENT, pane);
    }

    @FXML
    void manageCustomerOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.MANAGE_CUSTOMER, pane);
    }

    @FXML
    void manageServicesOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.MANAGE_SERVICES, pane);
    }

    @FXML
    void manageStockOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.MANAGE_PRODUCT, pane);
    }

    public void manageCustomerOrderOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.MANAGE_CUSTOMER_ORDERS, pane);
    }

    public void dashboardOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.DASHBOARD, pane);
    }

    public void manageServiceOrderOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.GIVEN_SERVICE, pane);
    }
}
