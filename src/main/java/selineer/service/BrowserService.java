package selineer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BrowserService {
    private WebSocketClient webSocketClient;

    // ToDo: Fix this!
    private String chromePath = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
    private String profileDirectory = "C:\\ChromeProfile";

    public void launchBrowser() {
        try {
            Process process = new ProcessBuilder(
                chromePath, // Verwendet den fest codierten Pfad
                "--remote-debugging-port=9222", // Aktiviert das Remote-Debugging
                "--user-data-dir=" + profileDirectory, // Nutzt das angegebene Profilverzeichnis
                "--disable-gpu" // Optional: Deaktiviert GPU-Beschleunigung
            ).start();
            System.out.println("Chrome gestartet.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectToBrowser() {
        try {
            String wsUrl = getWebSocketDebuggerUrl();
            if (wsUrl != null) {
                webSocketClient = new WebSocketClient(new URI(wsUrl)) {
                    @Override
                    public void onOpen(ServerHandshake handshake) {
                        System.out.println("Verbindung zum Browser hergestellt.");
                    }
    
                    @Override
                    public void onMessage(String message) {
                        System.out.println("Nachricht vom Browser: " + message);
                    }
    
                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        System.out.println("Verbindung geschlossen: " + reason);
                    }
    
                    @Override
                    public void onError(Exception ex) {
                        ex.printStackTrace();
                    }
                };
                webSocketClient.connect();
            } else {
                System.out.println("WebSocket-URL konnte nicht abgerufen werden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    public void navigateTo(String url) {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            String command = "{\"id\":1,\"method\":\"Page.navigate\",\"params\":{\"url\":\"" + url + "\"}}";
            webSocketClient.send(command);
        } else {
            System.out.println("WebSocket-Verbindung nicht aktiv.");
        }
    }

    public void closeBrowser() {
        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }

    public String getWebSocketDebuggerUrl() {
        try {
            URL url = new URL("http://localhost:9222/json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
    
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
    
            // JSON mit Jackson parsen
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonArray = objectMapper.readTree(response.toString());
    
            if (jsonArray.isArray() && jsonArray.size() > 0) {
                JsonNode firstPage = jsonArray.get(0);
                return firstPage.get("webSocketDebuggerUrl").asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
