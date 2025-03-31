package com.tetris.model;

import java.util.Random;

public class GameBoard {
    private final boolean[][] board;
    private final int[][] colors;
    private final int width;
    private final int height;
    private Tetromino currentTetromino;
    private boolean gameOver;
    private int clearedLines;
    private GameBoardListener listener;

    public interface GameBoardListener {
        void onLinesCleared(int linesCleared);
    }

    public void setListener(GameBoardListener listener) {
        this.listener = listener;
    }

    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new boolean[height][width];
        this.colors = new int[height][width];
        this.gameOver = false;
        this.clearedLines = 0;
        spawnTetromino();
    }

    public boolean spawnTetromino() {
        currentTetromino = Tetromino.createRandom();
        currentTetromino.setPosition(width / 2 - 2, -1);
        
        if (!isValidPosition(currentTetromino)) {
            gameOver = true;
            return false;
        }
        return true;
    }

    public boolean moveLeft() {
        if (currentTetromino == null) return false;
        currentTetromino.moveLeft();
        if (!isValidPosition(currentTetromino)) {
            currentTetromino.moveRight();
            return false;
        }
        return true;
    }

    public boolean moveRight() {
        if (currentTetromino == null) return false;
        currentTetromino.moveRight();
        if (!isValidPosition(currentTetromino)) {
            currentTetromino.moveLeft();
            return false;
        }
        return true;
    }

    public boolean moveDown() {
        if (currentTetromino == null) return false;
        currentTetromino.moveDown();
        if (!isValidPosition(currentTetromino)) {
            currentTetromino.moveUp();
            placeTetromino();
            return false;
        }
        return true;
    }

    public void rotate() {
        if (currentTetromino == null) return;
        currentTetromino.rotate();
        if (!isValidPosition(currentTetromino)) {
            currentTetromino.rotateBack();
        }
    }

    public void dropDown() {
        while (moveDown()) {}
    }

    private void placeTetromino() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (currentTetromino.getShape()[i][j]) {
                    int x = currentTetromino.getX() + j;
                    int y = currentTetromino.getY() + i;
                    if (y >= 0) {
                        board[y][x] = true;
                        colors[y][x] = currentTetromino.getType();
                    }
                }
            }
        }
        int linesCleared = clearLines();
        if (linesCleared > 0 && listener != null) {
            listener.onLinesCleared(linesCleared);
        }
        spawnTetromino();
    }

    public int clearLines() {
        int linesCleared = 0;
        boolean[][] newBoard = new boolean[height][width];
        int[][] newColors = new int[height][width];
        int writeRow = height - 1;
        
        // Проходим снизу вверх
        for (int currentRow = height - 1; currentRow >= 0; currentRow--) {
            boolean isLineFull = true;
            
            // Проверяем, заполнена ли строка
            for (int x = 0; x < width; x++) {
                if (!board[currentRow][x]) {
                    isLineFull = false;
                    break;
                }
            }
            
            // Если строка не заполнена, копируем её вниз
            if (!isLineFull) {
                for (int x = 0; x < width; x++) {
                    newBoard[writeRow][x] = board[currentRow][x];
                    newColors[writeRow][x] = colors[currentRow][x];
                }
                writeRow--;
            } else {
                linesCleared++;
            }
        }
        
        // Копируем значения обратно в основные массивы
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                board[y][x] = newBoard[y][x];
                colors[y][x] = newColors[y][x];
            }
        }
        
        if (linesCleared > 0) {
            clearedLines += linesCleared;
        }
        
        return linesCleared;
    }

    private boolean isLineFull(int line) {
        for (int j = 0; j < width; j++) {
            if (!board[line][j]) return false;
        }
        return true;
    }

    private boolean isValidPosition(Tetromino tetromino) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tetromino.getShape()[i][j]) {
                    int x = tetromino.getX() + j;
                    int y = tetromino.getY() + i;
                    
                    if (x < 0 || x >= width || y >= height) return false;
                    if (y >= 0 && board[y][x]) return false;
                }
            }
        }
        return true;
    }

    public boolean getCell(int x, int y) {
        return board[y][x];
    }

    public int getCellColor(int x, int y) {
        return colors[y][x];
    }

    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getClearedLines() {
        return clearedLines;
    }
} 