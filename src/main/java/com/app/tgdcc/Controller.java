package com.app.tgdcc;

import com.app.tgdcc.dccutils.DccEvent;
import com.app.tgdcc.restclient.EventListener;
import com.app.tgdcc.restclient.LogInService;
import com.app.tgdcc.restclient.RequestServcie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.tgdcc.telegram.updatehandlers.GroupHandlers;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@RestController
@Configuration
@CrossOrigin
@Component
public class Controller implements EventListener {
    LogInService logInService;
    RequestServcie requestServcie;
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private List<DccEvent> events = new ArrayList<>();

    @GetMapping("/start")
    public void startConnection(){
            this.logInService = new LogInService("http://localhost:8080/","cc", "cc");
            logInService.login();
            retrieveEvents();
    }


    public void retrieveEvents(){
        this.requestServcie = new RequestServcie("http://localhost:8080/",logInService.getToken());
        requestServcie.addEventListener(this);
        requestServcie.getAllEvents();

    public Controller() {
        loadTgApi();
        this.logInService = new LogInService("http://localhost:8080","cc", "cc");
        logInService.login();
        this.requestServcie = new RequestServcie("http://localhost:8080",logInService.getToken());
        requestServcie.getEvents();
    }

    @Override
    public void eventReceived(List<DccEvent> dccEvents) {
        events.addAll(dccEvents);
    }

    @GetMapping("/getEvents")
    public String getAllEvents(){
        StringBuilder str = new StringBuilder("Active events: ");
        for(DccEvent e : events){
            str.append(" ").append(e.getEventId());
        }
        return str.toString();
    }

    @GetMapping("/logout")
    public String logout(){
        logInService.logout();
        return "Logout successful";
    public void eventReceived(List<DccEvent> dccEvent) {

    }

    public void sendMessange(DccEvent dccEvent){
        String id = "-1001489343293";
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(id);
        sendMessageRequest.setText("Active event: " + dccEvent.getEventId());
    }

    public void loadTgApi() {
        try {
            ApiContextInitializer.init();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            try {
                telegramBotsApi.registerBot(new GroupHandlers());
            } catch (TelegramApiException e) {
                System.err.println("Error");
            }
        } catch (Exception e) {
            System.err.println("Error");
        }
    }
}
