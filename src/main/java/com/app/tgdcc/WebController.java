package com.app.tgdcc;

import com.app.tgdcc.dccutils.DccEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Configuration
@CrossOrigin
@Component
public class WebController {

    private final DccController dccController;
    private static final Logger LOGGER = LoggerFactory.getLogger(WebController.class);

    @Autowired
    public WebController(DccController dccController){
        this.dccController = dccController;
    }

    @GetMapping("/getEvents")
    public String getAllEvents(){
        StringBuilder str = new StringBuilder("Active events: ");
        for(DccEvent e : dccController.getActiveEvents()){
            str.append(" ").append(e.getEventId());
        }
        return str.toString();
    }

    @GetMapping("/start")
    public String startService(){
        dccController.startService();
        LOGGER.info("Start event service");
        return HttpStatus.OK.toString();
    }

    @GetMapping("/stop")
    public String stopService(){
        dccController.stopService();
        LOGGER.info("Stop event service");
        return HttpStatus.OK.toString();
    }

}
