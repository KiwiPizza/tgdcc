package com.app.tgdcc.restclient;


import com.app.tgdcc.dccutils.DccEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

public class RequestServcie {
    List<EventListener> eventListeners = new ArrayList<>();
    RestTemplate restTemplate;
    private final String host;
    private final String accessToken;

    public RequestServcie(String host, String accessToken) {
        this.host = host;
        this.accessToken = accessToken;
    }

    public void responseReceived(List<DccEvent> dccEvents){
        for (EventListener listener: eventListeners){
            listener.eventReceived(dccEvents);
        }
    }

    public void getEvents(){
        String uri = "api/events";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        //System.out.println(restTemplate.getForObject(host + uri,Object.class,request));
        ResponseEntity<String> response = restTemplate.exchange(host + uri + "?api_key=cc%3Acc", HttpMethod.GET, new HttpEntity<>(null,httpHeaders), String.class);
        System.out.println(response);

    }

}
