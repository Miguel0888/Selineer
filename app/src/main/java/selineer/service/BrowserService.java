package selineer.service;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BrowserService {
    private Playwright playwright;
    private Browser browser;
    private Page page;

    public BrowserService() {
        this.playwright = Playwright.create();
    }

    public void launchBrowser() {
        try {
            this.browser = playwright.chromium().launch();
            this.page = browser.newPage();
            System.out.println("Browser gestartet.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void navigateTo(String url) {
        if (page != null) {
            try {
                page.navigate(url);
                System.out.println("Navigiere zu URL: " + url);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Fehler beim Navigieren zur URL.");
            }
        } else {
            System.out.println("Der Browser ist nicht geöffnet.");
        }
    }

    public void closeBrowser() {
        if (browser != null) {
            try {
                browser.close();
                System.out.println("Browser geschlossen.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
