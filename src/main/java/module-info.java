module com.example.csv_spliter {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;

    opens com.example.csv_splitter to javafx.fxml;
    exports com.example.csv_splitter;
}
