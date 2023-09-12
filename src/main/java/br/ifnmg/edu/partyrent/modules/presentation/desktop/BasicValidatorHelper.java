package br.ifnmg.edu.partyrent.modules.presentation.desktop;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.scene.control.Label;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BasicValidatorHelper {
    public static boolean emptyFields(List<MFXTextField> fields, Label labelError) {
        AtomicBoolean errors = new AtomicBoolean(false);

        fields.forEach(field -> {
            if (field.getText().isBlank() || field.getText().isEmpty()) {
                errors.set(true);
            }
        });

        if (errors.get()) {
            labelError.visibleProperty().set(true);
            return true;
        }
        return false;
    }
}
