package br.ifnmg.edu.partyrent.modules.presentation.desktop.controllers;

import br.ifnmg.edu.partyrent.modules.presentation.desktop.shared.validators.BasicValidator;
import br.ifnmg.edu.partyrent.modules.presentation.desktop.shared.utils.Error;
import br.ifnmg.edu.partyrent.modules.presentation.desktop.shared.validators.ResponseValidator;
import br.ifnmg.edu.partyrent.modules.rentals.controllers.ServicesController;
import br.ifnmg.edu.partyrent.modules.rentals.dtos.CreateServiceDTO;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Service
@FxmlView("/presentation/scenes/service_create.fxml")
public class ServiceCreateController extends GenericController implements Initializable {
    @FXML
    private VBox vbox_root;
    @FXML
    private MFXTextField field_name;
    @FXML
    private MFXTextField field_description;
    @FXML
    private MFXTextField field_price;
    @FXML
    private Label label_error;

    @Autowired
    private ServicesController servicesController;

    @FXML
    private void back() {
        loadScene(vbox_root, ServicesManagerController.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label_error.visibleProperty().set(false);
    }

    @FXML
    private void done() {
        List<MFXTextField> fields = List.of(field_name, field_description, field_price);

        if (BasicValidator.emptyFields(fields, label_error)) return;

        Thread thread = new Thread(() -> {
            try {

                CreateServiceDTO createServiceDTO = new CreateServiceDTO(
                        field_name.getText(),
                        field_description.getText(),
                        new BigDecimal(field_price.getText().replace(",", "."))
                );

                ResponseEntity<Void> response = servicesController.store(createServiceDTO);
                ResponseValidator.validateStore(response, () -> loadScene(vbox_root, ServicesManagerController.class));
            } catch (Exception e) {
                Error.showError(e);
            }
        });

        thread.start();
    }
}
