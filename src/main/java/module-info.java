module comp3111.examsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires java.rmi;
    requires org.testfx;
    requires java.management;

    opens comp3111.examsystem to javafx.fxml;
    exports comp3111.examsystem;
    opens comp3111.examsystem.controller to javafx.fxml;
    exports comp3111.examsystem.controller;
    exports comp3111.examsystem.entity;
    opens comp3111.examsystem.entity to com.fasterxml.jackson.databind, javafx.fxml;
}
