package com.app.tgdcc.restclient;





import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Timer;
import java.util.TimerTask;

public class SessionService {
    public String token;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String host;
    private final HttpEntity<MultiValueMap<String, String>> request;
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionService.class);
    private final Timer heartBeatTimer = new Timer();

    public SessionService(String host, String username, String password) {
        this.host = host;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "password");
        map.add("username", username);
        map.add("password", password);
        this.request = new HttpEntity<>(map, headers);
    }

    public void POST_login(){
        try {
            TokenResponse response = restTemplate.postForObject(host + "api/token", request, TokenResponse.class);
            this.token = response.access_token;
            keepSessionAlive();
            LOGGER.info("Login established");
        } catch (NullPointerException e){
            LOGGER.error("Could not access token! ..however you may need to try again and ry to fix: {}", e.getMessage());
        }
        }

    public void POST_logout(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + getToken());
        heartBeatTimer.cancel();
        LOGGER.info("Logout successful, status: {} ", restTemplate.exchange(host+"api/token", HttpMethod.DELETE,
               new HttpEntity<>(null,httpHeaders),String.class).getStatusCode());
    }

    public String getToken() {
        return token;
    }

    public void keepSessionAlive(){
        heartBeatTimer.schedule(new TimerTask() {
            public void run() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set("Authorization", "Bearer " + getToken());
                LOGGER.info("Keep session alive: " + restTemplate.exchange(host+ "api/heartbeat" ,
                        HttpMethod.POST,new HttpEntity<>(null,httpHeaders),String.class).getStatusCode());
            }
        }, 0, 1000 * 60);
    }

    private static class TokenResponse {

        @JsonProperty("access_token")
        private String access_token;
        /*{
                "access_token": "AQAAANCMnd8BFdERjHoAwE_Cl-sBAAAAo7J....",
                "token_type": "bearer",
                "expires_in": 2591999,
                "user_name": "cc",
                "user_descriptor": "Default Administrator",
                "user_profile": "DEFAULT.ldl"
        }*/
    }



}
