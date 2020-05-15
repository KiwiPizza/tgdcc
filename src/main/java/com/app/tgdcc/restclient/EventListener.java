package com.app.tgdcc.restclient;

import com.app.tgdcc.dccutils.DccEvent;

import java.util.List;

public interface EventListener {

    void eventReceived(List<DccEvent> dccEvent);

}
