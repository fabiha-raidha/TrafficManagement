package oop.trafficproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class CitizenReportsEditOrCancelController
{
    @javafx.fxml.FXML
    private TableColumn tagCol_fxid;
    @javafx.fxml.FXML
    private TableView editOrCancelTabileView_fxid;
    @javafx.fxml.FXML
    private TableColumn reportStatusCol_fxid;
    @javafx.fxml.FXML
    private TableColumn reportIDCol_fxid;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void Edit_report_OnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void Cancel_report_OnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void BackToDashboardOnAction(ActionEvent actionEvent) {
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