package com.app.tgdcc.restclient;


import com.app.tgdcc.dccutils.DccEvent;
import com.app.tgdcc.dccutils.DccEventList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RequestService {
    List<EventListener> eventListeners = new ArrayList<>();
    RestTemplate restTemplate = new RestTemplate();
    private final String host;
    private final String accessToken;

    public RequestService(String host, String accessToken) {
        this.host = host;
        this.accessToken = accessToken;
    }

    public void addEventListener(EventListener eventListener){
        eventListeners.add(eventListener);
    }

    public void responseReceived(HashSet<DccEvent> dccEvents){
        for (EventListener listener: eventListeners){
            listener.eventsReceived(dccEvents);
        }
    }

    public void GET_AllActiveEvents(){
        String uri = "api/events";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        ResponseEntity<DccEventList> response = restTemplate.exchange(host + uri, HttpMethod.GET, new HttpEntity<>(null,httpHeaders), DccEventList.class);
        responseReceived(response.getBody().getEvents());
    }

}
