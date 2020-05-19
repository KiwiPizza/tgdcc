package com.app.tgdcc.restclient;


import com.app.tgdcc.dccutils.DccEvent;
import com.app.tgdcc.dccutils.DccEventList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventService {
    List<EventListener> eventListeners = new ArrayList<>();
    HashMap<Integer, DccEvent> events = new HashMap<>();
    RestTemplate restTemplate = new RestTemplate();
    private final String host;
    private final String accessToken;

    public EventService(String host, String accessToken) {
        this.host = host;
        this.accessToken = accessToken;
    }

    public void addEventListener(EventListener eventListener){
        eventListeners.add(eventListener);
    }

    public void notifyConsumer(DccEvent dccEvent){
        for (EventListener listener: eventListeners){
            listener.eventsReceived(dccEvent);
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
            if(!events.containsKey(event.getEventId())){
                notifyConsumer(event);
            }else {
             DccEvent existingEvent = events.get(event.getEventId());
             if(!existingEvent.getEventState().equals(event.getEventState())){
                 notifyConsumer(event);
             }
            }
        }
        
        
    }

}
