package com.tetris.controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.tetris.model.GameBoard;
import com.tetris.view.GameBoardView;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private static final int CELL_SIZE = 30;
    private static final double INITIAL_SPEED = 1.0; // Начальная скорость (1 секунда между падениями)
    
    private GameBoard gameBoard;
    private GameBoardView gameBoardView;
    private Timeline gameLoop;
    
    @FXML
    private VBox gameContainer;
    
    @FXML
    private Label linesLabel;
    
    @FXML
    private Label scoreLabel;
    
    @FXML
    private Label levelLabel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameBoard = new GameBoard(10, 20);
        gameBoardView = new GameBoardView(gameBoard, CELL_SIZE);
        
        gameBoard.setListener(linesCleared -> {
            Platform.runLater(() -> {
                updateLabels();
                updateGameSpeed();
            });
        });
        
        gameContainer.getChildren().add(gameBoardView);
        gameBoardView.requestFocus();
        
        startGameLoop();
        updateLabels();
    }
    
    private void startGameLoop() {
        gameLoop = new Timeline(new KeyFrame(Duration.seconds(INITIAL_SPEED), e -> update()));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }
    
    private void updateGameSpeed() {
        int level = calculateLevel();
        double speed = INITIAL_SPEED * Math.pow(0.8, level - 1); // Увеличиваем скорость на 20% с каждым уровнем
        speed = Math.max(0.1, speed); // Минимальная скорость 0.1 секунды
        
        gameLoop.stop();
        gameLoop = new Timeline(new KeyFrame(Duration.seconds(speed), e -> update()));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }
    
    private void update() {
        if (!gameBoard.isGameOver()) {
            if (!gameBoard.moveDown()) {
                int linesCleared = gameBoard.clearLines();
                if (linesCleared > 0) {
                    updateLabels();
                }
                if (!gameBoard.spawnTetromino()) {
                    gameOver();
                }
            }
            gameBoardView.draw();
        }
    }
    
    private void updateLabels() {
        linesLabel.setText(String.valueOf(gameBoard.getClearedLines()));
        scoreLabel.setText(String.valueOf(calculateScore()));
        levelLabel.setText(String.valueOf(calculateLevel()));
    }
    
    private int calculateScore() {
        return gameBoard.getClearedLines() * 100;
    }
    
    private int calculateLevel() {
        return 1 + (gameBoard.getClearedLines() / 10);
    }
    
    private void gameOver() {
        gameLoop.stop();
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Игра окончена");
            alert.setHeaderText("Игра окончена!");
            VBox content = new VBox(10);
            content.getChildren().addAll(
                new Label("Убрано линий: " + gameBoard.getClearedLines()),
                new Label("Счёт: " + calculateScore()),
                new Label("Уровень: " + calculateLevel())
            );
            alert.getDialogPane().setContent(content);
            alert.showAndWait();
        });
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
} 