module com.max1maka {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;
    requires Figure2;

    opens com.max1maka to javafx.fxml;
    exports com.max1maka;

    opens com.max1maka.figures;
    exports com.max1maka.figures;
}



