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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.salon.db.DBConnection;
import lk.ijse.salon.model.ServiceModel;
import lk.ijse.salon.to.Service;
import lk.ijse.salon.util.CrudUtil;
import lk.ijse.salon.view.tm.ServiceTm;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServicesFormController implements Initializable {

    public AnchorPane pane;
    public TableColumn colSerID;
    public TableColumn colName;
    public TableColumn colType;
    public TableColumn colPrice;
    public JFXTextField txtSerID;
    public JFXTextField txtName;
    public JFXTextField txtType;
    public JFXTextField txtPrice;
    public JFXTextField txtSearch;
    public TableView<ServiceTm> tblServices;
    public Label lblSerID;
    public Label lblName;
    public Label lblPrice;
    public Label lblType;

    private String searchText = "";

    String selectedID;

    private Matcher iDMatcher;
    private Matcher nameMatcher;
    private Matcher typeMatcher;
    private Matcher amountMatcher;

    public void initialize(URL location, ResourceBundle resources) {
        colSerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText=newValue;
            tableView(searchText);
        });

        tblServices.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedID = newValue.getId();
                System.out.println(newValue.getId());

                txtSerID.setText(newValue.getId());
                txtName.setText(newValue.getName());
                txtType.setText(newValue.getType());
                txtPrice.setText(newValue.getPrice());
            }
        });

        setPattern();
    }

    public void setPattern(){

        Pattern userIdPattern = Pattern.compile("^(SE0)([0-9]{0})([0-9]{1,})$");
        iDMatcher = userIdPattern.matcher(txtSerID.getId());

        Pattern userNamePattern = Pattern.compile("^([a-zA-Z]{4,})$");
        nameMatcher = userNamePattern.matcher(txtName.getText());
        typeMatcher = userNamePattern.matcher(txtType.getText());

        Pattern amountPattern = Pattern.compile("^[0-9]{1,}$");
        amountMatcher = amountPattern.matcher(txtPrice.getText());
    }

    private void tableView(String text){
        String searchText = "%" + text + "%";

        try {
            ObservableList<ServiceTm> tmList = FXCollections.observableArrayList();

            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM service WHERE serID LIKE ? || name LIKE ? || types LIKE ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,searchText);
            statement.setString(2,searchText);
            statement.setString(3,searchText);
            ResultSet set = statement.executeQuery();

            while (set.next()){
                ServiceTm serviceTm = new ServiceTm(set.getString(1),set.getString(2),
                        set.getString(3), set.getString(4));

                tmList.add(serviceTm);
            }

            tblServices.setItems(tmList);

        } catch (ClassNotFoundException | SQLException e)  {
            System.out.println(e);
        }
    }

    private void clearData(){
        txtSerID.clear();
        txtName.clear();
        txtType.clear();
        txtPrice.clear();
    }

    @FXML
    void addOnAction(ActionEvent event) {
        String serID = txtSerID.getText();
        String name = txtName.getText();
        String type = txtType.getText();
        double price = Double.parseDouble(txtPrice.getText());

        Service service = new Service (serID, name, type, price);
        try {
            boolean isAdded = ServiceModel.save(service);

            tableView(searchText);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Service Added!").show();
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
            boolean isDeleted = CrudUtil.execute("DELETE FROM service where SerID = ?", selectedID);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Service Deleted!").show();
                clearData();
                tableView(searchText);
            } else new Alert(Alert.AlertType.WARNING, "Something happened!").show();
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String serID = txtSearch.getText();
        try {
            Service service = ServiceModel.search(serID);
            if (service != null) {
                fillData(service);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillData(Service service) {
        txtSerID.setText(service.getSerID());
        txtName.setText(service.getName());
        txtType.setText(service.getType());
        txtPrice.setText(String.valueOf(service.getPrice()));
    }

    @FXML
    void txtSearchOnAction(MouseEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String name = txtName.getText();
        String type = txtType.getText();
        String price = txtPrice.getText();

        boolean isUpdate = CrudUtil.execute("UPDATE service set name = ?, types = ?, price = ? WHERE serID = ?",
                name, type, price, selectedID);

        if (isUpdate) {
            tableView(searchText);
            new Alert(Alert.AlertType.CONFIRMATION, "Service Updated!").show();
            clearData();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
        }
    }

    public void txtSearchOnAction(javafx.scene.input.MouseEvent mouseEvent) {

    }

    public void txtSerIDKeyTypeOnAction(KeyEvent keyEvent) {
        lblSerID.setText("");

        Pattern userIdPattern = Pattern.compile("^(SE0)([0-9]{0})([0-9]{1,})$");
        iDMatcher = userIdPattern.matcher(txtSerID.getText());

        if (!iDMatcher.matches()) {
            txtSerID.requestFocus();
            lblSerID.setText("Invalid ID");
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

    public void txtTypeKeyTypeOnAction(KeyEvent keyEvent) {
        lblType.setText("");

        Pattern userNamePattern = Pattern.compile("^([a-zA-Z]{4,})$");
        nameMatcher = userNamePattern.matcher(txtType.getText());

        if (!nameMatcher.matches()) {
            txtType.requestFocus();
            lblType.setText("Invalid Name");
        }
    }

    public void txtPriceKeyTypeOnAction(KeyEvent keyEvent) {
        lblPrice.setText("");

        Pattern amountPattern = Pattern.compile("^[0-9]{1,}$");
        amountMatcher = amountPattern.matcher(txtPrice.getText());

        if (!amountMatcher.matches()) {
            txtPrice.requestFocus();
            lblPrice.setText("Invalid Input");
        }
    }
}
