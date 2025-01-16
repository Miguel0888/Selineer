package comdirect;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        // Dynamischen Klassenpfad für plattformspezifische JavaFX-JARs setzen
        configurePlatformSpecificClasspath();

        // Starte die JavaFX-Anwendung
        launch(args);
    }

    @Override
    public void init() throws Exception {
        // Starte Spring Context
        springContext = new SpringApplicationBuilder(ComdirectApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Lade FXML über SpringFXMLLoader
        SpringFXMLLoader fxmlLoader = springContext.getBean(SpringFXMLLoader.class);
        Parent root = fxmlLoader.load("/views/main.fxml").load();

        primaryStage.setTitle("Comdirect Dashboard");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);

        // Beenden der Anwendung bei Fenster schließen
        primaryStage.setOnCloseRequest(event -> {
            try {
                stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        // Schließe den Spring Context, falls er initialisiert wurde
        if (springContext != null) {
            springContext.close();
        }
        // Beende die JavaFX-Plattform
        javafx.application.Platform.exit();

        // Optional: Erzwinge das Beenden der JVM
        System.exit(0);
    }

    private static void configurePlatformSpecificClasspath() {
        String os = System.getProperty("os.name").toLowerCase();
        String osSuffix;

        // Betriebssystem erkennen
        if (os.contains("win")) {
            osSuffix = "-win";
        } else if (os.contains("nux") || os.contains("nix")) {
            osSuffix = "-linux";
        } else if (os.contains("mac")) {
            osSuffix = "-mac";
        } else {
            throw new UnsupportedOperationException("Unsupported OS: " + os);
        }

        // Prüfe, ob wir aus einem gepackten JAR starten (BOOT-INF/lib vorhanden)
        String libPath = "BOOT-INF/lib";
        File libDir = new File(libPath);

        List<String> loaderPaths = new ArrayList<>();
        if (libDir.exists() && libDir.isDirectory()) {
            System.out.println("Running from packaged JAR.");
            // Plattformspezifische JARs aus BOOT-INF/lib laden
            for (File file : libDir.listFiles()) {
                String fileName = file.getName();
                if (fileName.endsWith(osSuffix + ".jar") ||
                        (!fileName.contains("-win") && !fileName.contains("-linux") && !fileName.contains("-mac"))) {
                    loaderPaths.add(file.getAbsolutePath());
                }
            }
        } else {
            System.out.println("Running from IDE or exploded JAR.");
            // IDE-Modus: Abhängigkeiten direkt aus dem Classpath laden
            // In diesem Fall ignorieren wir `loader.path` und setzen nichts weiter
            // Optional: Debug-Ausgabe
            System.out.println("Classpath-based execution, no loader.path configured.");
        }

        // Wenn plattformspezifische Loader-Pfade gefunden wurden, setzen
        if (!loaderPaths.isEmpty()) {
            System.setProperty("loader.path", String.join(",", loaderPaths));
        }
    }
}
