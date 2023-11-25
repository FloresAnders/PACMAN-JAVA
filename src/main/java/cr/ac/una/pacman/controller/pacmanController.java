package cr.ac.una.pacman.controller;

import cr.ac.una.pacman.model.PacManModel;
import cr.ac.una.pacman.finalPacman.PacManView;
import cr.ac.una.pacman.user.JSON;
import cr.ac.una.pacman.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class pacmanController extends Controller implements Initializable {

    final private static double FRAMES_PER_SECOND = 5.0;

    @FXML
    private Label scoreLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private Label gameOverLabel;
    @FXML
    private PacManView pacManView;
    private PacManModel pacManModel;

    private Timer timer;
    private static int ghostEatingModeCounter;
    private int level=1;
    private boolean paused;
    @FXML
    private ImageView imgv1;
    @FXML
    private ImageView imgv2;
    @FXML
    private ImageView imgv3;
    @FXML
    private ImageView imgv4;
    @FXML
    private ImageView imgv5;
    @FXML
    private ImageView imgv6;

    public pacmanController() {
        this.paused = false;
    }

    public void initialize() {
        
        this.pacManModel = new PacManModel(level);
        this.setLifes();
        this.update(PacManModel.Direction.NONE);
        ghostEatingModeCounter = 25;
        this.startTimer();
        Platform.runLater(() -> {
            getStage().getScene().setOnKeyPressed((t) -> {
                boolean keyRecognized = true;
                KeyCode code = t.getCode();
                PacManModel.Direction direction = PacManModel.Direction.NONE;
                if (code == KeyCode.LEFT) {
                    direction = PacManModel.Direction.LEFT;
                } else if (code == KeyCode.RIGHT) {
                    direction = PacManModel.Direction.RIGHT;
                } else if (code == KeyCode.UP) {
                    direction = PacManModel.Direction.UP;
                } else if (code == KeyCode.DOWN) {
                    direction = PacManModel.Direction.DOWN;
                } else if (code == KeyCode.G) {
                    pause();
                    this.pacManModel.startNewGame(level);
                    this.gameOverLabel.setText(String.format(""));
                    paused = false;
                    this.startTimer();
                } else {
                    keyRecognized = false;
                }
                if (keyRecognized) {
                    t.consume();
                    pacManModel.setCurrentDirection(direction);
                }
            });
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public void setclose(){
        getStage().close();
    }
    public void setLevel(int level){
        this.level = level;
    }
    private void setLifes(){
        imgv1.setImage(new Image("/cr/ac/una/pacman/images/pacmanRight.gif"));
        imgv2.setImage(new Image("/cr/ac/una/pacman/images/pacmanRight.gif"));
        imgv3.setImage(new Image("/cr/ac/una/pacman/images/pacmanRight.gif"));
        imgv4.setImage(new Image("/cr/ac/una/pacman/images/pacmanRight.gif"));
        imgv5.setImage(new Image("/cr/ac/una/pacman/images/pacmanRight.gif"));
        imgv6.setImage(new Image("/cr/ac/una/pacman/images/pacmanRight.gif"));
        if (JSON.getInstance().obtenerValorInteger("lifes") == 0) {
            imgv1.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
            imgv2.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
            imgv3.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
            imgv4.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
            imgv5.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
            imgv6.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
        }
        if (JSON.getInstance().obtenerValorInteger("lifes") == 1) {
            imgv2.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
            imgv3.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
            imgv4.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
            imgv5.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
            imgv6.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
        }
        else if (JSON.getInstance().obtenerValorInteger("lifes") == 2) {
            imgv3.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
            imgv4.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
            imgv5.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
            imgv6.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
        }
        else if (JSON.getInstance().obtenerValorInteger("lifes") == 3) {
            imgv4.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
            imgv5.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
            imgv6.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
        }
        else if (JSON.getInstance().obtenerValorInteger("lifes") == 4) {
           imgv5.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
            imgv6.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
        }
        else if (JSON.getInstance().obtenerValorInteger("lifes") == 5) {
            imgv6.setImage(new Image("/cr/ac/una/pacman/images/pacmanRightMuerte.gif"));
        }      
    }
    private void startTimer() {
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        update(pacManModel.getCurrentDirection());
                    }
                });
            }
        };

        long frameTimeInMilliseconds = (long) (1000.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    private void update(PacManModel.Direction direction) {
        this.pacManModel.step(direction);
        this.pacManView.update(pacManModel);
        this.scoreLabel.setText(String.format("Score: %d", this.pacManModel.getScore()));
        this.levelLabel.setText(String.format("Level: %d", this.pacManModel.getLevel()));
        if (pacManModel.isGameOver() && JSON.getInstance().obtenerValorInteger("lifes")<1) {
            pause();
            GameOverController g = (GameOverController) FlowController.getInstance().getController("gameOver");
            g.set(level, (scoreLabel.getText()));
            getStage().close();
            FlowController.getInstance().goViewInWindow("gameOver", false);
            
        }else{
            setLifes();
        }
        if (pacManModel.isYouWon()) {
            this.gameOverLabel.setText(String.format("YOU WON!"));
        }

        if (pacManModel.isGhostEatingMode()) {
            ghostEatingModeCounter--;
        }
        if (ghostEatingModeCounter == 0 && pacManModel.isGhostEatingMode()) {
            pacManModel.setGhostEatingMode(false);
        }
    }

    public void pause() {
        this.timer.cancel();
        this.paused = true;
    }

    public double getBoardWidth() {
        return PacManView.CELL_WIDTH * this.pacManView.getColumnCount();
    }

    public double getBoardHeight() {
        return PacManView.CELL_WIDTH * this.pacManView.getRowCount();
    }

    public static void setGhostEatingModeCounter() {
        ghostEatingModeCounter = 25;
    }

    public static int getGhostEatingModeCounter() {
        return ghostEatingModeCounter;
    }

    public boolean getPaused() {
        return paused;
    }

}
