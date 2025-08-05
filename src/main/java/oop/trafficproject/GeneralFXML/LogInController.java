package oop.trafficproject.GeneralFXML;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LogInController {

    @FXML
    private Label errorMessage;

    @FXML
    private TextField passwordTF;

    @FXML
    private TextField usernameTF;

    @FXML
    private Button cancelButton;


    @FXML
    public void onCancelButtonClicked(MouseEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    void onLogInButtonClicked(MouseEvent event) {

        if (!usernameTF.getText().isBlank() && !passwordTF.getText().isBlank()) {
            validateLogin();
        }

        else {
            errorMessage.setText("Please enter username and/ or password");

        }

    }

    public void validateLogin() {

    }

}
