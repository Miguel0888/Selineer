package com.microsoft.playwright.impl;

import java.util.function.Consumer;

import com.google.gson.JsonObject;

import com.microsoft.playwright.CDPSession;

// ToDo: Implement this to communicate with chrome browser!
public class CDPSessionImpl implements CDPSession {

    @Override
    public void detach() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void off(String eventName, Consumer<JsonObject> handler) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void on(String eventName, Consumer<JsonObject> handler) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public JsonObject send(String method, JsonObject args) {
        // TODO Auto-generated method stub
        return null;
    }

}
