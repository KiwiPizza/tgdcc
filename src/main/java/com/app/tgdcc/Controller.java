package com.app.tgdcc;

import com.app.tgdcc.dccutils.DccEvent;
import com.app.tgdcc.restclient.EventListener;
import com.app.tgdcc.restclient.LogInService;
import com.app.tgdcc.restclient.RequestServcie;

import java.util.List;


public class Controller implements EventListener {
    LogInService logInService;
    RequestServcie requestServcie;


    public Controller() {
        this.logInService = new LogInService("http://localhost:8080","cc", "cc");
        logInService.login();
        this.requestServcie = new RequestServcie("http://localhost:8080",logInService.getToken());
        requestServcie.getEvents();
    }

    @Override
    public void eventReceived(List<DccEvent> dccEvent) {
        System.out.println(dccEvent.get(0));
    }
}
