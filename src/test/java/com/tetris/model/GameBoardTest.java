package com.tetris.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    private GameBoard gameBoard;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;

    @BeforeEach
    void setUp() {
        gameBoard = new GameBoard(WIDTH, HEIGHT);
    }

    @Test
    void testInitialState() {
        assertFalse(gameBoard.isGameOver());
        assertEquals(0, gameBoard.getClearedLines());
        assertNotNull(gameBoard.getCurrentTetromino());
    }

    @Test
    void testMoveDown() {
        Tetromino initialTetromino = gameBoard.getCurrentTetromino();
        int initialY = initialTetromino.getY();
        
        assertTrue(gameBoard.moveDown());
        assertEquals(initialY + 1, gameBoard.getCurrentTetromino().getY());
    }

    @Test
    void testMoveLeft() {
        Tetromino initialTetromino = gameBoard.getCurrentTetromino();
        int initialX = initialTetromino.getX();
        
        gameBoard.moveLeft();
        assertEquals(initialX - 1, gameBoard.getCurrentTetromino().getX());
    }

    @Test
    void testMoveRight() {
        Tetromino initialTetromino = gameBoard.getCurrentTetromino();
        int initialX = initialTetromino.getX();
        
        gameBoard.moveRight();
        assertEquals(initialX + 1, gameBoard.getCurrentTetromino().getX());
    }

    @Test
    void testRotate() {
        Tetromino initialTetromino = gameBoard.getCurrentTetromino();
        boolean[][] initialShape = initialTetromino.getShape();
        
        gameBoard.rotate();
        assertNotEquals(initialShape, gameBoard.getCurrentTetromino().getShape());
    }

    @Test
    void testClearLines() {
        // Заполняем нижнюю строку
        for (int x = 0; x < WIDTH; x++) {
            gameBoard.getBoard()[HEIGHT - 1][x] = true;
            gameBoard.getColors()[HEIGHT - 1][x] = 1;
        }

        int linesCleared = gameBoard.clearLines();
        assertEquals(1, linesCleared);
        assertEquals(1, gameBoard.getClearedLines());

        // Проверяем, что строка очищена
        for (int x = 0; x < WIDTH; x++) {
            assertFalse(gameBoard.getBoard()[HEIGHT - 1][x]);
            assertEquals(0, gameBoard.getColors()[HEIGHT - 1][x]);
        }
    }

    @Test
    void testGameOver() {
        // Заполняем верхнюю строку
        for (int x = 0; x < WIDTH; x++) {
            gameBoard.getBoard()[0][x] = true;
            gameBoard.getColors()[0][x] = 1;
        }

        // Пытаемся создать новую фигуру
        assertFalse(gameBoard.spawnTetromino());
        assertTrue(gameBoard.isGameOver());
    }

    @Test
    void testCollisionDetection() {
        // Заполняем позицию под текущей фигурой
        Tetromino currentTetromino = gameBoard.getCurrentTetromino();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (currentTetromino.getShape()[i][j]) {
                    int x = currentTetromino.getX() + j;
                    int y = currentTetromino.getY() + i + 1;
                    if (y < HEIGHT) {
                        gameBoard.getBoard()[y][x] = true;
                        gameBoard.getColors()[y][x] = 1;
                    }
                }
            }
        }

        assertFalse(gameBoard.moveDown());
    }
} 