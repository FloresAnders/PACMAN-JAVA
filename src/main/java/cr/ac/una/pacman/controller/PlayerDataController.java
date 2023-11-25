/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.pacman.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.pacman.user.JSON;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author anders
 */
public class PlayerDataController extends Controller implements Initializable {

    @FXML
    private JFXTextField txtName;
    @FXML
    private Label lblMessage;
    @FXML
    private JFXButton btnSave;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @Override
    public void initialize() {
        
        
    }

    @FXML
    private void onBtnSave(ActionEvent event) {
        if (!txtName.getText().isEmpty()) {
             JSON.getInstance().modificarVariable("name", txtName.getText());
             JSON.getInstance().modificarVariable("isRegistered", true);
             JSON.getInstance().guardarCambios();
             getStage().close();
        }
        lblMessage.setText("Please, insert a valid name");
    }
    
}
