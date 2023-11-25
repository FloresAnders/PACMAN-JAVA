package cr.ac.una.pacman.controller;

import cr.ac.una.pacman.util.FlowController;
import java.awt.Color;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ScreenLoadController extends Controller implements Initializable {

    @FXML
    private ImageView imgAnima;
    @FXML
    private ProgressBar prrBar;
    Timeline t;
    private int level = 1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgAnima.setImage(new Image("/cr/ac/una/pacman/images/17hv.gif"));
        Platform.runLater(() -> {
             t = new Timeline();
        
        t.setCycleCount(5);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
               setprogress();
            }
        });
        t.getKeyFrames().add(keyFrame);
        t.play();
                
        });
    }    
    @Override
    public void initialize() {

    }
    public void setLevel(int level){
        this.level = level;
    }
    private void setprogress(){
        prrBar.setProgress(prrBar.getProgress()+0.2d);
        if (prrBar.getProgress() == 1) {
            pacmanController c = (pacmanController) FlowController.getInstance().getController("pacman");
            c.setLevel(level);
            t.stop();
            FlowController.getInstance().goViewInWindow("pacman", Boolean.FALSE);
            getStage().close();
        }
    }
}
