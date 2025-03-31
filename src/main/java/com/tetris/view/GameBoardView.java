package com.tetris.view;

import com.tetris.model.GameBoard;
import com.tetris.model.Tetromino;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class GameBoardView extends Canvas {
    private final GameBoard gameBoard;
    private final int cellSize;
    private final int boardWidth;
    private final int boardHeight;

    public GameBoardView(GameBoard gameBoard, int cellSize) {
        super(gameBoard.getWidth() * cellSize, gameBoard.getHeight() * cellSize);
        this.gameBoard = gameBoard;
        this.cellSize = cellSize;
        this.boardWidth = gameBoard.getWidth();
        this.boardHeight = gameBoard.getHeight();
        
        // Отключаем сглаживание для четких линий
        GraphicsContext gc = getGraphicsContext2D();
        gc.setImageSmoothing(false);

        // Устанавливаем фокус и обработчик клавиш
        setFocusTraversable(true);
        setOnKeyPressed(this::handleKeyPress);
    }

    private void handleKeyPress(KeyEvent event) {
        if (gameBoard.isGameOver()) return;

        switch (event.getCode()) {
            case LEFT -> gameBoard.moveLeft();
            case RIGHT -> gameBoard.moveRight();
            case DOWN -> gameBoard.moveDown();
            case UP -> gameBoard.rotate();
            case SPACE -> gameBoard.dropDown();
        }
        draw();
    }

    public void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

        // Рисуем фон игрового поля
        gc.setFill(Color.rgb(20, 20, 20));
        gc.fillRect(0, 0, getWidth(), getHeight());

        // Рисуем упавшие фигуры
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                if (gameBoard.getCell(x, y)) {
                    int colorIndex = gameBoard.getCellColor(x, y);
                    if (colorIndex >= 0) {
                        gc.setFill(Tetromino.getColor(colorIndex));
                        gc.fillRect(x * cellSize, y * cellSize, cellSize - 1, cellSize - 1);
                    }
                }
            }
        }

        // Рисуем текущую фигуру
        Tetromino currentTetromino = gameBoard.getCurrentTetromino();
        if (currentTetromino != null) {
            gc.setFill(Tetromino.getColor(currentTetromino.getType()));
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (currentTetromino.getShape()[i][j]) {
                        int x = currentTetromino.getX() + j;
                        int y = currentTetromino.getY() + i;
                        if (y >= 0) {
                            gc.fillRect(x * cellSize, y * cellSize, cellSize - 1, cellSize - 1);
                        }
                    }
                }
            }
        }
    }
} 