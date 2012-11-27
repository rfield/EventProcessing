package com.cvc.techarch.cep.producer.event;

/**
 * Created by IntelliJ IDEA.
 * User: RFIELD
 * Date: 11/16/11
 * Time: 2:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventProducerFactory {
    private static EventProducerFactory ourInstance = new EventProducerFactory();

    public static EventProducerFactory getInstance() {
        return ourInstance;
    }

    private EventProducerFactory() {
    }

    public EventProducer getEventProducer(EventType eventType) throws Exception {
        switch (eventType) {
            case STRING:
                return new DefaultEventProducerImpl();
            default:
                throw new Exception("This event type is not currently implemented.");
        }
    }
}
