package lk.ijse.salon.controller;

/*
    @author THINUX
    @created 15-Nov-22
*/

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.salon.model.CustomerModel;
import lk.ijse.salon.to.Customer;
import lk.ijse.salon.view.tm.CustomerTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerFormController {

    public AnchorPane pane;
    public JFXTextField txtCID;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtSearch;
    public TableView<CustomerTm> tblCustomer;
    public TableColumn colCID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public Label lblCID;
    public Label lblName;
    public Label lblContact;

    private String searchText = "";

    String selectedID;

    private Matcher iDMatcher;
    private Matcher nameMatcher;
    private Matcher contactMatcher;

    public static ObservableList obList = FXCollections.observableArrayList();

    public void initialize() throws SQLException, ClassNotFoundException {
        obList.clear();
        colCID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        ArrayList arrayList = CustomerModel.getDetails();

        for (Object e:arrayList) {
            obList.add(e);
        }

//        tableView(searchText);

        searchPart();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedID = newValue.getId();

                System.out.println(newValue.getId());

                txtCID.setText(newValue.getId());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtContact.setText(newValue.getContact());
            }
        });

        setPattern();
    }

    public void setPattern(){

        Pattern userIdPattern = Pattern.compile("^(C0)([0-9]{1})([0-9]{1,})$");
        iDMatcher = userIdPattern.matcher(txtCID.getId());

        Pattern userNamePattern = Pattern.compile("^([a-zA-Z]{4,})$");
        nameMatcher = userNamePattern.matcher(txtName.getText());

        Pattern userContactPattern = Pattern.compile("^(?:7|0|(?:\\+94))[0-9]{9,10}$");
        contactMatcher = userContactPattern.matcher(txtContact.getText());
    }

   /*
   private void tableView(String text) {
        String searchText = "%" + text + "%";

        try {

            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * From customer WHERE CID LIKE ? || name LIKE ? || address LIKE ? || contact LIKE ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, searchText);
            statement.setString(2, searchText);
            statement.setString(3, searchText);
            statement.setString(4, searchText);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                CustomerTm customerTm = new CustomerTm(set.getString(1), set.getString(2),
                        set.getString(3), set.getString(4));

                tmList.add(customerTm);
            }

            tblCustomer.setItems(tmList);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }
    */

    private void clearData() {
        txtCID.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
    }

    @FXML
    void addOnAction(ActionEvent event) {
        Customer customer = new Customer(txtCID.getText(), txtName.getText(),
                txtAddress.getText(), txtContact.getText());
        try {
            boolean isAdded = CustomerModel.save(customer);

//            tableView(searchText);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added!").show();
                clearData();
                initialize();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        /*setPattern();
        if(iDMatcher.matches()) {
            if (nameMatcher.matches()){
                if(contactMatcher.matches()) {

                } else {
                    txtContact.requestFocus();
                    lblContact.setText("Invalid Contact ");
                }
            }else{
                txtName.requestFocus();
                lblName.setText("Invalid Name ");
            }
        } else {
            txtCID.requestFocus();
            lblCID.setText("Invalid ID ");
        }*/
    }

    @FXML
    void deleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            boolean isDeleted = CustomerModel.delete(selectedID);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted!").show();
                clearData();
                initialize();

//                tableView(searchText);

            } else new Alert(Alert.AlertType.WARNING, "Something happened!").show();
        }
    }

    /*private void fillData(Customer customer) {
        txtCID.setText(customer.getCid());
        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
        txtContact.setText(customer.getContact());
    }*/

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        Customer customer = new Customer(txtCID.getText(), txtName.getText(),
                txtAddress.getText(), txtContact.getText());

        boolean isUpdate = CustomerModel.update(customer);

        if (isUpdate) {

//            tableView(searchText);

            new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated!").show();
            clearData();
            initialize();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
        }
    }

    private void searchPart() {
        FilteredList<CustomerTm> filteredList = new FilteredList(obList, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(customerTm -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (customerTm.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (customerTm.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(customerTm.getAddress()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(customerTm.getContact()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<CustomerTm> sortedList = new SortedList(filteredList);
        sortedList.comparatorProperty().bind(tblCustomer.comparatorProperty());
        tblCustomer.setItems(sortedList);
    }

    public void txtCIDKeyTypeOnAction(KeyEvent keyEvent) {
        lblCID.setText("");

        Pattern userIdPattern = Pattern.compile("^(C0)([0-9]{1})([0-9]{1,})$");
        iDMatcher = userIdPattern.matcher(txtCID.getText());

        if (!iDMatcher.matches()) {
            txtCID.requestFocus();
            lblCID.setText("Invalid ID");
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
