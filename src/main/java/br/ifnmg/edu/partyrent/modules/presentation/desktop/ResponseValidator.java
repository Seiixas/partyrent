package br.ifnmg.edu.partyrent.modules.presentation.desktop;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.springframework.http.ResponseEntity;

public class ResponseValidator<T> {

    public T validate(ResponseEntity responseEntity) {
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Response error!");
            return null;
        }

        if (responseEntity.getBody() == null) {
            System.out.println("Body error!");
            return null;
        }

        return (T) responseEntity.getBody();
    }

    public static void validateStore(ResponseEntity<Void> response, Runnable onSuccess) {
        if (response.getStatusCode().is2xxSuccessful()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "Cadastrado com sucesso!",
                        ButtonType.CLOSE);

                alert.showAndWait();

                onSuccess.run();
            });
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(
                        Alert.AlertType.ERROR,
                        "Ocorreu um erro de resposta!",
                        ButtonType.CLOSE);

                alert.showAndWait();
            });
        }
    }
}
