package br.ifnmg.edu.partyrent.modules.presentation.desktop.shared.utils;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.springframework.stereotype.Service;

@Service
public class Error {
    public static void showError(Exception e) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Um erro ocorreu: ".concat(e.getMessage()));
            alert.show();
        });
    }
}
