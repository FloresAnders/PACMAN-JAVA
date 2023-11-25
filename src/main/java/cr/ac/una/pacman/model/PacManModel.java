package cr.ac.una.pacman.model;

import cr.ac.una.pacman.controller.pacmanController;
import cr.ac.una.pacman.user.JSON;
import javafx.geometry.Point2D;
import javafx.fxml.FXML;
import java.io.*;
import java.util.*;

public class PacManModel {

    private String[] levelFiles = {
        "../Pacman/src/main/resources/cr/ac/una/pacman/levels/level1.txt",
        "../Pacman/src/main/resources/cr/ac/una/pacman/levels/level2.txt",
        "../Pacman/src/main/resources/cr/ac/una/pacman/levels/level3.txt",
        "../Pacman/src/main/resources/cr/ac/una/pacman/levels/level4.txt",
        "../Pacman/src/main/resources/cr/ac/una/pacman/levels/level5.txt",
        "../Pacman/src/main/resources/cr/ac/una/pacman/levels/level6.txt",
        "../Pacman/src/main/resources/cr/ac/una/pacman/levels/level7.txt",
        "../Pacman/src/main/resources/cr/ac/una/pacman/levels/level8.txt",
        "../Pacman/src/main/resources/cr/ac/una/pacman/levels/level9.txt",
        "../Pacman/src/main/resources/cr/ac/una/pacman/levels/level10.txt"
    };

