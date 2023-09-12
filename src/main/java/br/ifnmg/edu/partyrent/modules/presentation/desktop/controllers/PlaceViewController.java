package br.ifnmg.edu.partyrent.modules.presentation.desktop.controllers;

import br.ifnmg.edu.partyrent.modules.places.controllers.PlacesController;
import br.ifnmg.edu.partyrent.modules.places.entities.Place;
import br.ifnmg.edu.partyrent.modules.presentation.desktop.SessionManager;
import br.ifnmg.edu.partyrent.modules.users.entities.User;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    public VBox vbox_root;
    @FXML
    private MFXButton button_delete;
    @FXML
    private MFXButton button_rent;
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

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private PlacesController placesController;

    private Place place;

    @FXML
    private void back() {
        loadScene(image_back, HomeController.class);
    }

    @FXML
    private void rent() {
        loadScene(label_title, PlaceRentController.class);
    }

    @FXML
    private void delete() {
        // TODO: Confirm deletion and verifications
        placesController.delete(place.getId());
        loadScene(label_title, HomeController.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        place = (Place) sessionManager.getObject("place");
        User user = (User) sessionManager.getObject("user");

        if (!user.getOccupation().equals("admin")) {
            button_delete.visibleProperty().set(false);
        }
//        else {
//            button_rent.visibleProperty().set(false);
//        }

        label_title.setText(place.getName());
        label_description.setText(place.getDescription());
        label_address.setText(label_address.getText().formatted(place.getAddress().toString()));
        label_capacity.setText(label_capacity.getText().formatted(place.getCapacity().toString()));
        label_price.setText(label_price.getText().formatted(place.getPrice().toString()));
    }
}
