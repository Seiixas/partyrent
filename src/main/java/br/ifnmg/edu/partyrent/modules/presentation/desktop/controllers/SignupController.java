package br.ifnmg.edu.partyrent.modules.presentation.desktop.controllers;

import br.ifnmg.edu.partyrent.modules.addresses.dtos.CreateAddressDTO;
import br.ifnmg.edu.partyrent.modules.users.controllers.UsersController;
import br.ifnmg.edu.partyrent.modules.users.dtos.CreateUserDTO;
import br.ifnmg.edu.partyrent.shared.utils.Password;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@FxmlView("/presentation/scenes/signup.fxml")
public class SignupController extends GenericController implements Initializable {
    @FXML
    public MFXButton button_cancel;

    @FXML
    private MFXButton button_signup;

    @FXML
    private MFXDatePicker datepicker_birthdate;

    @FXML
    private MFXTextField field_cep;

    @FXML
    private MFXTextField field_city;

    @FXML
    private MFXTextField field_complement;

    @FXML
    private MFXTextField field_cpf;

    @FXML
    private MFXTextField field_district;

    @FXML
    private MFXTextField field_email;

    @FXML
    private MFXTextField field_name;

    @FXML
    private MFXTextField field_number;

    @FXML
    private MFXPasswordField field_password;

    @FXML
    private MFXPasswordField field_password_confirm;

    @FXML
    private MFXTextField field_phone;

    @FXML
    private MFXTextField field_rg;

    @FXML
    private MFXTextField field_state;

    @FXML
    private MFXTextField field_street;

    @FXML
    private VBox vbox_signup;

    @FXML
    private VBox vbox_errors;

    @FXML
    private Label label_empty_fields;

    @FXML
    private Label label_password_match;

    @FXML
    private Label label_password_security;

    @Autowired
    UsersController usersController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label_empty_fields = createErrorLabel("Todos os campos devem ser preenchidos!");
        label_password_match = createErrorLabel("As senhas devem coincidir!");
        label_password_security = createErrorLabel("As senhas devem conter pelo menos 8 caracteres, um caractere especial, um caractere maiúsculo, um caractere minúsculo e um dígito");

        datepicker_birthdate.setConverterSupplier(
                () -> new DateStringConverter("dd/MM/yyyy",
                        datepicker_birthdate.getLocale()));
    }

    private List<MFXTextField> fieldList() {
        return List.of(field_email, field_password, field_password_confirm, field_name, field_cpf, field_rg, field_phone, datepicker_birthdate, field_street, field_number, field_complement, field_district, field_city, field_state, field_cep);
    }

    private List<Control> controlList() {
        List<Control> controls = new ArrayList<>();

        controls.addAll(fieldList());
        controls.addAll(List.of(button_signup, button_cancel));

        return controls;
    }

    @FXML
    void goBack() {
        loadScene(vbox_signup, LoginController.class);
    }

    @FXML
    void signup() {
        // Remove the existing errors, if any
        vbox_errors.getChildren().removeAll(
                label_empty_fields,
                label_password_match,
                label_password_security);

        // Block all the controls available
        List<Control> controls = controlList();
        controls.forEach(control -> control.setDisable(true));

        // Create a hashmap of the available fields contents
        HashMap<String, String> fieldContents = new HashMap<>();
        fieldList().forEach(field -> fieldContents.put(field.getId(), field.getText()));

        // Atomic boolean due to lambda usage
        AtomicBoolean missingFields = new AtomicBoolean(false);

        // If any empty text is found, we can't proceed
        fieldContents.keySet().forEach(key -> {
            String value = fieldContents.get(key);
            if (value.isEmpty() || value.isBlank()) {
                missingFields.set(true);
            }
        });

        if (missingFields.get()) {
            addError(label_empty_fields);
            return;
        }

        if (!fieldContents.get("field_password").equals(fieldContents.get("field_password_confirm"))) {
            addError(label_password_match);
            return;
        }

        if (!Password.isValid(fieldContents.get("field_password")).isEmpty()) {
            addError(label_password_security);
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthDay = LocalDate.parse(fieldContents.get("datepicker_birthdate"), formatter);

        CreateAddressDTO addressDTO = new CreateAddressDTO(
                fieldContents.get("field_street"),
                fieldContents.get("field_district"),
                fieldContents.get("field_city"),
                fieldContents.get("field_state"),
                fieldContents.get("field_complement"),
                fieldContents.get("field_number"),
                0.0,
                0.0,
                fieldContents.get("cep")
        );

        CreateUserDTO userDTO = new CreateUserDTO(
                fieldContents.get("field_name"),
                fieldContents.get("field_password"),
                fieldContents.get("field_email"),
                birthDay,
                fieldContents.get("field_cpf"),
                fieldContents.get("field_rg"),
                "user",
                fieldContents.get("field_phone"),
                addressDTO
        );

        Thread thread = new Thread(() -> {
            try {
                ResponseEntity<Void> response = usersController.store(userDTO);

                if (response.getStatusCode().is2xxSuccessful()) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(
                                Alert.AlertType.CONFIRMATION,
                                "Usuário cadastrado com sucesso!",
                                ButtonType.CLOSE);

                        alert.showAndWait();

                        loadScene(vbox_signup, LoginController.class);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();

                Platform.runLater(() -> {
                    Alert alert = errorAlert(e);
                    alert.show();
                    alert.setOnCloseRequest(dialogEvent -> loadScene(vbox_signup, LoginController.class));
                });
            }
        });

        thread.start();
    }

    private static Alert errorAlert(Exception e) {
        String errorMessage = "Um erro ocorreu: %s";

        String formattedErrorMessage = String.format(
                errorMessage, e.getMessage());

        return new Alert(Alert.AlertType.ERROR, formattedErrorMessage);
    }

    private void addError(Label error) {
        vbox_errors.getChildren().add(error);
        controlList().forEach(control -> control.setDisable(false));
    }

    private Label createErrorLabel(String error) {
        Label label = new Label(error);

        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setTextFill(Paint.valueOf("#ff0526"));

        return label;
    }
}