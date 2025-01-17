package comdirect.controllers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {

    private JFrame frame;
    private JTextField addressBar;
    private JComboBox<String> browserSelector;
    private JComboBox<String> bookmarkSelector;
    private JEditorPane webView;
    private boolean isLoading = false;

    private boolean enableJavaScriptConsole = false; // Simuliert @Value("${comdirect.ui.enable-javascript-console}")
    private boolean enableJavaScriptDebug = false; // Simuliert @Value("${comdirect.ui.enable-javascript-debug}")

    public MainController() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Comdirect Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Toolbar erstellen
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton refreshButton = new JButton("Aktualisieren");
        JButton backButton = new JButton("Zurück");
        JButton forwardButton = new JButton("Vor");
        JButton homeButton = new JButton("Home");

        bookmarkSelector = new JComboBox<>(new String[]{"Bookmark 1", "Bookmark 2"});
        addressBar = new JTextField("Adresse eingeben und Enter drücken");
        browserSelector = new JComboBox<>(new String[]{"Browser 1", "Browser 2"});

        JButton loginButton = new JButton("Login");
        JButton proTraderButton = new JButton("ProTrader");

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
        toolBar.add(proTraderButton);

        // WebView (JEditorPane als Ersatz)
        webView = new JEditorPane();
        webView.setEditable(false);
        webView.setContentType("text/html");
        webView.setText("<html><body><h1>Willkommen im Comdirect Dashboard</h1></body></html>");

        JScrollPane webViewScrollPane = new JScrollPane(webView);

        // Action Listener hinzufügen
        refreshButton.addActionListener(e -> onRefreshClick());
        backButton.addActionListener(e -> onBackClick());
        forwardButton.addActionListener(e -> onForwardClick());
        homeButton.addActionListener(e -> onHomeClick());
        addressBar.addActionListener(e -> onAddressEntered());
        loginButton.addActionListener(e -> onLoginClick());
        proTraderButton.addActionListener(e -> onStartApplicationClick());
        bookmarkSelector.addActionListener(e -> onBookmarkSelectionChanged());

        // Layout konfigurieren
        frame.setLayout(new BorderLayout());
        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(webViewScrollPane, BorderLayout.CENTER);
    }

    public void show() {
        frame.setVisible(true);
    }

    // Implementierung der Event-Handler
    private void onRefreshClick() {
        if (isLoading) return;
        isLoading = true;
        displayHtmlInWebView("<html><body><h2>Seite aktualisiert</h2></body></html>");
        isLoading = false;
    }

    private void onBackClick() {
        JOptionPane.showMessageDialog(frame, "Zurück-Funktion wurde geklickt.");
    }

    private void onForwardClick() {
        JOptionPane.showMessageDialog(frame, "Vor-Funktion wurde geklickt.");
    }

    private void onHomeClick() {
        displayHtmlInWebView("<html><body><h1>Startseite geladen</h1></body></html>");
    }

    private void onAddressEntered() {
        String url = addressBar.getText();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }
        displayHtmlInWebView("<html><body><h2>Geladene URL: " + url + "</h2></body></html>");
    }

    private void onLoginClick() {
        JOptionPane.showMessageDialog(frame, "Login-Funktion wurde geklickt.");
    }

    private void onStartApplicationClick() {
        JOptionPane.showMessageDialog(frame, "ProTrader wurde gestartet.");
    }

    private void onBookmarkSelectionChanged() {
        String selectedBookmark = (String) bookmarkSelector.getSelectedItem();
        displayHtmlInWebView("<html><body><h2>Geladene Bookmark: " + selectedBookmark + "</h2></body></html>");
    }

    // HTML-Inhalte im WebView anzeigen
    private void displayHtmlInWebView(String htmlContent) {
        webView.setText(htmlContent);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainController controller = new MainController();
            controller.show();
        });
    }
}
