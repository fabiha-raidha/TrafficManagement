package oop.trafficproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;

public class CitizenReportGiveFeedbackController
{
    @javafx.fxml.FXML
    private TableView<GiveFeedBack> giveFeedBackTableView_fxid;
    @javafx.fxml.FXML
    private ComboBox<String> feedBackTypeComboBox_fxid;
    @javafx.fxml.FXML
    private TableColumn<GiveFeedBack, String> reportIDCol_fxid;
    @javafx.fxml.FXML
    private TextField feedbackMessage_Lable_fxid;
    @javafx.fxml.FXML
    private TableColumn<GiveFeedBack, String> messageCol_fxid;
    @javafx.fxml.FXML
    private TableColumn<GiveFeedBack, String> reportStatusCol_fxid;

    @javafx.fxml.FXML
    public void initialize() {



        reportIDCol_fxid.setCellValueFactory(new PropertyValueFactory<>("reportID"));
        messageCol_fxid.setCellValueFactory(new PropertyValueFactory<>("feedBackMessage"));
        reportStatusCol_fxid.setCellValueFactory(new PropertyValueFactory<>("reportStatus"));

        feedBackTypeComboBox_fxid.getItems().addAll("Positive","Negative","Suggestion");




        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream("giveFeedBack.bin");
            ois =new ObjectInputStream(fis);
            try {
                giveFeedBackTableView_fxid.getItems().clear();

                while (true){
                    GiveFeedBack report1 = (GiveFeedBack) ois.readObject();
                    //if (report1.getStatus().equals(comboboxfxid.getValue))
                    giveFeedBackTableView_fxid.getItems().add(report1);

                }

            }catch (EOFException e){}



        } catch (Exception e){}


    }

    @Deprecated
    public void BackToDashboardOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void Feedback_OnAction(ActionEvent actionEvent) {

        if (feedBackTypeComboBox_fxid.getValue()==null) {

            Utility.errorMessage01("Select A Type From FeedBack ComboBox");
            return;
        }
        if (feedbackMessage_Lable_fxid.getText().isEmpty()){

            Utility.errorMessage01("Provide a Short Description");
            return;
        }

        FileOutputStream fos = null;
        ObjectOutputStream obs = null;
        try{
            // String reportID, String reportStatus, String feedBackType,
            // String citizenID, String feedBackMessage, LocalDate submitDate
            GiveFeedBack gfeed = new GiveFeedBack(null,"Pending", feedBackTypeComboBox_fxid.getValue(), "2420829",feedbackMessage_Lable_fxid.getText(), LocalDate.now());
            File file = new File("giveFeedBack.bin");
            boolean feed1 = file.exists() && file.length() > 0;
            fos = new FileOutputStream("giveFeedBack.bin",feed1);
            obs = feed1? new Appendable(fos): new ObjectOutputStream(fos);
            obs.writeObject(gfeed);
            Utility.successMessage01("Your FeedBack Submitted");



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