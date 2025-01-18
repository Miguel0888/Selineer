package selineer.controller;

import selineer.service.BrowserService;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainController {
    private final BrowserService browserService;

    public MainController(BrowserService browserService) {
        this.browserService = browserService;
    }

    public void onLaunchBrowser() {
        browserService.launchBrowser();
    }

    public void onCloseBrowser() {
        browserService.closeBrowser();
    }

    public void setupListeners(JButton navigateButton, JTextField addressBar, JComboBox<String> browserSelector) {
        navigateButton.addActionListener(e -> {
            String url = addressBar.getText();
            String selectedBrowser = (String) browserSelector.getSelectedItem();
    
            if (!url.isEmpty() && selectedBrowser != null) {
                browserService.navigateTo(url);
            } else {
                JOptionPane.showMessageDialog(null, "Bitte eine URL eingeben und den Browser auswählen.");
            }
        });
    }
    
}
