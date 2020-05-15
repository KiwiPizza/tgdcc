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
        this.request = new HttpEntity<>(map, headers);
    }

    public void login(){
        try {
            TokenResponse response = restTemplate.postForObject(host + "api/token", request, TokenResponse.class);
            this.token = response.access_token;
            LOGGER.info("Login established");
        } catch (NullPointerException e){
            LOGGER.error("Could not access token! ..however you may need to try again", e.getCause());
        }
        }

    public void logout(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + getToken());
       LOGGER.info("Logout successful, status: {} ", restTemplate.exchange(host+"api/token", HttpMethod.DELETE,
               new HttpEntity<>(null,httpHeaders),String.class).getStatusCode());
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
