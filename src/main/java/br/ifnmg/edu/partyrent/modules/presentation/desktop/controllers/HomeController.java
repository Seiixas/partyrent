package br.ifnmg.edu.partyrent.modules.presentation.desktop.controllers;

import br.ifnmg.edu.partyrent.modules.places.controllers.PlacesController;
import br.ifnmg.edu.partyrent.modules.places.entities.Place;
import br.ifnmg.edu.partyrent.modules.places.entities.Specification;
import br.ifnmg.edu.partyrent.modules.users.entities.User;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

@Service
@FxmlView("/presentation/scenes/home.fxml")
public class HomeController extends GenericController implements Initializable {
    @FXML
    private MFXButton button_manage_specifications;

    @FXML
    private MFXButton button_manage_services;

    @Autowired
    private PlacesController placesController;

    @FXML
    private VBox vbox_places;

    @FXML
    private MFXButton button_add_place;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = (User) sessionManager.getObject("user");

        if (!currentUser.getOccupation().equals("admin")) {
            button_add_place.visibleProperty().set(false);
            button_manage_specifications.visibleProperty().set(false);
            button_manage_services.visibleProperty().set(false);
        }

        loadPlaces();
    }

    @FXML
    private void createPlace() {
        loadScene(vbox_places, PlaceAddController.class);
    }

    @FXML
    private void manageServices() {
        loadScene(vbox_places, ServicesManagerController.class);
    }

    private void loadPlaces() {
        ResponseEntity<List<Place>> allPlacesResponse = placesController.findAll(null, null, null);

        if (!allPlacesResponse.getStatusCode().is2xxSuccessful()) {
            System.out.println("ERRO");
            return;
        }

        List<Place> allPlaces = allPlacesResponse.getBody();

        if (allPlaces == null) {
            System.out.println("NULO");
            return;
        }

        if (allPlaces.isEmpty()) {
            VBox placeholder = new VBox();

            placeholder.alignmentProperty().set(Pos.CENTER);
            placeholder.minHeightProperty().bind(vbox_places.minHeightProperty());
            placeholder.minWidthProperty().bind(vbox_places.minWidthProperty());

            Label noPlacesLabel = new Label("Sem localizações disponíveis :(");

            noPlacesLabel.setTextFill(Paint.valueOf("#FFFFFF"));
            noPlacesLabel.fontProperty().set(Font.font(noPlacesLabel.getFont().toString(), 30));

            placeholder.getChildren().add(noPlacesLabel);
            vbox_places.getChildren().add(placeholder);
            return;
        }

        for (Place place : allPlaces) {
            vbox_places.getChildren().add(generatePlaceView(place));
        }
    }


    private Node generatePlaceView(Place place) {
        VBox root = new VBox();

        root.cursorProperty().set(Cursor.HAND);
        root.getStyleClass().add("card");

        root.setOnMouseClicked(mouseEvent -> {
            sessionManager.setObject("place", place);
            loadScene(vbox_places, PlaceViewController.class);
        });

        root.alignmentProperty().set(Pos.CENTER_LEFT);
        VBox.setMargin(root, new Insets(0, 0, 8, 8));
        setPadding(root, 8);

        Background background = new Background(new BackgroundFill(Paint.valueOf("#EBF2FA"), null, null));
        root.setBackground(background);

        Label name = createLabel(place.getName(), 28, true, noMargins());

        Label description = createLabel(
                place.getDescription(),
                14,
                true,
                new Insets(12, 0, 0, 0));

        Label address = createLabel(
                place.getAddress().toString(),
                10,
                true,
                new Insets(8, 0, 0, 0));

        Label price = createLabel(
                "Preço base: R$" + place.getPrice().toString().replace(".", ","),
                10,
                false,
                noMargins());

        Label capacity = createLabel(
                "Capacidade: " + place.getCapacity().toString() + " pessoas",
                10,
                false,
                new Insets(0, 0, 0, 0));

        root.getChildren().addAll(name, description, address, capacity, price);

        Set<Specification> specificationSet = place.getSpecifications();

        if (!specificationSet.isEmpty()) {
            Label specifications = createLabel(
                    "Especificações: ",
                    12,
                    false,
                    new Insets(8, 0, 0, 0));

            root.getChildren().add(specifications);

            place.getSpecifications().forEach(specification ->
                    root.getChildren().add(createSpecification(specification)
                    ));
        }

        return root;
    }

    private Node createSpecification(Specification specification) {
        return createLabel("* " + specification.getName(), 10, true, noMargins());
    }

    private Label createLabel(String text, int fontSize, boolean wrap, Insets margins) {
        Label label = new Label(text);

        label.setWrapText(wrap);
        setLabelFontSize(label, fontSize);
        VBox.setMargin(label, margins);

        return label;
    }

    private Insets noMargins() {
        return new Insets(0, 0, 0, 0);
    }

    private void setPadding(Pane pane, int padding) {
        pane.setPadding(new Insets(padding, padding, padding, padding));
    }

    private void setLabelFontSize(Label label, int size) {
        label.fontProperty().set(Font.font(label.getFont().getFamily(), size));
    }

}
