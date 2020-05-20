package com.app.tgdcc.restclient;


import com.app.tgdcc.databaseconfig.EventRepository;
import com.app.tgdcc.dccutils.DccEvent;
import com.app.tgdcc.dccutils.DccEventList;
import com.app.tgdcc.dccutils.DccTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

public class EventService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);
    List<EventListener> eventListeners = new ArrayList<>();
    RestTemplate restTemplate = new RestTemplate();
    private final String host;
    private final String accessToken;
    private final EventRepository eventRepository;

    public EventService(String host, String accessToken, EventRepository eventRepository) {
        this.host = host;
        this.accessToken = accessToken;
        this.eventRepository = eventRepository;

    }

    public void addEventListener(EventListener eventListener){
        eventListeners.add(eventListener);
    }

    public void notifyConsumer(DccEvent dccEvent){
        for (EventListener listener: eventListeners){
            listener.eventReceived(dccEvent);
        }
    }

    public void GET_AllActiveEvents(){
        String uri = "api/events";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        ResponseEntity<DccEventList> response = restTemplate.exchange(host + uri, HttpMethod.GET, new HttpEntity<>(null,httpHeaders), DccEventList.class);
        updateEventList(response.getBody());
    }
    
    public void updateEventList(DccEventList eventList){
        for(DccEvent event: eventList.getEvents()){
            if(!eventRepository.existsByEventId(event.getEventId())) {
                if((event.getEventState().equals(DccTypes.UNPROCESSED))){
                notifyConsumer(event);
                eventRepository.save(event);
                LOGGER.info("New event sent, ID: {}", event.getEventId());
                }
            }
        }
    }
}
