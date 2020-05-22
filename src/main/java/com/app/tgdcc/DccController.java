package com.app.tgdcc;

import com.app.tgdcc.databaseconfig.EventRepository;
import com.app.tgdcc.dccutils.DccEvent;
import com.app.tgdcc.dccutils.DccTypes;
import com.app.tgdcc.restclient.EventListener;
import com.app.tgdcc.restclient.EventService;
import com.app.tgdcc.restclient.SessionService;
import com.app.tgdcc.telegram.updatehandlers.ChatHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.*;
import java.util.*;


@Component
public class DccController implements EventListener {

    SessionService logInService;
    EventService eventService;
    ChatHandler groupHandlers;
    Timer eventPollingTimer = new Timer();
    public Set<DccEvent> activeEvents = new HashSet<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(DccController.class);
    private final EventRepository eventRepository;
    private final DccTypes types = new DccTypes();


    @Autowired
    public DccController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void eventReceived(DccEvent dccEvent) {
        sendMessage(dccEvent);
    }

    public void startService(){
        this.logInService = new SessionService("https://www.desigoccecosystem.com/WebService","Training0520", "Training");
        logInService.POST_login();
        loadTelegramAPI();
        this.eventService = new EventService("https://www.desigoccecosystem.com/WebService", logInService.getToken(), eventRepository);
        eventService.addEventListener(this);
        startEventPolling();
    }

    public void stopService() {
        eventPollingTimer.cancel();
        logInService.POST_logout();
    }


    //TODO create message format with bold descriptors and german text for category and status
    public void sendMessage(DccEvent dccEvent){
        String ChatID = "-1001489343293";
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(ChatID);
        sendMessageRequest.setText(
                "Event ID:" + "\n" +
                 dccEvent.getEventId() + "\n" +
                "Kategorie: " + "\n" +
                dccEvent.getEventCategory() + "\n" +
                "Status: " + "\n" +
                types.getGermanTextForEventState().get(dccEvent.getEventState()) + "\n" +
                "Grund: " + "\n" +
                dccEvent.getEventCause() + "\n" +
                "Quelle: " + "\n" +
                dccEvent.getSrcLocation()
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
                eventService.GET_AllActiveEvents();
            }
        }, 0, 1000 * 5);

    }
}
