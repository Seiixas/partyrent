package br.ifnmg.edu.partyrent.modules.presentation.desktop.components;

import br.ifnmg.edu.partyrent.modules.rentals.entities.Service;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ServiceComponent extends HBox {
    @FXML
    private Label label_name;

    @FXML
    private Label label_description;

    @FXML
    private Label label_price;

    @FXML
    public MFXButton button_delete;

    public ServiceComponent(Service service) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentation/components/service.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(ServiceComponent.this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadContent(service);
    }

    private void loadContent(Service service) {
        label_name.setText(label_name.getText().formatted(service.getName()));
        label_description.setText(label_description.getText().formatted(service.getDescription()));
        label_price.setText(label_price.getText().formatted(service.getPrice().toString()));
    }
}
