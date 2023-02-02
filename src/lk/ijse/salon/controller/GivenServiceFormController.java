package lk.ijse.salon.controller;

/*
    @author THINUX
    @created 30-Nov-22
*/

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.salon.model.CustomerModel;
import lk.ijse.salon.model.OrderModel;
import lk.ijse.salon.model.ServiceModel;
import lk.ijse.salon.to.Customer;
import lk.ijse.salon.to.Service;
import lk.ijse.salon.util.Navigation;
import lk.ijse.salon.util.Routes;
import lk.ijse.salon.view.tm.GivenServiceTm;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class GivenServiceFormController {


    public TableColumn colCID;
    public AnchorPane pane;
    public Label lblOID;
    public JFXTextField txtCustName;
    public Label lblDesc;
    public Label lblDate;
    public Label lblTime;
    public Label lblTotal;
    public TableColumn colDesc;
    public JFXComboBox cmbCID;
    public JFXComboBox cmbIID;
    public TableColumn colSerID;
    public TableColumn colPrice;
    public JFXComboBox cmbSerID;
    public Label lblPrice;
    public TableView<GivenServiceTm> tblServiceOrders;

    public void initialize()throws SQLException, ClassNotFoundException {

        colSerID.setCellValueFactory(new PropertyValueFactory<>("serID"));
        colCID.setCellValueFactory(new PropertyValueFactory<>("cid"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        loadOrderDate();
        loadOrderTime();
        loadCIDs();
        loadSerIDs();
        loadNextOID();
    }

    static ObservableList<GivenServiceTm> obList = FXCollections.observableArrayList();

    public void placeOrderOnAction(ActionEvent actionEvent) {

    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        String serId = String.valueOf(cmbSerID.getSelectionModel().getSelectedItem());
        String cid = String.valueOf(cmbCID.getSelectionModel().getSelectedItem());
        String desc = lblDesc.getText();
        double price = Double.parseDouble(lblPrice.getText());

        GivenServiceTm givenServiceTm = new GivenServiceTm(serId, cid, desc, price);

        obList.add(givenServiceTm);

        tblServiceOrders.setItems(obList);

        generateTotal();

        calculateTotal();
        cmbIID.requestFocus();
    }

    public void generateTotal(){
        double total = 0;
        for (int i = 0; i < tblServiceOrders.getItems().size(); i++) {
            total += Double.parseDouble(String.valueOf(colPrice.getCellData(i)));
        }
        lblTotal.setText(String.valueOf(total));
    }

    private void calculateTotal(){
        double total = 0.00;
        for (GivenServiceTm givenServiceTm : obList
        ) {
            total += givenServiceTm.getPrice();
        }
        lblTotal.setText(String.valueOf(total));
    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void newCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.MANAGE_CUSTOMER, pane);
    }

    private void loadOrderDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    private void loadOrderTime() {
        lblTime.setText(String.valueOf(LocalTime.now()));
    }

    private void loadCIDs() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> cIDList = CustomerModel.loadCIDs();

            for (String cID : cIDList) {
                observableList.add(cID);
            }
            cmbCID.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSerIDs() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> serviceList = ServiceModel.loadIIDs();

            for (String serID : serviceList) {
                observableList.add(serID);
            }
            cmbSerID.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadNextOID() {
        try {
            String oID = OrderModel.generateNextOID();
            lblOID.setText(oID);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbSerIDOnAction(ActionEvent actionEvent) {
        String serID = String.valueOf(cmbSerID.getValue());
        try {
            Service service = ServiceModel.search(serID);
            fillItemFields(service);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillItemFields(Service service) {
        lblDesc.setText(service.getName());
        lblPrice.setText(String.valueOf(service.getPrice()));
    }

    public void cmbCIDOnAction(ActionEvent actionEvent) {
        String cID = String.valueOf(cmbCID.getValue());
        try {
            Customer customer = CustomerModel.search(cID);
            fillCustomerFields(customer);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillCustomerFields(Customer customer) {
        txtCustName.setText(customer.getName());
    }
}
