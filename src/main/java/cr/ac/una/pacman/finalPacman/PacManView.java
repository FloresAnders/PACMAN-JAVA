package cr.ac.una.pacman.finalPacman;

import cr.ac.una.pacman.model.PacManModel;
import cr.ac.una.pacman.controller.pacmanController;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import cr.ac.una.pacman.model.PacManModel.CellValue;

public class PacManView extends Group {

    public final static double CELL_WIDTH = 20.0;

    @FXML
    private int rowCount;
    @FXML
    private int columnCount;
    private ImageView[][] cellViews;
    private Image pacmanRightImage;
    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image ghostImage;
    private Image ghost1Image;
    private Image ghost2Image;
    private Image ghost3Image;
    private Image blueGhostImage;
    private Image wallImage;
    private Image bigDotImage;
    private Image smallDotImage;

    public PacManView() {
        this.pacmanRightImage = new Image(getClass().getResourceAsStream("/cr/ac/una/pacman/images/pacmanRight.gif"));
        this.pacmanUpImage = new Image(getClass().getResourceAsStream("/cr/ac/una/pacman/images/pacmanUp.gif"));
        this.pacmanDownImage = new Image(getClass().getResourceAsStream("/cr/ac/una/pacman/images/pacmanDown.gif"));
        this.pacmanLeftImage = new Image(getClass().getResourceAsStream("/cr/ac/una/pacman/images/pacmanLeft.gif"));
        this.ghostImage = new Image(getClass().getResourceAsStream("/cr/ac/una/pacman/images/redghost.gif"));
        this.ghost1Image = new Image(getClass().getResourceAsStream("/cr/ac/una/pacman/images/ghost1.gif"));
        this.ghost2Image = new Image(getClass().getResourceAsStream("/cr/ac/una/pacman/images/ghost2.gif"));
        this.ghost3Image = new Image(getClass().getResourceAsStream("/cr/ac/una/pacman/images/ghost3.gif"));
        this.blueGhostImage = new Image(getClass().getResourceAsStream("/cr/ac/una/pacman/images/blueghost.gif"));
        this.wallImage = new Image(getClass().getResourceAsStream("/cr/ac/una/pacman/images/wall.png"));
        this.bigDotImage = new Image(getClass().getResourceAsStream("/cr/ac/una/pacman/images/whitedot.png"));
        this.smallDotImage = new Image(getClass().getResourceAsStream("/cr/ac/una/pacman/images/smalldot.png"));
    }

    private void initializeGrid() {
        if (this.rowCount > 0 && this.columnCount > 0) {
            this.cellViews = new ImageView[this.rowCount][this.columnCount];
            for (int row = 0; row < this.rowCount; row++) {
                for (int column = 0; column < this.columnCount; column++) {
                    ImageView imageView = new ImageView();
                    imageView.setX((double) column * CELL_WIDTH);
                    imageView.setY((double) row * CELL_WIDTH);
                    imageView.setFitWidth(CELL_WIDTH);
                    imageView.setFitHeight(CELL_WIDTH);
                    this.cellViews[row][column] = imageView;
                    this.getChildren().add(imageView);
                }
            }
        }
    }

    public void update(PacManModel model) {
        assert model.getRowCount() == this.rowCount && model.getColumnCount() == this.columnCount;

        for (int row = 0; row < this.rowCount; row++) {
            for (int column = 0; column < this.columnCount; column++) {
                CellValue value = model.getCellValue(row, column);
                if (value == CellValue.WALL) {
                    this.cellViews[row][column].setImage(this.wallImage);
                } else if (value == CellValue.BIGDOT) {
                    this.cellViews[row][column].setImage(this.bigDotImage);
                } else if (value == CellValue.SMALLDOT) {
                    this.cellViews[row][column].setImage(this.smallDotImage);
                } else {
                    this.cellViews[row][column].setImage(null);
                }

                if (row == model.getPacmanLocation().getX() && column == model.getPacmanLocation().getY() && (PacManModel.getLastDirection() == PacManModel.Direction.RIGHT || PacManModel.getLastDirection() == PacManModel.Direction.NONE)) {
                    this.cellViews[row][column].setImage(this.pacmanRightImage);
                } else if (row == model.getPacmanLocation().getX() && column == model.getPacmanLocation().getY() && PacManModel.getLastDirection() == PacManModel.Direction.LEFT) {
                    this.cellViews[row][column].setImage(this.pacmanLeftImage);
                } else if (row == model.getPacmanLocation().getX() && column == model.getPacmanLocation().getY() && PacManModel.getLastDirection() == PacManModel.Direction.UP) {
                    this.cellViews[row][column].setImage(this.pacmanUpImage);
                } else if (row == model.getPacmanLocation().getX() && column == model.getPacmanLocation().getY() && PacManModel.getLastDirection() == PacManModel.Direction.DOWN) {
                    this.cellViews[row][column].setImage(this.pacmanDownImage);
                }

                if (PacManModel.isGhostEatingMode() && (pacmanController.getGhostEatingModeCounter() == 6 || pacmanController.getGhostEatingModeCounter() == 4 || pacmanController.getGhostEatingModeCounter() == 2)) {
                    if (row == model.getGhostLocation().getX() && column == model.getGhostLocation().getY()) {
                        this.cellViews[row][column].setImage(this.ghostImage);
                    }
                    if (row == model.getGhost1Location().getX() && column == model.getGhost1Location().getY()) {
                        this.cellViews[row][column].setImage(this.ghost1Image);
                    }
                    if (row == model.getGhost2Location().getX() && column == model.getGhost2Location().getY()) {
                        this.cellViews[row][column].setImage(this.ghost2Image);
                    }
                    if (row == model.getGhost3Location().getX() && column == model.getGhost3Location().getY()) {
                        this.cellViews[row][column].setImage(this.ghost3Image);
                    }
                } else if (PacManModel.isGhostEatingMode()) {
                    if (row == model.getGhostLocation().getX() && column == model.getGhostLocation().getY()) {
                        this.cellViews[row][column].setImage(this.blueGhostImage);
                    }
                    if (row == model.getGhost1Location().getX() && column == model.getGhost1Location().getY()) {
                        this.cellViews[row][column].setImage(this.blueGhostImage);
                    }
                    if (row == model.getGhost2Location().getX() && column == model.getGhost2Location().getY()) {
                        this.cellViews[row][column].setImage(this.blueGhostImage);
                    }
                    if (row == model.getGhost3Location().getX() && column == model.getGhost3Location().getY()) {
                        this.cellViews[row][column].setImage(this.blueGhostImage);
                    }
                } else {
                    if (row == model.getGhostLocation().getX() && column == model.getGhostLocation().getY()) {
                        this.cellViews[row][column].setImage(this.ghostImage);
                    }
                    if (row == model.getGhost1Location().getX() && column == model.getGhost1Location().getY()) {
                        this.cellViews[row][column].setImage(this.ghost1Image);
                    }
                    if (row == model.getGhost2Location().getX() && column == model.getGhost2Location().getY()) {
                        this.cellViews[row][column].setImage(this.ghost2Image);
                    }
                    if (row == model.getGhost3Location().getX() && column == model.getGhost3Location().getY()) {
                        this.cellViews[row][column].setImage(this.ghost3Image);
                    }
                }
            }
        }
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        this.initializeGrid();
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        this.initializeGrid();
    }
}
