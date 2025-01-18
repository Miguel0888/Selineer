package selineer.adapter.ChromeDevProtocolHelper;

import java.util.HashMap;

import org.java_websocket.client.WebSocketClient;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.SessionFactory;
import selineer.adapter.ResponseAdapter;
import selineer.api.Response;

// ToDo: May be a Service, but state has to be stored elsewhere..
public class ConnectionHelper {
    private static SessionFactory sessionFactory; // ToDo: May be improved vor multiple sessions!

    public static void connectToBrowser(String host, int port) {
        try {
            // SessionFactory mit Host und Port initialisieren
            sessionFactory = new SessionFactory(host, port);

            // Verbindung testen: Sitzungen abrufen
            if (sessionFactory.list().isEmpty()) {
                throw new RuntimeException("Keine Sitzung gefunden. Verbindung fehlgeschlagen.");
            }

            System.out.println("Erfolgreich mit dem Browser verbunden!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Verbindung zum Browser fehlgeschlagen", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    // Socket version without CDP4J: /////////////////////////////////////////////////////////
    private WebSocketClient connectToBrowser() {
        WebSocketClient webSocketClient = null;
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
        return webSocketClient;
    }

    private String getWebSocketDebuggerUrl() {
        try {
            java.net.URL url = new java.net.URL("http://localhost:9222/json"); // ToDo: Improve!
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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void navigateTo(String url) {
        // Session explizit deklarieren
        io.webfolder.cdp.session.Session session = null;
        try {
            // Eine neue Sitzung erstellen
            session = sessionFactory.create();
            session.navigate(url);
            session.waitDocumentReady(); // Warten, bis die Seite geladen ist
            System.out.println("Navigiert zu: " + url);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Navigation fehlgeschlagen", e);
        } finally {
            // Sitzung sicher schließen
            if (session != null) {
                session.close();
            }
        }
    }

    // Socket version without CDP4J: /////////////////////////////////////////////////////////
    public Response navigate(String url, WebSocketClient webSocketClient) {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            // JSON-Befehl erstellen
            com.google.gson.JsonObject command = new com.google.gson.JsonObject();
            command.addProperty("id", 1);
            command.addProperty("method", "Page.navigate");
    
            // Parameter hinzufügen
            com.google.gson.JsonObject params = new com.google.gson.JsonObject();
            params.addProperty("url", url);
    
            // if (options != null) {
            //     if (options.referer != null) {
            //         params.addProperty("referer", options.referer);
            //     }
            //     if (options.timeout != null) {
            //         params.addProperty("timeout", options.timeout);
            //     }
            //     if (options.waitUntil != null) {
            //         params.addProperty("waitUntil", options.waitUntil.name().toLowerCase());
            //     }
            // }
    
            command.add("params", params);
            webSocketClient.send(command.toString());
    
            // Warte auf die Antwort (ToDo: Implementiere Wartelogik)
            byte[] body = "{}".getBytes(); // Platzhalter
            int status = 200;
            String statusText = "OK";
    
            // Erstelle und returniere ResponseAdapter
            return new ResponseAdapter(body, status, statusText, url, new HashMap<>(), null, false);
        }

        System.out.println("WebSocket-Verbindung nicht aktiv.");
        return null;
    }
}
