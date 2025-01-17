package selineer;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Web Testing Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the toolbar
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton refreshButton = new JButton("Aktualisieren");
        JButton backButton = new JButton("Zurück");
        JButton forwardButton = new JButton("Vor");
        JButton homeButton = new JButton("Home");

        JComboBox<String> bookmarkSelector = new JComboBox<>(new String[]{"Lesezeichen 1", "Lesezeichen 2"});
        JTextField addressBar = new JTextField("Adresse eingeben und Enter drücken");
        JComboBox<String> browserSelector = new JComboBox<>(new String[]{"Browser 1", "Browser 2"});

        JButton loginButton = new JButton("Login");
        JButton customButton = new JButton("N/A");

        toolBar.add(refreshButton);
        toolBar.addSeparator();
        toolBar.add(backButton);
        toolBar.add(forwardButton);
        toolBar.add(homeButton);
        toolBar.addSeparator();
        toolBar.add(bookmarkSelector);
        toolBar.add(addressBar);
        toolBar.add(browserSelector);
        toolBar.addSeparator();
        toolBar.add(loginButton);
        toolBar.add(customButton);

        // Create the WebView equivalent (using JEditorPane)
        JEditorPane webView = new JEditorPane();
        webView.setEditable(false);
        webView.setContentType("text/html"); // HTML rendering
        webView.setText("<html><body><h1>Willkommen im Web Testing Dashboard</h1></body></html>");
        
        // Add hyperlink listener to support links
        webView.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        webView.setPage(e.getURL());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Fehler beim Laden der Seite: " + e.getURL());
                    }
                }
            }
        });

        JScrollPane webViewScrollPane = new JScrollPane(webView);

        // Action Listeners
        refreshButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Seite aktualisieren"));
        backButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Zurück"));
        forwardButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Vor"));
        homeButton.addActionListener(e -> {
            try {
                webView.setPage("https://www.google.de");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Fehler beim Laden der Startseite.");
            }
        });
        addressBar.addActionListener((ActionEvent e) -> {
            try {
                webView.setPage(addressBar.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Ungültige URL: " + addressBar.getText());
            }
        });

        // Create the layout
        frame.setLayout(new BorderLayout());
        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(webViewScrollPane, BorderLayout.CENTER);

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
