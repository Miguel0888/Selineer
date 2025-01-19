package com.microsoft.playwright.impl.ChromeDevProtocolHelper;

import java.util.HashMap;

import org.java_websocket.client.WebSocketClient;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.SessionFactory;
import com.microsoft.playwright.impl.ResponseImpl;
import com.microsoft.playwright.Response;

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
}
