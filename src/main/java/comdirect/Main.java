package comdirect;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        // Startet die JavaFX-Anwendung
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Lädt die FXML-Datei
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
        Parent root = loader.load();

        // Erstellt die Szene und zeigt sie an
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Comdirect Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
