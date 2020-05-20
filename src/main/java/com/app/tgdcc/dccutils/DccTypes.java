package com.app.tgdcc.dccutils;

import java.util.HashMap;

public class DccTypes {

    //Event States
    /*
    Unprocessed
    ReadyToBeReset
    ReadyToBeClosed
    WaitingOPCompletion
    Acked
    WaitingForCommandExecution
    Closed
    */

    public static String UNPROCESSED = "Unprocessed";
    public static String READYTOBERESET = "ReadyToBeReset";
    public static String READYTOBECLOSED= "ReadyToBeClosed";
    public static String WAITINGOPCompletion = "WaitingOPCompletion";
    public static String ACKED = "Acked";
    public static String WAITINGFORCOMMANDEXECUTION = "WaitingForCommandExecution";
    public static String CLOSED = "Closed";

    public static String UNPROCESSED_de = "Neuer Event";
    public static String READYTOBERESET_de = "Bereit zum Zurückstellen";
    public static String READYTOBECLOSED_de = "Bereit zum Abschliessen";
    public static String WAITINGOPCompletion_de = "Warten bis OP Komplett";
    public static String ACKED_de = "Quittiert";
    public static String WAITINGFORCOMMANDEXECUTION_de = "Warten auf Befehle";
    public static String CLOSED_de = "Event Abgeschlossen";

    public HashMap<String, String> germanTextForEventState = new HashMap<>();

    public DccTypes() {
        germanTextForEventState.put(UNPROCESSED,UNPROCESSED_de);
        germanTextForEventState.put(READYTOBERESET,READYTOBERESET_de);
        germanTextForEventState.put(READYTOBECLOSED,READYTOBECLOSED_de);
        germanTextForEventState.put(WAITINGOPCompletion,WAITINGOPCompletion_de);
        germanTextForEventState.put(ACKED,ACKED_de);
        germanTextForEventState.put(WAITINGFORCOMMANDEXECUTION,WAITINGFORCOMMANDEXECUTION_de);
        germanTextForEventState.put(CLOSED,CLOSED_de);
    }

    public HashMap<String, String> getGermanTextForEventState() {
        return germanTextForEventState;
    }
}
