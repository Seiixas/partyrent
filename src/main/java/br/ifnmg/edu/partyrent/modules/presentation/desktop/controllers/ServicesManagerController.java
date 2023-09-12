package br.ifnmg.edu.partyrent.modules.presentation.desktop.controllers;

import br.ifnmg.edu.partyrent.modules.presentation.desktop.ResponseValidator;
import br.ifnmg.edu.partyrent.modules.presentation.desktop.components.ServiceComponent;
import br.ifnmg.edu.partyrent.modules.rentals.controllers.ServicesController;
import br.ifnmg.edu.partyrent.modules.rentals.entities.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@org.springframework.stereotype.Service
@FxmlView("/presentation/scenes/services_manager.fxml")
public class ServicesManagerController extends GenericController implements Initializable {
    @FXML
    private VBox vbox_content;

    @FXML
    private VBox vbox_root;

    @Autowired
    private ServicesController servicesController;

    @FXML
    private void back() {
        loadScene(vbox_root, HomeController.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var servicesResponse = servicesController.findAll();

        ResponseValidator<List<Service>> validator = new ResponseValidator<>();
        List<Service> services = validator.validate(servicesResponse);

        if (services == null)
            return;

        services.forEach(service -> {
            ServiceComponent serviceComponent = new ServiceComponent(service);
            vbox_content.getChildren().add(serviceComponent);

            serviceComponent.button_delete.setOnMouseClicked(mouseEvent -> {
                    servicesController.delete(service.getId());
                    loadScene(vbox_content, ServicesManagerController.class);
                }
            );
        });
    }
}
