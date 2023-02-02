package lk.ijse.salon.controller;

/*
    @author THINUX
    @created 16-Nov-22
*/

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.salon.model.CustomerModel;
import lk.ijse.salon.model.PaymentModel;
import lk.ijse.salon.to.Payment;
import lk.ijse.salon.view.tm.PaymentTm;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentFormController {
    public AnchorPane pane;
    public JFXTextField txtDesc;
    public JFXTextField txtDate;
    public JFXTextField txtCost;
    public JFXTextField txtSearch;
    public TableView<PaymentTm> tblPayment;
    public TableColumn colPID;
    public TableColumn colDate;
    public TableColumn colDesc;
    public TableColumn colCost;
    public TableColumn colSID;
    public Label lblPID;
    public TableColumn colName;
    public Label txtName;
    public JFXComboBox cmbSID;

    public static ObservableList obList = FXCollections.observableArrayList();
    public Label lblCost;

    private String searchText = "";

    String selectedID;

    private Matcher amountMatcher;


    public void initialize() throws SQLException, ClassNotFoundException {
        obList.clear();

        colPID.setCellValueFactory(new PropertyValueFactory<>("pid"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        ArrayList arrayList = PaymentModel.getDetails();

        for (Object e:arrayList) {
            obList.add(e);
        }

        tblPayment.setItems(obList);
        colPID.setSortType(TableColumn.SortType.DESCENDING);
        tblPayment.getSortOrder().add(colPID);

        loadNextPID();

        java.sql.Date date= java.sql.Date.valueOf(LocalDate.now());
        txtDate.setText(String.valueOf(date));

        searchPart();

        tblPayment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedID = newValue.getPid();

                System.out.println(newValue.getPid());

                lblPID.setText(newValue.getPid());
                txtDate.setText(String.valueOf(newValue.getDate()));
                txtDesc.setText(newValue.getDesc());
                txtCost.setText(String.valueOf(newValue.getCost()));
            }
        });

        setPattern();
    }

    public void setPattern(){

        Pattern amountPattern = Pattern.compile("^[0-9]{1,}$");
        amountMatcher = amountPattern.matcher(txtCost.getText());
    }

    private void clearData() {
        txtDesc.clear();
        txtCost.clear();
    }

    public void addOnAction(ActionEvent actionEvent) {
//        String pid = lblPID.getText();
//        java.sql.Date date = Date.valueOf(txtDate.getText());
//        String desc = txtDesc.getText();
//        Double cost = Double.parseDouble(txtCost.getText());
//
//        PaymentTm paymentTm = new PaymentTm(pid, date, desc, cost);

        Payment payment = new Payment(lblPID.getText(), txtDate.getText(),txtDesc.getText(), Double.parseDouble(txtCost.getText()));

        System.out.println(txtDate.getText()+txtDesc.getText() +Double.parseDouble(txtCost.getText()));
        try {
            boolean isAdded = PaymentModel.save(payment);

//            tableView(searchText);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Appointment Added!").show();
                obList.clear();
                initialize();

            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            boolean isDeleted = CustomerModel.delete(selectedID);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Deleted!").show();
                clearData();
                initialize();

            } else new Alert(Alert.AlertType.WARNING, "Something happened!").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Payment payment = new Payment(lblPID.getId(),txtDate.getText(), txtDesc.getText(), Double.valueOf(txtCost.getText()));

        boolean isUpdate = PaymentModel.update(payment);

        if (isUpdate) {

//            tableView(searchText);

            new Alert(Alert.AlertType.CONFIRMATION, "Payment Updated!").show();
            clearData();
            initialize();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
        }
    }

    public void searchOnAction(ActionEvent actionEvent) {
    }

    private void loadNextPID() {
        try {
            String pID = PaymentModel.generateNextPID();
            lblPID.setText(pID);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void payOnAction(ActionEvent actionEvent) {
    }

    private void searchPart() {
        FilteredList<PaymentTm> filteredList = new FilteredList(obList, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(paymentTm -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (paymentTm.getPid().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (paymentTm.getDesc().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(paymentTm.getDate()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(paymentTm.getCost()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<PaymentTm> sortedList = new SortedList(filteredList);
        sortedList.comparatorProperty().bind(tblPayment.comparatorProperty());
        tblPayment.setItems(sortedList);
    }

    public void txtCostKeyTypeOnAction(KeyEvent keyEvent) {
        lblCost.setText("");

        Pattern amountPattern = Pattern.compile("^[0-9]{1,}$");
        amountMatcher = amountPattern.matcher(txtCost.getText());

        if (!amountMatcher.matches()) {
            txtCost.requestFocus();
            lblCost.setText("Invalid Input");
        }
    }
}
