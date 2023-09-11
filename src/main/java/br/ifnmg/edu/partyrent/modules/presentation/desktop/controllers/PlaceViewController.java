package br.ifnmg.edu.partyrent.modules.presentation.desktop.controllers;

import br.ifnmg.edu.partyrent.modules.places.entities.Place;
import br.ifnmg.edu.partyrent.modules.presentation.desktop.SessionManager;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ResourceBundle;

@Service
@FxmlView("/presentation/scenes/place_view.fxml")
public class PlaceViewController extends GenericController implements Initializable {
    @FXML
    private Label label_title;
    @FXML
    private Label label_description;
    @FXML
    private Label label_address;
    @FXML
    private Label label_capacity;
    @FXML
    private Label label_price;
    @FXML
    private ImageView image_back;

    @FXML
    private GridPane grid_pane;

    @FXML
    private VBox vbox_root;

    @Autowired
    private SessionManager sessionManager;


    @FXML
    private void back() {
        loadScene(image_back, HomeController.class);
    }

    @FXML
    private void done() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Place place = (Place) sessionManager.getObject("place");

        vbox_root.addEventHandler(EventType.ROOT, event -> {
            vbox_root.minWidthProperty().bind(vbox_root.getScene().widthProperty());
            grid_pane.minWidthProperty().bind(grid_pane.getScene().widthProperty());
        });

        label_title.setText(place.getName());
        label_description.setText(place.getDescription());
        label_address.setText(place.getAddress().toString());
        label_capacity.setText(place.getCapacity().toString());
        label_price.setText(place.getPrice().toString());
    }
}
