package oop.trafficproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

public class CitizenReporterReportanIssueController
{
    @javafx.fxml.FXML
    private ComboBox<String> locationSelectComboBox;
    @javafx.fxml.FXML
    private TextArea shortDescriptionTextAreaFXID;
    @javafx.fxml.FXML
    private ComboBox<String> problemSelectComboBox;

    @javafx.fxml.FXML
    public void initialize() {

        locationSelectComboBox.getItems().addAll("Gulshan", "Banani", "Uttara", "Mirpur", "Mohakhali", "Farmgate", "Bashundhara", "Tejgaon", "Shahbagh", "Badda");
        problemSelectComboBox.getItems().addAll("Road Damage / Potholes", "Street Light Not Working", "Illegal Parking", "Public Transport Shortage", "Accident Spot Reporting", "Illegal Construction", "Tree Obstruction", "Broken Footpath");


    }

    @Deprecated
    public void BackToDashboardOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void Submit_report_OnAction(ActionEvent actionEvent) {

        if (problemSelectComboBox.getValue()==null){

            Utility.errorMessage01("Select A Value From Problem ComboBox");
            return;
        }
        if (locationSelectComboBox.getValue()==null){

            Utility.errorMessage01("Select A Value From Location ComboBox");
            return;
        }
        if (shortDescriptionTextAreaFXID.getText().isEmpty()){

            Utility.errorMessage01("Provide a Short Description");
            return;
        }

        FileOutputStream fos = null;
        ObjectOutputStream obs = null;
        try{
            // String reportID, String status,
            // String reportType, String location,
            // String citizenID, String message, LocalDate publicDate
            Report rep = new Report(null,"Pending", problemSelectComboBox.getValue(), locationSelectComboBox.getValue(),"2420829",shortDescriptionTextAreaFXID.getText(), LocalDate.now());
            File file = new File("reports.bin");
            boolean abc = file.exists() && file.length() > 0;
            fos = new FileOutputStream("reports.bin",abc);
            obs = abc? new Appendable(fos): new ObjectOutputStream(fos);
            obs.writeObject(rep);
            Utility.successMessage01("Report Created Successfully To The DataBase");



        }catch (Exception e){}

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