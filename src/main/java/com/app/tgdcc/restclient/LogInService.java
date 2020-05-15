package com.app.tgdcc.restclient;




import com.app.tgdcc.dccutils.DccEventList;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class LogInService {
    public String token;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String host;
    private final HttpEntity<MultiValueMap<String, String>> request;
    private static final Logger LOGGER = LoggerFactory.getLogger(LogInService.class);

    public LogInService(String host, String username, String password) {
        this.host = host;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "password");
        map.add("username", username);
        map.add("password", password);
        this.request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
    }

    public void login (){
        try {
            this.token = restTemplate.postForObject(host + "/api/token", request, TokenResponse.class).access_token;
            System.out.println("Token: " + this.token);

        } catch (NullPointerException e){
            LOGGER.error("can't login.. ");
        }
        }

    public String getToken() {
        return token;
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
