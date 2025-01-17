package selineer.service;

import selineer.adapter.PlaywrightAPI;

public class BrowserService {
    private final PlaywrightAPI playwrightAPI;

    public BrowserService(PlaywrightAPI playwrightAPI) {
        this.playwrightAPI = playwrightAPI;
    }

    public void launchBrowser() {
        playwrightAPI.launchBrowser();
    }

    public void navigateTo(String url) {
        playwrightAPI.navigate(url);
    }

    public void closeBrowser() {
        playwrightAPI.closeBrowser();
    }
}
