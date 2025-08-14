package oop.trafficproject;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileOutputStream;
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

        locationSelectComboBox.getItems().addAll("Dhaka","Budda","GulShan");
        problemSelectComboBox.getItems().addAll("Broken Road", "PatHole", "Road Light Fused");


    }

    @javafx.fxml.FXML
    public void BackToDashboardOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void Submit_report_OnAction(ActionEvent actionEvent) {

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



        }catch (Exception e){}

    }
}