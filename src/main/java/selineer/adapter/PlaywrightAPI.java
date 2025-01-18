package selineer.adapter;

public interface PlaywrightAPI {
    void launchBrowser();
    void navigate(String url);
    void closeBrowser();
}
