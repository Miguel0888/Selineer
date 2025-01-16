package comdirect.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "comdirect")
public class ComdirectConfig {

    private LoginConfig login;
    private UiConfig ui;
    private BrowserConfig browser;
    private List<Bookmark> bookmarks; // Liste für Bookmarks


    @Data
    public static class LoginConfig {
        private boolean useDifferentLoginUrl;
        private boolean autoLogin;
        private boolean autoCloseCookieBanner;
        private String url;
        private String urlLogin;
        private String user;
        private String pin;
        private boolean autoRedirectAfterLogin;
        private String urlRedirect;
    }

    @Data
    public static class UiConfig {
        private boolean loadHomePageAtStartup;
        private boolean autoCloseCookieBannerAtStartup;
        private String urlHome;
        private boolean enableJavascriptDebug;
        private boolean enableJavascriptConsole;
    }

    @Data
    public static class BrowserConfig {
        private boolean headless;
        private boolean allowDownloads;
        private boolean overrideDefaultDownloadFolder;
        private String downloadFolder;
        private String defaultBrowser;
        private String edgePath;
    }

    @Data
    public static class Bookmark {
        private String name;
        private String url;
    }
}
