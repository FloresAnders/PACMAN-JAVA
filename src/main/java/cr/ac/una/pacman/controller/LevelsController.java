/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.pacman.controller;

import cr.ac.una.pacman.user.JSON;
import cr.ac.una.pacman.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author anders
 */
public class LevelsController extends Controller implements Initializable {

    @FXML
    private ImageView img1;
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img3;
    @FXML
    private ImageView img4;
    @FXML
    private ImageView img5;
    @FXML
    private ImageView img6;
    @FXML
    private ImageView img7;
    @FXML
    private ImageView img8;
    @FXML
    private ImageView img9;
    @FXML
    private ImageView img10;
    @FXML
    private Label lbllevels;
    @FXML
    private Label lblmessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        img1.setImage(new Image("/cr/ac/una/pacman/images/joker.jpg"));
        img2.setImage(new Image("/cr/ac/una/pacman/images/simpsons.jpg"));
        img3.setImage(new Image("/cr/ac/una/pacman/images/ironman.jpg"));
        img4.setImage(new Image("/cr/ac/una/pacman/images/spiderman.jpg"));
        img5.setImage(new Image("/cr/ac/una/pacman/images/batman.jpg"));
        img6.setImage(new Image("/cr/ac/una/pacman/images/superman.jpg"));
        img7.setImage(new Image("/cr/ac/una/pacman/images/deadpool.jpg"));
        img8.setImage(new Image("/cr/ac/una/pacman/images/onepiece.jpg"));
        img9.setImage(new Image("/cr/ac/una/pacman/images/naruto.jpg"));
        img10.setImage(new Image("/cr/ac/una/pacman/images/dragonballz.jpg"));
        lbllevels.setText("Ultimo nivel alcanzado : "+ JSON.getInstance().obtenerValorInteger("level"));
    }    

    @Override
    public void initialize() {
        
    }

    @FXML
    private void img1(MouseEvent event) {
        if (JSON.getInstance().obtenerValorInteger("level")== 1) {
            ScreenLoadController c =  (ScreenLoadController) FlowController.getInstance().getController("screenLoad");
            c.setLevel(1);
        FlowController.getInstance().goViewInWindow("screenLoad", Boolean.FALSE);
        getStage().close();
        }   
    }

    @FXML
    private void img2(MouseEvent event) {
        if (JSON.getInstance().obtenerValorInteger("level") >= 2) {
            ScreenLoadController c =  (ScreenLoadController) FlowController.getInstance().getController("screenLoad");
            c.setLevel(2);
        FlowController.getInstance().goViewInWindow("screenLoad", Boolean.FALSE);
        getStage().close();
        }else{
            lblmessage.setText("Nivel 2 bloequeado.");
        }
    }

    @FXML
    private void img3(MouseEvent event) {
        if (JSON.getInstance().obtenerValorInteger("level") >= 3) {
           ScreenLoadController c =  (ScreenLoadController) FlowController.getInstance().getController("screenLoad");
            c.setLevel(3);
        FlowController.getInstance().goViewInWindow("screenLoad", Boolean.FALSE);
        getStage().close();
        }else{
            lblmessage.setText("Nivel 3 bloequeado.");
        }
    }

    @FXML
    private void img4(MouseEvent event) {
        if (JSON.getInstance().obtenerValorInteger("level") >= 4) {
            ScreenLoadController c =  (ScreenLoadController) FlowController.getInstance().getController("screenLoad");
            c.setLevel(4);
        FlowController.getInstance().goViewInWindow("screenLoad", Boolean.FALSE);
        getStage().close();
        }else{
            lblmessage.setText("Nivel 4 bloequeado.");
        }
    }

    @FXML
    private void img5(MouseEvent event) {
       if (JSON.getInstance().obtenerValorInteger("level") >= 5) {
            ScreenLoadController c =  (ScreenLoadController) FlowController.getInstance().getController("screenLoad");
            c.setLevel(5);
        FlowController.getInstance().goViewInWindow("screenLoad", Boolean.FALSE);
        getStage().close();
        }else{
            lblmessage.setText("Nivel 5 bloequeado.");
        } 
    }

    @FXML
    private void img6(MouseEvent event) {
       if (JSON.getInstance().obtenerValorInteger("level") >= 6) {
            ScreenLoadController c =  (ScreenLoadController) FlowController.getInstance().getController("screenLoad");
            c.setLevel(6);
        FlowController.getInstance().goViewInWindow("screenLoad", Boolean.FALSE);
        getStage().close();
        }else{
            lblmessage.setText("Nivel 6 bloequeado.");
        }  
    }

    @FXML
    private void img7(MouseEvent event) {
       if (JSON.getInstance().obtenerValorInteger("level") >= 7) {
            ScreenLoadController c =  (ScreenLoadController) FlowController.getInstance().getController("screenLoad");
            c.setLevel(7);
        FlowController.getInstance().goViewInWindow("screenLoad", Boolean.FALSE);
        getStage().close();
        }else{
            lblmessage.setText("Nivel 7 bloequeado.");
        }  
    }

    @FXML
    private void img8(MouseEvent event) {
        if (JSON.getInstance().obtenerValorInteger("level") >= 8) {
            ScreenLoadController c =  (ScreenLoadController) FlowController.getInstance().getController("screenLoad");
            c.setLevel(8);
        FlowController.getInstance().goViewInWindow("screenLoad", Boolean.FALSE);
        getStage().close();
        }else{
            lblmessage.setText("Nivel 8 bloequeado.");
        }  
    }

    @FXML
    private void img9(MouseEvent event) {
       if (JSON.getInstance().obtenerValorInteger("level") >= 9) {
            ScreenLoadController c =  (ScreenLoadController) FlowController.getInstance().getController("screenLoad");
            c.setLevel(9);
        FlowController.getInstance().goViewInWindow("screenLoad", Boolean.FALSE);
        getStage().close();
        }else{
            lblmessage.setText("Nivel 9 bloequeado.");
        }  
    }

    @FXML
    private void img10(MouseEvent event) {
        if (JSON.getInstance().obtenerValorInteger("level") == 10) {
            ScreenLoadController c =  (ScreenLoadController) FlowController.getInstance().getController("screenLoad");
            c.setLevel(10);
            FlowController.getInstance().goViewInWindow("screenLoad", Boolean.FALSE);
            getStage().close();
        }else{
            lblmessage.setText("Nivel 10 bloequeado.");
        }  
    }
    
}
