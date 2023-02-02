package lk.ijse.salon.controller;

/*
    @author THINUX
    @created 15-Nov-22
*/

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.salon.model.AppointmentModel;
import lk.ijse.salon.model.CustomerModel;
import lk.ijse.salon.to.Appointment;
import lk.ijse.salon.to.Customer;
import lk.ijse.salon.view.tm.AppointmentTm;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class AppointmentFormController {

    public AnchorPane pane;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtSearch;
    public TableView<AppointmentTm> tblAppointment;
    public JFXTextField txtDate;
    public JFXTextField txtTime;
    public JFXTextField txtContact;
    public Label lblAid;
    public JFXComboBox cmbCID;
    public Label lblAddress;
    public Label lblContact;
    public TableColumn colAID;
    public TableColumn colCID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colDate;
    public TableColumn colTime;
   public static ObservableList obList = FXCollections.observableArrayList();

    private String searchText = "";

    String selectedID;

    public void initialize() throws SQLException, ClassNotFoundException {
        obList.clear();

        colAID.setCellValueFactory(new PropertyValueFactory<>("aid"));
        colCID.setCellValueFactory(new PropertyValueFactory<>("cid"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));


        ArrayList arrayList = AppointmentModel.getDetails();

        for (Object e:arrayList) {
            obList.add(e);
        }

        tblAppointment.setItems(obList);
        colAID.setSortType(TableColumn.SortType.DESCENDING);
        tblAppointment.getSortOrder().add(colAID);

        loadNextAID();
        loadCustomerIDs();

        java.sql.Date date= java.sql.Date.valueOf(LocalDate.now());
        txtDate.setText(String.valueOf(date));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        Time time = Time.valueOf(simpleDateFormat.format(new java.util.Date()));

        txtTime.setText(String.valueOf(time));
    }

    private void tableView(String text){
        /*String searchText = "%" + text + "%";

        try {
            ObservableList<AppointmentTm> tmList = FXCollections.observableArrayList();

            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * From appointment WHERE AID LIKE ? || aDate LIKE ? || aTime LIKE ? || CID LIKE ?";
            String sql2 = "SELECT * From customer WHERE CID LIKE ? || name LIKE ? || address LIKE ? || contact LIKE ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement.setString(1,searchText);
            statement2.setString(1,searchText);
            statement2.setString(2,searchText);
            statement2.setString(3,searchText);
            statement.setString(2,searchText);
            statement.setString(3,searchText);
            ResultSet set = statement.executeQuery();

            while (set.next()){
                AppointmentTm appointmentTm = new AppointmentTm(set.getString(1),set.getString(2),
                        set.getString(3), set.getString(4), set.getString(5),
                        set.getString(6), set.getString(7));

                tmList.add(appointmentTm);
            }

            tblAppointment.setItems(tmList);

        } catch (ClassNotFoundException | SQLException e)  {
            System.out.println(e);
        }*/
    }

    /*private void clearData(){
        txtName.clear();
        lblAddress.setText("");
        txtContact.clear();
    }*/

    @FXML
    void addOnAction(ActionEvent event) {
        String iid = lblAid.getText();
        String cid = String.valueOf(cmbCID.getSelectionModel().getSelectedItem());
        String name = txtName.getText();
        String address = lblAddress.getText();
        int contact = Integer.parseInt(lblContact.getText());
        java.sql.Date aDate = Date.valueOf(txtDate.getText());
        Time aTime = Time.valueOf(txtTime.getText());

        AppointmentTm appointmentTm = new AppointmentTm(iid, cid, name, address, contact, aDate, aTime);

        Appointment appointment = new Appointment(lblAid.getText(), Date.valueOf(txtDate.getText()),
                Time.valueOf(txtTime.getText()), String.valueOf(cmbCID.getSelectionModel().getSelectedItem()));

        try {
            boolean isAdded = AppointmentModel.save(appointment);

            tableView(searchText);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Appointment Added!").show();
//                clearData();
                obList.clear();
                initialize();

            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) {

    }

    @FXML
    void searchOnAction(ActionEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) {

    }

    private void loadCustomerIDs() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> idList = CustomerModel.loadCIDs();

            for (String cID : idList) {
                observableList.add(cID);
            }
            cmbCID.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadNextAID() {
        try {
            String aID = generateNextAID(AppointmentModel.generateCurrentAID());
            lblAid.setText(aID);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextAID(String generateCurrentAID) {
        if (generateCurrentAID != null) {
            String[] split = generateCurrentAID.split("A");
            int aID = Integer.parseInt(split[1]);
            aID += 1;
            if(aID>=10){
                return "A0"+aID;
            }
            return "A00" + aID;
        }
        return "A001";
    }

    public void cmbCIDOnAction(ActionEvent event) {
        String cID = String.valueOf(cmbCID.getValue());
        try {
            Customer customer = CustomerModel.search(cID);
            fillItemFields(customer);
            txtDate.requestFocus();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillItemFields(Customer customer) {
        txtName.setText(customer.getName());
        lblAddress.setText(customer.getAddress());
        lblContact.setText(customer.getContact());
    }
}
