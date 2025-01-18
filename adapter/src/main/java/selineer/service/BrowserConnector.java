package selineer.service;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.SessionFactory;

public class BrowserConnector {
    private SessionFactory sessionFactory;

    public void connectToBrowser(String host, int port) {
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

    public SessionFactory getSessionFactory() {
        return sessionFactory;
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

    public void navigateToUrl(String url) {
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
}
