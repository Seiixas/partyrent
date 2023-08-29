package br.ifnmg.edu.partyrent.modules.presentation.desktop.controllers;

import br.ifnmg.edu.partyrent.modules.presentation.desktop.SessionManager;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

public class GenericController {
    @Autowired
    private ConfigurableApplicationContext springContext;

    @Autowired
    protected SessionManager sessionManager;

    public GenericController() {
    }

    public void loadScene(Node parent, Class controller) {
        FxWeaver fxWeaver = springContext.getBean(FxWeaver.class);
        Parent root = (Parent) fxWeaver.loadView(controller);
        Scene scene = new Scene(root);

        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);

        Stage stage = (Stage) parent.getScene().getWindow();

        stage.setScene(scene);
        stage.show();

    }

}