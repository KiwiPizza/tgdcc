package com.app.tgdcc.dccutils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;

public class DccEventList  {

    @JsonProperty("Events")
    HashSet<DccEvent> events;

    public HashSet<DccEvent> getEvents() {
        return events;
    }


}
