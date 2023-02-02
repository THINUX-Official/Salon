package lk.ijse.salon.controller;

/*
    @author THINUX
    @created 15-Nov-22
*/

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import lk.ijse.salon.util.Navigation;
import lk.ijse.salon.util.Routes;

import java.io.IOException;

public class LoginFormController {

    public static int status;
    public AnchorPane loginPane;
    public AnchorPane pane;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXPasswordField txtPassword;

    public void initialize()  {

    }

    @FXML
    void exitOnAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void loginOnAction(ActionEvent event) throws IOException {
        loginMethod();
    }

    private void loginMethod() throws IOException {
        if (txtUsername.getText().equals("1") && txtPassword.getText().equals("1")) {
            status = 1;
            Navigation.navigate(Routes.ADMIN_MENU, loginPane);
        } else if (txtUsername.getText().equals("2") && txtPassword.getText().equals("2")) {
            status = 2;
            Navigation.navigate(Routes.RECEPTION_MENU, loginPane);
        } else {
            new Alert(Alert.AlertType.WARNING, "Incorrect username or password!").show();
        }
    }

    public void loginEnterOnAction(ActionEvent actionEvent) throws IOException {
        loginMethod();
    }


}
