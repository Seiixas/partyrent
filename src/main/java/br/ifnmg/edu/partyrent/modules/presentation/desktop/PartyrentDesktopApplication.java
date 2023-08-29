package br.ifnmg.edu.partyrent.modules.presentation.desktop;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@EntityScan("br.ifnmg.edu.partyrent")
@ComponentScan({"br.ifnmg.edu.partyrent", "net.rgielen.fxweaver"})
@SpringBootApplication
public class PartyrentDesktopApplication {
    public static void main(String[] args) {
        Application.launch(PartyrentJavaFxApplication.class, args);
    }

}