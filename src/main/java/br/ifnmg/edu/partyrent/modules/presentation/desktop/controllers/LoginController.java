package br.ifnmg.edu.partyrent.modules.presentation.desktop.controllers;

import br.ifnmg.edu.partyrent.modules.users.controllers.AuthController;
import br.ifnmg.edu.partyrent.modules.users.controllers.UsersController;
import br.ifnmg.edu.partyrent.modules.users.dtos.LoginDTO;
import br.ifnmg.edu.partyrent.modules.users.dtos.LoginResponseDTO;
import br.ifnmg.edu.partyrent.modules.users.entities.User;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@FxmlView("/presentation/scenes/login.fxml")
public class LoginController extends GenericController {

    @FXML
    private MFXButton button_login;

//    @FXML
//    private MFXCheckbox checkbox_keep_connected;

    @FXML
    private MFXTextField field_email;

    @FXML
    private MFXPasswordField field_password;

    @FXML
    private VBox vbox_login;

    @Autowired
    private AuthController authController;

    @Autowired
    private UsersController usersController;

    @FXML
    public void initialize() {
        button_login.prefWidthProperty().bind(vbox_login.widthProperty());
    }

    @FXML
    public void signup() {
        loadScene(vbox_login, SignupController.class);
    }


    @FXML
    public void login() {
        String userEmail = field_email.getText();
        String userPassword = field_password.getText();

        LoginDTO loginDTO = new LoginDTO(userEmail, userPassword);

        Thread thread = new Thread(() -> {
            try {
                ResponseEntity<LoginResponseDTO> loginResponse = authController.login(loginDTO);
                LoginResponseDTO loginResponseDTO = loginResponse.getBody();

                // TODO: If the user email is not found on the database,
                // the login response body will be null.

                if (loginResponse.getBody() != null && loginResponseDTO != null) {
                    User user = usersController.findOne(loginResponseDTO.getUserId()).getBody();

                    sessionManager.setObject("user", user);
                    sessionManager.setObject("login_response", loginResponseDTO);

                    Platform.runLater(() -> loadScene(vbox_login, HomeController.class));
                }
            } catch (Exception e) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(AlertType.ERROR, "Erro de autenticação: ".concat(e.getMessage()));
                    alert.show();
                });
            }
        });

        thread.start();
    }

}
