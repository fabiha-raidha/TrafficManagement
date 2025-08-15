package oop.trafficproject;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

public class RoadMaintenanceOfficerAssignToCrewController
{
    @javafx.fxml.FXML
    private ComboBox crewSelComboBox_fxid;
    @javafx.fxml.FXML
    private DatePicker startDateLocalDate_fxid;
    @javafx.fxml.FXML
    private TextArea instructionTextAreaFXID;

    @javafx.fxml.FXML
    public void initialize() {
        boolean nac = true;

    }

    @javafx.fxml.FXML
    public void BackToDashboardOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void Submit_AssignCrew_OnAction(ActionEvent actionEvent) {
        boolean nac = true;
        if (nac) {
            Utility.errorMessage01("Not Working");


        }

    }
}