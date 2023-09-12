package br.ifnmg.edu.partyrent.modules.presentation.desktop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ResourceBundle;

@Service
@FxmlView("/presentation/scenes/services_manager.fxml")
public class ServicesManagerController extends GenericController implements Initializable {
    @FXML
    private VBox vbox_root;

    @FXML
    private void back() {
        loadScene(vbox_root, HomeController.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
