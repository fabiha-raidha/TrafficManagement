module oop.trafficproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens oop.trafficproject to javafx.fxml;
    exports oop.trafficproject;
}