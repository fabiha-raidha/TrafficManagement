package oop.trafficproject.DataAnalystController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ShareDataController {
    @FXML
    private Button sendBtn;
    @FXML
    private Button openShareModalBtn;
    @FXML
    private Label statusText;
    @FXML
    private TextField recipientsField;
    @FXML
    private TextArea messageArea;

    @FXML
    public void initialize() {
        // Set initial status
        statusText.setText("");

        // Set action for Open Share Modal button
        openShareModalBtn.setOnAction(event -> showShareModal());

        // Set action for Send button
        sendBtn.setOnAction(event -> handleSendAction());
    }

    private void showShareModal() {
        // Create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Share");
        alert.setHeaderText("Share Data Insights");
        alert.setContentText("Are you sure you want to share the data with the specified recipients?");

        // Customize the dialog stage
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getScene().getStylesheets().add("path/to/your/custom.css"); // Optional: Add custom styling

        // Show the dialog and wait for response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                statusText.setText("Modal confirmed. Ready to send.");
            }
        });
    }

    private void handleSendAction() {
        String recipients = recipientsField.getText().trim();
        String message = messageArea.getText().trim();

        if (recipients.isEmpty() || message.isEmpty()) {
            statusText.setText("Error: Please enter recipients and a message.");
            return;
        }

        // Simple validation for recipients (comma-separated emails)
        String[] recipientList = recipients.split(",");
        boolean valid = true;
        for (String recipient : recipientList) {
            String trimmedRecipient = recipient.trim();
            if (!trimmedRecipient.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                valid = false;
                break;
            }
        }

        if (!valid) {
            statusText.setText("Error: Invalid email format in recipients.");
            return;
        }

        // Simulate sending data
        System.out.println("Sending data to: " + recipients);
        System.out.println("Message: " + message);
        statusText.setText("Data sent successfully to " + recipients + "!");

        // Clear fields after successful send (optional)
        recipientsField.clear();
        messageArea.clear();
    }
}