package com.app.tgdcc.restclient;

import com.app.tgdcc.dccutils.DccEvent;



public interface EventListener {

    void eventReceived(DccEvent dccEvent);

}
