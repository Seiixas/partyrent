package br.ifnmg.edu.partyrent.modules.presentation.desktop;

import br.ifnmg.edu.partyrent.modules.presentation.desktop.controllers.LoginController;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class PartyrentJavaFxApplication extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void start(Stage stage) {
        FxWeaver fxWeaver = springContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(LoginController.class);
        Scene scene = new Scene(root);

        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.springContext = new SpringApplicationBuilder()
                .sources(PartyrentDesktopApplication.class)
                .run(args);
    }

    @Override
    public void stop() {
        this.springContext.close();
        Platform.exit();
    }
    
}