    public enum CellValue {
        EMPTY, SMALLDOT, BIGDOT, WALL, GHOSTHOME, GHOST1HOME, GHOST2HOME, GHOST3HOME, PACMANHOME
    };

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    };
    @FXML
    private int rowCount;
    @FXML
    private int columnCount;
    private CellValue[][] grid;
    private int score;
    private int level;
    private int dotCount;
    private static boolean gameOver;
    private static boolean youWon;
    private static boolean ghostEatingMode;
    private Point2D pacmanLocation;
    private Point2D pacmanVelocity;
    private Point2D ghostLocation;
    private Point2D ghostVelocity;
    private Point2D ghost1Location;
    private Point2D ghost1Velocity;
    private Point2D ghost2Location;
    private Point2D ghost2Velocity;
    private Point2D ghost3Location;
    private Point2D ghost3Velocity;
    private static Direction lastDirection;
    private static Direction currentDirection;

    public PacManModel(int level) {
        this.startNewGame(level);
    }

    public void initializeLevel(String fileName) {

        File file = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()) {
                lineScanner.next();
                columnCount++;
            }
            rowCount++;
        }
        columnCount = columnCount / rowCount;
        Scanner scanner2 = null;
        try {
            scanner2 = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        grid = new CellValue[rowCount][columnCount];
        int row = 0;
        int pacmanRow = 0;
        int pacmanColumn = 0;
        int ghostRow = 0;
        int ghostColumn = 0;
        int ghost1Row = 0;
        int ghost1Column = 0;
        int ghost2Row = 0;
        int ghost2Column = 0;
        int ghost3Row = 0;
        int ghost3Column = 0;
        while (scanner2.hasNextLine()) {
            int column = 0;
            String line = scanner2.nextLine();
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()) {
                String value = lineScanner.next();
                CellValue thisValue;
                if (value.equals("W")) {
                    thisValue = CellValue.WALL;
                } else if (value.equals("S")) {
                    thisValue = CellValue.SMALLDOT;
                    dotCount++;
                } else if (value.equals("B")) {
                    thisValue = CellValue.BIGDOT;
                    dotCount++;
                } else if (value.equals("0")) {
                    thisValue = CellValue.GHOSTHOME;
                    ghostRow = row;
                    ghostColumn = column;
                } else if (value.equals("1")) {
                    thisValue = CellValue.GHOST1HOME;
                    ghost1Row = row;
                    ghost1Column = column;
                } else if (value.equals("2")) {
                    thisValue = CellValue.GHOST2HOME;
                    ghost2Row = row;
                    ghost2Column = column;
                } else if (value.equals("3")) {
                    thisValue = CellValue.GHOST3HOME;
                    ghost3Row = row;
                    ghost3Column = column;
                } else if (value.equals("P")) {
                    thisValue = CellValue.PACMANHOME;
                    pacmanRow = row;
                    pacmanColumn = column;
                } else //(value.equals("E"))
                {
                    thisValue = CellValue.EMPTY;
                }
                grid[row][column] = thisValue;
                column++;
            }
            row++;
        }
        pacmanLocation = new Point2D(pacmanRow, pacmanColumn);
        pacmanVelocity = new Point2D(0, 0);
        ghostLocation = new Point2D(ghostRow, ghostColumn);
        ghostVelocity = new Point2D(-1, 0);
        ghost1Location = new Point2D(ghost1Row, ghost1Column);
        ghost1Velocity = new Point2D(-1, 0);
        ghost2Location = new Point2D(ghost2Row, ghost2Column);
        ghost2Velocity = new Point2D(-1, 0);
        ghost3Location = new Point2D(ghost3Row, ghost3Column);
        ghost3Velocity = new Point2D(-1, 0);
        currentDirection = Direction.NONE;
        lastDirection = Direction.NONE;
    }

    public void startNewGame(int level) {
        this.gameOver = false;
        this.youWon = false;
        this.ghostEatingMode = false;
        dotCount = 0;
        rowCount = 0;
        columnCount = 0;
        this.score = 0;
        this.level = level;
        this.initializeLevel(levelFiles[level-1]);
    }

    public void startNextLevel() {
        if (this.isLevelComplete()) {
            this.level++;
            rowCount = 0;
            columnCount = 0;
            youWon = false;
            ghostEatingMode = false;
            try {
                this.initializeLevel(levelFiles[level - 1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                //if there are no levels left in the level array, the game ends
                youWon = true;
                gameOver = true;
                level--;
            }
            if (level > JSON.getInstance().obtenerValorInteger("level")) {
                JSON.getInstance().modificarVariable("level", level);
            }
            JSON.getInstance().modificarVariable("score", this.score);
            
        }
    }

    public void movePacman(Direction direction) {
        Point2D potentialPacmanVelocity = changeVelocity(direction);
        Point2D potentialPacmanLocation = pacmanLocation.add(potentialPacmanVelocity);
        potentialPacmanLocation = setGoingOffscreenNewLocation(potentialPacmanLocation);
        if (direction.equals(lastDirection)) {
            if (grid[(int) potentialPacmanLocation.getX()][(int) potentialPacmanLocation.getY()] == CellValue.WALL) {
                pacmanVelocity = changeVelocity(Direction.NONE);
                setLastDirection(Direction.NONE);
            } else {
                pacmanVelocity = potentialPacmanVelocity;
                pacmanLocation = potentialPacmanLocation;
            }
        } else {

            if (grid[(int) potentialPacmanLocation.getX()][(int) potentialPacmanLocation.getY()] == CellValue.WALL) {
                potentialPacmanVelocity = changeVelocity(lastDirection);
                potentialPacmanLocation = pacmanLocation.add(potentialPacmanVelocity);

                if (grid[(int) potentialPacmanLocation.getX()][(int) potentialPacmanLocation.getY()] == CellValue.WALL) {
                    pacmanVelocity = changeVelocity(Direction.NONE);
                    setLastDirection(Direction.NONE);
                } else {
                    pacmanVelocity = changeVelocity(lastDirection);
                    pacmanLocation = pacmanLocation.add(pacmanVelocity);
                }
            } else {
                pacmanVelocity = potentialPacmanVelocity;
                pacmanLocation = potentialPacmanLocation;
                setLastDirection(direction);
            }
        }
    }

    public void moveGhosts() {
        Point2D[] ghostData = moveAGhost(ghostVelocity, ghostLocation);
        Point2D[] ghost1Data = moveAGhost(ghost1Velocity, ghost1Location);
        Point2D[] ghost2Data = moveAGhost(ghost2Velocity, ghost2Location);
        Point2D[] ghost3Data = moveAGhost(ghost3Velocity, ghost3Location);
        ghostVelocity = ghostData[0];
        ghostLocation = ghostData[1];
        ghost1Velocity = ghost1Data[0];
        ghost1Location = ghost1Data[1];
        ghost2Velocity = ghost2Data[0];
        ghost2Location = ghost2Data[1];
        ghost3Velocity = ghost3Data[0];
        ghost3Location = ghost3Data[1];

    }

    public Point2D[] moveAGhost(Point2D velocity, Point2D location) {
        Random generator = new Random();

        if (!ghostEatingMode) {

            if (location.getY() == pacmanLocation.getY()) {
                if (location.getX() > pacmanLocation.getX()) {
                    velocity = changeVelocity(Direction.UP);
                } else {
                    velocity = changeVelocity(Direction.DOWN);
                }
                Point2D potentialLocation = location.add(velocity);

                potentialLocation = setGoingOffscreenNewLocation(potentialLocation);

                while (grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == CellValue.WALL) {
                    int randomNum = generator.nextInt(4);
                    Direction direction = intToDirection(randomNum);
                    velocity = changeVelocity(direction);
                    potentialLocation = location.add(velocity);
                }
                location = potentialLocation;
            } else if (location.getX() == pacmanLocation.getX()) {
                if (location.getY() > pacmanLocation.getY()) {
                    velocity = changeVelocity(Direction.LEFT);
                } else {
                    velocity = changeVelocity(Direction.RIGHT);
                }
                Point2D potentialLocation = location.add(velocity);
                potentialLocation = setGoingOffscreenNewLocation(potentialLocation);
                while (grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == CellValue.WALL) {
                    int randomNum = generator.nextInt(4);
                    Direction direction = intToDirection(randomNum);
                    velocity = changeVelocity(direction);
                    potentialLocation = location.add(velocity);
                }
                location = potentialLocation;
            } else {
                Point2D potentialLocation = location.add(velocity);
                potentialLocation = setGoingOffscreenNewLocation(potentialLocation);
                while (grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == CellValue.WALL) {
                    int randomNum = generator.nextInt(4);
                    Direction direction = intToDirection(randomNum);
                    velocity = changeVelocity(direction);
                    potentialLocation = location.add(velocity);
                }
                location = potentialLocation;
            }
        }
        if (ghostEatingMode) {
            if (location.getY() == pacmanLocation.getY()) {
                if (location.getX() > pacmanLocation.getX()) {
                    velocity = changeVelocity(Direction.DOWN);
                } else {
                    velocity = changeVelocity(Direction.UP);
                }
                Point2D potentialLocation = location.add(velocity);
                potentialLocation = setGoingOffscreenNewLocation(potentialLocation);
                while (grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == CellValue.WALL) {
                    int randomNum = generator.nextInt(4);
                    Direction direction = intToDirection(randomNum);
                    velocity = changeVelocity(direction);
                    potentialLocation = location.add(velocity);
                }
                location = potentialLocation;
            } else if (location.getX() == pacmanLocation.getX()) {
                if (location.getY() > pacmanLocation.getY()) {
                    velocity = changeVelocity(Direction.RIGHT);
                } else {
                    velocity = changeVelocity(Direction.LEFT);
                }
                Point2D potentialLocation = location.add(velocity);
                potentialLocation = setGoingOffscreenNewLocation(potentialLocation);
                while (grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == CellValue.WALL) {
                    int randomNum = generator.nextInt(4);
                    Direction direction = intToDirection(randomNum);
                    velocity = changeVelocity(direction);
                    potentialLocation = location.add(velocity);
                }
                location = potentialLocation;
            } else {
                Point2D potentialLocation = location.add(velocity);
                potentialLocation = setGoingOffscreenNewLocation(potentialLocation);
                while (grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == CellValue.WALL) {
                    int randomNum = generator.nextInt(4);
                    Direction direction = intToDirection(randomNum);
                    velocity = changeVelocity(direction);
                    potentialLocation = location.add(velocity);
                }
                location = potentialLocation;
            }
        }
        Point2D[] data = {velocity, location};
        return data;

    }

    public Point2D setGoingOffscreenNewLocation(Point2D objectLocation) {

        if (objectLocation.getY() >= columnCount) {
            objectLocation = new Point2D(objectLocation.getX(), 0);
        }
        if (objectLocation.getY() < 0) {
            objectLocation = new Point2D(objectLocation.getX(), columnCount - 1);
        }
        return objectLocation;
    }

    public Direction intToDirection(int x) {
        if (x == 0) {
            return Direction.LEFT;
        } else if (x == 1) {
            return Direction.RIGHT;
        } else if (x == 2) {
            return Direction.UP;
        } else {
            return Direction.DOWN;
        }
    }

    public void sendGhostHome() {
        for (int row = 0; row < this.rowCount; row++) {
            for (int column = 0; column < this.columnCount; column++) {
                if (grid[row][column] == CellValue.GHOSTHOME) {
                    ghostLocation = new Point2D(row, column);
                }
            }
        }
        ghost1Velocity = new Point2D(-1, 0);
    }
    public void sendPacmanHome() {
        for (int row = 0; row < this.rowCount; row++) {
            for (int column = 0; column < this.columnCount; column++) {
                if (grid[row][column] == CellValue.PACMANHOME) {
                   pacmanLocation = new Point2D(row, column);
                }
            }
        }
        ghost1Velocity = new Point2D(-1, 0);
    }

    public void sendGhost1Home() {
        for (int row = 0; row < this.rowCount; row++) {
            for (int column = 0; column < this.columnCount; column++) {
                if (grid[row][column] == CellValue.GHOST1HOME) {
                    ghost1Location = new Point2D(row, column);
                }
            }
        }
        ghost1Velocity = new Point2D(-1, 0);
    }

    public void sendGhost2Home() {
        for (int row = 0; row < this.rowCount; row++) {
            for (int column = 0; column < this.columnCount; column++) {
                if (grid[row][column] == CellValue.GHOST2HOME) {
                    ghost2Location = new Point2D(row, column);
                }
            }
        }
        ghost2Velocity = new Point2D(-1, 0);
    }

    public void sendGhost3Home() {
        for (int row = 0; row < this.rowCount; row++) {
            for (int column = 0; column < this.columnCount; column++) {
                if (grid[row][column] == CellValue.GHOST3HOME) {
                    ghost3Location = new Point2D(row, column);
                }
            }
        }
        ghost3Velocity = new Point2D(-1, 0);
    }

    public void step(Direction direction) {
        this.movePacman(direction);
        CellValue pacmanLocationCellValue = grid[(int) pacmanLocation.getX()][(int) pacmanLocation.getY()];
        if (pacmanLocationCellValue == CellValue.SMALLDOT) {
            grid[(int) pacmanLocation.getX()][(int) pacmanLocation.getY()] = CellValue.EMPTY;
            dotCount--;
            score += 10;
        }
        if (pacmanLocationCellValue == CellValue.BIGDOT) {
            grid[(int) pacmanLocation.getX()][(int) pacmanLocation.getY()] = CellValue.EMPTY;
            dotCount--;
            score += 50;
            ghostEatingMode = true;
            pacmanController.setGhostEatingModeCounter();
        }
        if (ghostEatingMode) {
            if (pacmanLocation.equals(ghostLocation)) {
                sendGhostHome();
                score += 300;
            }
            if (pacmanLocation.equals(ghost1Location)) {
                sendGhost1Home();
                score += 300;
            }
            if (pacmanLocation.equals(ghost2Location)) {
                sendGhost2Home();
                score += 300;
            }
            if (pacmanLocation.equals(ghost3Location)) {
                sendGhost3Home();
                score += 300;
            }
        } else {
            if (pacmanLocation.equals(ghostLocation) || pacmanLocation.equals(ghost1Location) || pacmanLocation.equals(ghost2Location) || pacmanLocation.equals(ghost3Location)) {
                if (JSON.getInstance().obtenerValorInteger("lifes") < 1) {
                   gameOver = true;
                }else{
                   JSON.getInstance().modificarVariable("lifes", JSON.getInstance().obtenerValorInteger("lifes")-1);
                   JSON.getInstance().guardarCambios();
                   sendPacmanHome();
                }
                pacmanVelocity = new Point2D(0, 0);
            }
        }
        this.moveGhosts();
        if (ghostEatingMode) {
            if (pacmanLocation.equals(ghostLocation)) {
                sendGhostHome();
                score += 400;
            }
            if (pacmanLocation.equals(ghost1Location)) {
                sendGhost1Home();
                score += 400;
            }
            if (pacmanLocation.equals(ghost2Location)) {
                sendGhost2Home();
                score += 400;
            }
            if (pacmanLocation.equals(ghost3Location)) {
                sendGhost3Home();
                score += 400;
            }
        } else {
            if (pacmanLocation.equals(ghostLocation) || pacmanLocation.equals(ghost1Location) || pacmanLocation.equals(ghost2Location) || pacmanLocation.equals(ghost3Location)) {
                if (JSON.getInstance().obtenerValorInteger("lifes") < 1) {
                   gameOver = true;
                }else{
                   JSON.getInstance().modificarVariable("lifes", JSON.getInstance().obtenerValorInteger("lifes")-1);
                   JSON.getInstance().guardarCambios();
                   sendPacmanHome();
                }
                pacmanVelocity = new Point2D(0, 0);
            }
        }
        if (this.isLevelComplete()) {
            pacmanVelocity = new Point2D(0, 0);
            startNextLevel();
        }
    }

    public Point2D changeVelocity(Direction direction) {
        if (direction == Direction.LEFT) {
            return new Point2D(0, -1);
        } else if (direction == Direction.RIGHT) {
            return new Point2D(0, 1);
        } else if (direction == Direction.UP) {
            return new Point2D(-1, 0);
        } else if (direction == Direction.DOWN) {
            return new Point2D(1, 0);
        } else {
            return new Point2D(0, 0);
        }
    }

    public static boolean isGhostEatingMode() {
        return ghostEatingMode;
    }

    public static void setGhostEatingMode(boolean ghostEatingModeBool) {
        ghostEatingMode = ghostEatingModeBool;
    }

    public static boolean isYouWon() {
        return youWon;
    }

    public boolean isLevelComplete() {
        return this.dotCount == 0;
    }

    public static boolean isGameOver() {
        return gameOver;
    }
    public static void setGameOver(boolean game) {
        gameOver = game;
    }

    public CellValue[][] getGrid() {
        return grid;
    }

    public CellValue getCellValue(int row, int column) {
        assert row >= 0 && row < this.grid.length && column >= 0 && column < this.grid[0].length;
        return this.grid[row][column];
    }

    public static Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction direction) {
        currentDirection = direction;
    }

    public static Direction getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(Direction direction) {
        lastDirection = direction;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addToScore(int points) {
        this.score += points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDotCount() {
        return dotCount;
    }

    public void setDotCount(int dotCount) {
        this.dotCount = dotCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public Point2D getPacmanLocation() {
        return pacmanLocation;
    }

    public void setPacmanLocation(Point2D pacmanLocation) {
        this.pacmanLocation = pacmanLocation;
    }

    public Point2D getGhostLocation() {
        return ghostLocation;
    }

    public void setGhostLocation(Point2D ghostLocation) {
        this.ghostLocation = ghostLocation;
    }

    public Point2D getGhost1Location() {
        return ghost1Location;
    }

    public void setGhost1Location(Point2D ghost1Location) {
        this.ghost1Location = ghost1Location;
    }

    public Point2D getGhost2Location() {
        return ghost2Location;
    }

    public void setGhost2Location(Point2D ghost2Location) {
        this.ghost2Location = ghost2Location;
    }

    public Point2D getGhost3Location() {
        return ghost3Location;
    }

    public void setGhost3Location(Point2D ghost3Location) {
        this.ghost3Location = ghost3Location;
    }

    public Point2D getPacmanVelocity() {
        return pacmanVelocity;
    }

    public void setPacmanVelocity(Point2D velocity) {
        this.pacmanVelocity = velocity;
    }

    public Point2D getGhostVelocity() {
        return ghostVelocity;
    }

    public void setGhostVelocity(Point2D ghostVelocity) {
        this.ghostVelocity = ghostVelocity;
    }

    public Point2D getGhost1Velocity() {
        return ghost1Velocity;
    }

    public void setGhost1Velocity(Point2D ghost1Velocity) {
        this.ghost1Velocity = ghost1Velocity;
    }

    public Point2D getGhost2Velocity() {
        return ghost2Velocity;
    }

    public void setGhost2Velocity(Point2D ghost2Velocity) {
        this.ghost2Velocity = ghost2Velocity;
    }

    public Point2D getGhost3Velocity() {
        return ghost3Velocity;
    }

    public void setGhost3Velocity(Point2D ghost3Velocity) {
        this.ghost3Velocity = ghost3Velocity;
    }
}
