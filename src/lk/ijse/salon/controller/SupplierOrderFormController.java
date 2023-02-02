package lk.ijse.salon.controller;

/*
    @author THINUX
    @created 17-Nov-22
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
import lk.ijse.salon.model.*;
import lk.ijse.salon.to.*;
import lk.ijse.salon.view.tm.SupOrderTm;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupplierOrderFormController implements Initializable {
    public AnchorPane pane;
    public Label lblSOID;
    public Label lblDesc;
    public Label lblUnitPrice;
    public Label lblDate;
    public Label lblTime;
    public Label lblTotal;
    public TableView<SupOrderTm> tableSupOrder;
    public TableColumn colSID;
    public TableColumn colDesc;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public JFXComboBox cmbSID;
    public JFXComboBox cmbIID;
    public JFXTextField txtQty;
    public Label lblQtyOnHand;
    public JFXTextField txtSupName;
    public TableColumn colTotal;
    public TableColumn colIID;
    public Label lblQty;

    private Matcher amountMatcher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colIID.setCellValueFactory(new PropertyValueFactory<>("iid"));
        colSID.setCellValueFactory(new PropertyValueFactory<>("sid"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadOrderDate();
        loadOrderTime();
        loadSIDs();
        loadIIDs();
        loadNextSOID();

        setPattern();
    }

    public void setPattern(){

        Pattern amountPattern = Pattern.compile("^[0-9]{1,}$");
        amountMatcher = amountPattern.matcher(txtQty.getText());
    }

    static ObservableList<SupOrderTm> obList = FXCollections.observableArrayList();


    private void loadNextSOID() {
        try {
            String sOID = SupOrderModel.generateNextSOID();
            lblSOID.setText(sOID);
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

    private void loadSIDs() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> sIDList = SupplierModel.loadSIDs();

            for (String sID : sIDList) {
                observableList.add(sID);
            }
            cmbSID.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadOrderTime() {
        lblTime.setText(String.valueOf(LocalTime.now()));
    }

    private void loadOrderDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String sOID = lblSOID.getText();
        String sID = String.valueOf(cmbSID.getValue());
        Date date = Date.valueOf(lblDate.getText());
       /* double price = Double.parseDouble(lblUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());*/

        ArrayList<SupOrderDetail> supOrderDetails = new ArrayList<>();

        for (int i = 0; i < tableSupOrder.getItems().size(); i++) {
            String iID = String.valueOf(colIID.getCellData(i));
            double price = Double.parseDouble(String.valueOf(colUnitPrice.getCellData(i)));
            int qty = Integer.parseInt(String.valueOf(colQty.getCellData(i)));

            SupOrderDetail supOrderDetail = new SupOrderDetail(sOID, iID, price, qty);

            supOrderDetails.add(supOrderDetail);
        }

        SupOrder supOrder = new SupOrder(sOID, null, sID, supOrderDetails);

        boolean placeOrder = SupplierPlaceOrderModel.placeOrder(supOrder);

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
        String sid = String.valueOf(cmbSID.getSelectionModel().getSelectedItem());
        String desc = lblDesc.getText();
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        int qty = Integer.parseInt((txtQty.getText()));
        double total = qty * unitPrice;

        SupOrderTm supOrderTm = new SupOrderTm(iid, sid, desc, unitPrice, qty, total);


        for (int i = 0; i < tableSupOrder.getItems().size(); i++) {
            if (colIID.getCellData(i).equals(String.valueOf(cmbIID.getSelectionModel().getSelectedItem()))) {
                int tempQty = obList.get(i).getQty() + qty;

                double tot = unitPrice * tempQty;

                obList.get(i).setQty(tempQty);

                obList.get(i).setTotal(tot);

                tableSupOrder.refresh();

                generateTotal();

                return;
            }
        }

        obList.add(supOrderTm);

        tableSupOrder.setItems(obList);

        generateTotal();

        calculateTotal();
        cmbIID.requestFocus();
    }

    public void generateTotal(){
        double total = 0;
        for (int i = 0; i < tableSupOrder.getItems().size(); i++) {
            total += Double.parseDouble(String.valueOf(colTotal.getCellData(i)));

        }
        lblTotal.setText(String.valueOf(total));
    }

    private void calculateTotal(){
        double total = 0.00;
        for (SupOrderTm supOrderTm : obList
        ) {
            total += supOrderTm.getTotal();
        }
        lblTotal.setText(String.valueOf(total));
    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void cmbIIDOnAction(ActionEvent actionEvent) {
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

    public void cmbSIDOnAction(ActionEvent actionEvent) {
        String sID = String.valueOf(cmbSID.getValue());
        try {
            Supplier supplier = SupplierModel.search(sID);
            fillSupplierFields(supplier);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillSupplierFields(Supplier supplier) {
        txtSupName.setText(supplier.getName());
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
