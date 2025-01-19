
package com.microsoft.playwright.impl;

import com.microsoft.playwright.impl.ChromeDevProtocolHelper.ConnectionHelper;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.CDPSession;
import com.microsoft.playwright.Page;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import org.java_websocket.client.WebSocketClient;

public class BrowserImpl implements Browser {
    private final String browserName;
    private WebSocketClient webSocketClient;

    // ToDo: Fix this!
    private String chromePath = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
    private String profileDirectory = "C:\\ChromeProfile";

    public BrowserImpl(String browserName) {
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
        connectToBrowser("localhost", 9222);

        // Optionen könnten hier auf eine spezifische Art und Weise angewandt werden
        if (options != null) {
            System.out.println("Optionen für newPage: " + options);
        }

        return new PageImpl(webSocketClient);
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

    // CDP4J version: ////////////////////////////////////////////////////////////////////////
    private void connectToBrowser(String host, int port) {
        try {
            // Verwende den BrowserConnector-Helper
            ConnectionHelper.connectToBrowser("localhost", port); // Use helper, ToDo: Improve!

            // Verbindung testen
            if (ConnectionHelper.getSessionFactory() == null) {
                throw new RuntimeException("SessionFactory ist null. Verbindung fehlgeschlagen.");
            }

            System.out.println("Erfolgreich mit dem Browser verbunden über BrowserConnector!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Verbindung zum Browser fehlgeschlagen", e);
        }
    }
}
