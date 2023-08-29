package br.ifnmg.edu.partyrent.modules.presentation.desktop.controllers;

import br.ifnmg.edu.partyrent.modules.users.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Service;

@Service
@FxmlView("/presentation/scenes/home.fxml")
public class HomeController extends GenericController {

    @FXML
    private Label label;

    @FXML
    public void initialize() {
        User currentUser = (User) sessionManager.getObject("user");
        label.setText(currentUser.getName());
    }
}
