package oop.trafficproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CitizenReportGiveFeedbackController
{
    @javafx.fxml.FXML
    private TableColumn tagCol_fxid;
    @javafx.fxml.FXML
    private TableView giveFeedBackTableView_fxid;
    @javafx.fxml.FXML
    private TableColumn reportStatus_fxid;
    @javafx.fxml.FXML
    private ComboBox feedBackTypeComboBox_fxid;
    @javafx.fxml.FXML
    private TableColumn reportIDCol_fxid;
    @javafx.fxml.FXML
    private TextField feedbackMessage_Lable_fxid;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @Deprecated
    public void BackToDashboardOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void Feedback_OnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void BackToCitizenDashboardOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CitizenReporter-dashboard-01.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}