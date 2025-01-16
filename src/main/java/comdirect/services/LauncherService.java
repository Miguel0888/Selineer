// package comdirect.services;

// import org.jsoup.Jsoup;
// import org.jsoup.nodes.Document;
// import org.jsoup.nodes.Element;
// import org.springframework.stereotype.Service;

// import java.io.*;
// import java.net.URL;

// /**
//  * Service zum Herunterladen und Starten von JNLP-Dateien. (Java Web Start)
//  */
// @Service
// public class LauncherService {

//     /**
//      * Verarbeitet das Herunterladen und Starten der JNLP-Datei.
//      *
//      * @param jnlpUrl URL der JNLP-Datei
//      */
//     private void handleJNLPDownload(String jnlpUrl) {
//         try {
//             // JNLP-Datei herunterladen
//             File jnlpFile = downloadJNLP(jnlpUrl);

//             // Authentifizierungstoken aus der JNLP-Datei extrahieren
//             String authToken = parseJNLPFile(jnlpFile);

// //            // Anwendung mit dem Token starten
// //            startApplication(authToken);
// //
// //            // Erfolgsnachricht anzeigen
// //            showInfo("Success", "JNLP processed successfully", "The application has been started.");

//         } catch (Exception e) {
//             // Fehler anzeigen
// //            showError("Error", "Failed to handle JNLP file.", e.getMessage());
//             e.printStackTrace();
//         }
//     }

//     /**
//      * Lädt die JNLP-Datei herunter.
//      *
//      * @param jnlpUrl URL der JNLP-Datei
//      * @return Lokale Datei der heruntergeladenen JNLP-Datei
//      * @throws IOException Falls ein Fehler beim Download auftritt
//      */
//     private File downloadJNLP(String jnlpUrl) throws IOException {
//         URL url = new URL(jnlpUrl);
//         File jnlpFile = new File("session.jnlp");
//         try (InputStream in = url.openStream(); OutputStream out = new FileOutputStream(jnlpFile)) {
//             in.transferTo(out);
//         }
//         return jnlpFile;
//     }

//     /**
//      * Parst die JNLP-Datei und extrahiert das Authentifizierungstoken.
//      *
//      * @param jnlpFile Lokale Datei der JNLP-Datei
//      * @return Authentifizierungstoken
//      * @throws IOException Falls ein Fehler beim Parsen auftritt
//      */
//     private String parseJNLPFile(File jnlpFile) throws IOException {
//         Document doc = Jsoup.parse(jnlpFile, "UTF-8");
//         for (Element argument : doc.select("application-desc > argument")) {
//             if (argument.text().startsWith("authentifizierung=")) {
//                 return argument.text().split("=")[1];
//             }
//         }
//         throw new IllegalStateException("No authentifizierung token found in JNLP file");
//     }

//     /**
//      * Startet die Java-Anwendung mit dem extrahierten Token.
//      *
//      * @param authToken Authentifizierungstoken
//      * @throws IOException Falls ein Fehler beim Starten auftritt
//      */
//     private void startApplication(String authToken) throws IOException {
//         String javaCommand = String.format(
//                 "java -Xmx1024m -cp tbmxclient.jar:tbmxclient-oss.jar:tbmxclient-skin-comdirect.jar de.xtpro.xtpclient.XTPMain tbmx.client.authentifizierung=%s",
//                 authToken
//         );
//         Runtime.getRuntime().exec(javaCommand);
//     }
// }
