// package comdirect.controllers;

// import com.microsoft.playwright.Page;
// import comdirect.config.ComdirectConfig;
// import javafx.scene.control.Alert;
// import javafx.scene.control.TextInputDialog;

// import java.net.URLDecoder;
// import java.nio.charset.StandardCharsets;
// import java.util.Optional;

// /**
//  * Todo: Sollten nach DDD in dedizierte Service-Klassen, z. B. JavaScriptService oder WebViewService verschoben werden.
//  */
// public class BrowserUtils {
//     static String addDebugCode()
//     {
//         return "";
//     }

//     // Gibt console.log-Ausgaben in der WebView als JavaScript-Alerts aus
//     static String addConsoleLogCode() {
//         return "console.log = function(...messages) {" +
//                 "    let logDiv = document.getElementById('logDiv');" +
//                 "    if (!logDiv) {" +
//                 "        logDiv = document.createElement('div');" +
//                 "        logDiv.id = 'logDiv';" +
//                 "        logDiv.style.position = 'fixed';" +
//                 "        logDiv.style.top = '0';" +
//                 "        logDiv.style.left = '0';" +
//                 "        logDiv.style.width = '20%';" + // Nur 30% des Bildschirms breit
//                 "        logDiv.style.height = '100%';" + // Gesamte Höhe des Bildschirms
//                 "        logDiv.style.overflowY = 'auto';" + // Scrollbar für lange Logs
//                 "        logDiv.style.backgroundColor = 'red';" +
//                 "        logDiv.style.color = 'white';" +
//                 "        logDiv.style.padding = '10px';" +
//                 "        logDiv.style.fontSize = '14px';" +
//                 "        logDiv.style.zIndex = '9999';" +
//                 "        logDiv.style.whiteSpace = 'pre-wrap';" + // Ermöglicht Zeilenumbrüche
//                 "        document.body.appendChild(logDiv);" +
//                 "    }" +
//                 "    const logMessage = messages.map(m => typeof m === 'object' ? JSON.stringify(m, null, 2) : m).join(' ');" +
//                 "    const logEntry = document.createElement('div');" +
//                 "    logEntry.innerText = logMessage;" +
//                 "    logEntry.style.marginBottom = '5px';" + // Abstand zwischen Logs
//                 "    logDiv.appendChild(logEntry);" +
//                 "    logDiv.scrollTop = logDiv.scrollHeight;" + // Automatisch nach unten scrollen
//                 "};";
//     }

//     static String addBridgeCode() {
//         return "window.bridge = {" +
//                 "    onLinkClicked: function(href) {" +
//                 "        console.log('[Bridge] Link geklickt:', href);" +
//                 "        try {" +
//                 "            window.location.href = 'bridge://onLinkClicked?href=' + encodeURIComponent(href);" +
//                 "        } catch (error) {" +
//                 "            console.error('[Bridge] Fehler beim Verarbeiten von onLinkClicked:', error);" +
//                 "        }" +
//                 "    }," +
//                 "    onFormSubmitted: function(formData) {" +
//                 "        console.log('[Bridge] Formular abgeschickt:', formData);" +
//                 "        try {" +
//                 "            window.location.href = 'bridge://onFormSubmitted?formData=' + encodeURIComponent(formData);" +
//                 "        } catch (error) {" +
//                 "            console.error('[Bridge] Fehler beim Verarbeiten von onFormSubmitted:', error);" +
//                 "        }" +
//                 "    }" +
//                 "};" +
//                 // Logs für Events
//                 "document.querySelectorAll('a').forEach(link => link.addEventListener('click', e => {" +
//                 "    e.preventDefault();" + // Verhindert Standardnavigation
//                 "    console.log('[Event] Link geklickt:', link.href);" +
//                 "    window.bridge.onLinkClicked(link.href);" +
//                 "}));" +
//                 "document.querySelectorAll('form').forEach(form => form.addEventListener('submit', e => {" +
//                 "    e.preventDefault();" + // Verhindert Standardformularabsenden
//                 "    const formData = new FormData(form);" +
//                 "    const formObject = {};" +
//                 "    formData.forEach((value, key) => { formObject[key] = value; });" +
//                 "    console.log('[Event] Formular abgeschickt:', formObject);" +
//                 "    window.bridge.onFormSubmitted(JSON.stringify(formObject));" +
//                 "}));";
//     }


