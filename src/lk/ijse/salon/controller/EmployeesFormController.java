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
import lk.ijse.salon.model.EmployeeModel;
import lk.ijse.salon.to.Employee;
import lk.ijse.salon.util.CrudUtil;
import lk.ijse.salon.util.Navigation;
import lk.ijse.salon.util.Routes;
import lk.ijse.salon.view.tm.EmployeeTm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeesFormController {

    public AnchorPane pane;
    public JFXTextField txtEID;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtSearch;
    public TableView <EmployeeTm> tblEmployee;
    public TableColumn colEID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public Label lblEID;
    public Label lblName;
    public Label lblContact;

    private String searchText = "";

    String selectedID;

    private Matcher eIDMatcher;
    private Matcher eNameMatcher;
    private Matcher eContactMatcher;
    private Matcher emAddressMatcher;
    private Matcher emNicMatcher;
    private Matcher emPositionMatcher;

    public void salaryOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.MANAGE_SALARY, pane);
    }



    public void initialize() {
        colEID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        tableView(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText=newValue;
            tableView(searchText);
        });

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedID = newValue.getId();
                System.out.println(newValue.getId());

                txtEID.setText(newValue.getId());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtContact.setText(newValue.getContact());
            }
        });

        setPattern();
    }

    public void setPattern(){

        Pattern userIdPattern = Pattern.compile("^(E0)([0-9]{1})([0-9]{1,})$");
        eIDMatcher = userIdPattern.matcher(txtEID.getId());

        Pattern userNamePattern = Pattern.compile("^([a-zA-Z]{4,})$");
        eNameMatcher = userNamePattern.matcher(txtName.getText());

        Pattern userContactPattern = Pattern.compile("^(?:7|0|(?:\\+94))[0-9]{9,10}$");
        eContactMatcher = userContactPattern.matcher(txtContact.getText());
    }

    private void tableView(String text){
        String searchText = "%" + text + "%";

        try {
            ObservableList<EmployeeTm> tmList = FXCollections.observableArrayList();

            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * From employee WHERE EID LIKE ? || name LIKE ? || address LIKE ? || contact LIKE ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,searchText);
            statement.setString(2,searchText);
            statement.setString(3,searchText);
            statement.setString(4,searchText);
            ResultSet set = statement.executeQuery();

            while (set.next()){
                EmployeeTm employeeTm = new EmployeeTm(set.getString(1),set.getString(2),
                        set.getString(3), set.getString(4));

                tmList.add(employeeTm);
            }

            tblEmployee.setItems(tmList);

        } catch (ClassNotFoundException | SQLException e)  {
            System.out.println(e);
        }
    }

    private void clearData(){
        txtEID.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
    }
    
    @FXML
    void addOnAction(ActionEvent event) {
        String eID = txtEID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();

        Employee employee = new Employee(eID, name, address, contact);

        setPattern();
        if(eIDMatcher.matches()) {
            if (eNameMatcher.matches()){
                if(eContactMatcher.matches()) {

                } else {
                    txtContact.requestFocus();
                    lblContact.setText("Invalid Contact ");
                }
            }else{
                txtName.requestFocus();
                lblName.setText("Invalid Name ");
            }
        } else {
            txtEID.requestFocus();
            lblEID.setText("Invalid ID ");
        }

        try {
            boolean isAdded = EmployeeModel.save(employee);

            tableView(searchText);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Added!").show();
                clearData();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            boolean isDeleted = CrudUtil.execute("DELETE FROM employee where EID = ?", selectedID);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Service Deleted!").show();
                clearData();
                tableView(searchText);
            } else new Alert(Alert.AlertType.WARNING, "Something happened!").show();
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String eID = txtSearch.getText();
        try {
            Employee employee = EmployeeModel.search(eID);
            if (employee != null) {
                fillData(employee);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillData(Employee employee) {
        txtEID.setText(employee.getEID());
        txtName.setText(employee.getName());
        txtAddress.setText(employee.getAddress());
        txtContact.setText(employee.getContact());
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();

        boolean isUpdate = CrudUtil.execute("UPDATE employee set name = ?, address = ?, contact = ? WHERE EID = ?",
                name, address, contact, selectedID);

        if (isUpdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "Employee Updated!").show();
            clearData();
            tableView(searchText);
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
        }
    }

    public void txtEIDKeyTypeOnAction(KeyEvent keyEvent) {
        lblEID.setText("");

        Pattern userIdPattern = Pattern.compile("^(E0)([0-9]{1})([0-9]{1,})$");
        eIDMatcher = userIdPattern.matcher(txtEID.getText());

        if (!eIDMatcher.matches()) {
            txtEID.requestFocus();
            lblEID.setText("Invalid ID");
        }
    }

    public void txtNameKeyTypeOnAction(KeyEvent keyEvent) {
        lblName.setText("");

        Pattern userNamePattern = Pattern.compile("^([a-zA-Z]{4,})$");
        eNameMatcher = userNamePattern.matcher(txtName.getText());

        if (!eNameMatcher.matches()) {
            txtName.requestFocus();
            lblName.setText("Invalid Name");
        }
    }

    public void txtContactKeyTypeOnAction(KeyEvent keyEvent) {
        lblContact.setText("");

        Pattern userContactPattern = Pattern.compile("^(?:7|0|(?:\\+94))[0-9]{9,10}$");
        eContactMatcher = userContactPattern.matcher(txtContact.getText());

        if (!eContactMatcher.matches()) {
            txtContact.requestFocus();
            lblContact.setText("Invalid Number");
        }
    }
}
