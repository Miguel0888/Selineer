// package comdirect.controllers;

// import comdirect.services.BrowseService;

// public class WebViewBridge {
//     private MainController controller;
//     private BrowseService browseService;

//     public WebViewBridge(MainController controller, BrowseService browseService) {
//         this.controller = controller;
//         this.browseService = browseService;
//     }

//     public void testConnection() {
//         System.out.println("Bridge connected!");
//     }

//     public void logMessage(String message) {
//         System.out.println("WebView Log: " + message);
//     }

//     ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//     /// Event handlers for WebView interactions
//     /// These methods are called from JavaScript code in the WebView (see BrowserUtils.addBridgeCode())
//     ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//     public void onLinkClicked(String href) {
//         System.out.println("Benutzer hat Link geklickt: " + href);
//         System.out.println("Link geklickt: " + href);

//         try {
//             String absoluteUrl = BrowserUtils.resolveUrl(href, browseService.page);
//             System.out.println("Absolute URL: " + absoluteUrl);

//             if (!BrowserUtils.isValidUrl(absoluteUrl)) {
//                 BrowserUtils.showError("Fehler", "Ungültige URL", "Die URL \"" + absoluteUrl + "\" ist ungültig.");
//                 return;
//             }

//             controller.displayHtmlInWebView(browseService.navigateTo(absoluteUrl));
//         } catch (Exception e) {
//             e.printStackTrace();
//             BrowserUtils.showError("Fehler", "Link-Navigation fehlgeschlagen", e.getMessage());
//         }
//     }

//     public void onFormSubmitted(String formData) {
//         System.out.println("Formular wurde abgeschickt: " + formData);
//         System.out.println("Formular abgeschickt: " + formData);
//         try {
//             controller.displayHtmlInWebView(browseService.postForm(formData));
//         } catch (Exception e) {
//             e.printStackTrace();
//             BrowserUtils.showError("Fehler", "Formular-Verarbeitung fehlgeschlagen", e.getMessage());
//         }
//     }

//     void handleBridgeRequest(String url) {
//         if (url.startsWith("bridge://onLinkClicked")) {
//             String href = BrowserUtils.extractQueryParam(url, "href");
//             onLinkClicked(href);
//         } else if (url.startsWith("bridge://onFormSubmitted")) {
//             String formData = BrowserUtils.extractQueryParam(url, "formData");
//             onFormSubmitted(formData);
//         } else {
//             System.out.println("Unbekannter Bridge-Event: " + url);
//         }
//     }
// }