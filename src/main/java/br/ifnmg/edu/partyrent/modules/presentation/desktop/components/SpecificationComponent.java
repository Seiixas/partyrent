package br.ifnmg.edu.partyrent.modules.presentation.desktop.components;

import br.ifnmg.edu.partyrent.modules.places.entities.Specification;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class SpecificationComponent extends HBox {
    @FXML
    private Label label_name;

    @FXML
    private Label label_description;

    @FXML
    public MFXButton button_delete;

    public SpecificationComponent(Specification specification) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentation/components/specification.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(SpecificationComponent.this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadContent(specification);
    }

    private void loadContent(Specification specification) {
        label_name.setText(label_name.getText().formatted(specification.getName()));
        label_description.setText(label_description.getText().formatted(specification.getDescription()));
    }
}
