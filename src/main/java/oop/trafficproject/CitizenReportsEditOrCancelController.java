package oop.trafficproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;

public class CitizenReportsEditOrCancelController
{
    @javafx.fxml.FXML
    private TableColumn<Report, String> tagCol_fxid;
    @javafx.fxml.FXML
    private TableView<Report> editOrCancelTabileView_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report, String> reportStatusCol_fxid;
    @javafx.fxml.FXML
    private TableColumn<Report, String> reportIDCol_fxid;

    @javafx.fxml.FXML
    public void initialize() {

        tagCol_fxid.setCellValueFactory(new PropertyValueFactory<>("message"));
        reportStatusCol_fxid.setCellValueFactory(new PropertyValueFactory<>("status"));
        reportIDCol_fxid.setCellValueFactory(new PropertyValueFactory<>("reportID"));


        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream("reports.bin");
            ois =new ObjectInputStream(fis);
            try {

                while (true){
                    Report report1 = (Report)ois.readObject();
                    //if (report1.getStatus().equals(comboboxfxid.getValue))
                    editOrCancelTabileView_fxid.getItems().add(report1);

                }

            }catch (EOFException e){}



        } catch (Exception e){}

    }

    @javafx.fxml.FXML
    public void Edit_report_OnAction(ActionEvent actionEvent) {
        FileOutputStream fos = null;
        ObjectOutputStream obs = null;
        try{
            // String reportID, String status,
            // String reportType, String location,
            // String citizenID, String message, LocalDate publicDate
            Report rep = new Report(null,"Pending", reportStatusCol_fxid.getText(), null,"2420829",null, LocalDate.now());
            File file = new File("reports.bin");
            boolean abc = file.exists() && file.length() > 0;
            fos = new FileOutputStream("reports.bin",abc);
            obs = abc? new Appendable(fos): new ObjectOutputStream(fos);
            obs.writeObject(rep);
            Utility.successMessage01("Edit Save");



        }catch (Exception e){}
    }

    @javafx.fxml.FXML
    public void Cancel_report_OnAction(ActionEvent actionEvent) {
        FileOutputStream fos = null;
        ObjectOutputStream obs = null;
        try{
            // String reportID, String status,
            // String reportType, String location,
            // String citizenID, String message, LocalDate publicDate
            Report rep = new Report(null,"Pending", reportStatusCol_fxid.getText(), null,"2420829",null, LocalDate.now());
            File file = new File("reports.bin");
            boolean abc = file.exists() && file.length() > 0;
            fos = new FileOutputStream("reports.bin",abc);
            obs = abc? new Appendable(fos): new ObjectOutputStream(fos);
            obs.writeObject(rep);
            Utility.successMessage01("Cancel Save");



        }catch (Exception e){}
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