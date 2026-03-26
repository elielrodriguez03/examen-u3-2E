module com.example.prestamosextensiones {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.prestamosextensiones to javafx.fxml;
    opens com.example.prestamosextensiones.controller to javafx.fxml;
    opens com.example.prestamosextensiones.model to javafx.base;

    exports com.example.prestamosextensiones;
    exports com.example.prestamosextensiones.controller;
    exports com.example.prestamosextensiones.model;
}
