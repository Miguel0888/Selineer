package selineer.adapter.ChromeDevProtocolHelper;


import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import io.webfolder.cdp.session.SessionInfo;

public class NavigationHelper {

    public static void navigateTo(String url) {
        SessionFactory sessionFactory = ConnectionHelper.getSessionFactory();
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory ist nicht initialisiert. Verbindung zum Browser fehlt.");
        }

        Session session = null;
        try {
            // Prüfen, ob Sitzungen verfügbar sind
            if (!sessionFactory.list().isEmpty()) {
                // Erste verfügbare Sitzung auswählen
                SessionInfo sessionInfo = sessionFactory.list().get(0);
                System.out.println("Verfügbare Sitzung: " + sessionInfo);

                // Sitzung basierend auf der ID erstellen
                session = sessionFactory.create(sessionInfo.getId());
            } else {
                throw new RuntimeException("Keine vorhandene Sitzung verfügbar. Stelle sicher, dass der Browser gestartet ist.");
            }

            // Navigieren zur URL
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
