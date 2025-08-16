package oop.trafficproject.GeneralFXML;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import oop.trafficproject.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class LogInController {

    @FXML
    private Label errorMessage;

    @FXML
    private TextField passwordTF;

    @FXML
    private TextField usernameTF;

    @FXML
    private Button cancelButton;

    private List<User> usersList;

    private static final String DATA_FILE_PATH = "data/user.bin";

    public void initialize() {
        errorMessage.setText(" ");
        loadUsersFromBin();
    }

    @SuppressWarnings("unchecked")
    private void loadUsersFromBin() {
        File file = new File(DATA_FILE_PATH);
        if (!file.exists()) {
            System.out.println("No user data file found, starting with empty list.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();

            if (obj instanceof List<?>) {
                usersList = (List<User>) obj;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            errorMessage.setText("Unable to load saved users");
        }
    }

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

    private void validateLogin() {
        String username = usernameTF.getText().trim();
        String password = passwordTF.getText();

        if (usersList.isEmpty()) {
            errorMessage.setText("No users found in system");
            return;
        }

        for (User user : usersList) {
            if (user.getEmail().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                loadDashboard(user);
                return;
            }
        }
        errorMessage.setText("Invalid username or password");
    }

    private void loadDashboard(User user) {
        try {
            String fxmlPath;
            switch (user.getDesignation().toLowerCase()) {
                case "administrative officer":
                    fxmlPath = "/oop/trafficproject/AdministrativeOfficerFXML.fxml";
                    break;
                case "it officer":
                    fxmlPath = "oop/trafficproject/itofficer/dashboard.fxml";
                    break;
                default:
                    errorMessage.setText("Unknown role: " + user.getDesignation());
                    return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) usernameTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorMessage.setText("Error loading dashboard");
        }
    }
}
