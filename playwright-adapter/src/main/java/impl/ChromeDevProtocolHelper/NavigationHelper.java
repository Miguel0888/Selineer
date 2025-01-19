package com.microsoft.playwright.impl.ChromeDevProtocolHelper;

import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import io.webfolder.cdp.session.SessionInfo;

public class NavigationHelper {

    public static Session navigateTo(String url) {
        SessionFactory sessionFactory = ConnectionHelper.getSessionFactory();
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory ist nicht initialisiert. Verbindung zum Browser fehlt.");
        }
    
        Session session = null;
        try {
            if (!sessionFactory.list().isEmpty()) {
                SessionInfo sessionInfo = sessionFactory.list().get(0);
                // Debug:
                // System.out.println("Verfügbare Sitzung: " + sessionInfo);
    
                session = sessionFactory.connect(sessionInfo.getId());
            } else {
                throw new RuntimeException("Keine vorhandene Sitzung verfügbar. Stelle sicher, dass der Browser gestartet ist.");
            }
    
            session.navigate(url);
            session.waitDocumentReady();
            System.out.println("Navigiert zu: " + url);
            return session; // Sitzung wird zurückgegeben
        } catch (Exception e) {
            if (session != null) {
                session.close();
            }
            throw new RuntimeException("Navigation fehlgeschlagen", e);
        }
    }    
}
