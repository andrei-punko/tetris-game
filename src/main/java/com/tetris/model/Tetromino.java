package com.tetris.model;

import javafx.scene.paint.Color;
import java.util.Random;

public class Tetromino {
    private boolean[][] shape;
    private int x;
    private int y;
    private final int type;

    private static final boolean[][][] SHAPES = {
        // I
        {
            {false, false, false, false},
            {true, true, true, true},
            {false, false, false, false},
            {false, false, false, false}
        },
        // J
        {
            {false, false, false},
            {true, false, false},
            {true, true, true},
            {false, false, false}
        },
        // L
        {
            {false, false, false},
            {false, false, true},
            {true, true, true},
            {false, false, false}
        },
        // O
        {
            {true, true},
            {true, true},
            {false, false},
            {false, false}
        },
        // S
        {
            {false, false, false},
            {false, true, true},
            {true, true, false},
            {false, false, false}
        },
        // T
        {
            {false, false, false},
            {false, true, false},
            {true, true, true},
            {false, false, false}
        },
        // Z
        {
            {false, false, false},
            {true, true, false},
            {false, true, true},
            {false, false, false}
        }
    };

    private static final Random random = new Random(System.nanoTime());
    private static final int[] bag = new int[SHAPES.length];
    private static int bagIndex = SHAPES.length;

    private Tetromino(boolean[][] shape, int type) {
        this.shape = shape;
        this.type = type;
    }

    public static Tetromino createRandom() {
        // Используем систему "мешка" - все фигуры появляются в случайном порядке,
        // но каждая фигура появляется ровно один раз, прежде чем какая-либо появится снова
        if (bagIndex >= SHAPES.length) {
            // Заполняем мешок индексами фигур
            for (int i = 0; i < SHAPES.length; i++) {
                bag[i] = i;
            }
            // Перемешиваем мешок
            for (int i = SHAPES.length - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                int temp = bag[i];
                bag[i] = bag[j];
                bag[j] = temp;
            }
            bagIndex = 0;
        }

        int type = bag[bagIndex++];
        boolean[][] shape = new boolean[4][4];
        boolean[][] template = SHAPES[type];
        
        // Копируем шаблон в новую матрицу
        for (int i = 0; i < template.length; i++) {
            System.arraycopy(template[i], 0, shape[i], 0, template[i].length);
        }
        
        return new Tetromino(shape, type);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void moveDown() {
        y++;
    }

    public void moveUp() {
        y--;
    }

    public void rotate() {
        boolean[][] rotated = new boolean[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rotated[i][j] = shape[3 - j][i];
            }
        }
        shape = rotated;
    }

    public void rotateBack() {
        boolean[][] rotated = new boolean[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rotated[i][j] = shape[j][3 - i];
            }
        }
        shape = rotated;
    }

    public boolean[][] getShape() {
        return shape;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public static Color getColor(int type) {
        return switch (type) {
            case 0 -> Color.CYAN;    // I
            case 1 -> Color.BLUE;    // J
            case 2 -> Color.ORANGE;  // L
            case 3 -> Color.YELLOW;  // O
            case 4 -> Color.GREEN;   // S
            case 5 -> Color.PURPLE;  // T
            case 6 -> Color.RED;     // Z
            default -> Color.WHITE;
        };
    }
} 