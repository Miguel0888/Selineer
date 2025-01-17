package selineer.controller;

import selineer.service.BrowserService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    private final BrowserService browserService;

    public MainController(BrowserService browserService) {
        this.browserService = browserService;
    }

    public void setupListeners(JButton navigateButton, JTextField addressBar, JComboBox<String> browserSelector) {
        navigateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = addressBar.getText();
                if (!url.isEmpty() && browserSelector.getSelectedItem().equals("Chrome")) {
                    browserService.navigateTo(url);
                } else {
                    JOptionPane.showMessageDialog(null, "Bitte eine URL eingeben und den Browser auswählen.");
                }
            }
        });
    }

    public void onLaunchBrowser() {
        browserService.launchBrowser();
    }

    public void onCloseBrowser() {
        browserService.closeBrowser();
    }
}
