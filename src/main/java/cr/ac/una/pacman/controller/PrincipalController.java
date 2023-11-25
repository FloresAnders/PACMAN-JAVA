package cr.ac.una.pacman.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.pacman.user.JSON;
import cr.ac.una.pacman.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PrincipalController extends Controller implements Initializable {

    @FXML
    private JFXButton btnIniciar;
    @FXML
    private JFXButton btnJugador;
    @FXML
    private JFXButton btnConfig;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private JFXButton btnRanking;
    @FXML
    private ImageView imgHeader;
    @FXML
    private ImageView imgFooter;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
       imgHeader.setImage(new Image("/cr/ac/una/pacman/images/header.png"));
       imgFooter.setImage(new Image("/cr/ac/una/pacman/images/footer.png"));
    }    

    @FXML
    private void onBtnIniciar(ActionEvent event) {
        if (JSON.getInstance().obtenerValorBoolean("isRegistered")) {
            FlowController.getInstance().goViewInWindow("levels", Boolean.FALSE);
            getStage().close();
        }else{
        FlowController.getInstance().goViewInWindow("playerData", Boolean.FALSE);
        }
    }

    @FXML
    private void onBtnJugador(ActionEvent event) {
        
    }

    @FXML
    private void onBtnConfig(ActionEvent event) {
       
    }

    @FXML
    private void onBtnSalir(ActionEvent event) {
        getStage().close();
    }

    @Override
    public void initialize() {
        
    }

    @FXML
    private void onBtnRanking(ActionEvent event) {
    }
    
}
