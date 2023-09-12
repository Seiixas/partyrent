package br.ifnmg.edu.partyrent.modules.presentation.desktop.controllers;

import br.ifnmg.edu.partyrent.modules.places.controllers.SpecificationsController;
import br.ifnmg.edu.partyrent.modules.places.entities.Specification;
import br.ifnmg.edu.partyrent.modules.presentation.desktop.shared.validators.ResponseValidator;
import br.ifnmg.edu.partyrent.modules.presentation.desktop.components.SpecificationComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@org.springframework.stereotype.Service
@FxmlView("/presentation/scenes/specifications_manager.fxml")
public class SpecificationsManagerController extends GenericController implements Initializable {
    @FXML
    private VBox vbox_content;
    @FXML
    private VBox vbox_root;

    @Autowired
    private SpecificationsController specificationsController;

    @FXML
    private void back() {
        loadScene(vbox_root, HomeController.class);
    }

    @FXML
    private void create() {
        loadScene(vbox_root, SpecificationCreateController.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var servicesResponse = specificationsController.findAll();

        ResponseValidator<List<Specification>> validator = new ResponseValidator<>();
        List<Specification> specifications = validator.validate(servicesResponse);

        if (specifications == null)
            return;

        specifications.forEach(specification -> {
            SpecificationComponent serviceComponent = new SpecificationComponent(specification);
            vbox_content.getChildren().add(serviceComponent);

            serviceComponent.button_delete.setOnMouseClicked(mouseEvent -> {
                    specificationsController.delete(specification.getId());
                    loadScene(vbox_content, SpecificationsManagerController.class);
                }
            );
        });
    }
}
