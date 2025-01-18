package selineer.adapter;

import selineer.api.Response;
import selineer.api.Frame;
import selineer.api.Request;
import selineer.api.options.HttpHeader;
import selineer.api.options.SecurityDetails;
import selineer.api.options.ServerAddr;

import java.util.*;

public class ResponseAdapter implements Response {
    private final Map<String, String> headers = new HashMap<>();
    private final List<HttpHeader> headersArray = new ArrayList<>();
    private final byte[] body;
    private final int status;
    private final String statusText;
    private final String url;

    // Weitere Felder hinzufügen, falls benötigt
    private final boolean fromServiceWorker;
    private final Request request;

    public ResponseAdapter(byte[] body, int status, String statusText, String url, Map<String, String> headers, Request request, boolean fromServiceWorker) {
        this.body = body;
        this.status = status;
        this.statusText = statusText;
        this.url = url;
        this.headers.putAll(headers);
        this.request = request;
        this.fromServiceWorker = fromServiceWorker;
    }

    @Override
    public Map<String, String> allHeaders() {
        return headers;
    }

    @Override
    public byte[] body() {
        return body;
    }

    @Override
    public String finished() {
        // Im echten Playwright ist dies oft ein Stub, da es null zurückgibt
        return null;
    }

    @Override
    public Frame frame() {
        // Implementiere diese Methode, wenn du Frame-Unterstützung hinzufügst
        return null;
    }

    @Override
    public boolean fromServiceWorker() {
        return fromServiceWorker;
    }

    @Override
    public Map<String, String> headers() {
        return headers;
    }

    @Override
    public List<HttpHeader> headersArray() {
        return headersArray;
    }

    @Override
    public String headerValue(String name) {
        return headers.getOrDefault(name.toLowerCase(), null);
    }

    @Override
    public List<String> headerValues(String name) {
        List<String> values = new ArrayList<>();
        for (HttpHeader header : headersArray) {
            if (header.name.equalsIgnoreCase(name)) {
                values.add(header.value);
            }
        }
        return values;
    }

    @Override
    public boolean ok() {
        return status >= 200 && status < 300;
    }

    @Override
    public Request request() {
        return request;
    }

    @Override
    public SecurityDetails securityDetails() {
        // Kann optional implementiert werden
        return null;
    }

    @Override
    public ServerAddr serverAddr() {
        // Kann optional implementiert werden
        return null;
    }

    @Override
    public int status() {
        return status;
    }

    @Override
    public String statusText() {
        return statusText;
    }

    @Override
    public String text() {
        return body != null ? new String(body) : null;
    }

    @Override
    public String url() {
        return url;
    }
}
