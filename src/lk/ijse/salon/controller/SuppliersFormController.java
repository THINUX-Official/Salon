package lk.ijse.salon.controller;

/*
    @author THINUX
    @created 15-Nov-22
*/

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.salon.db.DBConnection;
import lk.ijse.salon.model.SupplierModel;
import lk.ijse.salon.to.Supplier;
import lk.ijse.salon.util.CrudUtil;
import lk.ijse.salon.util.Navigation;
import lk.ijse.salon.util.Routes;
import lk.ijse.salon.view.tm.SupplierTm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SuppliersFormController {

    public AnchorPane pane;
    public JFXTextField txtSID;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtSearch;
    public TableView<SupplierTm> tblSupplier;
    public TableColumn colSID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public Label lblSID;
    public Label lblName;
    public Label lblContact;

    private String searchText = "";

    String selectedID;

    private Matcher iDMatcher;
    private Matcher nameMatcher;
    private Matcher contactMatcher;

    public void initialize() {
        colSID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        tableView(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText=newValue;
            tableView(searchText);
        });

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                    selectedID = newValue.getId();
                System.out.println(newValue.getId());

                txtSID.setText(newValue.getId());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtContact.setText(newValue.getContact());
            }
        });

        setPattern();
    }

    public void setPattern(){

        Pattern userIdPattern = Pattern.compile("^(S0)([0-9]{1})([0-9]{1,})$");
        iDMatcher = userIdPattern.matcher(txtSID.getId());

        Pattern userNamePattern = Pattern.compile("^([a-zA-Z]{4,})$");
        nameMatcher = userNamePattern.matcher(txtName.getText());

        Pattern userContactPattern = Pattern.compile("^(?:7|0|(?:\\+94))[0-9]{9,10}$");
        contactMatcher = userContactPattern.matcher(txtContact.getText());
    }

    @FXML
    void orderOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.MANAGE_SUPPLIERS_ORDERS, pane);
    }

    private void tableView(String text){
        String searchText = "%" + text + "%";

        try {
            ObservableList<SupplierTm> tmList = FXCollections.observableArrayList();

            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * From supplier WHERE SID LIKE ? || name LIKE ? || address LIKE ? || contact LIKE ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,searchText);
            statement.setString(2,searchText);
            statement.setString(3,searchText);
            statement.setString(4,searchText);
            ResultSet set = statement.executeQuery();

            while (set.next()){
                SupplierTm supplierTm = new SupplierTm(set.getString(1),set.getString(2),set.getString(3),
                        set.getString(4));

                tmList.add(supplierTm);
            }

            tblSupplier.setItems(tmList);

        } catch (ClassNotFoundException | SQLException e)  {

        }
    }

    private void clearData(){
        txtSID.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
    }

    @FXML
    void addOnAction(ActionEvent event) {

        Supplier supplier = new Supplier(txtSID.getText(), txtName.getText(),
                txtAddress.getText(), txtContact.getText());
        try {
            boolean isAdded = SupplierModel.save(supplier);

            tableView(searchText);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added!").show();
                clearData();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            boolean isDeleted = CrudUtil.execute("DELETE FROM supplier where SID = ?", selectedID);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Deleted!").show();
                clearData();
                tableView(searchText);
            } else new Alert(Alert.AlertType.WARNING, "Something happened!").show();
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String sid  = txtSearch.getText();
        try {
            Supplier supplier = SupplierModel.search(sid);
            if (supplier != null) {
                fillData(supplier);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillData(Supplier supplier) {
        txtSID.setText(supplier.getSid());
        txtName.setText(supplier.getName());
        txtAddress.setText(supplier.getAddress());
        txtContact.setText(supplier.getContact());
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();

        boolean isUpdate = CrudUtil.execute("UPDATE supplier set name = ?, address = ?, contact = ? WHERE SID = ?",
                name, address, contact, selectedID);

        if (isUpdate) {
            tableView(searchText);
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated!").show();
            clearData();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
        }
    }

    public void txtSIDKeyTypeOnAction(KeyEvent keyEvent) {
        lblSID.setText("");

        Pattern userIdPattern = Pattern.compile("^(S0)([0-9]{1})([0-9]{1,})$");
        iDMatcher = userIdPattern.matcher(txtSID.getText());

        if (!iDMatcher.matches()) {
            txtSID.requestFocus();
            lblSID.setText("Invalid ID");
        }
    }

    public void txtNameKeyTypeOnAction(KeyEvent keyEvent) {
        lblName.setText("");

        Pattern userNamePattern = Pattern.compile("^([a-zA-Z]{4,})$");
        nameMatcher = userNamePattern.matcher(txtName.getText());

        if (!nameMatcher.matches()) {
            txtName.requestFocus();
            lblName.setText("Invalid Name");
        }
    }

    public void txtContactKeyTypeOnAction(KeyEvent keyEvent) {
        lblContact.setText("");

        Pattern userContactPattern = Pattern.compile("^(?:7|0|(?:\\+94))[0-9]{9,10}$");
        contactMatcher = userContactPattern.matcher(txtContact.getText());

        if (!contactMatcher.matches()) {
            txtContact.requestFocus();
            lblContact.setText("Invalid Number");
        }
    }
}



