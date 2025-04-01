package com.tetris;

import com.tetris.model.GameBoard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TetrisGame extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Прогрев JVM
        warmupJVM();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
        VBox root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Тетрис");
        primaryStage.show();
    }

    private void warmupJVM() {
        // Создаем временную игру для прогрева
        GameBoard warmupBoard = new GameBoard(10, 20);
        for (int i = 0; i < 100; i++) {
            warmupBoard.moveLeft();
            warmupBoard.moveRight();
            warmupBoard.rotate();
            warmupBoard.moveDown();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
} 