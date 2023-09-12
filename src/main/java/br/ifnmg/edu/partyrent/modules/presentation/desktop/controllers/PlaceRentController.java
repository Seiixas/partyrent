package br.ifnmg.edu.partyrent.modules.presentation.desktop.controllers;

import br.ifnmg.edu.partyrent.modules.places.entities.Place;
import br.ifnmg.edu.partyrent.modules.presentation.desktop.shared.utils.Error;
import br.ifnmg.edu.partyrent.modules.presentation.desktop.shared.validators.BasicValidator;
import br.ifnmg.edu.partyrent.modules.presentation.desktop.shared.validators.ResponseValidator;
import br.ifnmg.edu.partyrent.modules.rentals.controllers.RentalsController;
import br.ifnmg.edu.partyrent.modules.rentals.controllers.ServicesController;
import br.ifnmg.edu.partyrent.modules.rentals.dtos.CreateRentalDTO;
import br.ifnmg.edu.partyrent.modules.rentals.entities.Service;
import br.ifnmg.edu.partyrent.modules.users.dtos.LoginResponseDTO;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

@org.springframework.stereotype.Service
@FxmlView("/presentation/scenes/place_rent.fxml")
public class PlaceRentController extends GenericController implements Initializable {
    @FXML
    public VBox vbox_root;

    @FXML
    public Label error_label;

    @FXML
    private VBox vbox_content;

    @FXML
    private MFXDatePicker date_start;

    @FXML
    private MFXDatePicker date_end;

    @Autowired
    private ServicesController servicesController;

    @Autowired
    private RentalsController rentalsController;

    private final ArrayList<Service> selectedServices = new ArrayList<>();

    private Place place;

    private LoginResponseDTO loginResponseDTO;

    @FXML
    private void back() {
        loadScene(vbox_root, PlaceViewController.class);
    }

    @FXML
    private void done() {
        if (BasicValidator.emptyFields(List.of(date_start, date_end), error_label)) return;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate startDate = LocalDate.parse(date_start.getText(), formatter);
        LocalDate endDate = LocalDate.parse(date_end.getText(), formatter);

        List<UUID> servicesUUIDs = selectedServices.stream().map(Service::getId).toList();

        // TODO: Prompt for time

        CreateRentalDTO createRentalDTO = new CreateRentalDTO(
                place.getId(),
                servicesUUIDs,
                startDate.atTime(7, 0),
                endDate.atTime(17, 0)
        );

        try {
            var response = rentalsController.create(createRentalDTO, loginResponseDTO.getToken());
            ResponseValidator.validateStore(response, () -> {

            });
        } catch (Exception e) {
            Error.showError(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        place = (Place) sessionManager.getObject("place");
        loginResponseDTO = (LoginResponseDTO) sessionManager.getObject("login_response");

        date_start.setConverterSupplier(
                () -> new DateStringConverter("dd/MM/yyyy",
                        date_start.getLocale()));

        date_end.setConverterSupplier(
                () -> new DateStringConverter("dd/MM/yyyy",
                        date_end.getLocale()));

        error_label.visibleProperty().set(false);

        setupServices();
    }

    private void setupServices() {
        ResponseEntity<List<Service>> servicesResponse = servicesController.findAll();

        ResponseValidator<List<Service>> responseValidator = new ResponseValidator<>();
        List<Service> servicesList = responseValidator.validate(servicesResponse);

        ArrayList<String> serviceNames = new ArrayList<>();

        servicesList.forEach(specification -> serviceNames.add(specification.getName()));

        ListView<String> listView = new ListView<>();
        listView.setMaxWidth(200);
        listView.setPrefHeight(25 * servicesList.size());

        servicesList.forEach(specification -> listView.getItems().add(specification.getName()));

        listView.setCellFactory(CheckBoxListCell.forListView(item -> {
            BooleanProperty observable = new SimpleBooleanProperty();
            observable.addListener((obs, wasSelected, isNowSelected) -> {
                        int index = serviceNames.indexOf(item);
                        Service service = servicesList.get(index);

                        if (isNowSelected) {
                            selectedServices.add(service);
                        } else {
                            if (selectedServices.contains(service)) {
                                selectedServices.remove(index);
                            }
                        }
                    }
            );
            return observable;
        }));

        vbox_content.getChildren().add(listView);
    }
}
