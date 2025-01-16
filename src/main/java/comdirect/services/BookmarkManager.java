package comdirect.services;

import comdirect.config.ComdirectConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookmarkManager {
    private final BrowseService browseService;

    private final ComdirectConfig config;

    public BookmarkManager(BrowseService browseService, ComdirectConfig config) {
        this.browseService = browseService;
        this.config = config;
    }

    /**
     * Liefert eine Liste aller Bookmark-Namen.
     */
    public List<String> getBookmarkNames() {
        return config.getBookmarks().stream()
                .map(ComdirectConfig.Bookmark::getName)
                .collect(Collectors.toList());
    }

    /**
     * Liefert die URL zu einem bestimmten Bookmark-Namen.
     */
    public String getBookmarkUrlByName(String name) {
        return config.getBookmarks().stream()
                .filter(bookmark -> bookmark.getName().equals(name))
                .map(ComdirectConfig.Bookmark::getUrl)
                .findFirst()
                .orElse(null);
    }

    // ToDo: Fix this method, does currently find nothing!
    public String getPageName() {
        return getBookmarkNameByUrl(browseService.page.url());
    }

    /**
     * Liefert den Namen zu einer bestimmten URL.
     */
    private String getBookmarkNameByUrl(String url) {
        return config.getBookmarks().stream()
                .filter(bookmark -> bookmark.getUrl().equals(url))
                .map(ComdirectConfig.Bookmark::getName)
                .findFirst()
                .orElse(null); // Gibt null zurück, wenn keine Übereinstimmung gefunden wird
    }
}


