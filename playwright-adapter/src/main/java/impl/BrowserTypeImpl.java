package com.microsoft.playwright.impl;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;

import java.nio.file.Path;

public class BrowserTypeImpl implements BrowserType {
    private final String name;

    public BrowserTypeImpl(String name) {
        this.name = name;
    }

    @Override
    public Browser connect(String wsEndpoint) {
        // Implementiere Verbindung zu einem bestehenden Browser
        throw new UnsupportedOperationException("connect not implemented yet");
    }

    @Override
    public Browser connect(String wsEndpoint, ConnectOptions options) {
        // Implementiere Verbindung zu einem bestehenden Browser mit Optionen
        throw new UnsupportedOperationException("connect not implemented yet");
    }

    @Override
    public Browser connectOverCDP(String endpointURL) {
        // Implementiere Verbindung über CDP
        throw new UnsupportedOperationException("connectOverCDP not implemented yet");
    }

    @Override
    public Browser connectOverCDP(String endpointURL, ConnectOverCDPOptions options) {
        // Implementiere Verbindung über CDP mit Optionen
        throw new UnsupportedOperationException("connectOverCDP not implemented yet");
    }

    @Override
    public String executablePath() {
        // Gib den Pfad zum Browser-Executable zurück
        return null; // Passe dies an, falls erforderlich
    }

    @Override
    public Browser launch() {
        return launch(null);
    }

    @Override
    public Browser launch(LaunchOptions options) {
        // Starte den Browser mit den gegebenen Optionen
        System.out.println("Launching browser: " + name);
        return new BrowserImpl(name); // BrowserImpl muss implementiert sein
    }

    @Override
    public BrowserContext launchPersistentContext(Path userDataDir) {
        return launchPersistentContext(userDataDir, null);
    }

    @Override
    public BrowserContext launchPersistentContext(Path userDataDir, LaunchPersistentContextOptions options) {
        // Starte den Browser mit persistentem Kontext
        throw new UnsupportedOperationException("launchPersistentContext not implemented yet");
    }

    @Override
    public String name() {
        return name;
    }
}
