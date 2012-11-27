package com.cvc.techarch.cep.producer.event;

/**
 * Created by IntelliJ IDEA.
 * User: RFIELD
 * Date: 11/16/11
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
public enum EventType {
    STRING (0),
    XML (1),
    OBJECT (2);

    EventType(int val) {
        this.value = val;
    }

    public static EventType getEventType(int val) {
        switch (val) {
            case 2:
                return OBJECT;
            case 1:
                return XML;
            default:
            case 0:
                return STRING;
        }
    }

    private final int value;
}
