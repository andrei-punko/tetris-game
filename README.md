# Tetris Game

[![Build](https://github.com/andrei-punko/tetris-game/actions/workflows/build.yml/badge.svg)](https://github.com/andrei-punko/tetris-game/actions/workflows/build.yml)
[![Java](https://img.shields.io/badge/Java-21-red.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![JavaFX](https://img.shields.io/badge/JavaFX-21-orange.svg)](https://openjfx.io/)

Классическая игра Тетрис, реализованная на Java с использованием JavaFX.

## Требования

- Java 21 или выше
- Maven 3.6 или выше
- JavaFX 21

## Управление

- Стрелка влево: перемещение фигуры влево
- Стрелка вправо: перемещение фигуры вправо
- Стрелка вниз: ускорение падения
- Стрелка вверх: поворот фигуры
- Пробел: мгновенное падение

## Запуск игры

### Способ 1: Через скрипт (рекомендуется)

1. Дважды щелкните по файлу `start.bat`
2. Или запустите через командную строку:
   ```bash
   start.bat
   ```

### Способ 2: Через Maven

1. Откройте командную строку в директории проекта
2. Выполните команду:
   ```bash
   mvn clean javafx:run
   ```

## Сборка проекта

Для сборки проекта выполните:
```bash
mvn clean package
```

## Структура проекта

- `src/main/java/com/tetris/`
  - `model/` - классы модели игры
  - `view/` - классы представления
  - `controller/` - классы контроллера
  - `TetrisApp.java` - главный класс приложения
- `src/main/resources/` - ресурсы приложения
- `pom.xml` - файл конфигурации Maven
- `start.bat` - скрипт для запуска игры
