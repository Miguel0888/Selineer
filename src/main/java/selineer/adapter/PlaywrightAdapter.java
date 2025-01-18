package selineer.adapter;

import selineer.api.APIRequest;
import selineer.api.BrowserType;
import selineer.api.Playwright;
import selineer.api.Selectors;

public class PlaywrightAdapter implements Playwright {

    @Override
    public BrowserType chromium() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }
    
    
}
