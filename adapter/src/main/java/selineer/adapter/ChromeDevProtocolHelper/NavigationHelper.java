package selineer.adapter.ChromeDevProtocolHelper;


import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

public class NavigationHelper {

    public static void navigateToUrl(SessionFactory sessionFactory, String url) {
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory ist nicht initialisiert. Verbindung zum Browser fehlt.");
        }

        Session session = null;
        try {
            // Sitzung erstellen
            session = sessionFactory.create();

            // Navigieren
            session.navigate(url);

            // Warten auf Dokumentbereitschaft
            session.waitDocumentReady();

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
