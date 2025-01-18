
package selineer.adapter;

import selineer.api.Browser;
import selineer.api.BrowserContext;
import selineer.api.BrowserType;
import selineer.api.CDPSession;
import selineer.api.Page;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import org.java_websocket.client.WebSocketClient;

public class BrowserAdapter implements Browser {
    private final String browserName;
    private WebSocketClient webSocketClient;

    // ToDo: Fix this!
    private String chromePath = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
    private String profileDirectory = "C:\\ChromeProfile";

    public BrowserAdapter(String browserName) {
        this.browserName = browserName;
        launchBrowser();
    }

    @Override
    public BrowserType browserType() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void close(CloseOptions options) {
        if (webSocketClient != null) {
            // Optionen für das Schließen könnten hier berücksichtigt werden
            if (options != null) {
                System.out.println("Optionen für close: " + options);
            }
    
            webSocketClient.close();
            System.out.println("Browser geschlossen.");
        }
    }

    @Override
    public List<BrowserContext> contexts() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public boolean isConnected() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public CDPSession newBrowserCDPSession() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public BrowserContext newContext(NewContextOptions options) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page newPage(NewPageOptions options) {
        connectToBrowser();

        // Optionen könnten hier auf eine spezifische Art und Weise angewandt werden
        if (options != null) {
            System.out.println("Optionen für newPage: " + options);
        }

        return new PageAdapter(webSocketClient);
    }

    @Override
    public void offDisconnected(Consumer<Browser> handler) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void onDisconnected(Consumer<Browser> handler) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void startTracing(Page page, StartTracingOptions options) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public byte[] stopTracing() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public String version() {
        // TODO Auto-generated method stub
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    /// HELPER / NOT API CONFORM METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public void launchBrowser() {
        try {
            Process process = new ProcessBuilder(
                chromePath, // Verwendet den fest codierten Pfad
                "--remote-debugging-port=9222", // Aktiviert das Remote-Debugging
                "--user-data-dir=" + profileDirectory, // Nutzt das angegebene Profilverzeichnis
                "--disable-gpu" // Optional: Deaktiviert GPU-Beschleunigung
            ).start();

            System.out.println("Browser gestartet: " + browserName);

            // Optional: Wartezeit, bis der Browser gestartet ist
            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("Fehler beim Starten des Browsers:");
            e.printStackTrace();
        }
    }

    private void connectToBrowser() {
        try {
            String wsUrl = getWebSocketDebuggerUrl();
            if (wsUrl != null) {
                webSocketClient = new WebSocketClient(new java.net.URI(wsUrl)) {
                    @Override
                    public void onOpen(org.java_websocket.handshake.ServerHandshake handshake) {
                        System.out.println("WebSocket-Verbindung hergestellt!");
                    }

                    @Override
                    public void onMessage(String message) {
                        System.out.println("Nachricht vom Browser: " + message);
                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        System.out.println("WebSocket-Verbindung geschlossen: " + reason);
                    }

                    @Override
                    public void onError(Exception ex) {
                        ex.printStackTrace();
                    }
                };
                webSocketClient.connect();
                while (!webSocketClient.isOpen()) {
                    Thread.sleep(100);
                }
                System.out.println("WebSocket-Client ist aktiv.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getWebSocketDebuggerUrl() {
        try {
            java.net.URL url = new java.net.URL("http://localhost:9222/json");
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                com.google.gson.JsonArray jsonArray = com.google.gson.JsonParser.parseString(response.toString()).getAsJsonArray();
                if (jsonArray.size() > 0) {
                    com.google.gson.JsonObject firstPage = jsonArray.get(0).getAsJsonObject();
                    return firstPage.get("webSocketDebuggerUrl").getAsString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
