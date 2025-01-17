package selineer.adapter;

import org.openqa.selenium.WebDriver;

public interface PlaywrightAPI {
    void launchBrowser();
    void navigate(String url);
    void closeBrowser();
    WebDriver getWebDriver();
}
