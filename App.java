package com.zenmgmoba.app;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.zenmgmoba.service.PersistenceService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * ZenGM MOBA GM - Professional eSports Management Simulation
 * Entry point for the JavaFX + FXGL application
 */
public class App extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("ZenGM MOBA GM");
        settings.setVersion("1.0");
        settings.setWidth(1400);
        settings.setHeight(900);
        settings.setFullScreenAllowed(false);
        settings.setResizable(true);
    }

    @Override
    protected void initInput() {
        // Global input handling can be added here if needed
    }

    @Override
    protected void initUI() {
        try {
            // Initialize persistence service
            PersistenceService.initialize();

            // Load main menu
            Parent root = FXMLLoader.load(
                    getClass().getResource("/ui/view/MainMenu.fxml")
            );
            FXGL.getGameScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to initialize application: " + e.getMessage());
        }
    }

    @Override
    protected void onUpdate(double tpf) {
        // Game loop updates can be added here
    }

    public static void main(String[] args) {
        launch(args);
    }
}