//     static boolean requestCredentialsFromUser(ComdirectConfig config1) {
//         if (config1.getLogin().getUser() == null || config1.getLogin().getUser().isEmpty()) {
//             TextInputDialog userDialog = new TextInputDialog();
//             userDialog.setTitle("Zugangsnummer erforderlich");
//             userDialog.setHeaderText("Bitte geben Sie Ihre Zugangsnummer ein:");
//             userDialog.setContentText("Zugangsnummer:");
//             Optional<String> result = userDialog.showAndWait();
//             if( result.isEmpty())
//             {
//                 return false;
//             }
//             result.ifPresent(userNumber -> config1.getLogin().setUser(userNumber));
//         }

//         if (config1.getLogin().getPin() == null || config1.getLogin().getPin().isEmpty()) {
//             TextInputDialog pinDialog = new TextInputDialog();
//             pinDialog.setTitle("PIN erforderlich");
//             pinDialog.setHeaderText("Bitte geben Sie Ihre PIN ein:");
//             pinDialog.setContentText("PIN:");
//             Optional<String> result = pinDialog.showAndWait();
//             if( result.isEmpty())
//             {
//                 return false;
//             }
//             result.ifPresent(pin -> config1.getLogin().setPin(pin)); // ToDo: Never save PIN/Password readably!
//         }
//         return true;
//     }

//     static void showError(String title, String header, String content) {
//         Alert alert = new Alert(Alert.AlertType.ERROR);
//         alert.setTitle(title);
//         alert.setHeaderText(header);
//         alert.setContentText(content);
//         alert.showAndWait();
//     }

//     public static void closeCookieBanner(Page page) {
//         if (page.locator("button:has-text('Alle akzeptieren')").isVisible()) {
//             page.click("button:has-text('Alle akzeptieren')");
//             System.out.println("Cookie-Banner akzeptiert.");
//         }
//     }

//     ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//     /// Helper methods
//     ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//     static String extractQueryParam(String url, String param) {
//         try {
//             String query = url.split("\\?")[1];
//             String[] pairs = query.split("&");
//             for (String pair : pairs) {
//                 String[] keyValue = pair.split("=");
//                 if (keyValue[0].equals(param)) {
//                     return URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.name());
//                 }
//             }
//         } catch (Exception e) {
//             System.err.println("Fehler beim Extrahieren des Parameters: " + e.getMessage());
//         }
//         return null;
//     }

//     static String resolveUrl(String href, Page page) {
//         try {
//             if (href.startsWith("//")) {
//                 // Protokoll-relative URL ergänzen
//                 return "https:" + href;
//             }

//             if (href.startsWith("#")) {
//                 // Interner Anker, prüfe, ob der Anker bereits in der aktuellen URL vorhanden ist
//                 String currentUrl = page.url();
//                 if (currentUrl.contains(href)) {
//                     // Anker ist bereits vorhanden, URL unverändert zurückgeben
//                     return currentUrl;
//                 }

//                 // Anker hinzufügen, wenn er noch nicht vorhanden ist
//                 return currentUrl + href;
//             }

//             if (href.startsWith("http://") || href.startsWith("https://")) {
//                 // Absolute URL
//                 return href;
//             }

//             // Relative URL in absolute URL umwandeln
//             String baseUrl = page.url();
//             return new java.net.URL(new java.net.URL(baseUrl), href).toString();
//         } catch (Exception e) {
//             System.err.println("Fehler beim Erstellen der absoluten URL: " + e.getMessage());
//             return href; // Fallback auf den Original-Link
//         }
//     }

//     static boolean isValidUrl(String url) {
//         try {
//             new java.net.URI(url);
//             return true;
//         } catch (Exception e) {
//             System.err.println("Ungültige URL: " + url);
//             return false;
//         }
//     }
// }
