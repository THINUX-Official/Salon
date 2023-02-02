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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.salon.model.EmployeeModel;
import lk.ijse.salon.to.Employee;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SalaryFormController implements Initializable {

    public JFXTextField txtCost;
    public JFXTextField txtSearch;
    public TableView tableSalary;
    public TableColumn colEID;
    public TableColumn colName;
    public TableColumn colDate;
    public TableColumn colCost;
    public Label txtName;
    public JFXComboBox cmbEID;
    public JFXTextField txtDate;
    public JFXTextField txtEmpName;
    private AnchorPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadEIDs();
        java.sql.Date date= java.sql.Date.valueOf(LocalDate.now());
        txtDate.setText(String.valueOf(date));
    }

    private void loadEIDs() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> eIDList = EmployeeModel.loadCIDs();

            for (String eID : eIDList) {
                observableList.add(eID);
            }
            cmbEID.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void payOnAction(ActionEvent actionEvent) {
    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void cmbEIDOnAction(ActionEvent actionEvent) {
        String eID = String.valueOf(cmbEID.getValue());
        try {
            Employee employee = EmployeeModel.search(eID);
            fillEmployeeFields(employee);
            txtCost.requestFocus();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillEmployeeFields(Employee employee) {
        txtEmpName.setText(employee.getName());
    }
}
