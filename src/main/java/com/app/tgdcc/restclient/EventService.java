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

import javax.swing.event.ListDataEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

    //TODO Implement Subscription, replace polling!!
    public void updateEventList(DccEventList eventList){
        for(DccEvent event: eventList.getEvents()){
            if(!eventRepository.existsByEventId(event.getEventId()) &&
              (event.getEventState().equals(DccTypes.UNPROCESSED))) {
                notifyConsumer(event);
                eventRepository.save(event);
                LOGGER.info("New event sent, ID: {}", event.getEventId());
            }
        }
        HashMap<Integer,DccEvent> sqlEventsUnprocessed = new HashMap<>();
        eventRepository.findAllByEventState(DccTypes.UNPROCESSED).forEach(sqlEvent->{
            sqlEventsUnprocessed.put(sqlEvent.getEventId(), sqlEvent);
        });
        for(DccEvent dccEvent : eventList.getEvents()){
            sqlEventsUnprocessed.remove(dccEvent.getEventId());
        }
       for (DccEvent sqlEvent: sqlEventsUnprocessed.values()) {
           eventRepository.delete(sqlEvent);
           sqlEvent.setEventState(DccTypes.CLOSED);
           eventRepository.save(sqlEvent);
           notifyConsumer(sqlEvent);
           LOGGER.info("Event closed, ID: {}", sqlEvent.getEventId());
       }
    }

}
