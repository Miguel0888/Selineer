// package comdirect;

// import javafx.fxml.FXMLLoader;
// import org.springframework.context.ApplicationContext;
// import org.springframework.stereotype.Component;

// import java.io.IOException;
// import java.io.InputStream;

// @Component
// public class SpringFXMLLoader {

//     private final ApplicationContext applicationContext;

//     public SpringFXMLLoader(ApplicationContext applicationContext) {
//         this.applicationContext = applicationContext;
//     }

//     public FXMLLoader load(String fxmlPath) throws IOException {
//         FXMLLoader loader = new FXMLLoader();
//         loader.setControllerFactory(applicationContext::getBean); // Use Spring to create controllers
//         InputStream fxmlStream = getClass().getResourceAsStream(fxmlPath);
//         if (fxmlStream == null) {
//             throw new IOException("Could not find FXML file: " + fxmlPath);
//         }
//         loader.setLocation(getClass().getResource(fxmlPath));
//         return loader;
//     }
// }
