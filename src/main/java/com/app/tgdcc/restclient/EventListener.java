package com.app.tgdcc.restclient;

import com.app.tgdcc.dccutils.DccEvent;

import java.util.HashSet;


public interface EventListener {

    void eventsReceived(HashSet<DccEvent> dccEvent);

}
