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
import lk.ijse.salon.model.ItemModel;
import lk.ijse.salon.to.Item;
import lk.ijse.salon.util.CrudUtil;
import lk.ijse.salon.view.tm.ItemTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProductFormController {

    public AnchorPane pane;
    public JFXTextField txtIID;
    public JFXTextField txtDesc;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtSearch;
    public TableView<ItemTm> tblProduct;
    public TableColumn colIID;
    public TableColumn colDesc;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public Label lblIID;
    public Label lblUnitPrice;
    public Label lblQtyOnHand;

    private String searchText = "";

    String selectedID;

    public void initialize() {
        colIID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        tableView(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText=newValue;
            tableView(searchText);
        });

        tblProduct.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedID = newValue.getId();
                System.out.println(newValue.getId());

                txtIID.setText(newValue.getId());
                txtDesc.setText(newValue.getDesc());
                txtUnitPrice.setText(newValue.getUnitPrice());
                txtQtyOnHand.setText(newValue.getQtyOnHand());
            }
        });
    }

    private void tableView(String text){
        String searchText = "%" + text + "%";

        try {
            ObservableList<ItemTm> tmList = FXCollections.observableArrayList();

            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * From item WHERE IID LIKE ? || description LIKE ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,searchText);
            statement.setString(2,searchText);
            ResultSet set = statement.executeQuery();

            while (set.next()){
                ItemTm itemTm = new ItemTm(set.getString(1),set.getString(2),
                        set.getString(3), set.getString(4));

                tmList.add(itemTm);
            }

            tblProduct.setItems(tmList);

        } catch (ClassNotFoundException | SQLException e)  {
            System.out.println(e);
        }
    }

    private void clearData(){
        txtIID.clear();
        txtDesc.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
    }

    @FXML
    void addOnAction(ActionEvent event) {
        String iid = txtIID.getText();
        String desc = txtDesc.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        Item item = new Item(iid, desc, unitPrice, qtyOnHand);
        try {
            boolean isAdded = ItemModel.save(item);

            tableView(searchText);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Added!").show();
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
            boolean isDeleted = CrudUtil.execute("DELETE FROM item where IID = ?", selectedID);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Product Deleted!").show();
                clearData();
                tableView(searchText);
            } else new Alert(Alert.AlertType.WARNING, "Something happened!").show();
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String iid = txtSearch.getText();
        try {
            Item item = ItemModel.search(iid);
            if (item != null) {
                fillData(item);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillData(Item item) {
        txtIID.setText(item.getIid());
        txtDesc.setText(item.getDesc());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String desc = txtDesc.getText();
        String unitPrice = txtUnitPrice.getText();
        String qtyOnHand = txtQtyOnHand.getText();

        boolean isUpdate = CrudUtil.execute("UPDATE item set description = ?, unitPrice = ?, qtyOnHand = ? WHERE IID = ?",
                desc, unitPrice, qtyOnHand, selectedID);

        if (isUpdate) {
            tableView(searchText);
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated!").show();
            clearData();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
        }
    }

    public void txtIIDKeyTypeOnAction(KeyEvent keyEvent) {
    }

    public void txtUnitPriceKeyTypeOnAction(KeyEvent keyEvent) {
    }

    public void txtQtyOnHandKeyTypeOnAction(KeyEvent keyEvent) {
    }
}
