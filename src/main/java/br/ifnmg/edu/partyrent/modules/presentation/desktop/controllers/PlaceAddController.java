package br.ifnmg.edu.partyrent.modules.presentation.desktop.controllers;

import br.ifnmg.edu.partyrent.modules.addresses.dtos.CreateAddressDTO;
import br.ifnmg.edu.partyrent.modules.places.controllers.PlacesController;
import br.ifnmg.edu.partyrent.modules.places.dtos.CreatePlaceDTO;
import br.ifnmg.edu.partyrent.modules.places.entities.Specification;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@FxmlView("/presentation/scenes/place_add.fxml")
public class PlaceAddController extends GenericController implements Initializable {
    @FXML
    private Label error_label;

    // ====================
    // Basic properties
    // ====================

    @FXML
    private MFXTextField field_name;
    @FXML
    private MFXTextField field_description;
    @FXML
    public MFXTextField field_capacity;
    @FXML
    public MFXTextField field_price;

    // ====================
    // Address
    // ====================

    @FXML
    private MFXTextField field_street;
    @FXML
    private MFXTextField field_number;
    @FXML
    private MFXTextField field_district;
    @FXML
    private MFXTextField field_state;
    @FXML
    private MFXTextField field_cep;
    @FXML
    private MFXTextField field_city;
    @FXML
    private MFXTextField field_complement;

    // ====================
    // Specifications
    // ====================

    @FXML
    public VBox vbox_specifications;

    // ====================
    // Others
    // ====================

    @FXML
    private ImageView image_back;

    private final ArrayList<Specification> selectedSpecifications = new ArrayList<>();

    @Autowired
    private PlacesController placesController ;

    private List<MFXTextField> fieldList() {
        return List.of(field_name, field_description, field_capacity, field_price, field_street, field_number, field_complement, field_district, field_city, field_state, field_cep);
    }

    @FXML
    private void done() {
        System.out.println(selectedSpecifications);

        List<MFXTextField> controls = fieldList();
        controls.forEach(control -> control.setDisable(true));

        HashMap<String, String> fieldContents = new HashMap<>();
        fieldList().forEach(field -> fieldContents.put(field.getId(), field.getText()));

        AtomicBoolean missingFields = new AtomicBoolean(false);

        fieldContents.keySet().forEach(key -> {
            String value = fieldContents.get(key);
            if (value.isEmpty() || value.isBlank()) {
                missingFields.set(true);
            }
        });

        if (missingFields.get()) {
            error_label.setVisible(true);
            fieldList().forEach(field -> field.setDisable(false));
            return;
        }

        CreateAddressDTO addressDTO = new CreateAddressDTO(
                fieldContents.get("field_street"),
                fieldContents.get("field_district"),
                fieldContents.get("field_city"),
                fieldContents.get("field_state"),
                fieldContents.get("field_complement"),
                fieldContents.get("field_number"),
                0.0,
                0.0,
                fieldContents.get("field_cep")
        );

        System.out.println(addressDTO);

        CreatePlaceDTO placeDTO = new CreatePlaceDTO(
                fieldContents.get("field_name"),
                fieldContents.get("field_description"),
                Integer.parseInt(fieldContents.get("field_capacity")),
                new BigDecimal(fieldContents.get("field_price").replace(",", ".")),
                addressDTO
        );

        System.out.println(placeDTO);

        Thread thread = new Thread(() -> {
            try {
                ResponseEntity<Void> response = placesController.store(placeDTO);

                if (response.getStatusCode().is2xxSuccessful()) {

                    Platform.runLater(() -> {
                        Alert alert = new Alert(
                                Alert.AlertType.CONFIRMATION,
                                "Localização cadastrada com sucesso!",
                                ButtonType.CLOSE);

                        alert.showAndWait();

                        loadScene(vbox_specifications, HomeController.class);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();

                Platform.runLater(() -> {
                    String errorMessage = "Um erro ocorreu: %s";

                    String formattedErrorMessage = String.format(
                            errorMessage, e.getMessage());

                    new Alert(Alert.AlertType.ERROR, formattedErrorMessage).show();
                });
            }
        });

        thread.start();
    }

    @FXML
    private void back() {
        loadScene(image_back, HomeController.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        error_label.setVisible(false);
//        setupSpecifications();
    }

//    private void setupSpecifications() {
//        ResponseEntity<List<Specification>> specificationListResponse = specificationsController.findAll();
//
//        if (!specificationListResponse.getStatusCode().is2xxSuccessful()) {
//            System.out.println("ResponseError");
//            return;
//        }
//
//        if (specificationListResponse.getBody() == null) {
//            System.out.println("ResponseBody is null!");
//            return;
//        }
//
//        List<Specification> specificationList = specificationListResponse.getBody();
//        ArrayList<String> specificationNames = new ArrayList<>();
//
//        specificationList.forEach(specification -> specificationNames.add(specification.getName()));
//
//        ListView<String> listView = new ListView<>();
//        listView.setMaxWidth(200);
//
//        specificationList.forEach(specification -> listView.getItems().add(specification.getName()));
//
//        listView.setCellFactory(CheckBoxListCell.forListView(item -> {
//            BooleanProperty observable = new SimpleBooleanProperty();
//            observable.addListener((obs, wasSelected, isNowSelected) -> {
//                        int index = specificationNames.indexOf(item);
//                        Specification selectedSpecification = specificationList.get(index);
//
//                        if (isNowSelected) {
//                            selectedSpecifications.add(selectedSpecification);
//                        } else {
//                            if (selectedSpecifications.contains(selectedSpecification)) {
//                                selectedSpecifications.remove(index);
//                            }
//                        }
//                    }
//            );
//            return observable;
//        }));
//
//        vbox_specifications.getChildren().add(listView);
//    }
}
