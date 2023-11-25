module cr.ac.una.pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.base;
    requires java.desktop;
    requires com.jfoenix;
    requires com.fasterxml.jackson.databind;
    
    exports cr.ac.una.pacman;
    opens cr.ac.una.pacman to javafx.fxml,com.jfoenix,javafx.controls,javafx.graphics;
    opens cr.ac.una.pacman.finalPacman to javafx.fxml,com.jfoenix,javafx.controls,javafx.graphics;
    opens cr.ac.una.pacman.controller to javafx.fxml,com.jfoenix,javafx.controls;
    
}
