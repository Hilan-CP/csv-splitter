module com.example.csv_spliter {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.csv_spliter to javafx.fxml;
    exports com.example.csv_spliter;
}
