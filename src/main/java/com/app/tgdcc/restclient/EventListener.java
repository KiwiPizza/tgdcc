package com.app.tgdcc.restclient;

import com.app.tgdcc.dccutils.DccEvent;

import java.util.HashSet;
import java.util.List;

public interface EventListener {

    void eventReceived(HashSet<DccEvent> dccEvent);

}
