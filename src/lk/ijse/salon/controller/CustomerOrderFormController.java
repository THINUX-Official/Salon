package lk.ijse.salon.controller;

/*
    @author THINUX
    @created 16-Nov-22
*/

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.salon.model.CustomerModel;
import lk.ijse.salon.model.ItemModel;
import lk.ijse.salon.model.OrderModel;
import lk.ijse.salon.model.PlaceOrderModel;
import lk.ijse.salon.to.Customer;
import lk.ijse.salon.to.Item;
import lk.ijse.salon.to.Order;
import lk.ijse.salon.to.OrderDetail;
import lk.ijse.salon.util.Navigation;
import lk.ijse.salon.util.Routes;
import lk.ijse.salon.view.tm.OrderTm;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerOrderFormController implements Initializable {

    public TableColumn colCID;
    public AnchorPane pane;
    public Label lblOID;
    public JFXTextField txtCustName;
    public Label lblDesc;
    public Label lblUnitPrice;
    public Label lblQtyOnHand;
    public Label lblDate;
    public Label lblTime;
    public Label lblTotal;
    public TableView<OrderTm> tblOrders;
    public TableColumn colIID;
    public TableColumn colDesc;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colTotal;
    public JFXComboBox cmbCID;
    public JFXComboBox cmbIID;
    public JFXTextField txtQty;
    public Label lblQty;

    private Matcher amountMatcher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colIID.setCellValueFactory(new PropertyValueFactory<>("iid"));
        colCID.setCellValueFactory(new PropertyValueFactory<>("cid"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadOrderDate();
        loadOrderTime();
        loadCIDs();
        loadIIDs();
        loadNextOID();

        setPattern();
    }

    public void setPattern(){

        Pattern amountPattern = Pattern.compile("^[0-9]{1,}$");
        amountMatcher = amountPattern.matcher(txtQty.getText());
    }

    static ObservableList<OrderTm> obList = FXCollections.observableArrayList();

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String oID = lblOID.getText();
        String cID = String.valueOf(cmbCID.getValue());
        Date date = Date.valueOf(lblDate.getText());
       /* double price = Double.parseDouble(lblUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());*/

        ArrayList<OrderDetail> orderDetails = new ArrayList<>();

        for (int i = 0; i < tblOrders.getItems().size(); i++) {
            String iID = String.valueOf(colIID.getCellData(i));
            double price = Double.parseDouble(String.valueOf(colUnitPrice.getCellData(i)));
            int qty = Integer.parseInt(String.valueOf(colQty.getCellData(i)));

            OrderDetail orderDetail = new OrderDetail(oID, iID, price, qty);

            orderDetails.add(orderDetail);
        }

        Order order = new Order(oID, null, cID, orderDetails);

        boolean placeOrder = PlaceOrderModel.placeOrder(order);

        if (placeOrder){
            if (placeOrder) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Successful!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something Happened!").show();
            }
        }
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        String iid = String.valueOf(cmbIID.getSelectionModel().getSelectedItem());
        String cid = String.valueOf(cmbCID.getSelectionModel().getSelectedItem());
        String desc = lblDesc.getText();
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        int qty = Integer.parseInt((txtQty.getText()));
        double total = qty * unitPrice;

        OrderTm orderTm = new OrderTm(iid, cid, desc, unitPrice, qty, total);


        for (int i = 0; i < tblOrders.getItems().size(); i++) {
            if (colIID.getCellData(i).equals(String.valueOf(cmbIID.getSelectionModel().getSelectedItem()))) {
                int tempQty = obList.get(i).getQty() + qty;

                double tot = unitPrice * tempQty;

                obList.get(i).setQty(tempQty);

                obList.get(i).setTotal(tot);

                tblOrders.refresh();

                generateTotal();

                return;
            }
        }

        obList.add(orderTm);

        tblOrders.setItems(obList);

        generateTotal();

        calculateTotal();
        cmbIID.requestFocus();
    }

    public void generateTotal(){
        double total = 0;
        for (int i = 0; i < tblOrders.getItems().size(); i++) {
            total += Double.parseDouble(String.valueOf(colTotal.getCellData(i)));

        }
        lblTotal.setText(String.valueOf(total));
    }

    private int isAlreadyExists(String iID){
        for (int i = 0; i <obList.size(); i++) {
            if (obList.get(i).getIid().equals(iID)){
                return i;
            }
        }
        return -1;
    }

    private void calculateTotal(){
        double total = 0.00;
        for (OrderTm orderTm : obList
        ) {
            total += orderTm.getTotal();
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

    private void loadIIDs() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> itemList = ItemModel.loadIIDs();

            for (String iID : itemList) {
                observableList.add(iID);
            }
            cmbIID.setItems(observableList);
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

    public void cmbIIDOnAction(ActionEvent event) {
        String iID = String.valueOf(cmbIID.getValue());
        try {
            Item item = ItemModel.search(iID);
            fillItemFields(item);
            txtQty.requestFocus();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillItemFields(Item item) {
        lblDesc.setText(item.getDesc());
        lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        lblQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
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

    public void txtQtyKeyTypeOnAction(KeyEvent keyEvent) {
        lblQty.setText("");

        Pattern amountPattern = Pattern.compile("^[0-9]{1,}$");
        amountMatcher = amountPattern.matcher(txtQty.getText());

        if (!amountMatcher.matches()) {
            txtQty.requestFocus();
            lblQty.setText("Invalid Input");
        }
    }
}
