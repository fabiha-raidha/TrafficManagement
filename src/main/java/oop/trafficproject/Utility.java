package oop.trafficproject;

import javafx.scene.control.Alert;

public class Utility {
    public static void errorMessage01 (String message)  {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(message);
        a.setTitle("Error!.. ");
        a.showAndWait();
    }
    public static void successMessage01 (String message)  {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText(message);
        a.setTitle("Congrats..! ");
        a.showAndWait();
    }

}
