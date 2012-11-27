package com.cvc.techarch.cep.producer.event;

/**
 * Created by IntelliJ IDEA.
 * User: RFIELD
 * Date: 11/16/11
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Event {

    public Event() {
    }

    public Event(String eventID, String payload) {
        this.eventID = eventID;
        this.payload = payload;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventID=" + eventID +
                ", payload='" + payload + '\'' +
                '}';
    }

    String eventID;
    String payload;
}
