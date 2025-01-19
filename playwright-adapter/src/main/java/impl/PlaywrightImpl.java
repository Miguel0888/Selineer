package com.microsoft.playwright.impl;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Selectors;

public class PlaywrightImpl implements Playwright {

    @Override
    public BrowserType chromium() {
        return new BrowserTypeImpl("chromium");
    }

    @Override
    public void close() {
        // ToDo: Ressourcen freigeben, falls nötig!
        System.out.println("Playwright geschlossen.");
    }

    @Override
    public BrowserType firefox() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public APIRequest request() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Selectors selectors() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BrowserType webkit() {
        // TODO Auto-generated method stub
        return null;
    }

    public static Playwright create(CreateOptions options) {
        // ToDo: May use options
        return new PlaywrightImpl();
    }
    
    
}
