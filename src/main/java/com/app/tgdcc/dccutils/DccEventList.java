package com.app.tgdcc.dccutils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class DccEventList {

    @JsonProperty("Events")
    ArrayList<DccEvent> events;

    public ArrayList<DccEvent> getEvents() {
        return events;
    }
}
