package oop.trafficproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

public class RoadMaintenanceOfficerAssignToCrewController
{
    @javafx.fxml.FXML
    private ComboBox<String> crewSelComboBox_fxid;
    @javafx.fxml.FXML
    private DatePicker startDateLocalDate_fxid;
    @javafx.fxml.FXML
    private TextArea instructionTextAreaFXID;

    @javafx.fxml.FXML
    public void initialize() {



        crewSelComboBox_fxid.getItems().addAll("Team ATS","Team BBS","Team CUTS");


    }

    @javafx.fxml.FXML
    public void BackToDashboardOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RoadMaintenanceOfficer-dashboard-01.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void Submit_AssignCrew_OnAction(ActionEvent actionEvent) {
        if (crewSelComboBox_fxid.getValue()==null){

            Utility.errorMessage01("Select A Crew From ComboBox");
            return;
        }
        if (startDateLocalDate_fxid.getValue()==null){

            Utility.errorMessage01("Select Today or Future Date");
            return;
        }
        if (instructionTextAreaFXID.getText().isEmpty()){

            Utility.errorMessage01("Provide a Short Instruction");
            return;
        }

        FileOutputStream fos = null;
        ObjectOutputStream obs = null;
        try{
            // String reportID, String status,
            // String reportType, String location,
            // String citizenID, String message, LocalDate publicDate
            Report rep = new Report(null,"Pending", crewSelComboBox_fxid.getValue(), null,"2420829",instructionTextAreaFXID.getText(), LocalDate.now());
            File file = new File("reports.bin");
            boolean abc = file.exists() && file.length() > 0;
            fos = new FileOutputStream("reports.bin",abc);
            obs = abc? new Appendable(fos): new ObjectOutputStream(fos);
            obs.writeObject(rep);
            Utility.successMessage01("Assigned Crew Successfully");



        }catch (Exception e){}


        }

    }
