package com.app.tgdcc;

import com.app.tgdcc.dccutils.DccEvent;
import com.app.tgdcc.restclient.EventListener;
import com.app.tgdcc.restclient.LogInService;
import com.app.tgdcc.restclient.RequestServcie;
import com.app.tgdcc.telegram.updatehandlers.GroupHandlers;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


public class Controller implements EventListener {
    LogInService logInService;
    RequestServcie requestServcie;


    public Controller() {
        loadTgApi();
        this.logInService = new LogInService("http://localhost:8080","cc", "cc");
        logInService.login();
        this.requestServcie = new RequestServcie("http://localhost:8080",logInService.getToken());
        requestServcie.getEvents();
    }

    @Override
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
