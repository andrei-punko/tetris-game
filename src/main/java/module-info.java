module com.tetris {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.tetris to javafx.fxml;
    opens com.tetris.controller to javafx.fxml;
    exports com.tetris;
    exports com.tetris.controller;
    exports com.tetris.model;
} 