package com.app.tgdcc;

import com.app.tgdcc.dccutils.DccEvent;
import com.app.tgdcc.restclient.EventListener;
import com.app.tgdcc.restclient.EventService;
import com.app.tgdcc.restclient.SessionService;
import com.app.tgdcc.telegram.updatehandlers.ChatHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;


@Component
public class DccController implements EventListener {

    SessionService logInService;
    EventService requestService;
    ChatHandler groupHandlers;
    Timer eventPollingTimer = new Timer();
    public Set<DccEvent> activeEvents = new HashSet<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(DccController.class);

    @Override
    public void eventsReceived(DccEvent dccEvent) {
        sendMessage(dccEvent);
    }

    public void startService(){
        this.logInService = new SessionService("http://localhost:8080/","cc", "cc");
        logInService.POST_login();
        loadTelegramAPI();
        this.requestService = new EventService("http://localhost:8080/", logInService.getToken());
        requestService.addEventListener(this);
        startEventPolling();
    }

    public void stopService() {
        eventPollingTimer.cancel();
        logInService.POST_logout();
    }

    public void sendMessage(DccEvent dccEvent){
        String ChatID = "-1001489343293";
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(ChatID);
        sendMessageRequest.setText(
                "Event ID: " + dccEvent.getEventId() + "\n" +
                "Priorität: " + dccEvent.getEventCategory() + "\n" +
                "Status: " + dccEvent.getEventState() + "\n" +
                "Grund: " + dccEvent.getEventCause()
        );
        groupHandlers.sendMessage(sendMessageRequest);

    }

    public void loadTelegramAPI() {
        this.groupHandlers = new ChatHandler();
        try {
            ApiContextInitializer.init();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            LOGGER.info("Telegram bot successful registered");
            try {
                telegramBotsApi.registerBot(groupHandlers);
            } catch (TelegramApiException e) {
                LOGGER.error("Register TG API BOT: " + e.getMessage());
            }
        } catch (Exception e) {
            LOGGER.error("Initialize TG API: " + e.getMessage());
        }
    }

    public Set<DccEvent> getActiveEvents() {
        return activeEvents;
    }

    public void startEventPolling(){
        eventPollingTimer.schedule(new TimerTask() {
            public void run() {
                requestService.GET_AllActiveEvents();
            }
        }, 0, 1000 * 5);

    }
}
