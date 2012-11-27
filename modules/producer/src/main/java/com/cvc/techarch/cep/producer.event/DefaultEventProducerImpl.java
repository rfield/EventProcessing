package com.cvc.techarch.cep.producer.event;

/**
 * Created by IntelliJ IDEA.
 * User: RFIELD
 * Date: 11/16/11
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultEventProducerImpl implements EventProducer {

    public synchronized Event getNext() {
        return new Event(
                "Event-" + eventNumber,
                "This is event number [" + eventNumber++ + "]");
    }

    static int eventNumber = 0;
}
