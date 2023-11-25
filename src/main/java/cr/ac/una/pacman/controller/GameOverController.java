/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.pacman.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.pacman.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class GameOverController extends Controller implements Initializable {

    @FXML
    private ImageView imgOver;
    @FXML
    private Label lblCurrentPoint;
    @FXML
    private JFXButton btnFollow;
    @FXML
    private JFXButton btnAband;

    int level = 1;
    String points = "0";
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgOver.setImage(new Image("/cr/ac/una/pacman/images/GAME-OVER.png"));
        lblCurrentPoint.setText("Puntos de la partida : "+ points);
    }    

    @Override
    public void initialize() {
        
    }

    public void set(int level,String points){
        this.level = level;
        this.points = points;
    }
    @FXML
    private void onBtnFollow(ActionEvent event) {
        pacmanController c = (pacmanController) FlowController.getInstance().getController("pacman");
        c.setLevel(level);
        FlowController.getInstance().goViewInWindow("pacman", Boolean.FALSE);
        getStage().close();
    }

    @FXML
    private void onBtnAband(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("principal", Boolean.FALSE);
        getStage().close();
    }
    
}
