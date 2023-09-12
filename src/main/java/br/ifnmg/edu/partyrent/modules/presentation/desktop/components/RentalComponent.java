package br.ifnmg.edu.partyrent.modules.presentation.desktop.components;

import br.ifnmg.edu.partyrent.modules.rentals.entities.Rental;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class RentalComponent extends HBox {
    @FXML
    private Label label_name;

    @FXML
    private Label label_description;

    public RentalComponent(Rental rental) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentation/components/rental.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(RentalComponent.this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadContent(rental);
    }

    private void loadContent(Rental rental) {
        label_name.setText(label_name.getText().formatted(rental.getStart_date()));
        label_description.setText(label_description.getText().formatted(rental.getEnd_date()));
    }
}
