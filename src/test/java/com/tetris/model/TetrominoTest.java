package com.tetris.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TetrominoTest {
    
    @Test
    void testCreateRandom() {
        Tetromino tetromino = Tetromino.createRandom();
        assertNotNull(tetromino);
        assertTrue(tetromino.getX() >= 0 && tetromino.getX() < 10);
        assertEquals(0, tetromino.getY());
    }

    @Test
    void testMoveDown() {
        Tetromino tetromino = Tetromino.createRandom();
        int initialY = tetromino.getY();
        
        tetromino.moveDown();
        assertEquals(initialY + 1, tetromino.getY());
    }

    @Test
    void testMoveLeft() {
        Tetromino tetromino = Tetromino.createRandom();
        int initialX = tetromino.getX();
        
        tetromino.moveLeft();
        assertEquals(initialX - 1, tetromino.getX());
    }

    @Test
    void testMoveRight() {
        Tetromino tetromino = Tetromino.createRandom();
        int initialX = tetromino.getX();
        
        tetromino.moveRight();
        assertEquals(initialX + 1, tetromino.getX());
    }

    @Test
    void testRotate() {
        Tetromino tetromino = Tetromino.createRandom();
        boolean[][] initialShape = tetromino.getShape();
        
        tetromino.rotate();
        assertNotEquals(initialShape, tetromino.getShape());
    }

    @Test
    void testGetType() {
        Tetromino tetromino = Tetromino.createRandom();
        assertTrue(tetromino.getType() >= 0 && tetromino.getType() < 7);
    }

    @Test
    void testGetShape() {
        Tetromino tetromino = Tetromino.createRandom();
        boolean[][] shape = tetromino.getShape();
        assertNotNull(shape);
        assertEquals(4, shape.length);
        assertEquals(4, shape[0].length);
    }
} 