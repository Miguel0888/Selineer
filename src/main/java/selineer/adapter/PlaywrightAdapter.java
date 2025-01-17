package selineer.adapter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class PlaywrightAdapter implements PlaywrightAPI {
    private WebDriver webDriver;

    @Override
    public void launchBrowser() {
        // Stelle sicher, dass der Pfad zum Chromedriver gesetzt ist
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        this.webDriver = new ChromeDriver();
    }

    @Override
    public void navigate(String url) {
        if (webDriver != null) {
            webDriver.get(url);
        } else {
            throw new IllegalStateException("Browser is not launched yet!");
        }
    }

    @Override
    public void closeBrowser() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
    }

    @Override
    public WebDriver getWebDriver() {
        return this.webDriver;
    }
}
