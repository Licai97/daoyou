module com.example.daoyou4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.daoyou4 to javafx.fxml;
    exports com.example.daoyou4;
}